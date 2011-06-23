package org.obliquid.helpers;

import static org.junit.Assert.*;

import org.junit.Test;

public class SqlHelperShould {

    @Test
    public void extractFieldName() {
        assertEquals("id_hosting", SqlHelper.extractFieldName("id_hosting"));
        assertEquals("h.address", SqlHelper.extractFieldName("h.address"));
        assertEquals("how_many", SqlHelper.extractFieldName("COUNT(*) AS how_many"));
    }

    @Test
    public void buildWhereAndOrderByForSdb() {
        assertEquals(" WHERE id>='' ORDER BY id", SqlHelper.buildWhereAndOrderByForSdb("id"));
        assertEquals(" WHERE id>='' ORDER BY id DESC", SqlHelper.buildWhereAndOrderByForSdb("id DESC"));
    }

}
