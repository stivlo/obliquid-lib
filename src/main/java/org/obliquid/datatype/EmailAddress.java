package org.obliquid.datatype;

import org.apache.commons.validator.EmailValidator;

/**
 * Hold and validate an email address
 * 
 * @author stivlo
 */
public class EmailAddress extends DataType {

    private static final long serialVersionUID = 1L;

    public EmailAddress() {
        super();
    }

    public EmailAddress(String anEmail) throws IllegalArgumentException {
        set(anEmail);
    }

    @Override
    public boolean isValid(String anEmail) {
        return EmailValidator.getInstance().isValid(anEmail);
    }

}
