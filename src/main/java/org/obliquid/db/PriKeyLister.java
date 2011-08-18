package org.obliquid.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.ImmutableList;

/**
 * List the primary keys of a table.
 * 
 * @author stivlo
 * 
 */
public class PriKeyLister {

        /** A MetaDb instance. */
        private MetaDb db;

        /** Default constructor. */
        public PriKeyLister() {
        }

        /**
         * Constructor injecting a MetaDb instance.
         * 
         * @param dbIn
         *                a MetaDb instance
         */
        public PriKeyLister(final MetaDb dbIn) {
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
         * Get the list of primary key of the table specified.
         * 
         * @param table
         *                the table
         * @return a list of primary key fields
         * @throws SQLException
         *                 in case of problems
         */
        public final ImmutableList<String> getPriKeys(final String table) throws SQLException {
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
         * A static method that creates a new Object of this type, connects to
         * the DB, gets the primary keys of the specified table, disconnects
         * from the DB and returns them.
         * 
         * @param table
         *                the table to consider
         * @return list of the primary keys of the table
         * @throws SQLException
         *                 in case of problems (non existing table, connection
         *                 problem, credentials problems)
         */
        public static List<String> getPriKeysWithAutoDb(final String table) throws SQLException {
                PriKeyLister lister = new PriKeyLister();
                lister.db = new MetaDbImpl();
                lister.db.getConnection();
                List<String> priKeys = lister.getPriKeys(table);
                lister.releaseConnection();
                return priKeys;
        }

        /**
         * Injects a DB Connection.
         * 
         * @param dbIn
         *                a MetaDb instance
         * @throws SQLException
         *                 in case of problem connecting
         */
        public final void setDb(final MetaDb dbIn) throws SQLException {
                db = dbIn;
                db.getConnection();
        }

}
