package org.obliquid.datatype.companytaxid;

import org.obliquid.datatype.DataType;
import org.obliquid.datatype.vatid.VatId;

/**
 * Hold and validate a Spanish Company Tax Id (NIF).
 * 
 * @author stivlo
 */
public class SpanishCompanyTaxId extends CompanyTaxId {

    private static final long serialVersionUID = 1L;

    private final DataType vatId;

    protected SpanishCompanyTaxId() {
        vatId = VatId.createInstance("ES");
    }

    @Override
    public boolean isValid(String taxIdString) {
        return vatId.isValid(taxIdString);
    }

}
