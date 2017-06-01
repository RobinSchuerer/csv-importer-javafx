package de.robinschuerer.bank.dkb;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import de.robinschuerer.bank.AccountExportReader;
import de.robinschuerer.bank.AbstractAccountExportReader;
import de.robinschuerer.bank.dto.AccountMovementDto;
import de.robinschuerer.bank.util.ConverterUtil;

@Service
public class DkbAccountReader extends AbstractAccountExportReader implements AccountExportReader {

    @Override
    public List<AccountMovementDto> importData(final File file, final UUID ticket, final ConcurrentHashMap<UUID, Integer> status) {
        final List<DkbAccountExportEntry> exportEntries = loadObjectList(DkbAccountExportEntry.class, file);

        return exportEntries
            .stream()
            .map((imported) -> AccountMovementDto
                .newBuilder()
                .withMovementDate(imported.getBuchungstag())
                .withDescription(imported.getBuchungsBeschreibung())
                .withValue(ConverterUtil.currencyToBigDecimal(imported.getBetrag()))
                .build())
            .collect(Collectors.toList());
    }

    @Override
    public String getType() {
        return "DKB Konto";
    }

}
