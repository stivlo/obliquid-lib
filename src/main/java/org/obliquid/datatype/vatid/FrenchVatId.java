package org.obliquid.datatype.vatid;

import java.util.Locale;

public class FrenchVatId extends VatId {

        private static final long serialVersionUID = 1L;

        @Override
        public boolean isValid(String data) {
                return true;
        }

        @Override
        public String getFormattedString(final Locale locale)
                        throws IllegalStateException {
                return getData();
        }

}
