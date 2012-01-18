package org.obliquid.datatype.impl;

import java.math.BigDecimal;
import java.util.Locale;

import org.obliquid.datatype.VatPercent;
import org.obliquid.datatype.strategy.IntegerStrategy;
import org.obliquid.helpers.BdHelper;

/**
 * Hold and validate a VAT percentage, computeVatPercent(fromCountry, toCountry)
 * helps to find a percentage. Is implemented only for Italy, and only for my
 * type of business (food, books have different percentages, but I don't need
 * them).
 * 
 * @author stivlo
 */
public class VatPercentImpl implements VatPercent {

        /**
         * Universal serial identifier.
         */
        private static final long serialVersionUID = 1L;

        /** Integer Strategy implementation (Strategy Pattern). */
        private IntegerStrategy integerStrategy = new IntegerStrategy();

        /**
         * The maxPercent allowed (excluded).
         */
        private final int maxPercent = 60;

        /**
         * Without specifying the country any percentage between 0 (included)
         * and maxPercent (excluded) will be accepted.
         * 
         * @param data
         *                the data to be checked: a number represented as a
         *                string in the range 0-maxPercent
         * @return true if valid
         */
        @Override
        public final boolean isValid(final Integer data) {
                if (data == null) {
                        return false;
                }
                try {
                        BigDecimal percentage = new BigDecimal(data);
                        if (BdHelper.compare(percentage, maxPercent) >= 0
                                        || BdHelper.compare(percentage, 0) < 0) {
                                return false;
                        }
                } catch (NumberFormatException ex) {
                        return false;
                }
                return true;
        }

        @Override
        public final int computeVatPercent(final String invoiceFromCountry, final String invoiceToCountry) {
                final int italyVat = 21;
                if (invoiceFromCountry.equals("IT")) {
                        if (invoiceToCountry.equals("IT")) {
                                return italyVat;
                        }
                        return 0;
                }
                throw new UnsupportedOperationException(
                                "getVatPercent() implemented only for invoiceFromCounty='IT'");
        }

        /**
         * @param locale
         *                the locale to use
         * @return the value followed by the percent sign.
         * @throws IllegalStateException
         *                 if the value has not been set
         */
        @Override
        public final String formatData(final Locale locale) throws IllegalStateException {
                return getData() + "%";
        }

        @Override
        public final void setData(final Integer theData) throws IllegalArgumentException {
                if (!isValid(theData)) {
                        throw new IllegalArgumentException();
                }
                integerStrategy.setData(theData);
        }

        @Override
        public final void setDataFromString(final String theData) throws IllegalArgumentException {
                if (!isTheStringValid(theData)) {
                        throw new IllegalArgumentException();
                }
                integerStrategy.setData(Integer.parseInt(theData));
        }

        @Override
        public final boolean isTheStringValid(final String theData) {
                try {
                        int data = Integer.parseInt(theData);
                        return isValid(data);
                } catch (NumberFormatException ex) {
                        return false;
                }
        }

        @Override
        public final Integer getData() throws IllegalStateException {
                return integerStrategy.getData();
        }

        @Override
        public final boolean isAssigned() {
                return integerStrategy.isAssigned();
        }

}
