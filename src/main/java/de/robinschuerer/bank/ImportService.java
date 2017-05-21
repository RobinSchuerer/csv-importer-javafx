package de.robinschuerer.bank;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import de.robinschuerer.bank.dto.AccountMovementDto;

@Service
public class ImportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImportService.class);

    @Autowired(required = false)
    private Collection<AccountExportReader> accountExportReader;

    public List<AccountMovementDto> importData(final File file, final String type) {
        if(Strings.isNullOrEmpty(type)){
            LOGGER.info("Kein Import Typ gewählt!");
            return Lists.newArrayList();
        }
        final Optional<AccountExportReader> reader = accountExportReader
            .stream()
            .filter(each -> each.getType().equals(type))
            .findFirst();
        if(!reader.isPresent()){
            LOGGER.error("Kein Reader für {} gefunden!", type);
            return Lists.newArrayList();
        }

        return reader.get().importData(file, UUID.randomUUID(), new ConcurrentHashMap<>());
    }

    public List<AccountMovementDto> importData(
        final UUID ticket,
        final ConcurrentHashMap<UUID, Integer> status,
        final File file,
        final String type) {

        if(Strings.isNullOrEmpty(type)){
            LOGGER.info("Kein Import Typ gewählt!");
            return Lists.newArrayList();
        }
        final Optional<AccountExportReader> reader = accountExportReader
            .stream()
            .filter(each -> each.getType().equals(type))
            .findFirst();
        if(!reader.isPresent()){
            LOGGER.error("Kein Reader für {} gefunden!", type);
            return Lists.newArrayList();
        }

        return reader.get().importData(file, ticket, status);

    }
}
