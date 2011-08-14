package org.obliquid.datatype;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.Test;
import org.obliquid.datatype.impl.LanguageTypeImpl;

/**
 * Class under test: LanguageTypeImpl.
 * 
 * @author stivlo
 * 
 */
public class LanguageTypeImplShould {

        /**
         * The language "it" is valid.
         */
        @Test
        public final void itIsValid() {
                LanguageType language = new LanguageTypeImpl();
                assertTrue(language.isValid("it"));
                assertTrue(language.isTheStringValid("it"));
        }

        /**
         * The language "en" is valid.
         */
        @Test
        public final void enIsValid() {
                LanguageType language = new LanguageTypeImpl();
                assertTrue(language.isValid("en"));
                assertTrue(language.isTheStringValid("en"));
        }

        /**
         * The language "xp" isn't valid.
         */
        @Test
        public final void xpIsntValid() {
                LanguageType language = new LanguageTypeImpl();
                assertFalse(language.isValid("xp"));
                assertFalse(language.isTheStringValid("xp"));
        }

        /**
         * null is not valid.
         */
        @Test
        public final void nullIsntValid() {
                LanguageType language = new LanguageTypeImpl();
                assertFalse(language.isValid(null));
                assertFalse(language.isTheStringValid(null));
        }

        /**
         * Setting null throws Exception.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void settingNullThrowsException() {
                LanguageType language = new LanguageTypeImpl();
                language.setData(null);
        }

        /**
         * setFromString(null) throws Exception.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void setFromStringNullThrowsException() {
                LanguageType language = new LanguageTypeImpl();
                language.setDataFromString(null);
        }

        /**
         * Setting 'it' retrieves 'it' or 'Italiano'.
         */
        @Test
        public final void settingItRetrievesItOrItaliano() {
                LanguageType language = new LanguageTypeImpl();
                language.setData("it");
                assertEquals("it", language.getData());
                assertEquals("Italiano", language.formatData(Locale.getDefault()));
        }

        /**
         * Setting with setFromString() 'en' and retrieving 'en' or 'English'.
         */
        @Test
        public final void setFromStringEnRetrievesEnOrEnglish() {
                LanguageType language = new LanguageTypeImpl();
                language.setDataFromString("en");
                assertEquals("en", language.getData());
                assertEquals("English", language.formatData(Locale.getDefault()));
        }

        /**
         * Setting 'xx' throws Exception.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void settingXxThrowsException() {
                LanguageType language = new LanguageTypeImpl();
                language.setData("xx");
        }

        /**
         * Getting without setting throws Exception.
         */
        @Test(expected = IllegalStateException.class)
        public final void gettingWithoutSettingThrowsException() {
                LanguageType language = new LanguageTypeImpl();
                language.getData();
        }

        /**
         * Formatting without setting throws Exception.
         */
        @Test(expected = IllegalStateException.class)
        public final void formattingWithoutSettingThrowsException() {
                LanguageType language = new LanguageTypeImpl();
                language.formatData(Locale.getDefault());
        }

}
