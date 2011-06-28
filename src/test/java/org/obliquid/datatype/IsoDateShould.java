package org.obliquid.datatype;

import static org.junit.Assert.*;
import org.junit.Test;

public class IsoDateShould {

    @Test
    public void isValidTest() {
        IsoDate isoDate = new IsoDate();
        assertFalse(isoDate.isValid(""));
        assertTrue(isoDate.isValid("2011-12-05"));
    }

    @Test
    public void getFormattedStringTest() {
        IsoDate isoDate = new IsoDate();
        isoDate.set("2011-02-22");
        assertEquals("February 22, 2011", isoDate.getFormattedString());
        assertEquals("2011-02-22", isoDate.toString());
    }

    @Test
    public void daysInTheYearTest() {
        IsoDate isoDate = new IsoDate();
        isoDate.set("2011-02-22");
        assertEquals(365, isoDate.daysInTheYear());
        isoDate.set("2008-02-22");
        assertEquals(366, isoDate.daysInTheYear());
    }

    @Test
    public void beNotValidForNull() {
        IsoDate isoDate = new IsoDate();
        assertFalse(isoDate.isValid(null));
    }

    @Test
    public void computeDaysUntil() {
        IsoDate isoDate = new IsoDate();
        isoDate.set("2010-11-01");
        assertEquals(5, isoDate.daysUntil("2010-11-06"));
    }

}
