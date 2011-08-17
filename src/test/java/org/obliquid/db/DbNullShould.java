package org.obliquid.db;

import static org.junit.Assert.assertEquals;

import java.sql.Types;

import org.junit.Test;

/**
 * Class under test: DbNull.
 * 
 * @author stivlo
 * 
 */
public class DbNullShould {

        /**
         * Preserve the type set in the constructor.
         */
        @Test
        public final void preserveTheTypeSetInTheConstructor() {
                DbNull dbNull = new DbNull(Types.BOOLEAN);
                assertEquals(Types.BOOLEAN, dbNull.getFieldType());
        }

        /**
         * Preserve the type set with the setter.
         */
        @Test
        public final void preserveTheTypeSetWithSetter() {
                DbNull dbNull = new DbNull();
                dbNull.setFieldType(Types.DATE);
                assertEquals(Types.DATE, dbNull.getFieldType());
        }

}
