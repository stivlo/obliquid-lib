package org.obliquid.datatype.personaltaxid;

/**
 * Hold and validate an Italian Personal Tax Id
 * 
 * @author stivlo
 */
public class ItalianPersonalTaxId extends PersonalTaxId {

    private static final long serialVersionUID = 1L;

    private static final int expectedLength = 16;

    protected ItalianPersonalTaxId() {
        //use the createInstance() method in the parent class
    }

    /**
     * Check Italian Tax Id http://www.icosaedro.it/cf-pi/index.html
     */
    @Override
    public boolean isValid(String taxId) {
        message = "";
        if (hasInvalidLength(taxId) || hasInvalidCharacter(taxId)) {
            return false;
        }
        if (computeCheckDigit(taxId) != taxId.charAt(15)) {
            message = "Wrong check digit";
            return false;
        }
        return true;
    }

    /**
     * Compute the check digit
     * 
     * @param taxId
     * @return
     */
    private char computeCheckDigit(String taxId) {
        int setdisp[] = { 1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16, 10, 22,
                25, 24, 23 };
        int charWeight = 0, aChar;
        for (int i = 1; i <= 13; i += 2) {
            aChar = taxId.charAt(i);
            if (aChar >= '0' && aChar <= '9') {
                charWeight = charWeight + aChar - '0';
            } else {
                charWeight = charWeight + aChar - 'A';
            }
        }
        for (int i = 0; i <= 14; i += 2) {
            aChar = taxId.charAt(i);
            if (aChar >= '0' && aChar <= '9') {
                aChar = aChar - '0' + 'A';
            }
            charWeight = charWeight + setdisp[aChar - 'A'];
        }
        return (char) (charWeight % 26 + 'A');
    }

    /**
     * Check for invalid length
     * 
     * @param taxId
     * @return true if the String has an invalid length
     */
    private boolean hasInvalidLength(String taxId) {
        if (taxId.length() != expectedLength) {
            message = "Invalid length";
            return true;
        }
        return false;
    }

    /**
     * Check for invalid characters
     * 
     * @param taxId
     * @return true if there are invalid characters, false otherwise
     */
    private boolean hasInvalidCharacter(String taxId) {
        char aChar;
        for (int i = 0; i < expectedLength; i++) {
            aChar = taxId.charAt(i);
            if (!(aChar >= '0' && aChar <= '9' || aChar >= 'A' && aChar <= 'Z')) {
                message = "Invalid character";
                return true;
            }
        }
        return false;
    }

}
