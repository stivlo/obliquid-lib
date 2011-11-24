package org.obliquid.datatype.vatid;

import org.obliquid.datatype.impl.VatIdImpl;

/**
 * Hold and verify an Italian VAT Id. Formal verification as explained at
 * http://www.icosaedro.it/cf-pi/index.html
 * 
 * @author stivlo
 */
public class ItalianVatId extends VatIdImpl {

        /**
         * Universal serial identifier.
         */
        private static final long serialVersionUID = 1L;

        /** Expected length, including the prefix 'IT-'. */
        private static final int EXPECTED_LEN = 14;

        /** Length of the prefix 'IT-'. */
        private static final int PREFIX_LEN = 3;

        /** Length of the body, excluding the prefix. */
        private static final int BODY_LEN = 11;

        /**
         * Validates IT VAT-Id.
         * 
         * @param vatIdString
         *                the vat id to be checked
         * @return true if is valid
         */
        @Override
        public final boolean isValid(final String vatIdString) {
                if (vatIdString == null) {
                        return false;
                }
                String start = vatIdString.substring(0, PREFIX_LEN);
                if (!start.equals("IT-")) {
                        //setMessage("An Italian Vat Id should start with 'IT-', instead is starting with '"
                        //                + start + "'");
                        return false;
                }
                return checkItalianVatId(vatIdString);
        }

        /**
         * Check for invalid length.
         * 
         * @param vatId
         *                the vat id to check
         * @return true if the String has an invalid length
         */
        private boolean hasInvalidLengthItalian(final String vatId) {
                if (vatId.length() != EXPECTED_LEN) {
                        //setMessage("Invalid length");
                        return true;
                }
                return false;
        }

        /**
         * Check for invalid characters.
         * 
         * @param vatId
         *                Italian vat id to be checked
         * @return true if there are invalid characters, false otherwise
         */
        private boolean hasInvalidCharacterItalian(final String vatId) {
                char aChar;
                if (!vatId.substring(0, PREFIX_LEN).equals("IT-")) {
                        //setMessage("Prefix must be IT-");
                        return true;
                }
                String vatIdNumberPart = vatId.substring(PREFIX_LEN);
                for (int i = 0; i < BODY_LEN; i++) {
                        aChar = vatIdNumberPart.charAt(i);
                        if (aChar < '0' || aChar > '9') {
                                //setMessage("Only digits allowed");
                                return true;
                        }
                }
                return false;
        }

        /**
         * Check whether an Italian Vat Id is valid.
         * 
         * @param vatIdString
         *                the vat id string to check.
         * @return true if valid
         */
        private boolean checkItalianVatId(final String vatIdString) {
                int i, aChar, s;
                final int base = 10;
                //setMessage("");
                if (hasInvalidLengthItalian(vatIdString) || hasInvalidCharacterItalian(vatIdString)) {
                        return false;
                }
                String vatIdNum = vatIdString.substring(PREFIX_LEN); //numeric part of the vatId
                s = 0;
                for (i = 0; i <= BODY_LEN - 2; i += 2) {
                        s += vatIdNum.charAt(i) - '0';
                }
                for (i = 1; i <= BODY_LEN - 2; i += 2) {
                        aChar = 2 * (vatIdNum.charAt(i) - '0');
                        if (aChar > base - 1) {
                                aChar = aChar - (base - 1);
                        }
                        s += aChar;
                }
                if ((base - s % base) % base != vatIdNum.charAt(base) - '0') {
                        //setMessage("Wrong check digit");
                        return false;
                }
                return true;
        }

}
