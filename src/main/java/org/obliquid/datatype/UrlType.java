package org.obliquid.datatype;

import org.apache.commons.validator.UrlValidator;

/**
 * Hold and validate a URL
 * 
 * @author stivlo
 */
public class UrlType extends DataType {

    private static final long serialVersionUID = 1L;

    @Override
    public boolean isValid(String urlString) {
        UrlValidator urlValidator = new UrlValidator();
        return urlValidator.isValid(urlString);
    }

}
