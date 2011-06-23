package org.obliquid.db;

import static org.junit.Assert.*;

import java.sql.Types;

import org.junit.Test;

public class DbNullShould {

    @Test
    public void preserveTheTypeSetInTheConstructor() {
        DbNull dbNull = new DbNull(Types.BOOLEAN);
        assertEquals(Types.BOOLEAN, dbNull.getFieldType());
    }

    @Test
    public void preserveTheTypeSetWithSetter() {
        DbNull dbNull = new DbNull();
        dbNull.setFieldType(Types.DATE);
        assertEquals(Types.DATE, dbNull.getFieldType());
    }

}
