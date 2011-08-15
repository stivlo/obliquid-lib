package org.obliquid.db;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

/**
 * Provides an Iterator for all the tables of the currently selected DB. Example
 * Usage:
 * 
 * <pre>
 * //if you've already a db connection use the other constructor
 * TableIteratorBuilder builder = new TableIteratorBuilder();
 * builder.getDb(); //open the db connection
 * Iterator&lt;String&gt; it = builder.tableIterator();
 * builder.releaseConnection(); //we can close it already 
 * while (it.hasNext()) {
 *         String table = it.next();
 * }
 * </pre>
 * 
 * @author stivlo
 */
public class TableIteratorBuilder {

        /**
         * A MetaDb instance.
         */
        private MetaDb db;

        /**
         * Default constructor.
         */
        public TableIteratorBuilder() {
                super();
        }

        /**
         * Constructor passing an already built MetaDb.
         * 
         * @param dbIn
         *                a MetaDb instance
         */
        public TableIteratorBuilder(final MetaDb dbIn) {
                super();
                db = dbIn;
        }

        /**
         * Release a connection. If it's a stand-alone Connection, the
         * connection is closed, otherwise is returned to the pool.
         */
        public final void releaseConnection() {
                if (db != null) {
                        db.releaseConnection();
                }
        }

        /**
         * Return an Iterator with all the table names of the current DB.
         * 
         * @return an Iterator of table names
         * @throws SQLException
         *                 when there are DB problems
         */
        public final Iterator<String> tableIterator() throws SQLException {
                //we have to do this trick to get a List of String
                @SuppressWarnings("unchecked")
                List<String> allTables = (List<String>) (List<?>) db.selectColumn("SHOW TABLES");
                return allTables.iterator();
        }

        /**
         * Return an Iterator with all the table names of the specified DB.
         * 
         * @param database
         *                the DB to check
         * @return an Iterator of table names
         * @throws SQLException
         *                 when there are DB problems
         */
        public final Iterator<String> tableIterator(final String database) throws SQLException {
                String currentDb = (String) db.selectField("SELECT DATABASE()");
                db.execute("USE " + database);
                Iterator<String> it = tableIterator();
                db.execute("USE " + currentDb); //preserve the currently selected db
                return it;
        }

        /**
         * A static method that creates a new Object of this type, connects to
         * the DB, gets the tables, disconnects from the DB and returns a
         * tableIterator.
         * 
         * @return an Iterator of table names
         * @throws SQLException
         *                 when there are problems with DB
         */
        public static final Iterator<String> tableIteratorWithAutoDb() throws SQLException {
                TableIteratorBuilder builder = new TableIteratorBuilder();
                builder.db = new MetaDbImpl();
                builder.db.getConnection();
                Iterator<String> tableIterator = builder.tableIterator();
                builder.releaseConnection();
                return tableIterator;
        }

        /**
         * Injects DB connection class.
         * 
         * @param dbIn
         *                a MetaDb instance.
         * @throws SQLException
         *                 when there DB problems.
         */
        public final void setDb(final MetaDb dbIn) throws SQLException {
                db = dbIn;
                db.getConnection();
        }

}
