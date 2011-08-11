package org.obliquid.datatype.companytaxid;

import java.util.Locale;

import org.obliquid.datatype.deprecated.DataTypeClass;
import org.obliquid.datatype.vatid.VatId;

/**
 * Hold and validate a Spanish Company Tax Id (NIF).
 * 
 * @author stivlo
 */
public class SpanishCompanyTaxId extends CompanyTaxId {

        /**
         * Universal Serial Identifier.
         */
        private static final long serialVersionUID = 1L;

        private final DataTypeClass vatId;

        protected SpanishCompanyTaxId() {
                vatId = VatId.createInstance("ES");
        }

        @Override
        public boolean isValid(String taxIdString) {
                return vatId.isValid(taxIdString);
        }

        @Override
        public String getFormattedString(Locale locale)
                        throws IllegalStateException {
                // TODO Auto-generated method stub
                return null;
        }

}
