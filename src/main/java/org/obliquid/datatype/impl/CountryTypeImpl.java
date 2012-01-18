package org.obliquid.datatype.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.obliquid.datatype.CountryType;
import org.obliquid.datatype.strategy.StringStrategy;

/**
 * Hold and validate a two letter ISO Country code.
 * 
 * @author stivlo
 * 
 */
public class CountryTypeImpl implements CountryType {

        /**
         * Universal serial identifier.
         */
        private static final long serialVersionUID = 1L;

        /**
         * Basic String Strategy.
         */
        private StringStrategy stringStrategy = new StringStrategy();

        /**
         * List of countries that I care about.
         */
        @SuppressWarnings("serial")
        private static final Map<String, Map<String, String>> COUNTRIES = Collections
                        .unmodifiableMap(new HashMap<String, Map<String, String>>() {
                                {
                                        put("en", Collections.unmodifiableMap(new HashMap<String, String>() {
                                                {
                                                        put("AU", "Australia");
                                                        put("CH", "Switzerland");
                                                        put("CN", "China");
                                                        put("DE", "Germany");
                                                        put("ES", "Spain");
                                                        put("FR", "France");
                                                        put("GB", "Great Britain");
                                                        put("HK", "Hong Kong");
                                                        put("ID", "Indonesia");
                                                        put("IN", "India");
                                                        put("IT", "Italy");
                                                        put("MY", "Malaysia");
                                                        put("NL", "Netherlands");
                                                        put("TH", "Thailand");
                                                        put("PH", "Philippines");
                                                        put("RO", "Romania");
                                                        put("UK", "United Kingdom");
                                                        put("US", "United States");
                                                }
                                        }));
                                        put("it", Collections.unmodifiableMap(new HashMap<String, String>() {
                                                {
                                                        put("AU", "Australia");
                                                        put("CH", "Svizzera");
                                                        put("CN", "Cina");
                                                        put("DE", "Germania");
                                                        put("ES", "Spagna");
                                                        put("FR", "Francia");
                                                        put("GB", "Gran Bretagna");
                                                        put("HK", "Hong Kong");
                                                        put("ID", "Indonesia");
                                                        put("IN", "India");
                                                        put("IT", "Italia");
                                                        put("MY", "Malesia");
                                                        put("NL", "Paesi Bassi");
                                                        put("TH", "Tailandia");
                                                        put("PH", "Filippine");
                                                        put("RO", "Romania");
                                                        put("UK", "Regno Unito");
                                                        put("US", "Stati Uniti");
                                                }
                                        }));
                                }
                        });

        /**
         * Check if it's a valid two letter ISO Country abbreviation.
         * 
         * @param abbrev
         *                a two letter Country abbreviation
         * @return a two letter Country abbreviation, such as "IT" for Italy.
         */
        @Override
        public final boolean isValid(final String abbrev) {
                if (abbrev == null) {
                        return false;
                }
                return COUNTRIES.get("en").containsKey(abbrev);
        }

        /**
         * Return the full name for the Country, in the language specified. Only
         * English and Italian are supported and English is the default.
         * 
         * @param locale
         *                used to specify the language to use
         * @return Country full name
         * @throws IllegalStateException
         *                 if the Country wasn't set
         */
        @Override
        public final String formatData(final Locale locale) throws IllegalStateException {
                String language = locale.getLanguage();
                if (!language.equals("en") && !language.equals("it")) {
                        language = "en"; //English is the default
                }
                return COUNTRIES.get(language).get(stringStrategy.getData());
        }

        /**
         * Return the two letter ISO Country abbreviation.
         * 
         * @return two letter Country abbreviation.
         * @throws IllegalStateException
         *                 if the Country wasn't set.
         */
        @Override
        public final String getData() throws IllegalStateException {
                return stringStrategy.getData();
        }

        /**
         * Set the Country as a two letter ISO Country abbreviation.
         * 
         * @param abbrev
         *                two letter Country abbreviation
         * @throws IllegalArgumentException
         *                 if the Country is not in my list of accepted
         *                 Countries.
         */
        @Override
        public final void setData(final String abbrev) throws IllegalArgumentException {
                if (!isValid(abbrev)) {
                        throw new IllegalArgumentException("Country code '" + abbrev + "' isn't valid");
                }
                stringStrategy.setData(abbrev);
        }

        /**
         * Return the full Country name in the current language.
         * 
         * @return the full Country name such as "Italy" or "Italia" for "IT"
         * @throws IllegalStateException
         *                 when the Country hasn't been set.
         */
        @Override
        public final String toString() throws IllegalStateException {
                return formatData(Locale.getDefault());
        }

        @Override
        public final boolean isTheStringValid(final String theData) {
                return isValid(theData);
        }

        @Override
        public final void setDataFromString(final String theData) throws IllegalArgumentException {
                setData(theData);
        }

        @Override
        public final boolean isAssigned() {
                return stringStrategy.isAssigned();
        }

}
