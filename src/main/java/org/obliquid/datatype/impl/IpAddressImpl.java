package org.obliquid.datatype.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;

import org.obliquid.datatype.IpAddress;
import org.obliquid.datatype.strategy.StringStrategy;

/**
 * Hold and validate an IP address.
 * 
 * @author stivlo
 * 
 */
public class IpAddressImpl implements IpAddress {

        /**
         * Universal serial identifier.
         */
        private static final long serialVersionUID = 1L;

        /** Basic strategy for Strings. */
        private StringStrategy stringStrategy = new StringStrategy();

        @Override
        public final boolean isValid(final String ipAddressString) {
                if (ipAddressString == null || ipAddressString.length() == 0) {
                        return false;
                }
                try {
                        InetAddress.getByName(ipAddressString);
                } catch (UnknownHostException e) {
                        return false;
                }
                return true;
        }

        @Override
        public final boolean isTheStringValid(final String ipAddressString) {
                return isValid(ipAddressString);
        }

        @Override
        public final String formatData(final Locale locale) throws IllegalStateException {
                return stringStrategy.formatData(locale);
        }

        @Override
        public final String getData() throws IllegalStateException {
                return stringStrategy.getData();
        }

        @Override
        public final void setData(final String theData) throws IllegalArgumentException {
                if (!isValid(theData)) {
                        throw new IllegalArgumentException();
                }
                stringStrategy.setData(theData);
        }

        @Override
        public final void setDataFromString(final String theData) throws IllegalArgumentException {
                setData(theData);
        }

        @Override
        public final boolean isAssigned() {
                return stringStrategy.isAssigned();
        }

}
