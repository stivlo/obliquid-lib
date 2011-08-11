package org.obliquid.datatype;

import java.util.Locale;

import org.obliquid.datatype.deprecated.DataTypeClass;

/**
 * Hold and validate an Italian post code (must be 5 digits).
 * 
 * @author stivlo
 * 
 */
public class ItalianPostCode extends DataTypeClass {

        /**
         * Universal Serial Identifier.
         */
        private static final long serialVersionUID = 1L;

        /**
         * Expected length of an Italian post code.
         */
        private final int expectedLength = 5;

        @Override
        public boolean isValid(final String data) {
                if (data == null || data.length() != expectedLength) {
                        return false;
                }
                for (int i = 0; i < expectedLength; i++) {
                        if (!Character.isDigit(data.charAt(i))) {
                                return false;
                        }
                }
                return true;
        }

        @Override
        public String getFormattedString(final Locale locale)
                        throws IllegalStateException {
                return getData();
        }

}
