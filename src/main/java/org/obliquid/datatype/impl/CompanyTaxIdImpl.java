package org.obliquid.datatype.impl;

import java.util.Locale;

import org.obliquid.datatype.TaxId;
import org.obliquid.datatype.companytaxid.ItalianCompanyTaxId;
import org.obliquid.datatype.companytaxid.SpanishCompanyTaxId;
import org.obliquid.datatype.strategy.StringStrategy;

/**
 * Base class for Company Tax Id.
 * 
 * @author stivlo
 */
public abstract class CompanyTaxIdImpl implements TaxId {

        /** String store algorithm (Strategy Pattern). */
        private StringStrategy stringStrategy = new StringStrategy();

        /**
         * Create an instance of a CompanyTaxId, according to the countryCode.
         * 
         * @param countryCode
         *                two letter country ISO abbreviation
         * @return a newly created CompanyTaxId
         * @throws IllegalArgumentException
         *                 if we don't have a class for the specified
         *                 countryCode
         */
        public static final CompanyTaxIdImpl createInstance(final String countryCode)
                        throws IllegalArgumentException {
                if (countryCode.equals("IT")) {
                        return new ItalianCompanyTaxId();
                } else if (countryCode.equals("ES")) {
                        return new SpanishCompanyTaxId();
                }
                throw new IllegalArgumentException("CompanyTaxId doesn't support the country " + countryCode
                                + "yet");
        }

        @Override
        public final void setData(final String companyTaxId) {
                if (!isValid(companyTaxId)) {
                        throw new IllegalArgumentException("Tax Id '" + companyTaxId + "' isn't valid");
                }
                stringStrategy.setData(companyTaxId);
        }

        @Override
        public final void setDataFromString(final String companyTaxId) {
                setData(companyTaxId);
        }

        @Override
        public final boolean isTheStringValid(final String companyTaxId) {
                return isValid(companyTaxId);
        }

        @Override
        public final String getData() {
                return stringStrategy.getData();
        }

        @Override
        public final String formatData(final Locale locale) {
                return stringStrategy.formatData(locale);
        }

}
