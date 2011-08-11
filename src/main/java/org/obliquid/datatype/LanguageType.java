package org.obliquid.datatype;

import java.util.Locale;

import org.obliquid.datatype.deprecated.DataTypeClass;

/**
 * Hold and validate ISO language codes, only IT/EN for now
 * 
 * @author stivlo
 * 
 */
public class LanguageType extends DataTypeClass {

        private static final long serialVersionUID = 1L;

        @Override
        public boolean isValid(String languageString) {
                return languageString.equals("IT")
                                || languageString.equals("EN");
        }

        @Override
        public String getFormattedString(Locale locale)
                        throws IllegalStateException {
                // TODO Auto-generated method stub
                return null;
        }

}
