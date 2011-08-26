package org.obliquid.datatype.impl;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.obliquid.datatype.Password;
import org.obliquid.datatype.strategy.StringStrategy;
import org.obliquid.helpers.StringHelper;

/**
 * Hold and validate a password. i.e. a String of at least 8 characters and
 * maximum 30 characters and composed only of a-z lower case characters, A-Z
 * upper case characters, numbers and the underscore.
 * 
 * @author stivlo
 * 
 */
public class PasswordImpl implements Password {

        /**
         * The string strategy algorithm implementation.
         */
        private StringStrategy stringStrategy = new StringStrategy();

        /**
         * Validation pattern for passwords.
         */
        private final Pattern validationPattern = Pattern.compile("[a-zA-Z0-9_\\-\\.\\!\\?;]{5,30}+");

        /**
         * Return an empty String, we don't want to show passwords.
         * 
         * @param locale
         *                current locale, ignored
         * 
         * @return an empty String
         */
        @Override
        public final String formatData(final Locale locale) {
                return "";
        }

        /**
         * Check the validity of a SHA-1 password.
         * 
         * @param passwordSha1
         *                the SHA-1 encoded password
         * @return true if the password is valid
         */
        @Override
        public final boolean isValid(final String passwordSha1) {
                final int sha1Length = 40;
                return passwordSha1 != null && passwordSha1.length() == sha1Length;
        }

        /**
         * Check the validity of a clear text password.
         * 
         * @param passwordClearText
         *                the password in clear text
         * @return true if the password is valid
         */
        @Override
        public final boolean isTheStringValid(final String passwordClearText) {
                boolean valid = false;
                if (passwordClearText == null) {
                        return false; //password is null
                } else if (passwordClearText.length() == 0) {
                        return false; //password is empty
                } else {
                        Matcher matcher = validationPattern.matcher(passwordClearText);
                        valid = matcher.find();
                        if (valid && (matcher.start() != 0 || matcher.end() != passwordClearText.length())) {
                                return false; //password is invalid
                        }
                }
                return valid;
        }

        /**
         * Set the password in clear text and store it as a SHA-1 hash.
         * 
         * @param passwordClearText
         *                the password in clear text
         * @throws IllegalArgumentException
         *                 when the password is not valid (including when is
         *                 null or empty).
         */
        @Override
        public final void setDataFromString(final String passwordClearText) throws IllegalArgumentException {
                if (!isTheStringValid(passwordClearText)) {
                        throw new IllegalArgumentException("The clear text password isn't valid");
                }
                String passwordSha1 = StringHelper.computeSha1OfString(passwordClearText);
                setData(passwordSha1);
        }

        /**
         * Set a already encoded SHA-1 password.
         * 
         * @param passwordSha1
         *                the password encoded in SHA-1
         * @throws IllegalArgumentException
         *                 when the password is not SHA-1 encoded.
         */
        @Override
        public final void setData(final String passwordSha1) throws IllegalArgumentException {
                if (!isValid(passwordSha1)) {
                        throw new IllegalArgumentException("Not a valid SHA-1 encoded password: '"
                                        + passwordSha1 + "'.");
                }
                stringStrategy.setData(passwordSha1);
        }

        @Override
        public final String getData() throws IllegalStateException {
                return stringStrategy.getData();
        }

        @Override
        public final boolean isAssigned() {
                return stringStrategy.isAssigned();
        }

}
