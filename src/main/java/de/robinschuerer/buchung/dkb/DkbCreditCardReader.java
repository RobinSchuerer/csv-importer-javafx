package de.robinschuerer.buchung.dkb;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import de.robinschuerer.buchung.AbstractAccountExportReader;
import de.robinschuerer.buchung.AccountExportReader;
import de.robinschuerer.buchung.ui.AccountMovementDto;
import de.robinschuerer.buchung.util.ConverterUtil;

@Service
public class DkbCreditCardReader extends AbstractAccountExportReader
    implements AccountExportReader {

    @Override
    public List<AccountMovementDto> importData(final File file) {
        return loadObjectList(DkbCreditCardExportEntry.class, file)
            .stream()
            .map(dkbCreditCardExportEntry -> AccountMovementDto
                .newBuilder()
                .withValue(ConverterUtil.currencyToBigDecimal(dkbCreditCardExportEntry.getBetrag()))
                .withMovementDate(dkbCreditCardExportEntry.getWertstellung())
                .withDescription(dkbCreditCardExportEntry.getBeschreibung())
                .build())
            .collect(Collectors.toList());
    }

    @Override
    public String getType() {
        return "DKB Kreditkarte";
    }
}
