package org.obliquid.datatype.vatid;

import java.util.Locale;

/**
 * Hold and verify a Spanish VAT Id. For the moment the class only checks that
 * the first 3 letters are "ES-" and the total length is 12.
 * 
 * @author stivlo
 */
public class SpanishVatId extends VatId {

        /**
         * Universal version identifier.
         */
        private static final long serialVersionUID = 1L;

        protected SpanishVatId() {
                //use createInstance() method in the parent class 
        }

        @Override
        public boolean isValid(final String data) {
                setMessage("");
                String start = data.substring(0, 3);
                if (!start.equals("ES-")) {
                        setMessage("A Spanish Vat Id should start with 'ES-', instead is starting with '"
                                        + start + "'");
                        return false;
                }
                if (data.length() != 12) {
                        setMessage("A Spanish Vat Id should be long 12 chars, instead it's "
                                        + data.length() + " chars");
                        return false;
                }
                return true;
        }

        @Override
        public String getFormattedString(final Locale locale)
                        throws IllegalStateException {
                return getData();
        }

}
