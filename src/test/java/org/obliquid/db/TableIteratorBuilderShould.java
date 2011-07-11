package org.obliquid.db;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class TableIteratorBuilderShould {

    @Test
    public void returnSixteenTablesForDb2() throws SQLException {
        TableIteratorBuilder builder = new TableIteratorBuilder();
        builder.setDb(new MetaDbImpl());
        Iterator<String> tableIterator = builder.tableIterator();
        builder.releaseConnection();
        int count = 0;
        while (tableIterator.hasNext()) {
            tableIterator.next();
            count++;
        }
        assertEquals(16, count);
    }

    @Test
    public void returnZeroTablesForDb1() throws SQLException {
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

    @Test(expected = SQLException.class)
    public void throwExceptionForOtherDbs() throws SQLException {
        TableIteratorBuilder builder = new TableIteratorBuilder();
        try {
            builder.setDb(new MetaDbImpl());
            builder.tableIterator("wrongdb");
        } finally {
            builder.releaseConnection(); //safe
        }
    }

    @Test
    public void dbFieldShouldRecognizeTextTypes() throws SQLException {
        Iterator<String> tableIt = TableIteratorBuilder.tableIteratorWithAutoDb();
        boolean isText;
        List<String> textType = new ImmutableList.Builder<String>().add("varchar").add("text").add("char")
                .build();
        while (tableIt.hasNext()) {
            Iterator<DbField> fieldIt = FieldIteratorBuilder.fieldIteratorWithAutoDb(tableIt.next());
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
