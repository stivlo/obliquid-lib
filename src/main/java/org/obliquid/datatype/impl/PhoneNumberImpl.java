package org.obliquid.datatype.impl;

import java.util.Locale;

import org.obliquid.datatype.PhoneNumber;
import org.obliquid.datatype.strategy.StringStrategy;

/**
 * Hold and validate and normalise phone number.
 * 
 * @author stivlo
 */
public class PhoneNumberImpl implements PhoneNumber {

        /**
         * Universal serial identifier.
         */
        private static final long serialVersionUID = 1L;

        /** String strategy algorithm. (Strategy Pattern). */
        private StringStrategy stringStrategy = new StringStrategy();

        /**
         * The minimum number of digits.
         */
        private static final int MIN_LENGTH = 6;

        /**
         * Check whether a phone number is valid.
         * 
         * @param phoneString
         *                the phone number to be checked
         * @return true if the phone number is valid
         */
        @Override
        public final boolean isValid(final String phoneString) {
                String aPhone = normalize(phoneString);
                if (aPhone.length() < MIN_LENGTH) {
                        return false;
                }
                Character aChar;
                for (int i = 0; i < aPhone.length(); i++) {
                        aChar = aPhone.charAt(i);
                        if (i == 0 && aChar == '+') {
                                continue;
                        }
                        if (aChar < '0' || aChar > '9') {
                                if (aChar == '+' && i == 0) {
                                        continue;
                                }
                                return false;
                        }
                }
                return true;
        }

        @Override
        public final boolean isTheStringValid(final String phoneString) {
                return isValid(phoneString);
        }

        @Override
        public final void setData(final String data) throws IllegalArgumentException {
                if (!isValid(data)) {
                        throw new IllegalArgumentException(getClass().getName() + " '" + data
                                        + "' is not valid");
                }
                stringStrategy.setData(normalize(data));
        }

        @Override
        public final void setDataFromString(final String phoneString) {
                setData(phoneString);
        }

        /**
         * Clean the phone number from hyphens, dots and parenthesis.
         * 
         * @param phoneString
         *                the phone number to clean
         * @return a cleaned phone number
         */
        private String normalize(final String phoneString) {
                String phoneStringOut = phoneString.trim().replaceAll(" ", "").replaceAll("/", "")
                                .replaceAll("\\.", "").replaceAll("-", "").replaceAll("\\(", "")
                                .replaceAll("\\)", "");
                //System.out.println("phone: " + phoneString);
                return phoneStringOut;
        }

        @Override
        public final String formatData(final Locale locale) throws IllegalStateException {
                return getData();
        }

        @Override
        public final String getData() {
                return stringStrategy.getData();
        }

        @Override
        public final boolean isAssigned() {
                return stringStrategy.isAssigned();
        }

}
