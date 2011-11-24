package org.obliquid.datatype.companytaxid;

import org.obliquid.datatype.TaxId;

import org.obliquid.datatype.impl.CompanyTaxIdImpl;
import org.obliquid.datatype.impl.PersonalTaxIdImpl;
import org.obliquid.datatype.impl.VatIdImpl;

/**
 * Hold and validate an Italian Company Tax Id. Allowed values are a VatId or a
 * PersonalTaxId.
 * 
 * @author stivlo
 */
public class ItalianCompanyTaxId extends CompanyTaxIdImpl {

        /**
         * Universal serial identifier.
         */
        private static final long serialVersionUID = 1L;

        /** Hold instances of Italian vatId and personalTaxId. */
        private final TaxId vatId, personalTaxId;

        /**
         * Default constructor.
         */
        public ItalianCompanyTaxId() {
                personalTaxId = PersonalTaxIdImpl.createInstance("IT");
                vatId = VatIdImpl.createInstance("IT");
        }

        /**
         * Check if an Italian Company Tax Id is valid, i.e. if is valid as a
         * personal tax Id or as a Vat Id.
         * 
         * @param taxIdString
         *                the tax Id to be checked
         * @return true if valid
         */
        @Override
        public final boolean isValid(final String taxIdString) {
                return personalTaxId.isValid(taxIdString) || vatId.isValid(taxIdString);
        }

}
