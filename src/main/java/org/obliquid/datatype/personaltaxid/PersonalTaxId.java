package org.obliquid.datatype.personaltaxid;

import org.obliquid.datatype.DataType;

/**
 * Base class for PersonalTaxIds
 * 
 * @author stivlo
 */
public abstract class PersonalTaxId extends DataType {

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
    public static PersonalTaxId createInstance(String countryCode) {
        if (countryCode.equals("IT")) {
            return new ItalianPersonalTaxId();
        } else if (countryCode.equals("FR")) {
            return new FrenchPersonalTaxId();
        } else if (countryCode.equals("RO")) {
            return new ItalianPersonalTaxId();
        }
        throw new IllegalArgumentException("PersonalTaxId doesn't support the country " + countryCode
                + " yet");
    }

}
