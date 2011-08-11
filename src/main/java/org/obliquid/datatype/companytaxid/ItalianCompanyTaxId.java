package org.obliquid.datatype.companytaxid;

import java.util.Locale;

import org.obliquid.datatype.deprecated.DataTypeClass;
import org.obliquid.datatype.personaltaxid.PersonalTaxId;
import org.obliquid.datatype.vatid.VatId;

/**
 * Hold and validate an Italian Company Tax Id. Allowed values are a VatId or a
 * PersonalTaxId
 * 
 * @author stivlo
 */
public class ItalianCompanyTaxId extends CompanyTaxId {

        private static final long serialVersionUID = 1L;

        private final DataTypeClass personalTaxId, vatId;

        protected ItalianCompanyTaxId() {
                personalTaxId = PersonalTaxId.createInstance("IT");
                vatId = VatId.createInstance("IT");
        }

        @Override
        public boolean isValid(String taxIdString) {
                return personalTaxId.isValid(taxIdString) || vatId.isValid(taxIdString);
        }

        @Override
        public String getFormattedString(Locale locale) throws IllegalStateException {
                // TODO Auto-generated method stub
                return null;
        }

}
