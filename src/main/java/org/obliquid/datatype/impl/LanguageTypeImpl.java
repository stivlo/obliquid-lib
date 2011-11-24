package org.obliquid.datatype.impl;

import java.util.Locale;

import org.obliquid.datatype.LanguageType;
import org.obliquid.datatype.strategy.StringStrategy;

import com.google.common.collect.ImmutableMap;

/**
 * Hold and validate ISO language codes, only "it", "en" for now.
 * 
 * @author stivlo
 * 
 */
public class LanguageTypeImpl implements LanguageType {

        /**
         * Universal serial identifier.
         */
        private static final long serialVersionUID = 1L;

        /**
         * Supported languages with their translation in the language itself.
         */
        private ImmutableMap<String, String> supported = ImmutableMap.of("en", "English", "it", "Italiano");

        /**
         * String strategy implementation.
         */
        private StringStrategy stringStrategy = new StringStrategy();

        /**
         * Check whether the languageString is one of the supported languages.
         * 
         * @param languageString
         *                the two letters ISO abbreviation.
         * @return true if the languageString is valid
         */
        @Override
        public final boolean isValid(final String languageString) {
                return supported.containsKey(languageString);
        }

        /**
         * Check whether the languageString is one of the supported languages.
         * Alias for isValid().
         * 
         * @param languageString
         *                the two letters ISO abbreviation.
         * @return true if the languageString is valid
         */
        @Override
        public final boolean isTheStringValid(final String languageString) {
                return isValid(languageString);
        }

        /**
         * Convert the languageString in the language name in the language
         * itself. Example: it -> Italiano, en -> English.
         * 
         * @param locale
         *                the locale is ignored because each language name is
         *                shown in the same language.
         * @return the full language name such as "Italiano" or "English"
         * @throws IllegalStateException
         *                 if language wasn't set
         */
        @Override
        public final String formatData(final Locale locale) throws IllegalStateException {
                return supported.get(stringStrategy.getData());
        }

        /**
         * Return the language two chars code.
         * 
         * @return the language code
         * @throws IllegalStateException
         *                 if the language wasn't set
         */
        @Override
        public final String getData() throws IllegalStateException {
                return stringStrategy.getData();
        }

        /**
         * Set the two chars language code.
         * 
         * @param languageString
         *                the two chars ISO language code.
         * @throws IllegalArgumentException
         *                 if the language isn't one of the accepted ones.
         */
        @Override
        public final void setData(final String languageString) throws IllegalArgumentException {
                if (!isValid(languageString)) {
                        throw new IllegalArgumentException("The language code '" + languageString
                                        + "' isn't valid");
                }
                stringStrategy.setDataFromString(languageString);
        }

        /**
         * Alias for setData.
         * 
         * @param languageString
         *                two letter ISO language code.
         * @throws IllegalArgumentException
         *                 if the language is not supported.
         */
        @Override
        public final void setDataFromString(final String languageString) throws IllegalArgumentException {
                setData(languageString);
        }

        @Override
        public final boolean isAssigned() {
                return stringStrategy.isAssigned();
        }

}
