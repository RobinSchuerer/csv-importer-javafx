package de.robinschuerer.bank.controller;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import de.robinschuerer.bank.ImportService;
import de.robinschuerer.bank.dto.AccountMovementDto;

@RestController
@RequestMapping("/api/csv")
public class CsvUploadController {

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
                result = importService.importData(
                    ticket,
                    status,
                    multipartToFile(file),
                    "DKB Kreditkarte");
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }

            resultData.put(ticket, result);
        });

        return ticket.toString();
    }

    @GetMapping("/status/{ticket}")
    public Integer status(@PathVariable("ticket") UUID ticket) {

        return status.get(ticket);
    }

    @GetMapping("/result/{ticket}")
    public List<AccountMovementDto> result(@PathVariable("ticket") UUID ticket) {

        return resultData.get(ticket);
    }

    public File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        File convFile = new File("/tmp/"+multipart.getOriginalFilename());
        multipart.transferTo(convFile);
        return convFile;
    }

}
