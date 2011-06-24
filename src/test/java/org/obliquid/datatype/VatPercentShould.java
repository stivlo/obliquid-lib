package org.obliquid.datatype;

import static org.junit.Assert.*;

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
    public void ZeroIsValid() {
        assertTrue(percent.isValid("0"));
    }

    @Test
    public void TwentyIsValid() {
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

}
