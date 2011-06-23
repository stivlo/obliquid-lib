package org.obliquid.datatype;

/**
 * Hold and validate ISO language codes, only IT/EN for now
 * 
 * @author stivlo
 * 
 */
public class LanguageType extends DataType {

    private static final long serialVersionUID = 1L;

    @Override
    public boolean isValid(String languageString) {
        return languageString.equals("IT") || languageString.equals("EN");
    }

}
