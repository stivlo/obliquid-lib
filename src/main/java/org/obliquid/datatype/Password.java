package org.obliquid.datatype;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.obliquid.helpers.StringHelper;

/**
 * Hold and validate a password. i.e. a String of at least 8 characters and maximum 32 characters
 * and composed only of a-z lowercase characters, A-Z uppercase characters, numbers and the
 * underscore.
 * 
 * @author stivlo
 * 
 */
public class Password extends DataType {

    private static final long serialVersionUID = 1L;

    private Pattern validationPattern = Pattern.compile("[a-zA-Z0-9_\\-\\.]{5,32}+");

    public Password() {
        super();
    }

    public Password(String passwordClearText) {
        super();
        set(passwordClearText);
    }

    /**
     * Return an empty String, we don't want to show passwords
     */
    @Override
    public String getFormattedString(Locale locale) {
        return "";
    }

    /**
     * Check the validity of a clear text password
     */
    @Override
    public boolean isValid(String passwordClearText) {
        boolean valid = false;
        message = "";
        if (passwordClearText == null) {
            message = "password is null";
        } else if (passwordClearText.length() == 0) {
            message = "password '" + passwordClearText + "' is empty";
            valid = true;
        } else {
            Matcher matcher = validationPattern.matcher(passwordClearText);
            valid = matcher.find();
            if (valid && (matcher.start() != 0 || matcher.end() != passwordClearText.length())) {
                message = "invalid password '" + passwordClearText + "'";
                valid = false;
            }
        }
        return valid;
    }

    /**
     * Set the password in clear text and store it as a SHA-1 hash
     */
    @Override
    public void set(String passwordClearText) {
        super.set(passwordClearText); //check validity
        data = StringHelper.computeSha1OfString(passwordClearText); //encode assignment
    }

    /**
     * Set a already encoded SHA-1 password
     * 
     * @param passwordSha1
     */
    public void setSha1Encoded(String passwordSha1) {
        data = passwordSha1;
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
