package de.robinschuerer.bank.controller;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;

import de.robinschuerer.bank.ImportService;
import de.robinschuerer.bank.dto.AccountMovementDto;

@RestController
@RequestMapping("/api/csv")
public class CsvUploadController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvUploadController.class);

    @Autowired
    private ImportService importService;

    @Autowired
    private TaskExecutor taskExecutor;

    private ConcurrentHashMap<UUID, Integer> status = new ConcurrentHashMap<>();

    private ConcurrentHashMap<UUID, List<AccountMovementDto>> resultData = new
        ConcurrentHashMap<>();

    @PostMapping
    public String handleFileUpload(@RequestParam("file") MultipartFile file) throws
        IllegalStateException, IOException {

        final UUID ticket = UUID.randomUUID();
        taskExecutor.execute(() -> {
            final List<AccountMovementDto> result;
            try {
                final File tmpFile = multipartToFile(file, ticket);
                try {
                    result = importService.importData(
                        ticket,
                        status,
                        tmpFile,
                        "DKB Kreditkarte");

                    LOGGER.info("putting result {} for {}", result, ticket);
                } finally {
                    FileUtils.deleteQuietly(tmpFile);
                }
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }

            status.put(ticket, -1);
            resultData.put(ticket, result);
        });

        return ticket.toString();
    }

    @GetMapping("/status/{ticket}")
    public Integer status(@PathVariable("ticket") UUID ticket) {

        final Integer progress = status.get(ticket);
        if (progress == -1) {
            return 100;
        }
        return progress;
    }

    @GetMapping(value = "/result/{ticket}", produces = "application/json")
    public List<AccountMovementDto> result(@PathVariable("ticket") UUID ticket) {
        if (status.get(ticket) == null) {
            LOGGER.warn("No upload available for {}", ticket);

            return Lists.newArrayList();
        }

        if (!isReady(ticket)) {
            waitUntilReady();
            return result(ticket);
        }

        final List<AccountMovementDto> data = resultData.get(ticket);

        if (data == null) {
            LOGGER.warn("No result for {}", ticket);

            return Lists.newArrayList();
        }

        return data;
    }

    private void waitUntilReady() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean isReady(final @PathVariable("ticket") UUID ticket) {
        return status.get(ticket) == -1;
    }

    public File multipartToFile(MultipartFile multipart, UUID ticket) throws IllegalStateException,
        IOException {

        File convFile = new File("/tmp/" + ticket + ".csv");
        multipart.transferTo(convFile);
        return convFile;
    }

}
