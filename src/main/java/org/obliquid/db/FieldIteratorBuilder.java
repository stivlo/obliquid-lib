package org.obliquid.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Provides an Iterator for all the fields of the specified table. Example
 * Usage:
 * 
 * <pre>
 * //if you've already a DB connection use the other constructor
 * FieldIteratorBuilder builder = new FieldIteratorBuilder();
 * builder.getDb(); //open the DB connection
 * Iterator&lt;DbField&gt; it = builder.fieldIterator(&quot;my_table&quot;);
 * builder.releaseConnection(); //we can close it already 
 * while (it.hasNext()) {
 *         DbField field = it.next();
 * }
 * </pre>
 * 
 * @author stivlo
 */
public class FieldIteratorBuilder {

        /** A MetaDb Instance. */
        private MetaDb db;

        /** Default constructor. */
        public FieldIteratorBuilder() {
                super();
        }

        /**
         * Constructor passing a MetaDb instance.
         * 
         * @param dbIn
         *                a MetaDb instance
         */
        public FieldIteratorBuilder(final MetaDb dbIn) {
                super();
                this.db = dbIn;
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
         * Injects DB connection class.
         * 
         * @param dbIn
         *                injects a MetaDb instance
         * @throws SQLException
         *                 in case something goes wrong
         */
        public final void setDb(final MetaDb dbIn) throws SQLException {
                db = dbIn;
                db.getConnection();
        }

        /**
         * Build a Field list for a table.
         * 
         * @param tableName
         *                the table requested
         * @return a Field List
         * @throws SQLException
         *                 in case the query goes wrong
         */
        public final Iterator<DbField> fieldIterator(final String tableName) throws SQLException {
                final int fieldPos = 0, typePos = 1, keyPos = 3;
                DbField aField;
                List<DbField> fieldList = new ArrayList<DbField>();
                List<List<Object>> allFields = db.selectAll("DESCRIBE " + tableName);
                for (List<Object> fieldRawData : allFields) {
                        aField = new DbField((String) fieldRawData.get(fieldPos),
                                        (String) fieldRawData.get(typePos),
                                        (String) fieldRawData.get(keyPos));
                        fieldList.add(aField);
                }
                return fieldList.iterator();
        }

        /**
         * A static method that creates a new Object of this type, connects to
         * the DB, gets the fields of the specified table, disconnects from the
         * DB and returns a tableIterator.
         * 
         * @param table
         *                the table to iterate on
         * @return an Iterator of table names
         * @throws SQLException
         *                 in case of problems
         */
        public static Iterator<DbField> fieldIteratorWithAutoDb(final String table) throws SQLException {
                FieldIteratorBuilder builder = new FieldIteratorBuilder();
                builder.db = new MetaDbImpl();
                builder.db.getConnection();
                Iterator<DbField> fieldIterator = builder.fieldIterator(table);
                builder.releaseConnection();
                return fieldIterator;
        }

}
