package org.obliquid.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Provides an Iterator for all the fields of the specified table. Example Usage:
 * 
 * <pre>
 * //if you've already a db connection use the other constructor
 * FieldIteratorBuilder builder = new FieldIteratorBuilder();
 * builder.getDb(); //open the db connection
 * Iterator&lt;DbField&gt; it = builder.fieldIterator(&quot;my_table&quot;);
 * builder.releaseConnection(); //we can close it already 
 * while (it.hasNext()) {
 *     DbField field = it.next();
 * }
 * </pre>
 * 
 * @author stivlo
 */
public class FieldIteratorBuilder {

    private MetaDb db;

    public FieldIteratorBuilder() {
        super();
    }

    public FieldIteratorBuilder(MetaDb db) {
        super();
        this.db = db;
    }

    /**
     * Release a connection. If it's a standalone Connection, the connection is closed, otherwise is
     * returned to the pool.
     */
    public void releaseConnection() {
        if (db != null) {
            db.releaseConnection();
        }
    }

    /**
     * Injects db connection class
     * 
     * @param db
     * @throws SQLException
     */
    public void setDb(MetaDb db) throws SQLException {
        this.db = db;
        db.getConnection();
    }

    /**
     * Build a Field list for a table
     * 
     * @param tableName
     *            the table requested
     * @return a Field List
     * @throws SQLException
     */
    public Iterator<DbField> fieldIterator(String tableName) throws SQLException {
        List<DbField> fieldList = new ArrayList<DbField>();
        List<List<Object>> allFields = db.selectAll("DESCRIBE " + tableName);
        for (List<Object> fieldRawData : allFields) {
            DbField aField = new DbField((String) fieldRawData.get(0), (String) fieldRawData.get(1),
                    (String) fieldRawData.get(3));
            fieldList.add(aField);
        }
        return fieldList.iterator();
    }

    /**
     * A static method that creates a new Object of this type, connects to the db, gets the fields
     * of the specified table, disconnects from the db and returns a tableIterator
     * 
     * @return an Iterator of table names
     * @throws SQLException
     */
    public static Iterator<DbField> fieldIteratorWithAutoDb(String table) throws SQLException {
        FieldIteratorBuilder builder = new FieldIteratorBuilder();
        builder.db = new MetaDbImpl();
        builder.db.getConnection();
        Iterator<DbField> fieldIterator = builder.fieldIterator(table);
        builder.releaseConnection();
        return fieldIterator;
    }

}
