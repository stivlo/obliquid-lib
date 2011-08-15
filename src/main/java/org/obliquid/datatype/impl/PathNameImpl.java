package org.obliquid.datatype.impl;

import java.util.Locale;

import org.obliquid.datatype.PathName;
import org.obliquid.datatype.strategy.StringStrategy;

/**
 * Hold and validate a relative path according to my rules (I admit only lower
 * case and upper case letters, numbers, hyphen, underscore, the dot and Unix
 * path separator.
 * 
 * @author stivlo
 * 
 */
public class PathNameImpl implements PathName {

        /**
         * External String Strategy implementation. (Strategy Pattern)
         */
        private StringStrategy stringStrategy = new StringStrategy();

        @Override
        public final boolean isValid(final String data) {
                char aChar, lastChar = 0;
                if (data == null) {
                        return false;
                }
                for (int i = 0; i < data.length(); i++) {
                        aChar = data.charAt(i);
                        if (lastChar == aChar && aChar == '/') {
                                return false; //two consecutive path separators are not allowed
                        }
                        lastChar = aChar;
                        if ((aChar >= 'a' && aChar <= 'z') || (aChar >= 'A' && aChar <= 'Z')
                                        || (aChar >= '0' && aChar <= '9') || aChar == '_' || aChar == '-'
                                        || aChar == '/' || aChar == '.') {
                                continue;
                        }
                        return false;
                }
                return true;
        }

        @Override
        public final boolean isTheStringValid(final String theData) {
                return isValid(theData);
        }

        @Override
        public final String formatData(final Locale locale) throws IllegalStateException {
                return getData();
        }

        @Override
        public final String getData() throws IllegalStateException {
                return stringStrategy.getData();
        }

        @Override
        public final void setDataFromString(final String theData) throws IllegalArgumentException {
                // TODO Auto-generated method stub

        }

        @Override
        public final void setData(final String theData) throws IllegalArgumentException {
                // TODO Auto-generated method stub

        }

}
