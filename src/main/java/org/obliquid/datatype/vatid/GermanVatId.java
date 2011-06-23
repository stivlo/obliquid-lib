package org.obliquid.datatype.vatid;

public class GermanVatId extends VatId {

    private static final long serialVersionUID = 1L;

    @Override
    public boolean isValid(String data) {
        return true;
    }

}
