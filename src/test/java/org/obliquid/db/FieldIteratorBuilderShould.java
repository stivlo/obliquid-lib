package org.obliquid.db;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.Iterator;

import org.junit.Test;

/**
 * Class under test: FieldIteratorBuilder.
 * 
 * @author stivlo
 * 
 */
public class FieldIteratorBuilderShould {

        /**
         * Count the number of fields for table film.
         * 
         * @throws SQLException
         *                 in case of DB problems: tipically connection
         */
        @Test
        public final void returnThirteenFieldsForTableFilm() throws SQLException {
                final int fieldCount = 13;
                FieldIteratorBuilder builder = new FieldIteratorBuilder();
                builder.setDb(new MetaDbImpl());
                Iterator<DbField> fieldIterator = builder.fieldIterator("film");
                builder.releaseConnection();
                int count = 0;
                while (fieldIterator.hasNext()) {
                        fieldIterator.next();
                        count++;
                }
                assertEquals(fieldCount, count);
        }

        /**
         * Check the number of fields for the table language.
         * 
         * @throws SQLException
         *                 in case of DB problems: typically connection
         */
        @Test
        public final void returnTheRightFieldsForTableLanguage() throws SQLException {
                final int fieldCount = 3;
                String[] fieldName = new String[] { "language_id", "name", "last_update" };
                String[] fieldType = new String[] { "tinyint", "char", "timestamp" };
                FieldIteratorBuilder builder = new FieldIteratorBuilder();
                builder.setDb(new MetaDbImpl());
                Iterator<DbField> fieldIterator = builder.fieldIterator("language");
                builder.releaseConnection();
                int count = 0;
                while (fieldIterator.hasNext()) {
                        DbField aField = fieldIterator.next();
                        assertEquals(fieldName[count], aField.getName());
                        assertEquals(fieldType[count], aField.getType());
                        count++;
                }
                assertEquals(fieldCount, count);
        }

}
