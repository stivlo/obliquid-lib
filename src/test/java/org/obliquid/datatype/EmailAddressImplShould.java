package org.obliquid.datatype;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.Test;
import org.obliquid.datatype.impl.EmailAddressImpl;

/**
 * Check the class EmailAddress.
 * 
 * @author stivlo
 * 
 */
public class EmailAddressImplShould {

        /**
         * Should accept a valid email and be able to retrieve it.
         */
        @Test
        public final void acceptValidEmail() {
                String emailString = "stivlo@example.com";
                EmailAddressImpl email = new EmailAddressImpl();
                email.setData(emailString);
                assertEquals(emailString, email.getData());
                assertEquals(emailString, email.toString());
                assertEquals(emailString, email.formatData(Locale.UK));
        }

        /**
         * Should throw Exception when setting a wrong email.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void throwExceptionWhenSettingWrongEmail() {
                String emailString = "stivlo@example.c";
                EmailAddressImpl email = new EmailAddressImpl();
                email.setData(emailString);
        }

        /**
         * Should throw Exception when setting a null email.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void throwExceptionWhenSettingNullEmail() {
                String emailString = null;
                EmailAddressImpl email = new EmailAddressImpl();
                email.setData(emailString);
        }

        /**
         * Should not accept null, but should not crash.
         */
        @Test
        public final void notValidForNull() {
                EmailAddressImpl email = new EmailAddressImpl();
                assertFalse(null, email.isValid(null));
        }

        /**
         * A wrong email isn't valid.
         */
        @Test
        public final void aWrongEmailIsntValid() {
                String emailString = "stivlo@example.c";
                EmailAddressImpl email = new EmailAddressImpl();
                assertFalse(email.isValid(emailString));
        }

        /**
         * isTheStringValid() is an alias for isValid().
         */
        @Test
        public final void isTheStringValid() {
                String emailString = "stivlo@.com";
                EmailAddressImpl email = new EmailAddressImpl();
                assertFalse(email.isTheStringValid(emailString));
        }

        /**
         * A correct email is valid.
         */
        @Test
        public final void aCorrectEmailIsValid() {
                String emailString = "stivlo@example.com";
                EmailAddressImpl email = new EmailAddressImpl();
                assertTrue(email.isValid(emailString));
        }

        /**
         * An email address with leading and trailing space is valid.
         */
        @Test
        public final void anAddressWithLeadingAndTrailingSpaceIsValid() {
                String emailString = "   stivlo@example.com   ";
                EmailAddressImpl email = new EmailAddressImpl();
                assertTrue(email.isValid(emailString));
        }

        /**
         * An email address with leading and trailing space will be compacted.
         */
        @Test
        public final void anAddressWithLeadingAndTrailingSpaceWillBeCompacted() {
                String emailString = "   stivlo@example.com   ";
                EmailAddressImpl email = new EmailAddressImpl();
                email.setData(emailString);
                assertEquals(emailString.trim(), email.getData());
        }

        /**
         * An empty email is valid.
         */
        @Test
        public final void anEmptyEmailIsValid() {
                EmailAddressImpl email = new EmailAddressImpl();
                email.setData("");
                assertTrue(email.isValid(""));
                email.setData("  ");
                assertTrue(email.isValid("  "));
        }

        /**
         * An empty email gets trimmed.
         */
        @Test
        public final void anEmptyEmailGetsTrimmed() {
                EmailAddressImpl email = new EmailAddressImpl();
                email.setData("");
                assertEquals("", email.getData());
                email.setData("  ");
                assertEquals("", email.getData());
        }

        /**
         * setDataFromString() is an alias of setData().
         */
        @Test
        public final void setDataFromString() {
                EmailAddressImpl email = new EmailAddressImpl();
                email.setDataFromString("");
                assertEquals("", email.getData());
        }

}
