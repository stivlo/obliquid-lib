package org.obliquid.datatype;

import org.apache.commons.validator.EmailValidator;

/**
 * Hold and validate a comma separated list of email addresses (spaces allowed)
 * 
 * @author stivlo
 */
public class EmailList extends DataType {

    private static final long serialVersionUID = 1L;

    public EmailList() {
        super();
    }

    public EmailList(String anEmail) throws IllegalArgumentException {
        set(anEmail);
    }

    @Override
    public boolean isValid(String data) {
        String[] emails = data.split(",");
        for (String email : emails) {
            if (!EmailValidator.getInstance().isValid(email.trim())) {
                return false;
            }
        }
        return true;
    }

}
