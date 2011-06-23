package org.obliquid.datatype;

import java.io.Serializable;

/**
 * Abstract base class for scalar data types. set()/get() the data safely and check if isValid()
 * 
 * @author stivlo
 */
public abstract class DataType implements Serializable {

    /**
     * Universal version identifier
     */
    private static final long serialVersionUID = 1L;

    /** Our data store */
    protected String data = null;

    protected String message = "";

    /**
     * Return the data formatted for displaying
     * 
     * @return the data as a string
     * @throws IllegalStateException
     *             if the data is not in a valid state (null)
     */
    public String getFormattedString() throws IllegalStateException {
        if (data == null) {
            throw new IllegalStateException();
        }
        return data;
    }

    /**
     * Return the raw data as a String
     * 
     * @return the data as a string
     * @throws IllegalStateException
     *             if the data is not in a valid state (null)
     */
    public String getRawString() throws IllegalStateException {
        if (data == null) {
            throw new IllegalStateException();
        }
        return data;
    }

    /**
     * Set the data, checking if is valid.
     * 
     * @param data
     *            the data as a String
     * @throws IllegalArgumentException
     *             if the argument is not valid, in that case data is set to null which is an
     *             illegal state (so the getter will throw a IllegalStateException
     */
    public void set(String data) throws IllegalArgumentException {
        if (!isValid(data)) {
            throw new IllegalArgumentException("\n        " + getClass().getName() + " '" + data
                    + "' is not valid\n" + "        message: " + message);
        }
        this.data = data;
    }

    /**
     * Check if the data is valid without assigning it
     * 
     * @param data
     *            the data as a String
     * @return true if the data is valid, false otherwise
     */
    public abstract boolean isValid(String data);

    /**
     * Check whether a value has been set without throwing exceptions
     * 
     * @return true if a value has been assigned
     */
    public boolean isAssigned() {
        return !(data == null);
    }

    /**
     * Return a message about the last validation
     * 
     * @return a message or the empty String
     */
    public String getMessage() {
        return message;
    }

    /**
     * Return a raw String representation of the object. When data is null returns the empty String.
     */
    @Override
    public String toString() {
        if (data == null) {
            return "";
        }
        return data;
    }

}
