package org.obliquid.datatype.impl;

import java.util.Locale;

import org.obliquid.datatype.BooleanType;
import org.obliquid.datatype.DataType;
import org.obliquid.datatype.strategy.BooleanStrategy;

/**
 * Represents a boolean value, raw as Y/N.
 * 
 * @author stivlo
 */
public class BooleanTypeImpl implements BooleanType {

        /**
         * Boolean Strategy.
         */
        private DataType<Boolean> booleanStrategy = new BooleanStrategy();

        /**
         * Output as "true" or "false".
         * 
         * @param locale
         *                the locale to use
         * @return a formatted string
         * @throws IllegalStateException
         *                 if it's not either true or false.
         */
        @Override
        public final String formatData(final Locale locale) throws IllegalStateException {
                return booleanStrategy.formatData(locale);
        }

        @Override
        public final Boolean getData() throws IllegalStateException {
                return booleanStrategy.getData();
        }

        @Override
        public final void setDataFromString(final String theData) throws IllegalArgumentException {
                booleanStrategy.setDataFromString(theData);
        }

        /**
         * Check if the value is valid: in this case, always.
         * 
         * @param theData
         *                the data to be checked
         * @return true if is valid
         */
        @Override
        public final boolean isValid(final Boolean theData) {
                return theData != null;
        }

        /**
         * Check if the value is valid without setting it.
         * 
         * @param booleanYN
         *                should be either "Y" or "N" upper case
         * @return true if is valid
         */
        @Override
        public final boolean isTheStringValid(final String booleanYN) {
                if (booleanYN == null) {
                        return false;
                }
                return (booleanYN.equals("Y") || booleanYN.equals("N"));
        }

        @Override
        public final void setData(final Boolean theData) throws IllegalArgumentException {
                booleanStrategy.setData(theData);
        }

        @Override
        public final String toString() {
                return formatData(Locale.getDefault());
        }

}
