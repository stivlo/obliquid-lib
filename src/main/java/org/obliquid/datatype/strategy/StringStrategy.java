package org.obliquid.datatype.strategy;

import java.util.Locale;

import org.obliquid.datatype.DataType;

/**
 * Implement basic String DataType behaviour for reuse. (Strategy Pattern).
 * 
 * @author stivlo
 * 
 */
public class StringStrategy implements DataType<String> {

        /**
         * The data held.
         */
        private String data;

        @Override
        public final String formatData(final Locale locale) throws IllegalStateException {
                return getData();
        }

        @Override
        public final String getData() throws IllegalStateException {
                if (data == null) {
                        throw new IllegalStateException("The status is null");
                }
                return data;
        }

        @Override
        public final void setDataFromString(final String theData) throws IllegalArgumentException {
                setData(theData);
        }

        @Override
        public final void setData(final String theData) throws IllegalArgumentException {
                if (theData == null) {
                        throw new IllegalArgumentException("The argument can't be null");
                }
                data = theData;
        }

}
