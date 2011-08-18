package org.obliquid.datatype.impl;

import java.util.Locale;

import org.apache.commons.validator.EmailValidator;
import org.obliquid.datatype.EmailAddress;
import org.obliquid.datatype.strategy.StringStrategy;

/**
 * Hold and validate an email address.
 * 
 * @author stivlo
 */
public class EmailAddressImpl implements EmailAddress {

        /**
         * String strategy implementation.
         */
        private StringStrategy stringStrategy = new StringStrategy();

        /**
         * Check if the email address is valid, an empty email is OK.
         * 
         * @param anEmail
         *                the email to check
         * @return true if is valid
         */
        @Override
        public final boolean isValid(final String anEmail) {
                if (anEmail == null) {
                        return false;
                }
                String trimmedEmail = anEmail.trim();
                if (trimmedEmail.length() == 0) {
                        return true;
                }
                return EmailValidator.getInstance().isValid(trimmedEmail);
        }

        @Override
        public final boolean isTheStringValid(final String theData) {
                return isValid(theData);
        }

        /**
         * Just return the email address, no formatting actually required.
         * 
         * @param locale
         *                the locale used to display the formatted message
         * @return the stored email address
         */
        @Override
        public final String formatData(final Locale locale) {
                return getData();
        }

        /**
         * Return the email address, trimmed.
         * 
         * @return trimmed email.
         * @throws IllegalStateException
         *                 if the email hasn't been set.
         */
        @Override
        public final String getData() throws IllegalStateException {
                return stringStrategy.getData();
        }

        /**
         * Set the email address.
         * 
         * @param email
         *                email address to set
         * @throws IllegalArgumentException
         *                 if the email isn't valid.
         */
        @Override
        public final void setData(final String email) throws IllegalArgumentException {
                if (!isValid(email)) {
                        throw new IllegalArgumentException("Email '" + email + "' isn't valid");
                }
                stringStrategy.setData(email.trim());
        }

        @Override
        public final void setDataFromString(final String email) throws IllegalArgumentException {
                setData(email);
        }

        /**
         * Return the trimmed email.
         * 
         * @return trimmed email address.
         */
        @Override
        public final String toString() {
                return stringStrategy.getData();
        }

        @Override
        public final boolean isAssigned() {
                return stringStrategy.isAssigned();
        }

}
