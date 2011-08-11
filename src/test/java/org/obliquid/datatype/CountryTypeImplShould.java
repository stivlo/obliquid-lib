package org.obliquid.datatype;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Test;
import org.obliquid.datatype.impl.CountryTypeImpl;

/**
 * Test the class country code.
 * 
 * @author stivlo
 * 
 */
public class CountryTypeImplShould {

        /**
         * The country code should be valid for "IT".
         */
        @Test
        public final void beValidForIT() {
                CountryType code = new CountryTypeImpl();
                assertTrue(code.isValid("IT"));
        }

        /**
         * isTheStringValid() is actually the same as isValid().
         */
        @Test
        public final void beValidForITString() {
                CountryType code = new CountryTypeImpl();
                assertTrue(code.isTheStringValid("IT"));
        }

        /**
         * The country code should not be valid for null.
         */
        @Test
        public final void beNotValidForNull() {
                CountryType code = new CountryTypeImpl();
                assertFalse(code.isValid(null));
        }

        /**
         * The country code should not be valid for "XX".
         */
        @Test
        public final void beNotValidForXX() {
                CountryTypeImpl code = new CountryTypeImpl();
                assertFalse(code.isValid("XX"));
        }

        /**
         * Set "IT" should work and return "IT" for getData() and "Italy" for
         * formatData().
         */
        @Test
        public final void setGetAndFormatDataIT() {
                CountryType code = new CountryTypeImpl();
                code.setData("IT");
                assertEquals("IT", code.getData());
                assertEquals("Italy", code.formatData(Locale.UK));
                assertEquals("Italia", code.formatData(Locale.ITALY));
                assertEquals("Italy", code.formatData(Locale.KOREA)); //English is the default
                String subString = "Ital"; //Language independent
                assertEquals(subString, code.toString().substring(0, subString.length()));
        }

        /**
         * Set "UK" should work and return "UK" for getData() and
         * "United Kingdom" for formatData().
         */
        @Test
        public final void setGetAndFormatDataUK() {
                CountryType code = new CountryTypeImpl();
                code.setData("UK");
                assertEquals("UK", code.getData());
                assertEquals("United Kingdom", code.formatData(Locale.UK));
                assertEquals("Regno Unito", code.formatData(Locale.ITALY));
                assertEquals("United Kingdom", code.formatData(Locale.KOREA)); //English is the default
        }

        /**
         * setDataFromString() is the same as setData().
         */
        @Test
        public final void setDataFromString() {
                CountryType code = new CountryTypeImpl();
                code.setDataFromString("UK");
                assertEquals("UK", code.getData());
        }

        /**
         * Setting "XX" is not a valid Country and should throw Exception.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void throwExceptionWhensettingXX() {
                CountryType code = new CountryTypeImpl();
                code.setData("XX");
        }

        /**
         * Setting null is not allowed and should throw Exception.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void throwExceptionWhensettingNull() {
                CountryType code = new CountryTypeImpl();
                code.setData(null);
        }

}
