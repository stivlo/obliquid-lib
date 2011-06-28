package org.obliquid.datatype;

import static org.junit.Assert.*;

import org.junit.Test;

public class ItalianPostCodeShould {

    @Test
    public void beValidForFiveDigits() {
        ItalianPostCode postCode = new ItalianPostCode();
        assertTrue(postCode.isValid("03429"));
    }

    @Test
    public void notValidForNull() {
        ItalianPostCode postCode = new ItalianPostCode();
        assertFalse(postCode.isValid(null));
    }

    @Test
    public void notValidForFourDigits() {
        ItalianPostCode postCode = new ItalianPostCode();
        assertFalse(postCode.isValid("0329"));
    }

    @Test
    public void notValidForFiveLetters() {
        ItalianPostCode postCode = new ItalianPostCode();
        assertFalse(postCode.isValid("AXGVZ"));
    }

}
