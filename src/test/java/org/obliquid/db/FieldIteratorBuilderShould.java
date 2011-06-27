package org.obliquid.db;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.Iterator;

import org.junit.Test;

public class FieldIteratorBuilderShould {

    @Test
    public void returnThirteenFieldsForTableFilm() throws SQLException {
        FieldIteratorBuilder builder = new FieldIteratorBuilder();
        builder.getDb();
        Iterator<DbField> fieldIterator = builder.fieldIterator("film");
        builder.releaseConnection();
        int count = 0;
        while (fieldIterator.hasNext()) {
            fieldIterator.next();
            count++;
        }
        assertEquals(13, count);
    }

    @Test
    public void returnTheRightFieldsForTableLanguage() throws SQLException {
        String[] fieldName = new String[] { "language_id", "name", "last_update" };
        String[] fieldType = new String[] { "tinyint", "char", "timestamp" };
        FieldIteratorBuilder builder = new FieldIteratorBuilder();
        builder.getDb();
        Iterator<DbField> fieldIterator = builder.fieldIterator("language");
        builder.releaseConnection();
        int count = 0;
        while (fieldIterator.hasNext()) {
            DbField aField = fieldIterator.next();
            assertEquals(fieldName[count], aField.getName());
            assertEquals(fieldType[count], aField.getType());
            count++;
        }
        assertEquals(3, count);
    }

}
