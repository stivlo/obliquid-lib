package org.obliquid.datatype;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.obliquid.datatype.deprecated.DataTypeClass;

/**
 * Hold and validate a Username. i.e. a String of at least 3 character and
 * maximum 30 characters and composed only of a-z lower case characters, numbers
 * and the underscore. Just check for formal validity, doesn't check the DB.
 * Empty username is accepted.
 * 
 * @author stivlo
 */
public class UsernameImpl extends DataTypeClass {

        /**
         * Universal version identifier.
         */
        private static final long serialVersionUID = 1L;

        /**
         * Validation pattern.
         */
        private Pattern validationPattern = Pattern.compile("[A-Za-z0-9_]{3,30}+");

        /**
         * Validation failed message.
         */
        private String failedValidationMessage = "Username can only be composed of a-z letters "
                        + "(lowercase or uppercase), numbers and the underscore. Minimum length "
                        + "is 3 chars, maximum length is 30 chars.";

        /**
         * Construct an empty Username.
         */
        public UsernameImpl() {
                super();
        }

        /**
         * Construct a Username with usernameString.
         * 
         * @param usernameString
         *                the username to set
         * @throws IllegalArgumentException
         *                 when the username is not valid
         */
        public UsernameImpl(final String usernameString) throws IllegalArgumentException {
                set(usernameString);
        }

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
                if (username == null) {
                        setMessage("Username can't be null");
                        return false;
                } else if (username.length() == 0) {
                        setMessage("");
                        valid = true; //accept the empty string
                } else {
                        Matcher matcher = validationPattern.matcher(username);
                        valid = matcher.find();
                        if (valid && (matcher.start() != 0 || matcher.end() != username.length())) {
                                setMessage(failedValidationMessage);
                                valid = false;
                        }
                }
                return valid;
        }

        /**
         * Use a different validation pattern String (this is meant to allow
         * transfer of existing data, not for new passwords).
         * 
         * @param validationPatternString
         *                regex pattern
         */
        public final void setValidationPattern(final String validationPatternString) {
                validationPattern = Pattern.compile(validationPatternString);
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
        public final String getFormattedString(final Locale locale) throws IllegalStateException {
                return getData();
        }

}
