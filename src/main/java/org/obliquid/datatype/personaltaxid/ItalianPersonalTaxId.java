package org.obliquid.datatype.personaltaxid;

import org.obliquid.datatype.impl.PersonalTaxIdImpl;

/**
 * Hold and validate an Italian Personal Tax Id.
 * 
 * @author stivlo
 */
public class ItalianPersonalTaxId extends PersonalTaxIdImpl {

        /** Expected length of an Italian Personal Tax Id. */
        private static final int EXPECTED_LEN = 16;

        /**
         * Check Italian Tax Id http://www.icosaedro.it/cf-pi/index.html .
         * 
         * @param taxId
         *                the taxId to check
         * @return true if valid
         */
        @Override
        public final boolean isValid(final String taxId) {
                if (hasInvalidLength(taxId) || hasInvalidCharacter(taxId)) {
                        return false;
                }
                if (computeCheckDigit(taxId) != taxId.charAt(EXPECTED_LEN - 1)) {
                        return false;
                }
                return true;
        }

        /**
         * Compute the check digit.
         * 
         * @param taxId
         *                tax Id for which we have to compute the check digit
         * @return the check digit
         */
        private char computeCheckDigit(final String taxId) {
                final int lettersInAlphabet = 26;
                final int maxOdd = 13, maxEven = 14;
                final int[] setdisp = { 1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14,
                                16, 10, 22, 25, 24, 23 };
                int charWeight = 0, aChar;
                for (int i = 1; i <= maxOdd; i += 2) {
                        aChar = taxId.charAt(i);
                        if (aChar >= '0' && aChar <= '9') {
                                charWeight = charWeight + aChar - '0';
                        } else {
                                charWeight = charWeight + aChar - 'A';
                        }
                }
                for (int i = 0; i <= maxEven; i += 2) {
                        aChar = taxId.charAt(i);
                        if (aChar >= '0' && aChar <= '9') {
                                aChar = aChar - '0' + 'A';
                        }
                        charWeight = charWeight + setdisp[aChar - 'A'];
                }
                return (char) (charWeight % lettersInAlphabet + 'A');
        }

        /**
         * Check for invalid length.
         * 
         * @param taxId
         *                the taxId to check
         * @return true if the String has an invalid length
         */
        private boolean hasInvalidLength(final String taxId) {
                if (taxId == null || taxId.length() != EXPECTED_LEN) {
                        return true;
                }
                return false;
        }

        /**
         * Check for invalid characters.
         * 
         * @param taxId
         *                taxId to be checked
         * @return true if there are invalid characters, false otherwise
         */
        private boolean hasInvalidCharacter(final String taxId) {
                char aChar;
                for (int i = 0; i < EXPECTED_LEN; i++) {
                        aChar = taxId.charAt(i);
                        if (!(aChar >= '0' && aChar <= '9' || aChar >= 'A' && aChar <= 'Z')) {
                                return true;
                        }
                }
                return false;
        }

}
