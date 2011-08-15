package org.obliquid.helpers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Class under test SqlHelper.
 * 
 * @author stivlo
 * 
 */
public class SqlHelperShould {

        /**
         * Test extractFieldName.
         */
        @Test
        public final void extractFieldName() {
                assertEquals("id_hosting", SqlHelper.extractFieldName("id_hosting"));
                assertEquals("h.address", SqlHelper.extractFieldName("h.address"));
                assertEquals("how_many", SqlHelper.extractFieldName("COUNT(*) AS how_many"));
        }

        /**
         * Test buildWhereAndOrderByForSdb().
         */
        @Test
        public final void buildWhereAndOrderByForSdb() {
                assertEquals(" WHERE id>='' ORDER BY id", SqlHelper.buildWhereAndOrderByForSdb("id"));
                assertEquals(" WHERE id>='' ORDER BY id DESC",
                                SqlHelper.buildWhereAndOrderByForSdb("id DESC"));
        }

}
