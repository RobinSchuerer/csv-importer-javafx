package de.robinschuerer.bank;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;

import de.robinschuerer.bank.domain.AccountMovement;
import de.robinschuerer.bank.dto.AccountMovementDto;

@Service
public class UploadService {

    private static Logger LOGGER = LoggerFactory.getLogger(UploadService.class);

    @Autowired
    private RestTemplate restTemplate;

    public void upload(final AccountMovementDto accountMovementDto) {
        if (accountMovementDto.isDisabled()) {
            LOGGER.info("Skipping", accountMovementDto);

            return;
        }

        final AccountMovement accountMovement = AccountMovement
            .newBuilder()
            .withTags(Lists.newArrayList(accountMovementDto.getTag()))
            .withPositionDate(transformDate(accountMovementDto.getBuchungsDatum()))
            .withAccountId(10008)
            .withAmount(accountMovementDto.getValueAsNumber())
            .build();

        restCall(accountMovement);

    }

    private String transformDate(final LocalDate exportEntry) {
        return DateTimeFormatter.ISO_DATE.format(exportEntry);
    }

    private void restCall(final AccountMovement accountMovement) {
        try {
            final ResponseEntity<ObjectNode> response = restTemplate.postForEntity("http://accounting.xnoname.com/api/accountmovement",
                accountMovement, ObjectNode.class);

            if(response.getStatusCode() == HttpStatus.OK){
                LOGGER.info("Uploaded {}", accountMovement);

                return;
            }


            LOGGER.info("Upload fehlgeschlagen: {}", response.toString());
        } catch (Exception exception) {
            LOGGER.error("Upload fehlgeschlagen", exception);
        }
    }
}
