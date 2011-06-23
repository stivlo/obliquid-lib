package org.obliquid.datatype;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Hold and validate a two letter ISO Country code
 * 
 * @author stivlo
 * 
 */
public class CountryCode extends DataType {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("serial")
    private static final Map<String, String> countries = Collections
            .unmodifiableMap(new HashMap<String, String>() {
                {
                    put("AU", "Australia");
                    put("ES", "Spain");
                    put("HK", "Hong Kong");
                    put("IT", "Italy");
                    put("TH", "Thailand");
                    put("PH", "Philippines");
                    put("UK", "United Kingdom");
                }
            });

    @Override
    public boolean isValid(String data) {
        if (data == null) {
            return false;
        }
        if (data.length() != 2) {
            return false;
        }
        return true;
    }

    public String getCountry() {
        return countries.get(data);
    }

}
