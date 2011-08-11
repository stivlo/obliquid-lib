package org.obliquid.datatype;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Test;

/**
 * Check class VatPercent.
 * 
 * @author stivlo
 * 
 */
public class VatPercentShould {

        /**
         * a VatPercent instance that will be created for each test.
         */
        private VatPercent percent = new VatPercent();

        /**
         * Check that the invalid number "xxa" doesn't validate.
         */
        @Test
        public final void invalidNumberIsNotValid() {
                assertFalse(percent.isValid("xxa"));
        }

        /**
         * Check that "102" doesn't validate.
         */
        @Test
        public final void greaterThanHundredIsNotValid() {
                assertFalse(percent.isValid("102"));
        }

        /**
         * Check that "100" doesn't validate.
         */
        @Test
        public final void hundredIsNotValid() {
                assertFalse(percent.isValid("100"));
        }

        /**
         * Check that "0" does validate.
         */
        @Test
        public final void zeroIsValid() {
                assertTrue(percent.isValid("0"));
        }

        /**
         * Check that "20" validates.
         */
        @Test
        public final void twentyIsValid() {
                assertTrue(percent.isValid("20"));
        }

        /**
         * Check VAT percent from Italy to Italy.
         */
        @Test
        public final void computeVatPercentInsideItalyIsTwenty() {
                final int expectedPercent = 20;
                assertEquals(expectedPercent,
                                VatPercent.computeVatPercent("IT", "IT"));
        }

        /**
         * Check VAT percent from Italy to UK.
         */
        @Test
        public final void computeVatPercentFromItalyToAbroadIsZero() {
                assertEquals(0, VatPercent.computeVatPercent("IT", "UK"));
        }

        /**
         * Check VAT percent from Spain to Italy.
         */
        @Test(expected = UnsupportedOperationException.class)
        public final void computeVatPercentFromEsDoesntWork() {
                assertEquals(0, VatPercent.computeVatPercent("ES", "IT"));
        }

        /**
         * Check that null is not valid.
         */
        @Test
        public final void beNotValidForNull() {
                assertFalse(percent.isValid(null));
        }

        /**
         * Check that asking a formatted string without setting a value, throws
         * exception.
         */
        @Test(expected = IllegalStateException.class)
        public final void throwIllegalStateExceptionForGetFormattedStringOnNewObject() {
                VatPercent vatP = new VatPercent();
                vatP.getFormattedString(Locale.US);
        }

        /**
         * Check that asking raw data without setting a value, throws exception.
         */
        @Test(expected = IllegalStateException.class)
        public final void throwIllegalStateExceptionForGetRawStringOnNewObject() {
                VatPercent vatP = new VatPercent();
                vatP.getData();
        }

}
