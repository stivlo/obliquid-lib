package org.obliquid.datatype.companytaxid;

import org.obliquid.datatype.TaxId;
import org.obliquid.datatype.impl.CompanyTaxIdImpl;
import org.obliquid.datatype.impl.VatIdImpl;

/**
 * Hold and validate a Spanish Company Tax Id (NIF).
 * 
 * @author stivlo
 */
public class SpanishCompanyTaxId extends CompanyTaxIdImpl {

        @Override
        public final boolean isValid(final String taxIdString) {
                TaxId vatId = VatIdImpl.createInstance("ES");
                return vatId.isValid(taxIdString);
        }

}
