package org.obliquid.datatype;

import java.math.BigDecimal;

import org.obliquid.helpers.BdHelper;

/**
 * Hold and validate a VAT percentage, computeVatPercent(fromCountry, toCountry) helps to find a
 * percentage. Is implemented only for Italy, and only for my type of business (food, books have
 * different percentages, but I don't need them).
 * 
 * @author stivlo
 */
public class VatPercent extends DataType {

    private static final long serialVersionUID = 1L;

    /**
     * Without specifiying the country any percentage between 0 (included) and 100 (excluded) will
     * be accepted.
     */
    @Override
    public boolean isValid(String data) {
        if (data == null) {
            return false;
        }
        try {
            BigDecimal percentage = new BigDecimal(data);
            if (BdHelper.compare(percentage, 100) >= 0 || BdHelper.compare(percentage, 0) < 0) {
                return false;
            }
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    /**
     * Compute the VAT percentage to be applied
     * 
     * @param invoiceFromCountry
     *            the country of the supplier
     * @param invoiceToCountry
     *            the country of the customer
     * @return vat percentage
     */
    public static int computeVatPercent(final String invoiceFromCountry, final String invoiceToCountry) {
        if (invoiceFromCountry.equals("IT")) {
            if (invoiceToCountry.equals("IT")) {
                return 20;
            }
            return 0;
        }
        throw new UnsupportedOperationException("getVatPercent() implemented only for invoiceFromCounty='IT'");
    }

}
