package org.obliquid.datatype;

import org.junit.Test;

public class CountryCodeShould {

    @Test
    public void beValidForIt() {
        CountryCode code = new CountryCode();
        code.isValid("IT");
    }

}
