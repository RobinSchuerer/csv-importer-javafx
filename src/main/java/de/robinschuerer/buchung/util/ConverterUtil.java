package de.robinschuerer.buchung.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import javax.annotation.Nonnull;

public class ConverterUtil {

    private ConverterUtil() {

    }

    @Nonnull
    public static BigDecimal currencyToBigDecimal(final String currency){

        try {
        final Double doubleValue = NumberFormat.getNumberInstance(Locale.GERMANY).parse(currency)
            .doubleValue();
            return new BigDecimal(doubleValue.toString());
        } catch (ParseException e) {
            return BigDecimal.ZERO;
        }
    }

    @Nonnull
    public static String asCurrency(final BigDecimal value) {
        return NumberFormat.getCurrencyInstance(Locale.GERMANY).format(value.doubleValue());
    }
}
