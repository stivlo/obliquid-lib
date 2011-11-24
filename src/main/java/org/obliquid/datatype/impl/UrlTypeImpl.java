package org.obliquid.datatype.impl;

import java.util.Locale;

import org.apache.commons.validator.UrlValidator;
import org.obliquid.datatype.UrlType;
import org.obliquid.datatype.strategy.StringStrategy;

/**
 * Hold and validate a URL.
 * 
 * @author stivlo
 */
public class UrlTypeImpl implements UrlType {

        /**
         * Universal serial identifier.
         */
        private static final long serialVersionUID = 1L;

        /**
         * String store algorithm (Strategy Pattern).
         */
        private StringStrategy stringStrategy = new StringStrategy();

        @Override
        public final boolean isValid(final String urlString) {
                UrlValidator urlValidator = new UrlValidator();
                return urlValidator.isValid(urlString);
        }

        @Override
        public final boolean isTheStringValid(final String urlString) {
                return isValid(urlString);
        }

        @Override
        public final String getData() throws IllegalStateException {
                return stringStrategy.getData();
        }

        @Override
        public final String formatData(final Locale locale) throws IllegalStateException {
                return getData();
        }

        @Override
        public final void setDataFromString(final String theData) throws IllegalArgumentException {
                setData(theData);
        }

        @Override
        public final void setData(final String theData) throws IllegalArgumentException {
                if (!isValid(theData)) {
                        throw new IllegalArgumentException("URL '" + theData + "' isn't valid");
                }
                stringStrategy.setData(theData);
        }

        @Override
        public final boolean isAssigned() {
                return stringStrategy.isAssigned();
        }

}
