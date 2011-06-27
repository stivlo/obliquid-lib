package org.obliquid.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.ImmutableList;

public class PriKeyLister extends HasDb {

    public PriKeyLister() {
        super();
    }

    public PriKeyLister(MetaDb db) {
        super();
    }

    /**
     * Get the list of primary key of the table specified
     * 
     * @param table
     *            the table
     * @return a list of primary key fields
     * @throws SQLException
     */
    public ImmutableList<String> getPriKeys(String table) throws SQLException {
        List<String> priKeys = new ArrayList<String>();
        FieldIteratorBuilder builder = new FieldIteratorBuilder(db);
        Iterator<DbField> fieldIterator = builder.fieldIterator(table);
        while (fieldIterator.hasNext()) {
            DbField field = fieldIterator.next();
            if (field.isPrimary()) {
                priKeys.add(field.getName());
            }
        }
        return ImmutableList.copyOf(priKeys);
    }

    /**
     * A static method that creates a new Object of this type, connects to the db, gets the primary
     * keys of the specified table, disconnects from the db and returns them
     * 
     * @return list of the primary keys of the table
     * @throws SQLException
     */
    public static List<String> getPriKeysWithAutoDb(String table) throws SQLException {
        PriKeyLister lister = new PriKeyLister();
        lister.getDb();
        List<String> priKeys = lister.getPriKeys(table);
        lister.releaseConnection();
        return priKeys;
    }

}
