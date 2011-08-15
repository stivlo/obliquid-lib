package org.obliquid.datatype.vatid;

import org.obliquid.datatype.deprecated.DataTypeClass;

/**
 * Hold and validate a EU VAT Id. For a webservice allowing proper verification:
 * http://ec.europa.eu/taxation_customs/vies/checkVatService.wsdl
 * 
 * @author stivlo
 */
public abstract class VatId extends DataTypeClass {

        private static final long serialVersionUID = 1L;

        /**
         * Testing the constructor.
         * 
         * @param countryCode
         *                two letters country code.
         * @return a VATId class for the country.
         */
        public static final VatId createInstance(final String countryCode) {
                if (countryCode.equals("IT")) {
                        return new ItalianVatId();
                } else if (countryCode.equals("ES")) {
                        return new SpanishVatId();
                } else if (countryCode.equals("FR")) {
                        return new FrenchVatId();
                } else if (countryCode.equals("RO")) {
                        return new ItalianVatId();
                } else if (countryCode.equals("DE")) {
                        return new GermanVatId();
                }
                throw new IllegalArgumentException("VatId doesn't support the country " + countryCode
                                + " yet");
        }

}
