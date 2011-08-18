package org.obliquid.helpers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Locale;

import org.junit.Test;

/**
 * Class under test BdHelper.
 * 
 * @author stivlo
 * 
 */
public class BdHelperShould {

        /**
         * Return zero when comparing a BigDecimal to the same value as int.
         */
        @Test
        public final void returnZeroWhenComparingABigDecimalToTheSameValueAsInt() {
                BigDecimal first = new BigDecimal("389");
                final int second = 389;
                assertEquals(0, BdHelper.compare(first, second));
        }

        /**
         * Return a negative value when comparing a BigDecimal smaller than an
         * int.
         */
        @Test
        public final void returnANegativeValueWhenComparingABigDecimalSmallerThanAnInt() {
                BigDecimal first = new BigDecimal("389");
                final int second = 589;
                assertTrue(BdHelper.compare(first, second) < 0);
        }

        /**
         * Return a positive value when comparing a BigDecimal bigger than an
         * int.
         */
        @Test
        public final void returnAPositiveValueWhenComparingABigDecimalBiggerThanAnInt() {
                BigDecimal first = new BigDecimal("399");
                final int second = 389;
                assertTrue(BdHelper.compare(first, second) > 0);
        }

        /**
         * Divide two BigDecimals.
         */
        @Test
        public final void divideTwoBigDecimals() {
                BigDecimal dividend = new BigDecimal("78.988");
                BigDecimal divisor = new BigDecimal("4.1258");
                final int scale = 2;
                BigDecimal result = new BigDecimal("19.14");
                assertEquals(0, result.compareTo(BdHelper.divide(dividend, divisor, scale)));
        }

        /**
         * Divide a BigDecimal by an integer.
         */
        @Test
        public final void divideABigDecimalByAnInteger() {
                BigDecimal dividend = new BigDecimal("938.1132");
                final int divisor = 3;
                final int scale = 2;
                BigDecimal result = new BigDecimal("312.70");
                assertEquals(0, result.compareTo(BdHelper.divide(dividend, divisor, scale)));
        }

        /**
         * Divide two integers.
         */
        @Test
        public final void divideTwoIntegers() {
                final int dividend = 98;
                final int divisor = 3;
                final int scale = 2;
                BigDecimal result = new BigDecimal("32.67");
                assertEquals(0, result.compareTo(BdHelper.divide(dividend, divisor, scale)));
        }

        /**
         * Multiply two BigDecimal.
         */
        @Test
        public final void multiplyTwoBigDecimals() {
                BigDecimal first = new BigDecimal("82.913");
                BigDecimal second = new BigDecimal("0.13893");
                int scale = 2;
                BigDecimal result = new BigDecimal("11.52");
                assertEquals(0, result.compareTo(BdHelper.multiply(first, second, scale)));
        }

        /**
         * Multiply a BigDecimal by an int.
         */
        @Test
        public final void multiplyABigDecimalByAnInt() {
                BigDecimal first = new BigDecimal("13.8312");
                final int second = 25;
                final int scale = 2;
                BigDecimal result = new BigDecimal("345.78");
                assertEquals(0, result.compareTo(BdHelper.multiply(first, second, scale)));
        }

        /**
         * Multiply two int.
         */
        @Test
        public final void multiplyTwoInt() {
                final int first = 3;
                final int second = 32;
                int scale = 2;
                assertEquals(0, new BigDecimal("96").compareTo(BdHelper.multiply(first, second, scale)));
        }

        /**
         * Zero padding test 1.
         */
        @Test
        public final void zeroPad1() {
                final int totalLength = 5;
                final int scale = 2;
                Locale.setDefault(Locale.US);
                String result = BdHelper.zeroPad(new BigDecimal("34.2"), totalLength, scale);
                assertEquals("34.20", result);
        }

        /**
         * Zero padding test 2.
         */
        @Test
        public final void zeroPad2() {
                final int totalLength = 6;
                final int scale = 2;
                String result = BdHelper.zeroPad(new BigDecimal("12.1"), totalLength, scale);
                assertEquals("012.10", result);
                result = BdHelper.zeroPad(new BigDecimal("34.2343"), totalLength, scale);
                assertEquals("034.23", result);
                result = BdHelper.zeroPad(new BigDecimal("34.2363"), 1, scale);
                assertEquals("34.24", result); // rounded
        }

        /**
         * Refuse to zero pad a negative value.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void refuseToZeroPadANegativeValue() {
                final int totalLength = 5;
                final int scale = 2;
                BdHelper.zeroPad(new BigDecimal("-234.2"), totalLength, scale);
        }

}
