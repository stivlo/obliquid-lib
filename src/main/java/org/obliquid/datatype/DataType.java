package org.obliquid.datatype;

import java.util.Locale;

/**
 * Interface for all DataTypes.
 * 
 * @param <T>
 *                the Class of the object holded
 * @author stivlo
 * 
 */
public interface DataType<T> {

        /**
         * Return the data formatted for displaying.
         * 
         * @param locale
         *                the locale to be used.
         * @return the data as a string
         * @throws IllegalStateException
         *                 if the data is not in a valid state (null)
         */
        String formatData(Locale locale) throws IllegalStateException;

        /**
         * Return the raw data as a String.
         * 
         * @return the data as a string
         * @throws IllegalStateException
         *                 if the data is not in a valid state (null)
         */
        T getData() throws IllegalStateException;

        /**
         * Set the data, checking if is valid.
         * 
         * @param theData
         *                the data as a String
         * @throws IllegalArgumentException
         *                 if the argument is not valid
         */
        void setDataFromString(final String theData) throws IllegalArgumentException;

        /**
         * Set the data, checking if is valid.
         * 
         * @param theData
         *                the data as a String
         * @throws IllegalArgumentException
         *                 if the argument is not valid
         */
        void setData(final T theData) throws IllegalArgumentException;

}
