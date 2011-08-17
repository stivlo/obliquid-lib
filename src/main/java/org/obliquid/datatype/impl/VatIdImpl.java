package org.obliquid.datatype.impl;

import java.util.Locale;

import org.obliquid.datatype.TaxId;
import org.obliquid.datatype.strategy.StringStrategy;
import org.obliquid.datatype.vatid.FrenchVatId;
import org.obliquid.datatype.vatid.GermanVatId;
import org.obliquid.datatype.vatid.ItalianVatId;
import org.obliquid.datatype.vatid.SpanishVatId;

/**
 * Hold and validate a EU VAT Id. For a web service allowing proper
 * verification: http://ec.europa.eu/taxation_customs/vies/checkVatService.wsdl
 * 
 * @author stivlo
 */
public abstract class VatIdImpl implements TaxId {

        /** String store algorithm (Strategy Pattern). */
        private StringStrategy stringStrategy = new StringStrategy();

        /**
         * Testing the constructor.
         * 
         * @param countryCode
         *                two letters country code.
         * @return a VATId class for the country.
         */
        public static final VatIdImpl createInstance(final String countryCode) {
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

        @Override
        public final boolean isTheStringValid(final String vatId) {
                return isValid(vatId);
        }

        @Override
        public final String getData() {
                return stringStrategy.getData();
        }

        @Override
        public final String formatData(final Locale locale) {
                return stringStrategy.formatData(locale);
        }

        @Override
        public final void setData(final String vatId) throws IllegalArgumentException {
                if (!isValid(vatId)) {
                        throw new IllegalArgumentException("The VatId '" + vatId + "' isn't valid");
                }
                stringStrategy.setData(vatId);
        }

        @Override
        public final void setDataFromString(final String vatId) throws IllegalArgumentException {
                setData(vatId);
        }

}
