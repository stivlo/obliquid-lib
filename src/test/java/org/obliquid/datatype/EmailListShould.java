package org.obliquid.datatype;

import static org.junit.Assert.*;

import org.junit.Test;

public class EmailListShould {

    @Test
    public void testIsValid() {
        EmailList list = new EmailList();
        assertFalse(list.isValid(""));
        assertTrue(list.isValid("stivlo@example.com"));
        assertTrue(list.isValid("stivlo@example.com, anotherone@example.com"));
        assertFalse(list.isValid("stivlo@example.com, errata"));
    }

}
