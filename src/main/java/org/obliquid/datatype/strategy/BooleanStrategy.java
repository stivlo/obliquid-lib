package org.obliquid.datatype.strategy;

import java.util.Locale;

import org.obliquid.datatype.DataType;

/**
 * Implement Boolean Data Type behaviour for reuse. (Strategy Pattern)
 * 
 * @author stivlo
 * 
 */
public class BooleanStrategy implements DataType<Boolean> {

        /**
         * Universal serial identifier.
         */
        private static final long serialVersionUID = 1L;

        /**
         * The data item.
         */
        private Boolean data;

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
                if (data == null) {
                        throw new IllegalStateException();
                }
                if (data.booleanValue()) {
                        return "true";
                }
                return "false";
        }

        @Override
        public final Boolean getData() throws IllegalStateException {
                if (data == null) {
                        throw new IllegalStateException();
                }
                return data;
        }

        @Override
        public final void setDataFromString(final String theData) throws IllegalArgumentException {
                if (theData == null) {
                        throw new IllegalArgumentException("A BooleanType in String Form can't be null.");
                }
                if (theData.equals("Y")) {
                        data = true;
                } else if (theData.equals("N")) {
                        data = false;
                } else {
                        throw new IllegalArgumentException("BooleanType should be 'Y' or 'N' expected, '"
                                        + theData + "' supplied.");
                }
        }

        @Override
        public final void setData(final Boolean theData) throws IllegalArgumentException {
                if (theData == null) {
                        throw new IllegalArgumentException();
                }
                data = theData;
        }

        @Override
        public final boolean isAssigned() {
                return data != null;
        }

}
