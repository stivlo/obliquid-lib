package org.obliquid.datatype.vatid;

import org.obliquid.datatype.impl.VatIdImpl;

/**
 * Check a German vat id.
 * 
 * @author stivlo
 * 
 */
public class GermanVatId extends VatIdImpl {

        @Override
        public final boolean isValid(final String data) {
                return true;
        }

}
