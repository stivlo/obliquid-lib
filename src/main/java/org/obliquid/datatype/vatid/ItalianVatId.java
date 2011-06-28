package org.obliquid.datatype.vatid;

/**
 * Hold and verify an Italian VAT Id. Formal verification as explained at
 * http://www.icosaedro.it/cf-pi/index.html
 * 
 * @author stivlo
 */
public class ItalianVatId extends VatId {

    /**
     * Universal version identifier
     */
    private static final long serialVersionUID = 1L;

    protected ItalianVatId() {
        //disable new() from outside this package
    }

    /**
     * For the moment only validates IT VAT-Id
     */
    @Override
    public boolean isValid(String vatIdString) {
        if (vatIdString == null || vatIdString.length() < 4) {
            return false;
        }
        String start = vatIdString.substring(0, 3);
        if (!start.equals("IT-")) {
            message = "An Italian Vat Id should start with 'IT-', instead is starting with '" + start + "'";
            return false;
        }
        return checkItalianVatId(vatIdString);
    }

    /**
     * Check for invalid length
     * 
     * @param vatId
     * @return true if the String has an invalid length
     */
    private boolean hasInvalidLengthItalian(String vatId) {
        if (vatId.length() != 14) {
            message = "Invalid length";
            return true;
        }
        return false;
    }

    /**
     * Check for invalid characters
     * 
     * @param vatId
     * @return true if there are invalid characters, false otherwise
     */
    private boolean hasInvalidCharacterItalian(String vatId) {
        char aChar;
        if (!vatId.substring(0, 3).equals("IT-")) {
            message = "Prefix must be IT-";
            return true;
        }
        vatId = vatId.substring(3);
        for (int i = 0; i < 11; i++) {
            aChar = vatId.charAt(i);
            if (aChar < '0' || aChar > '9') {
                message = "Only digits allowed";
                return true;
            }
        }
        return false;
    }

    private boolean checkItalianVatId(String vatIdString) {
        int i, aChar, s;
        message = "";
        if (hasInvalidLengthItalian(vatIdString) || hasInvalidCharacterItalian(vatIdString)) {
            return false;
        }
        String vatIdNum = vatIdString.substring(3); //numeric part of the vatId
        s = 0;
        for (i = 0; i <= 9; i += 2) {
            s += vatIdNum.charAt(i) - '0';
        }
        for (i = 1; i <= 9; i += 2) {
            aChar = 2 * (vatIdNum.charAt(i) - '0');
            if (aChar > 9) {
                aChar = aChar - 9;
            }
            s += aChar;
        }
        if ((10 - s % 10) % 10 != vatIdNum.charAt(10) - '0') {
            message = "Wrong check digit";
            return false;
        }
        return true;
    }

}
