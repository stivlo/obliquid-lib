package org.obliquid.datatype;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.Test;
import org.obliquid.datatype.impl.PasswordImpl;

/**
 * Test class PasswordImpl.
 * 
 * @author stivlo
 * 
 */
public class PasswordImplShould {

        /**
         * a SHA-1 null password isn't valid.
         */
        @Test
        public final void nullSha1IsntValid() {
                Password p = new PasswordImpl();
                assertFalse(p.isValid(null));
        }

        /**
         * a clear text null password isn't valid.
         */
        @Test
        public final void nullClearTextIsntValid() {
                Password p = new PasswordImpl();
                assertFalse(p.isTheStringValid(null));
        }

        /**
         * an empty Sha1 password isn't valid.
         */
        @Test
        public final void anEmptySha1PasswordIsntValid() {
                Password p = new PasswordImpl();
                assertFalse(p.isValid(""));
        }

        /**
         * an empty clear text password isn't valid.
         */
        @Test
        public final void anEmptyClearTextPasswordIsntValid() {
                Password p = new PasswordImpl();
                assertFalse(p.isTheStringValid(""));
        }

        /**
         * A four letters Sha1 password isn't valid.
         */
        @Test
        public final void aFourLettersSha1PasswordIsntValid() {
                Password p = new PasswordImpl();
                assertFalse(p.isValid("abcd"));
        }

        /**
         * A four letters clear text password isn't valid.
         */
        @Test
        public final void aFourLettersClearTextPasswordIsntValid() {
                Password p = new PasswordImpl();
                assertFalse(p.isTheStringValid("abcd"));
        }

        /**
         * A clear text password ending with $ is not valid.
         */
        @Test
        public final void passwordEndingWithDollarIsntValid() {
                Password p = new PasswordImpl();
                assertFalse(p.isTheStringValid("abcdefgh$"));
        }

        /**
         * A clear text password starting with $ is not valid.
         */
        @Test
        public final void passwordStartingWithDollarIsntValid() {
                Password p = new PasswordImpl();
                assertFalse(p.isTheStringValid("$abcdefgh"));
        }

        /**
         * A clear text password with five bangs is valid.
         */
        @Test
        public final void fiveBangsAreValid() {
                Password p = new PasswordImpl();
                assertTrue(p.isTheStringValid("!!!!!"));
        }

        /**
         * A clear text password with five dots is valid.
         */
        @Test
        public final void fiveDotsAreValid() {
                Password p = new PasswordImpl();
                assertTrue(p.isTheStringValid("....."));
        }

        /**
         * A clear text password with five question marks is valid.
         */
        @Test
        public final void fiveQuestionMarksAreValid() {
                Password p = new PasswordImpl();
                assertTrue(p.isTheStringValid("?????"));
        }

        /**
         * A clear text password with five underscores is valid.
         */
        @Test
        public final void fiveUnderscoresAreValid() {
                Password p = new PasswordImpl();
                assertTrue(p.isTheStringValid("_____"));
        }

        /**
         * A clear text password with five hyphens is valid.
         */
        @Test
        public final void fiveHyphensAreValid() {
                Password p = new PasswordImpl();
                assertTrue(p.isTheStringValid("-----"));
        }

        /**
         * A clear text password with five semicolons is valid.
         */
        @Test
        public final void fiveSemicolonsAreValid() {
                Password p = new PasswordImpl();
                assertTrue(p.isTheStringValid(";;;;;"));
        }

        /**
         * A 40 letters Sha1 password is valid.
         */
        @Test
        public final void aFortyLettersSha1PasswordIsValid() {
                String sha1Password = "abcdefghijklmnopqrstuvwxyzabcdefacdergab";
                Password p = new PasswordImpl();
                assertTrue(p.isValid(sha1Password));
        }

        /**
         * A 8 letters Sha1 password isn't valid.
         */
        @Test
        public final void aEightLettersSha1PasswordIsntValid() {
                String sha1Password = "abcdefgh";
                Password p = new PasswordImpl();
                assertFalse(p.isValid(sha1Password));
        }

        /**
         * setting a null clear text password throws Exception.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void settingANullClearTextPasswordThrowsException() {
                Password p = new PasswordImpl();
                p.setDataFromString(null);
        }

        /**
         * setting a null clear Sha1 password throws Exception.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void settingANullSha1PasswordThrowsException() {
                Password p = new PasswordImpl();
                p.setData(null);
        }

        /**
         * setting an empty clear text password throws Exception.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void settingAnEmptyClearTextPasswordThrowsException() {
                Password p = new PasswordImpl();
                p.setDataFromString(null);
        }

        /**
         * setting an empty clear Sha1 password throws Exception.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void settingAnEmptySha1PasswordThrowsException() {
                Password p = new PasswordImpl();
                p.setData(null);
        }

        /**
         * Setting a four characters clear text password throws Exception.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void settingAFourCharsClearTextPasswordThrowsException() {
                Password p = new PasswordImpl();
                p.setDataFromString("abcd");
        }

        /**
         * Setting a four character Sha1 password throws Exception.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void settingAFourCharsSha1PasswordThrowsException() {
                Password p = new PasswordImpl();
                p.setData("abcd");
        }

        /**
         * Setting and retrieving a Sha1 password.
         */
        @Test
        public final void settingAndRetrievingASha1Password() {
                final String sha1Password = "0123456789012345678901234567890123456789";
                Password p = new PasswordImpl();
                p.setData(sha1Password);
                assertEquals(sha1Password, p.getData());
                assertEquals("", p.formatData(Locale.getDefault()));
        }

        /**
         * Setting a clear text password and retrieving a Sha1 password.
         */
        @Test
        public final void settingAndRetrievingAClearTextPassword() {
                Password p = new PasswordImpl();
                p.setDataFromString("abcdefg");
                assertEquals("2fb5e13419fc89246865e7a324f476ec624e8740", p.getData());
                assertEquals("", p.formatData(Locale.getDefault()));
        }

}
