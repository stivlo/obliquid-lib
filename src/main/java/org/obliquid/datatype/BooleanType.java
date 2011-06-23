package org.obliquid.datatype;

/**
 * Represents a boolean value, raw as Y/N
 * 
 * @author stivlo
 */
public class BooleanType extends DataType {

    private static final long serialVersionUID = 1L;

    /**
     * Check if the value is valid without setting it
     * 
     * @param booleanYN
     *            should be either Y or N uppercase
     */
    @Override
    public boolean isValid(String booleanYN) {
        return (booleanYN.equals("Y") || booleanYN.equals("N"));
    }

    /**
     * Check if the value is valid without setting it
     * 
     * @param booleanYN
     *            should be either Y or N uppercase
     */
    public boolean isValid(Character booleanYN) {
        return isValid(booleanYN.toString());
    }

    /**
     * Return the raw value converted as boolean
     * 
     * @return true if the value is Y, false otherwise
     */
    public boolean getAsBoolean() {
        return data.equals("Y");
    }

    /**
     * Set the raw value from a boolean
     * 
     * @param booleanValue
     *            a boolean value
     */
    public void set(boolean booleanValue) {
        if (booleanValue) {
            set("Y");
        } else {
            set("N");
        }
    }

    /**
     * Set the raw value from a Character
     * 
     * @param booleanYN
     *            should be either 'Y' or 'N' uppercase
     * @throws IllegalArgumentException
     *             if is not 'Y' or 'N'
     */
    public void set(Character booleanYN) {
        set(booleanYN.toString());
    }

}
