package org.obliquid.datatype.vatid;

import java.util.Locale;

/**
 * 
 * @author stivlo
 * 
 */
public class GermanVatId extends VatId {

        /**
         * Universal Serial Identifier.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public boolean isValid(final String data) {
                return true;
        }

        @Override
        public String getFormattedString(final Locale locale)
                        throws IllegalStateException {
                return getData();
        }

}
