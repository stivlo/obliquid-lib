package org.obliquid.db;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

/**
 * Provides an Iterator for all the tables of the currently selected DB. Example Usage:
 * 
 * <pre>
 * //if you've already a db connection use the other constructor
 * TableIteratorBuilder builder = new TableIteratorBuilder();
 * builder.getDb(); //open the db connection
 * Iterator&lt;String&gt; it = builder.tableIterator();
 * builder.releaseConnection(); //we can close it already 
 * while (it.hasNext()) {
 *     String table = it.next();
 * }
 * </pre>
 * 
 * @author stivlo
 */
public class TableIteratorBuilder extends HasDb {

    public TableIteratorBuilder() {
        super();
    }

    public TableIteratorBuilder(MetaDb db) {
        super();
    }

    /**
     * Return an Iterator with all the table names of the current db
     * 
     * @return an Iterator of table names
     * @throws SQLException
     */
    public Iterator<String> tableIterator() throws SQLException {
        //we have to do this trick to get a List of String
        @SuppressWarnings("unchecked")
        List<String> allTables = (List<String>) (List<?>) db.selectColumn("SHOW TABLES");
        return allTables.iterator();
    }

    /**
     * Return an Iterator with all the table names of the specified db
     * 
     * @param database
     *            the db to check
     * @return an Iterator of table names
     * @throws SQLException
     */
    public Iterator<String> tableIterator(String database) throws SQLException {
        String currentDb = (String) db.selectField("SELECT DATABASE()");
        db.execute("USE " + database);
        Iterator<String> it = tableIterator();
        db.execute("USE " + currentDb); //preserve the currently selected db
        return it;
    }

}
