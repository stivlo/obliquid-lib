package org.obliquid.helpers;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Locale;

import org.junit.Test;

public class BdHelperShould {

        @Test
        public void returnZeroWhenComparingABigDecimalToTheSameValueAsInt() {
                BigDecimal first = new BigDecimal("389");
                int second = 389;
                assertEquals(0, BdHelper.compare(first, second));
        }

        @Test
        public void returnANegativeValueWhenComparingABigDecimalSmallerThanAnInt() {
                BigDecimal first = new BigDecimal("389");
                int second = 589;
                assertTrue(BdHelper.compare(first, second) < 0);
        }

        @Test
        public void returnAPositiveValueWhenComparingABigDecimalBiggerThanAnInt() {
                BigDecimal first = new BigDecimal("399");
                int second = 389;
                assertTrue(BdHelper.compare(first, second) > 0);
        }

        @Test
        public void divideTwoBigDecimals() {
                BigDecimal dividend = new BigDecimal("78.988");
                BigDecimal divisor = new BigDecimal("4.1258");
                int scale = 2;
                BigDecimal result = new BigDecimal("19.14");
                assertEquals(0, result.compareTo(BdHelper.divide(dividend, divisor, scale)));
        }

        @Test
        public void divideABigDecimalByAnInteger() {
                BigDecimal dividend = new BigDecimal("938.1132");
                int divisor = 3;
                int scale = 2;
                BigDecimal result = new BigDecimal("312.70");
                assertEquals(0, result.compareTo(BdHelper.divide(dividend, divisor, scale)));
        }

        @Test
        public void divideTwoIntegers() {
                int dividend = 98;
                int divisor = 3;
                int scale = 2;
                BigDecimal result = new BigDecimal("32.67");
                assertEquals(0, result.compareTo(BdHelper.divide(dividend, divisor, scale)));
        }

        @Test
        public void multiplyTwoBigDecimals() {
                BigDecimal first = new BigDecimal("82.913");
                BigDecimal second = new BigDecimal("0.13893");
                int scale = 2;
                BigDecimal result = new BigDecimal("11.52");
                assertEquals(0, result.compareTo(BdHelper.multiply(first, second, scale)));
        }

        @Test
        public void multiplyABigDecimalByAnInt() {
                BigDecimal first = new BigDecimal("13.8312");
                int second = 25;
                int scale = 2;
                BigDecimal result = new BigDecimal("345.78");
                assertEquals(0, result.compareTo(BdHelper.multiply(first, second, scale)));
        }

        @Test
        public void multiplyTwoInt() {
                int first = 3;
                int second = 32;
                int scale = 2;
                assertEquals(0, new BigDecimal("96").compareTo(BdHelper.multiply(first, second, scale)));
        }

        @Test
        public void zeroPad() {
                Locale.setDefault(Locale.US);
                String result = BdHelper.zeroPad(new BigDecimal("34.2"), 5, 2);
                assertEquals("34.20", result);
                result = BdHelper.zeroPad(new BigDecimal("12.1"), 6, 2);
                assertEquals("012.10", result);
                result = BdHelper.zeroPad(new BigDecimal("34.2343"), 6, 2);
                assertEquals("034.23", result);
                result = BdHelper.zeroPad(new BigDecimal("34.2363"), 1, 2);
                assertEquals("34.24", result); // rounded
        }

        @Test(expected = IllegalArgumentException.class)
        public void refuseToZeroPadANegativeValue() {
                BdHelper.zeroPad(new BigDecimal("-234.2"), 5, 2);
        }

}
