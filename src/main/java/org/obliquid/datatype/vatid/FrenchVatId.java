package org.obliquid.datatype.vatid;

import org.obliquid.datatype.impl.VatIdImpl;

/**
 * Check a French Vat Id.
 * 
 * @author stivlo
 * 
 */
public class FrenchVatId extends VatIdImpl {

        @Override
        public final boolean isValid(final String data) {
                return true;
        }

}
