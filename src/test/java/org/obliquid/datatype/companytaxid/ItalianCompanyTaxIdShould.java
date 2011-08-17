package org.obliquid.datatype.companytaxid;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

/**
 * Class under test: ItalianCompanyTaxId.
 * 
 * @author stivlo
 * 
 */
public class ItalianCompanyTaxIdShould {

        /**
         * Check with a valid Tax Id.
         */
        @Test
        public final void beValidForAValidTaxId() {
                ItalianCompanyTaxId taxId = new ItalianCompanyTaxId();
                assertTrue(taxId.isValid("RSSMRA85T10A562S"));
        }

        /**
         * Check with a valid Vat Id.
         */
        @Test
        public final void beValidForAValidVatId() {
                ItalianCompanyTaxId taxId = new ItalianCompanyTaxId();
                assertTrue(taxId.isValid("IT-01032450072"));
        }

        /**
         * Check with a wrong String.
         */
        @Test
        public final void notBeValidForAnotherString() {
                ItalianCompanyTaxId taxId = new ItalianCompanyTaxId();
                assertFalse(taxId.isValid("AAAF"));
        }

        /**
         * null isn't valid.
         */
        @Test
        public final void notBeValidForNull() {
                ItalianCompanyTaxId taxId = new ItalianCompanyTaxId();
                assertFalse(taxId.isValid(null));
        }

        /**
         * Check with a Tax Id with a wrong check digit.
         */
        @Test
        public final void notBeValidForTaxIdWithWrongCheckDigit() {
                ItalianCompanyTaxId taxId = new ItalianCompanyTaxId();
                assertFalse(taxId.isValid("RSSMRA85T10A512S"));
        }

        /**
         * Check with a Tax Id with invalid characters.
         */
        @Test
        public final void notBeValidForTaxIdWithInvalidCharacters() {
                ItalianCompanyTaxId taxId = new ItalianCompanyTaxId();
                assertFalse(taxId.isValid("RSSMRA8.T10A512S"));
        }

}
