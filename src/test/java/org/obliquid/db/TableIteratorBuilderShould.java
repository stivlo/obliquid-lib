package org.obliquid.db;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Iterator;

import org.junit.Test;

public class TableIteratorBuilderShould {

    @Test
    public void returnSixteenTablesForDb2() throws SQLException {
        TableIteratorBuilder builder = new TableIteratorBuilder();
        builder.getDb();
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
        builder.getDb();
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
            builder.getDb();
            builder.tableIterator("wrongdb");
        } finally {
            builder.releaseConnection(); //safe
        }
    }

}
