package org.obliquid.datatype.personaltaxid;

import java.util.Locale;

public class FrenchPersonalTaxId extends PersonalTaxId {

        private static final long serialVersionUID = 1L;

        @Override
        public boolean isValid(String data) {
                return true;
        }

        @Override
        public String getFormattedString(Locale locale) throws IllegalStateException {
                // TODO Auto-generated method stub
                return null;
        }

}
