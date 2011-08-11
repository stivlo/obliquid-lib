package org.obliquid.datatype;

/**
 * DataType validator. (Strategy Pattern).
 * 
 * @author stivlo
 * 
 * @param <T>
 *                the data type held
 */
public interface DataTypeValidator<T> {

        /**
         * Check if the data is valid without assigning it.
         * 
         * @param theData
         *                the data to be checked
         * @return true if the data is valid, false otherwise
         */
        boolean isValid(T theData);

        /**
         * Check if the String representation of the data is valid without
         * assigning it.
         * 
         * @param theData
         *                the data to be checked
         * @return true if the data is valid, false otherwise
         */
        boolean isTheStringValid(String theData);

}
