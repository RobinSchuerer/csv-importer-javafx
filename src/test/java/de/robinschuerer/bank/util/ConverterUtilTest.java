package de.robinschuerer.bank.util;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class ConverterUtilTest {

    @Test
    public void currencyWith1000erPoint() {
        assertEquals(new BigDecimal("23456.78"), ConverterUtil.currencyToBigDecimal("23.456,78"));
        assertEquals(new BigDecimal("456.78"), ConverterUtil.currencyToBigDecimal("456,78"));
        assertEquals(new BigDecimal("0.78"), ConverterUtil.currencyToBigDecimal("0,78"));
        ConverterUtil.currencyToBigDecimal("8.801,14");
    }
}
