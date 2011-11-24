package org.obliquid.datatype.vatid;

import org.obliquid.datatype.impl.VatIdImpl;

/**
 * Hold and verify a Spanish VAT Id. For the moment the class only checks that
 * the first 3 letters are "ES-" and the total length is 12.
 * 
 * @author stivlo
 */
public class SpanishVatId extends VatIdImpl {

        /**
         * Universal serial identifier.
         */
        private static final long serialVersionUID = 1L;

        /** Expected length of a Spanish Vat Id. */
        private static final int EXPECTED_LEN = 12;

        @Override
        public final boolean isValid(final String data) {
                final int prefixLen = 3;
                //setMessage("");
                String start = data.substring(0, prefixLen);
                if (!start.equals("ES-")) {
                        //setMessage("A Spanish Vat Id should start with 'ES-', instead is starting with '"
                        //                + start + "'");
                        return false;
                }
                if (data.length() != EXPECTED_LEN) {
                        //setMessage("A Spanish Vat Id should be long 12 chars, instead it's " + data.length()
                        //                + " chars");
                        return false;
                }
                return true;
        }

}
