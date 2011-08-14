package org.obliquid.datatype.impl;

import java.util.Locale;

import org.obliquid.datatype.ItalianPostCode;
import org.obliquid.datatype.strategy.StringStrategy;

/**
 * Hold and validate an Italian post code (must be 5 digits).
 * 
 * @author stivlo
 * 
 */
public class ItalianPostCodeImpl implements ItalianPostCode {

        /**
         * String strategy (strategy pattern).
         */
        private StringStrategy stringStrategy = new StringStrategy();

        /**
         * Expected length of an Italian post code.
         */
        private final int expectedLength = 5;

        @Override
        public final boolean isValid(final String theData) {
                if (theData == null || theData.length() != expectedLength) {
                        return false;
                }
                for (int i = 0; i < expectedLength; i++) {
                        if (!Character.isDigit(theData.charAt(i))) {
                                return false;
                        }
                }
                return true;
        }

        @Override
        public final boolean isTheStringValid(final String theData) {
                return isValid(theData);
        }

        @Override
        public final String formatData(final Locale locale) throws IllegalStateException {
                return getData();
        }

        @Override
        public final String getData() throws IllegalStateException {
                return stringStrategy.getData();
        }

        @Override
        public final void setData(final String theData) throws IllegalArgumentException {
                if (!isValid(theData)) {
                        throw new IllegalArgumentException("The postcode '" + theData + "' isn't valid");
                }
                stringStrategy.setData(theData);
        }

        @Override
        public final void setDataFromString(final String theData) throws IllegalArgumentException {
                setData(theData);
        }

}
