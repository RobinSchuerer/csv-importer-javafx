package de.robinschuerer.bank.diba;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import de.robinschuerer.bank.AbstractAccountExportReader;
import de.robinschuerer.bank.AccountExportReader;
import de.robinschuerer.bank.dto.AccountMovementDto;
import de.robinschuerer.bank.util.ConverterUtil;

@Service
public class DibaAccountExportReader extends AbstractAccountExportReader
    implements AccountExportReader {

    @Override
    public List<AccountMovementDto> importData(final File file, final UUID ticket, final ConcurrentHashMap<UUID, Integer> status) {
        final List<DibaExportEntry> exportEntries = loadObjectList(DibaExportEntry.class, file);

        return exportEntries
            .stream()
            .map((imported) -> AccountMovementDto
                .newBuilder()
                .withMovementDate(imported.getBuchung())
                .withDescription(imported.getBuchungsBeschreibung())
                .withValue(ConverterUtil.currencyToBigDecimal(imported.getBetrag()))
                .build())
            .collect(Collectors.toList());
    }

    @Override
    public String getType() {
        return "DiBa Konto";
    }

}
