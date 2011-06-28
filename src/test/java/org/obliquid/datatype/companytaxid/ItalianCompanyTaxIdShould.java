package org.obliquid.datatype.companytaxid;

import static org.junit.Assert.*;

import org.junit.Test;

public class ItalianCompanyTaxIdShould {

    @Test
    public void beValidForAValidTaxId() {
        ItalianCompanyTaxId taxId = new ItalianCompanyTaxId();
        assertTrue(taxId.isValid("RSSMRA85T10A562S"));
    }

    @Test
    public void beValidForAValidVatId() {
        ItalianCompanyTaxId taxId = new ItalianCompanyTaxId();
        assertTrue(taxId.isValid("IT-01032450072"));
    }

    @Test
    public void notBeValidForAnotherString() {
        ItalianCompanyTaxId taxId = new ItalianCompanyTaxId();
        assertFalse(taxId.isValid("AAAF"));
    }

    @Test
    public void notBeValidForNull() {
        ItalianCompanyTaxId taxId = new ItalianCompanyTaxId();
        assertFalse(taxId.isValid(null));
    }

    @Test
    public void notBeValidForTaxIdWithWrongCheckDigit() {
        ItalianCompanyTaxId taxId = new ItalianCompanyTaxId();
        assertFalse(taxId.isValid("RSSMRA85T10A512S"));
    }

    @Test
    public void notBeValidForTaxIdWithInvalidCharacters() {
        ItalianCompanyTaxId taxId = new ItalianCompanyTaxId();
        assertFalse(taxId.isValid("RSSMRA8.T10A512S"));
    }

}
