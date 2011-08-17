package org.obliquid.datatype.impl;

import java.util.Locale;

import org.obliquid.datatype.TaxId;
import org.obliquid.datatype.personaltaxid.FrenchPersonalTaxId;
import org.obliquid.datatype.personaltaxid.ItalianPersonalTaxId;
import org.obliquid.datatype.strategy.StringStrategy;

/**
 * Base class for PersonalTaxIds.
 * 
 * @author stivlo
 */
public abstract class PersonalTaxIdImpl implements TaxId {

        /** StringStrategy algorithm. */
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
        public static final TaxId createInstance(final String countryCode) throws IllegalArgumentException {
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

        @Override
        public final boolean isTheStringValid(final String personalTaxId) {
                return isValid(personalTaxId);
        }

        @Override
        public final void setData(final String personalTaxId) throws IllegalStateException {
                if (!isValid(personalTaxId)) {
                        throw new IllegalStateException("The personalTaxId '" + personalTaxId
                                        + "' isn't valid");
                }
                stringStrategy.setData(personalTaxId);
        }

        @Override
        public final void setDataFromString(final String personalTaxId) throws IllegalStateException {
                setData(personalTaxId);
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
