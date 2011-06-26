package org.obliquid.db;

import java.sql.SQLException;

import org.obliquid.config.AppConfig;

/**
 * Base class for Objects needing a Db Connection.
 * 
 * @author stivlo
 */
public class HasDb {

    private static AppConfig conf;

    /** An instance of the Db class */
    protected MetaDb db = null;

    /**
     * Creates a new instance of HasDb
     */
    public HasDb() {
        synchronized (this) {
            if (conf == null) {
                conf = AppConfig.getInstance();
            }
        }
    }

    /**
     * Creates a new instance of HasDb, passing an open db
     * 
     * @param db
     *            a MetaDb instance
     */
    public HasDb(MetaDb db) {
        this();
        this.db = db;
    }

    /**
     * Get a new Db Object without the connection pooling
     * 
     * @return a MetaDB object
     * @throws SQLException
     */
    public MetaDb getStandaloneDb() throws SQLException {
        if (db == null) {
            db = new MetaDb();
            db.getConnection(false); // don't use pool
        }
        return db;
    }

    /**
     * Get a new Db Object using the connection pooling
     * 
     * @return a MetaDB object
     * @throws SQLException
     */
    public MetaDb getDb() throws SQLException {
        if (db == null) {
            db = new MetaDb();
            db.getConnection();
        }
        return db;
    }

    /**
     * Set Db object
     * 
     * @param dbIn
     */
    public void setDb(MetaDb dbIn) {
        this.db = dbIn;
    }

    public void log(String msg, int level) {
        conf.log(msg, level);
    }

    public static AppConfig getConfig() {
        return conf;
    }

    public void releaseConnection() throws SQLException {
        MetaDb curDb = getDb();
        if (curDb != null) {
            curDb.releaseConnection();
        }
    }

}