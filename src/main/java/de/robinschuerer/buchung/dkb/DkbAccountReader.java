package de.robinschuerer.buchung.dkb;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import de.robinschuerer.buchung.AccountExportReader;
import de.robinschuerer.buchung.AbstractAccountExportReader;
import de.robinschuerer.buchung.ui.AccountMovementDto;
import de.robinschuerer.buchung.util.ConverterUtil;

@Service
public class DkbAccountReader extends AbstractAccountExportReader implements AccountExportReader {

    @Override
    public List<AccountMovementDto> importData(final File file) {
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
