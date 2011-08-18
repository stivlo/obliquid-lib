package org.obliquid.datatype;

/**
 * Hold and validate a VAT percentage.
 * 
 * @author stivlo
 * 
 */
public interface VatPercent extends DataType<Integer>, DataTypeValidator<Integer> {

        /**
         * Compute the VAT percentage to be applied.
         * 
         * @param invoiceFromCountry
         *                the country of the supplier
         * @param invoiceToCountry
         *                the country of the customer
         * @return vat percentage
         */
        int computeVatPercent(String invoiceFromCountry, String invoiceToCountry);

}
