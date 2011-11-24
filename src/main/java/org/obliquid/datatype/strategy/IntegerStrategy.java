package org.obliquid.datatype.strategy;

import java.util.Locale;

import org.obliquid.datatype.DataType;

/**
 * Implement Integer Data Type behaviour for reuse. (Strategy Pattern).
 * 
 * @author stivlo
 * 
 */
public class IntegerStrategy implements DataType<Integer> {

        /**
         * Universal serial identifier.
         */
        private static final long serialVersionUID = 1L;

        /** The data item. */
        private Integer data;

        /**
         * Return the data as a String.
         * 
         * @param locale
         *                locale to use
         * @return the data as a String
         * @throws IllegalStateException
         *                 when the data wasn't set.
         */
        @Override
        public final String formatData(final Locale locale) throws IllegalStateException {
                return String.valueOf(getData());
        }

        @Override
        public final Integer getData() throws IllegalStateException {
                if (data == null) {
                        throw new IllegalStateException();
                }
                return data;
        }

        @Override
        public final void setDataFromString(final String theData) throws IllegalArgumentException {
                if (theData == null) {
                        throw new IllegalArgumentException();
                }
                data = Integer.parseInt(theData);
        }

        @Override
        public final void setData(final Integer theData) throws IllegalArgumentException {
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
