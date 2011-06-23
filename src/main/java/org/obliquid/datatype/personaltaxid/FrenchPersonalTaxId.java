package org.obliquid.datatype.personaltaxid;

public class FrenchPersonalTaxId extends PersonalTaxId {

    private static final long serialVersionUID = 1L;

    @Override
    public boolean isValid(String data) {
        return true;
    }

}
