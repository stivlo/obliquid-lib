package org.obliquid.datatype.companytaxid;

import org.obliquid.datatype.deprecated.DataTypeClass;

/**
 * Base class for Company Tax Ids.
 * 
 * @author stivlo
 */
public abstract class CompanyTaxId extends DataTypeClass {

    private static final long serialVersionUID = 1L;

    /**
     * Create an instance of a CompanyTaxId, according to the countryCode.
     * 
     * @param countryCode
     *            two letter country ISO abbreviation
     * @return a newly created CompanyTaxId
     * @throws IllegalArgumentException
     *             if we don't have a class for the specified countryCode
     */
    public static CompanyTaxId createInstance(String countryCode) {
        if (countryCode.equals("IT")) {
            return new ItalianCompanyTaxId();
        } else if (countryCode.equals("ES")) {
            return new SpanishCompanyTaxId();
        }
        throw new IllegalArgumentException("CompanyTaxId doesn't support the country " + countryCode + "yet");
    }

}
