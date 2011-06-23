package org.obliquid.datatype;

/**
 * Hold and validate an Italian postcode (must be 5 digits)
 * 
 * @author stivlo
 * 
 */
public class ItalianPostCode extends DataType {

    /**
     * Universal Serial Identifier
     */
    private static final long serialVersionUID = 1L;

    @Override
    public boolean isValid(String data) {
        if (data == null || data.length() != 5) {
            return false;
        }
        for (int i = 0; i < 5; i++) {
            if (!Character.isDigit(data.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
