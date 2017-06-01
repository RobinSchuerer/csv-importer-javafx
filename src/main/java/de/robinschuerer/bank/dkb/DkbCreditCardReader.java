package de.robinschuerer.bank.dkb;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import de.robinschuerer.bank.AbstractAccountExportReader;
import de.robinschuerer.bank.AccountExportReader;
import de.robinschuerer.bank.dto.AccountMovementDto;
import de.robinschuerer.bank.util.ConverterUtil;

@Service
public class DkbCreditCardReader extends AbstractAccountExportReader
    implements AccountExportReader {

    @Override
    public List<AccountMovementDto> importData(
        final File file,
        final UUID ticket,
        final ConcurrentHashMap<UUID, Integer> status) {
        final AtomicLong counter = new AtomicLong(0);

        final List<DkbCreditCardExportEntry> entries = loadObjectList(DkbCreditCardExportEntry.class, file);
        return entries
            .stream()
            .map(dkbCreditCardExportEntry -> {
                status.put(ticket, getProgress(counter, entries));

                return AccountMovementDto
                    .newBuilder()
                    .withValue(ConverterUtil.currencyToBigDecimal(dkbCreditCardExportEntry.getBetrag()))
                    .withMovementDate(dkbCreditCardExportEntry.getWertstellung())
                    .withDescription(dkbCreditCardExportEntry.getBeschreibung())
                    .build();
            }).peek(accountMovementDto -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            })
            .collect(Collectors.toList());
    }

    private int getProgress(final AtomicLong counter, final List<DkbCreditCardExportEntry> entries) {
        final long count = counter.incrementAndGet();

        return Math.round((float) count / entries.size() * 100);
    }

    @Override
    public String getType() {
        return "DKB Kreditkarte";
    }
}
