package org.obliquid.helpers;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Format currency according the current Locale
 * 
 * @author stivlo
 */
public class CurrencyHelper {

    private static DecimalFormat currencyFormat = new DecimalFormat("###,###,##0.00");

    /**
     * Format currency with two decimal places, using the current Locale
     * 
     * @param number
     * @return formatted String
     */
    public static String format(final int number) {
        return currencyFormat.format(number);
    }

    /**
     * Format currency with two decimal places, using the current Locale
     * 
     * @param number
     * @return formatted String
     */
    public static String format(final BigDecimal number) {
        return currencyFormat.format(number);
    }

}
