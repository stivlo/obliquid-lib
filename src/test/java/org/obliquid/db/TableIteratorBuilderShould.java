package org.obliquid.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

/**
 * Class under test TableIteratorBuilder.
 * 
 * @author stivlo
 * 
 */
public class TableIteratorBuilderShould {

        /**
         * Return all tables.
         * 
         * @throws SQLException
         *                 in case of problems
         */
        @Test
        @Ignore
        public final void returnSixteenTablesForDb2() throws SQLException {
                final int expectedTables = 16;
                TableIteratorBuilder builder = new TableIteratorBuilder();
                builder.setDb(new MetaDbImpl());
                Iterator<String> tableIterator = builder.tableIterator();
                builder.releaseConnection();
                int count = 0;
                while (tableIterator.hasNext()) {
                        tableIterator.next();
                        count++;
                }
                assertEquals(expectedTables, count);
        }

        /**
         * In the DB obliquid_db1 there are no tables.
         * 
         * @throws SQLException
         *                 in case of problems
         */
        @Test
        @Ignore
        public final void returnZeroTablesForDb1() throws SQLException {
                TableIteratorBuilder builder = new TableIteratorBuilder();
                builder.setDb(new MetaDbImpl());
                Iterator<String> tableIterator = builder.tableIterator("obliquid_db1");
                builder.releaseConnection();
                int count = 0;
                while (tableIterator.hasNext()) {
                        tableIterator.next();
                        count++;
                }
                assertEquals(0, count);
        }

        /**
         * When selecting a wrong DB an exception will be thrown.
         * 
         * @throws SQLException
         *                 should be thrown
         */
        @Test(expected = SQLException.class)
        @Ignore
        public final void throwExceptionForOtherDbs() throws SQLException {
                TableIteratorBuilder builder = new TableIteratorBuilder();
                try {
                        builder.setDb(new MetaDbImpl());
                        builder.tableIterator("wrongdb");
                } finally {
                        builder.releaseConnection(); //safe
                }
        }

        /**
         * Text field types should be recognised.
         * 
         * @throws SQLException
         *                 in case of problems
         */
        @Test
        @Ignore
        public final void dbFieldShouldRecognizeTextTypes() throws SQLException {
                Iterator<String> tableIt = TableIteratorBuilder.tableIteratorWithAutoDb();
                boolean isText;
                List<String> textType = new ImmutableList.Builder<String>().add("varchar").add("text")
                                .add("char").build();
                while (tableIt.hasNext()) {
                        Iterator<DbField> fieldIt = FieldIteratorBuilder.fieldIteratorWithAutoDb(tableIt
                                        .next());
                        while (fieldIt.hasNext()) {
                                DbField field = fieldIt.next();
                                isText = field.isText();
                                if (isText) {
                                        assertTrue(textType.contains(field.getType()));
                                }
                        }
                }
        }

}
