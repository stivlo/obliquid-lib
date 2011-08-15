package org.obliquid.datatype;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Test;
import org.obliquid.datatype.impl.VatPercentImpl;

/**
 * Check class VatPercentImpl.
 * 
 * @author stivlo
 * 
 */
public class VatPercentImplShould {

        /**
         * Check that the invalid number "xxa" doesn't validate.
         */
        @Test
        public final void invalidNumberIsNotValid() {
                VatPercent percent = new VatPercentImpl();
                assertFalse(percent.isTheStringValid("xxa"));
        }

        /**
         * Check that "102" doesn't validate.
         */
        @Test
        public final void stringGreaterThanHundredIsntValid() {
                VatPercent percent = new VatPercentImpl();
                assertFalse(percent.isTheStringValid("102"));
        }

        /**
         * Check that 102 doesn't validate.
         */
        @Test
        public final void integerGreaterThanHundredIsntValid() {
                VatPercent percent = new VatPercentImpl();
                final int greaterThan100 = 102;
                assertFalse(percent.isValid(greaterThan100));
        }

        /**
         * Check that "100" doesn't validate.
         */
        @Test
        public final void hundredIsNotValid() {
                VatPercent percent = new VatPercentImpl();
                assertFalse(percent.isTheStringValid("100"));
        }

        /**
         * Check that "0" does validate.
         */
        @Test
        public final void zeroIsValid() {
                VatPercent percent = new VatPercentImpl();
                assertTrue(percent.isTheStringValid("0"));
        }

        /**
         * Check that 0 does validate.
         */
        @Test
        public final void integerZeroIsValid() {
                VatPercent percent = new VatPercentImpl();
                assertTrue(percent.isValid(0));
        }

        /**
         * Check that "20" validates.
         */
        @Test
        public final void twentyIsValid() {
                VatPercent percent = new VatPercentImpl();
                assertTrue(percent.isTheStringValid("20"));
        }

        /**
         * Check that 20 validates.
         */
        @Test
        public final void integerTwentyIsValid() {
                VatPercent percent = new VatPercentImpl();
                final int twenty = 20; //no special meaning at all, just testing
                assertTrue(percent.isValid(twenty));
        }

        /**
         * Check VAT percent from Italy to Italy.
         */
        //        @Test
        //        public final void computeVatPercentInsideItalyIsTwenty() {
        //                final int expectedPercent = 20;
        //                assertEquals(expectedPercent, VatPercentImpl.computeVatPercent("IT", "IT"));
        //        }

        /**
         * Check VAT percent from Italy to UK.
         */
        //        @Test
        //        public final void computeVatPercentFromItalyToAbroadIsZero() {
        //                assertEquals(0, VatPercentImpl.computeVatPercent("IT", "UK"));
        //        }

        /**
         * Check VAT percent from Spain to Italy.
         */
        //        @Test(expected = UnsupportedOperationException.class)
        //        public final void computeVatPercentFromEsDoesntWork() {
        //                assertEquals(0, VatPercentImpl.computeVatPercent("ES", "IT"));
        //        }

        /**
         * Check that String null is not valid.
         */
        @Test
        public final void beNotValidForStringNull() {
                VatPercent percent = new VatPercentImpl();
                assertFalse(percent.isTheStringValid(null));
        }

        /**
         * Check that Integer null is not valid.
         */
        @Test
        public final void beNotValidForIntegerNull() {
                VatPercent percent = new VatPercentImpl();
                assertFalse(percent.isValid(null));
        }

        /**
         * Check that asking a formatted string without setting a value, throws
         * exception.
         */
        @Test(expected = IllegalStateException.class)
        public final void throwIllegalStateExceptionForGetFormattedStringOnNewObject() {
                VatPercent vatP = new VatPercentImpl();
                vatP.formatData(Locale.US);
        }

        /**
         * Check that asking raw data without setting a value, throws exception.
         */
        @Test(expected = IllegalStateException.class)
        public final void throwIllegalStateExceptionForGetRawStringOnNewObject() {
                VatPercent vatP = new VatPercentImpl();
                vatP.getData();
        }

        /**
         * Set Integer 0 and retrieves it.
         */
        @Test
        public final void setIntegerZeroAndRetrievesIt() {
                VatPercent percent = new VatPercentImpl();
                percent.setData(0);
                assertEquals(0, 0 + percent.getData());
        }

        /**
         * Set String 0 and retrieves it.
         */
        @Test
        public final void setStringZeroAndRetrievesIt() {
                VatPercent percent = new VatPercentImpl();
                percent.setDataFromString("0");
                assertEquals("0%", percent.formatData(Locale.getDefault()));
                assertEquals(0, 0 + percent.getData());
        }

        /**
         * Set Integer 24 and retrieves it.
         */
        @Test
        public final void setInteger24AndRetrievesIt() {
                final int twentyFour = 24;
                VatPercent percent = new VatPercentImpl();
                percent.setData(twentyFour);
                assertEquals(twentyFour + "%", percent.formatData(Locale.getDefault()));
                assertEquals(twentyFour, 0 + percent.getData());
        }

        /**
         * Set String 24 and retrieves it.
         */
        @Test
        public final void setString24AndRetrievesIt() {
                final int twentyFour = 24;
                VatPercent percent = new VatPercentImpl();
                percent.setDataFromString("" + twentyFour);
                assertEquals(twentyFour + "%", percent.formatData(Locale.getDefault()));
                assertEquals(twentyFour, 0 + percent.getData());
        }

        /**
         * Setting Integer -1 throws exception.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void setIntegerMinusOneThrowsException() {
                VatPercent percent = new VatPercentImpl();
                percent.setData(-1);
        }

        /**
         * Setting String "-1" throws exception.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void setStringMinusOneThrowsException() {
                VatPercent percent = new VatPercentImpl();
                percent.setDataFromString("-1");
        }

        /**
         * Setting Integer null throws exception.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void setIntegerNullThrowsException() {
                VatPercent percent = new VatPercentImpl();
                percent.setData(null);
        }

        /**
         * Setting String null throws exception.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void setStringNullThrowsException() {
                VatPercent percent = new VatPercentImpl();
                percent.setDataFromString(null);
        }

        /**
         * Setting String "xaa" throws exception.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void setStringXaaThrowsException() {
                VatPercent percent = new VatPercentImpl();
                percent.setDataFromString("xaa");
        }

        /**
         * Setting Integer 100 throws exception.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void setInteger100ThrowsException() {
                final int hundred = 100;
                VatPercent percent = new VatPercentImpl();
                percent.setData(hundred);
        }

        /**
         * Setting String "100" throws exception.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void setString100ThrowsException() {
                VatPercent percent = new VatPercentImpl();
                percent.setDataFromString("100");
        }

}
