package org.obliquid.datatype.impl;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.obliquid.datatype.Username;
import org.obliquid.datatype.strategy.StringStrategy;

/**
 * Hold and validate a Username. i.e. a String of at least 2 characters and
 * maximum 30 characters and composed only of a-z lower case characters, numbers
 * and the underscore. Just check for formal validity, doesn't check the DB.
 * Empty username is accepted.
 * 
 * @author stivlo
 */
public class UsernameImpl implements Username {

        /** A String Strategy, component of the Strategy Pattern. */
        private StringStrategy stringStrategy = new StringStrategy();

        /**
         * Validation pattern.
         */
        private Pattern validationPattern = Pattern.compile("[A-Za-z0-9_]{2,30}+");

        /**
         * Validation failed message.
         */
        //        private String failedValidationMessage = "Username can only be composed of a-z letters "
        //                        + "(lower case or upper case), numbers and the underscore. Minimum length "
        //                        + "is 3 chars, maximum length is 30 chars.";

        /**
         * Check if the username is valid without assigning it.
         * 
         * @param username
         *                the username as a String
         * @return true if the username is valid, false otherwise
         */
        @Override
        public final boolean isValid(final String username) {
                boolean valid = false;
                if (username == null || username.length() == 0) {
                        return false;
                }
                Matcher matcher = validationPattern.matcher(username);
                valid = matcher.find();
                if (valid && (matcher.start() != 0 || matcher.end() != username.length())) {
                        return false;
                }
                return valid;
        }

        @Override
        public final boolean isTheStringValid(final String username) {
                return isValid(username);
        }

        /**
         * Get the username as is.
         * 
         * @param locale
         *                the locale to use
         * @return an unformatted username.
         * @throws IllegalStateException
         *                 if the value wasn't set
         */
        @Override
        public final String formatData(final Locale locale) throws IllegalStateException {
                return getData();
        }

        @Override
        public final String getData() throws IllegalStateException {
                return stringStrategy.getData();
        }

        @Override
        public final void setDataFromString(final String username) throws IllegalArgumentException {
                setData(username);
        }

        @Override
        public final void setData(final String username) throws IllegalArgumentException {
                if (!isValid(username)) {
                        throw new IllegalArgumentException();
                }
                stringStrategy.setData(username);
        }

        @Override
        public final boolean isAssigned() {
                return stringStrategy.isAssigned();
        }

}
