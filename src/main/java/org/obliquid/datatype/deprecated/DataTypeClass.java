package org.obliquid.datatype.deprecated;

import java.io.Serializable;
import java.util.Locale;

/**
 * Abstract base class for scalar data types. set()/get() the data safely and
 * check if isValid()
 * 
 * @deprecated
 * @author stivlo
 */
@Deprecated
public abstract class DataTypeClass implements Serializable {

        /**
         * Universal version identifier.
         */
        private static final long serialVersionUID = 1L;

        /** Our data store. */
        private String data = null;

        /** A message about validation problems to show to the user. */
        private String message = "";

        /**
         * Return the data formatted for displaying.
         * 
         * @param locale
         *                the locale to be used.
         * @return the data as a string
         * @throws IllegalStateException
         *                 if the data is not in a valid state (null)
         */
        public abstract String getFormattedString(Locale locale) throws IllegalStateException;

        /**
         * Return the raw data as a String.
         * 
         * @return the data as a string
         * @throws IllegalStateException
         *                 if the data is not in a valid state (null)
         */
        public final String getData() throws IllegalStateException {
                if (data == null) {
                        throw new IllegalStateException();
                }
                return data;
        }

        /**
         * Set the data, checking if is valid.
         * 
         * @param theData
         *                the data as a String
         * @throws IllegalArgumentException
         *                 if the argument is not valid, in that case data is
         *                 set to null which is an illegal state (so the getter
         *                 will throw a IllegalStateException
         */
        public void set(final String theData) throws IllegalArgumentException {
                if (!isValid(data)) {
                        throw new IllegalArgumentException("\n        " + getClass().getName() + " '" + data
                                        + "' is not valid\n" + "        message: " + message);
                }
                data = theData;
        }

        /**
         * Check if the data is valid without assigning it.
         * 
         * @param theData
         *                the data as a String
         * @return true if the data is valid, false otherwise
         */
        public abstract boolean isValid(String theData);

        /**
         * Check whether a value has been set without throwing exceptions.
         * 
         * @return true if a value has been assigned
         */
        public final boolean isAssigned() {
                return !(data == null);
        }

        /**
         * Return a message about the last validation.
         * 
         * @return a message or the empty String
         */
        public final String getMessage() {
                return message;
        }

        /**
         * Set a message about the last validation.
         * 
         * @param theMessage
         *                the message to set
         */
        public final void setMessage(final String theMessage) {
                message = theMessage;
        }

        /**
         * Return a raw String representation of the object. When data is null
         * returns the empty String.
         * 
         * @return a String representation of the object.
         */
        @Override
        public final String toString() {
                if (data == null) {
                        return "";
                }
                return data;
        }

}
