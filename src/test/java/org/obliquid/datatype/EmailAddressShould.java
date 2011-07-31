package org.obliquid.datatype;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Test;

public class EmailAddressShould {

    @Test
    public void test1() {
        String emailString = "stivlo@example.com";
        EmailAddress email = new EmailAddress();
        email.set(emailString);
        assertEquals(emailString, email.getRawString());
        assertEquals(emailString, email.toString());
        email = new EmailAddress(emailString);
        assertEquals(emailString, email.getFormattedString(Locale.US));
    }

    @Test
    public void notValidForNull() {
        EmailAddress email = new EmailAddress();
        assertFalse(null, email.isValid(null));
    }

}
