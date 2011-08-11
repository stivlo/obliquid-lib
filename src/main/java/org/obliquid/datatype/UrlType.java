package org.obliquid.datatype;

import java.util.Locale;

import org.apache.commons.validator.UrlValidator;
import org.obliquid.datatype.deprecated.DataTypeClass;

/**
 * Hold and validate a URL.
 * 
 * @author stivlo
 */
public class UrlType extends DataTypeClass {

        /**
         * Universal serial identifier.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public final boolean isValid(final String urlString) {
                UrlValidator urlValidator = new UrlValidator();
                return urlValidator.isValid(urlString);
        }

        @Override
        public final String getFormattedString(final Locale locale)
                        throws IllegalStateException {
                return getData();
        }

}
