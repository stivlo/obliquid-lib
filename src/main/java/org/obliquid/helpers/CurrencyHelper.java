package org.obliquid.helpers;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Format currency according the current Locale.
 * 
 * @author stivlo
 */
public final class CurrencyHelper {

        /** Formatting mask. */
        private static DecimalFormat currencyFormat = new DecimalFormat("###,###,##0.00");

        /** Utility class. */
        private CurrencyHelper() {
        }

        /**
         * Format currency with two decimal places, using the current Locale.
         * 
         * @param number
         *                the number to be formatted
         * @return formatted String
         */
        public static String format(final int number) {
                return currencyFormat.format(number);
        }

        /**
         * Format currency with two decimal places, using the current Locale.
         * 
         * @param number
         *                the number to be formatted
         * @return formatted String
         */
        public static String format(final BigDecimal number) {
                return currencyFormat.format(number);
        }

}
