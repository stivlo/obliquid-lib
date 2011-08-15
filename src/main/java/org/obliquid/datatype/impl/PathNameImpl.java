package org.obliquid.datatype.impl;

import java.util.Locale;

import org.obliquid.datatype.PathName;

/**
 * Hold and validate a relative path according to my rules (I admit only lower
 * case and upper case letters, numbers, hyphen, underscore, the dot and Unix
 * path separator.
 * 
 * @author stivlo
 * 
 */
public class PathNameImpl implements PathName {

        @Override
        public final boolean isValid(final String data) {
                char aChar;
                for (int i = 0; i < data.length(); i++) {
                        aChar = data.charAt(i);
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
                // TODO Auto-generated method stub
                return false;
        }

        @Override
        public final String formatData(final Locale locale) throws IllegalStateException {
                return getData();
        }

        @Override
        public final String getData() throws IllegalStateException {
                // TODO Auto-generated method stub
                return null;
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
