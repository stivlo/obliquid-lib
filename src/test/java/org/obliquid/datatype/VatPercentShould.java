package org.obliquid.datatype;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Test;

public class VatPercentShould {

    public static VatPercent percent = new VatPercent();

    @Test
    public void invalidNumberIsNotValid() {
        assertFalse(percent.isValid("xxa"));
    }

    @Test
    public void greaterThanHundredIsNotValid() {
        assertFalse(percent.isValid("102"));
    }

    @Test
    public void hundredIsNotValid() {
        assertFalse(percent.isValid("100"));
    }

    @Test
    public void zeroIsValid() {
        assertTrue(percent.isValid("0"));
    }

    @Test
    public void twentyIsValid() {
        assertTrue(percent.isValid("20"));
    }

    @Test
    public void computeVatPercentInsideItalyIsTwenty() {
        assertEquals(20, VatPercent.computeVatPercent("IT", "IT"));
    }

    @Test
    public void computeVatPercentFromItalyToAbroadIsZero() {
        assertEquals(0, VatPercent.computeVatPercent("IT", "UK"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void computeVatPercentFromEsDoesntWork() {
        assertEquals(0, VatPercent.computeVatPercent("ES", "IT"));
    }

    @Test
    public void beNotValidForNull() {
        assertFalse(percent.isValid(null));
    }

    @Test(expected = IllegalStateException.class)
    public void throwIllegalStateExceptionForGetFormattedStringOnNewObject() {
        VatPercent vatP = new VatPercent();
        vatP.getFormattedString(Locale.US);
    }

    @Test(expected = IllegalStateException.class)
    public void throwIllegalStateExceptionForGetRawStringOnNewObject() {
        VatPercent vatP = new VatPercent();
        vatP.getRawString();
    }

}
