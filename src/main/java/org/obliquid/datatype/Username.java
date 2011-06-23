package org.obliquid.datatype;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hold and validate a Username. i.e. a String of at least 3 character and maximum 32 characters and
 * composed only of a-z lowercase characters, numbers and the underscore.
 * 
 * @author stivlo
 */
public class Username extends DataType {

    /**
     * Universal version identifier
     */
    private static final long serialVersionUID = 1L;

    private Pattern validationPattern = Pattern.compile("[a-z0-9_]{2,32}+");

    public Username(String usernameString) throws IllegalArgumentException {
        set(usernameString);
    }

    public Username() {
        super();
    }

    /**
     * Check if the username is valid without assigning it
     * 
     * @param username
     *            the username as a String
     * @return true if the username is valid, false otherwise
     */
    @Override
    public boolean isValid(String username) {
        boolean valid = false;
        if (username == null) {
            return false;
        } else if (username.length() == 0) {
            valid = true; //accept the empty string
        } else {
            Matcher matcher = validationPattern.matcher(username);
            valid = matcher.find();
            if (valid && (matcher.start() != 0 || matcher.end() != username.length())) {
                valid = false;
            }
        }
        return valid;
    }

    /**
     * Use a different validation pattern String (this is meant to allow transfer of existing data,
     * not for new passwords).
     * 
     * @param validationPatternString
     */
    public void setValidationPattern(String validationPatternString) {
        validationPattern = Pattern.compile(validationPatternString);
    }

}
