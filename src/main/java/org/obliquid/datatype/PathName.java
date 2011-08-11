package org.obliquid.datatype;

import java.util.Locale;

import org.obliquid.datatype.deprecated.DataTypeClass;

/**
 * Hold and validate a relative path according to my rules (I admit only lower
 * case and upper case letters, numbers, dash, underscore, the dot and unix path
 * separator.
 * 
 * @author stivlo
 * 
 */
public class PathName extends DataTypeClass {

        private static final long serialVersionUID = 1L;

        @Override
        public boolean isValid(String data) {
                char aChar;
                for (int i = 0; i < data.length(); i++) {
                        aChar = data.charAt(i);
                        if ((aChar >= 'a' && aChar <= 'z')
                                        || (aChar >= 'A' && aChar <= 'Z')
                                        || (aChar >= '0' && aChar <= '9')
                                        || aChar == '_' || aChar == '-'
                                        || aChar == '/' || aChar == '.') {
                                continue;
                        }
                        return false;
                }
                return true;
        }

        @Override
        public String getFormattedString(final Locale locale)
                        throws IllegalStateException {
                return getData();
        }

}
