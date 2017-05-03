package de.robinschuerer.buchung.diba;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import de.robinschuerer.buchung.AbstractAccountExportReader;
import de.robinschuerer.buchung.AccountExportReader;
import de.robinschuerer.buchung.ui.AccountMovementDto;
import de.robinschuerer.buchung.util.ConverterUtil;

@Service
public class DibaAccountExportReader extends AbstractAccountExportReader
    implements AccountExportReader {

    @Override
    public List<AccountMovementDto> importData(final File file) {
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
