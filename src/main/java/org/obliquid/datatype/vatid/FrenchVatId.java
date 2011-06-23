package org.obliquid.datatype.vatid;

public class FrenchVatId extends VatId {

    private static final long serialVersionUID = 1L;

    @Override
    public boolean isValid(String data) {
        return true;
    }

}
