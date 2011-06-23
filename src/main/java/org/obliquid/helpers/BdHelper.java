package org.obliquid.helpers;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Make it easier to do some operations with BigDecimals. Using RoundingMode.HALF_EVEN where
 * applicable.
 * 
 * @author stivlo
 * 
 */
public class BdHelper {

    private static int roundingMode = BigDecimal.ROUND_HALF_EVEN;

    /**
     * Compare the BigDecimal first to the int second.
     * 
     * @param first
     *            first number to compare
     * @param second
     *            second number to compare
     * @return a negative value, 0, or a positive value as this BigDecimal is numerically less than,
     *         equal to, or greater than val.
     */
    public static int compare(final BigDecimal first, final int second) {
        return first.compareTo(new BigDecimal(second));
    }

    /**
     * Divide two numbers with the given scale.
     * 
     * @param dividend
     *            the number to be divided
     * @param divisor
     *            the second operand
     * @param scale
     *            number of digits after the decimal point to be computed
     * @return the result of the division
     */
    public static BigDecimal divide(final BigDecimal dividend, final BigDecimal divisor, final int scale) {
        return dividend.divide(divisor, scale, roundingMode);
    }

    /**
     * Divide two numbers with the given scale.
     * 
     * @param dividend
     *            the number to be divided
     * @param divisor
     *            the second operand
     * @param scale
     *            number of digits after the decimal point to be computed
     * @return the result of the division
     */
    public static BigDecimal divide(final BigDecimal dividend, final int divisor, final int scale) {
        return dividend.divide(new BigDecimal(divisor), scale, roundingMode);
    }

    /**
     * Divide two numbers with the given scale.
     * 
     * @param dividend
     *            the number to be divided
     * @param divisor
     *            the second operand
     * @param scale
     *            number of digits after the decimal point to be computed
     * @return the result of the division
     */
    public static BigDecimal divide(final int dividend, final int divisor, final int scale) {
        BigDecimal dividendBd = new BigDecimal(dividend);
        return dividendBd.divide(new BigDecimal(divisor), scale, roundingMode);
    }

    /**
     * Multiplies first by second
     * 
     * @param first
     *            first number to multiply
     * @param second
     *            second number to multiply
     * @param scale
     *            the number of digits to the right of the decimal point
     * @return a new BigDecimal containing the result
     */
    public static BigDecimal multiply(final BigDecimal first, final BigDecimal second, final int scale) {
        return first.multiply(second).setScale(scale, roundingMode);
    }

    /**
     * Multiplies first by second
     * 
     * @param first
     *            first number to multiply
     * @param second
     *            second number to multiply
     * @param scale
     *            the number of digits to the right of the decimal point
     * @return a new BigDecimal containing the result
     */
    public static BigDecimal multiply(final BigDecimal first, final int second, final int scale) {
        return first.multiply(new BigDecimal(second)).setScale(scale, roundingMode);
    }

    /**
     * Multiplies first by second
     * 
     * @param first
     *            first number to multiply
     * @param second
     *            second number to multiply
     * @param scale
     *            the number of digits to the right of the decimal point
     * @return a new BigDecimal containing the result
     */
    public static BigDecimal multiply(final int first, final int second, final int scale) {
        return new BigDecimal(first).multiply(new BigDecimal(second)).setScale(scale, roundingMode);
    }

    /**
     * Zero pad the decimal part of the argument until it reach the specified scale and then zero
     * pad the integer part until the whole resulting string reach the specified totalLength (also
     * the decimal separator is counted).
     * 
     * @param value
     *            a BigDecimal to be zero-padded
     * @param totalLength
     *            the total length, including the decimal point and the decimal digits
     * @param scale
     *            number of digits after the decimal point
     * @return a zero-padded String representation
     * @throws IllegalArgumentException
     *             when the BigDecimal is negative, since is not supported
     */
    public static String zeroPad(final BigDecimal value, final int totalLength, final int scale) {
        if (BdHelper.compare(value, 0) < 0) {
            throw new IllegalArgumentException("BigDecimal attribute can't be negative");
        }
        String format, zero = "0";
        int integerLength = totalLength - scale - 1;
        BigDecimal rounded = value.setScale(scale, BigDecimal.ROUND_HALF_EVEN);
        format = StringHelper.repeat(zero, integerLength) + "." + StringHelper.repeat(zero, scale);
        DecimalFormat df = new DecimalFormat(format);
        return df.format(rounded.doubleValue());
    }

}
