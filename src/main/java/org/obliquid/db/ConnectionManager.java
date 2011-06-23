package org.obliquid.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.obliquid.config.AppConfig;

/**
 * Hold a Connection, don't use this class directly, it's used by MetaDB, it's not a public class.
 * 
 * @author stivlo
 * 
 */
class ConnectionManager {

    /** Number of open connections, or pool connections we're using */
    private static volatile int nOpen = 0;

    /** Db Connection */
    private Connection conn = null;

    private final String driver, url, username, password, dataSourceName;

    private final boolean usePool;

    private final AppConfig conf;

    /** A JNDI reference to DataSource */
    private static volatile DataSource ds = null;

    protected ConnectionManager() {
        conf = AppConfig.getInstance();
        if (conf == null) {
            driver = null;
            url = null;
            username = null;
            password = null;
            dataSourceName = null;
            usePool = false;
        } else {
            driver = conf.getProperty("dbDriver");
            url = conf.getProperty("dbUrl");
            username = conf.getProperty("dbUsername");
            password = conf.getProperty("dbPassword");
            dataSourceName = conf.getProperty("dbDataSourceName");
            usePool = conf.getPropertyAsBoolean("dbUsePool");
        }
    }

    /**
     * Return a JNDI DataSource name. Example of a JNDI data source name
     * "java:/comp/env/jdbc/myDbName";
     * 
     * @return a String with a dataSource name
     * @throws SQLException
     */
    protected String getDataSourceName() throws SQLException {
        if (dataSourceName == null || dataSourceName.length() == 0) {
            String msg = "Define Config parameter getDataSourceName()";
            conf.log(msg, AppConfig.ERROR);
            throw new SQLException(msg);
        }
        return dataSourceName;
    }

    /**
     * Return the database driver to use.
     * 
     * <pre>
     *  MySQL: "com.mysql.jdbc.Driver"
     *  cTree: "ctree.jdbc.ctreeDriver"
     *  Derby: "org.apache.derby.jdbc.ClientDriver"
     * </pre>
     * 
     * @return a String with the driver name
     * @throws SQLException
     */
    protected String getDriver() throws SQLException {
        if (driver == null || driver.length() == 0) {
            String msg = "Define parameter getDriver()";
            conf.log(msg, AppConfig.ERROR);
            throw new SQLException(msg);
        }
        return driver;
    }

    /**
     * Return the connection url.
     * 
     * <pre>
     *  MySQL: "jdbc:mysql://<HOST>[:<PORT>]/<DB>"
     *  cTree: "jdbc:ctree:<PORT>@<HOST>:<DB>"
     *  Derby: "jdbc:derby://<HOST>[:<PORT>]/<DB>"
     * </pre>
     * 
     * @return a String with the connection url
     * @throws SQLException
     */
    public String getUrl() throws SQLException {
        if (url == null || url.length() == 0) {
            String msg = "Define parameter getUrl()";
            conf.log(msg, AppConfig.ERROR);
            throw new SQLException(msg);
        }
        return url;
    }

    /**
     * Return database username.
     * 
     * @return a String with the username
     * @throws SQLException
     */
    protected String getUsername() throws SQLException {
        if (username == null || username.length() == 0) {
            String msg = "Define parameter getUsername()";
            conf.log(msg, AppConfig.ERROR);
            throw new SQLException(msg);
        }
        return username;
    }

    /**
     * Return database password. Please define JFig parameter 'database.password'.
     * 
     * @return a String with the password.
     * @throws SQLException
     */
    protected String getPassword() throws SQLException {
        if (password == null || password.length() == 0) {
            String msg = "Define parameter getPassword()";
            conf.log(msg, AppConfig.ERROR);
            throw new SQLException(msg);
        }
        return password;
    }

    /**
     * Get a Connection either from the pool or standalone, as specified in JFig parameter
     * database.usePool ("true"/"false"), or from the internal instance variable holding a
     * Connection. Default: false In order to use this method, you've to extend this class and
     * override getDataSourceName() method.
     * 
     * @return a Connection to the Db that should be used only to handle special cases, normally
     *         methods from this class should be enough for common Db operations.
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        if (usePool) {
            return getPoolableConnection();
        }
        return getStandaloneConnection();
    }

    /** Really get a new Standalone Connection */
    private Connection getStandaloneConnection() throws SQLException {
        try {
            Class.forName(getDriver());
            // System.out.println("DriverManager.getConnection( "+getUrl()+", "+getUsername()+", "+getPassword())+")");
            conn = DriverManager.getConnection(getUrl(), getUsername(), getPassword());
        } catch (Exception ex) {
            throw new SQLException(ex);
        }
        synchronized (ConnectionManager.class) {
            nOpen++;
        }
        if (nOpen > 1) {
            conf.log("getStandaloneConnection() nOpen=" + nOpen, AppConfig.INFO);
        }
        // Thread.dumpStack();
        return conn;
    }

    /**
     * Really get a new Poolable Connection
     */
    private Connection getPoolableConnection() throws SQLException {
        if (ds == null) {
            try {
                InitialContext ctx = new InitialContext();
                ds = (DataSource) ctx.lookup(getDataSourceName());
            } catch (NamingException ex) {
                throw new SQLException(ex.toString());
            }
        }
        conn = ds.getConnection();
        synchronized (ConnectionManager.class) {
            nOpen++;
        }
        if (nOpen > 1)
            conf.log("getPoolableConnection() nOpen=" + nOpen, AppConfig.INFO);
        return conn;
    }

    /**
     * Release a connection. If it's a standalone Connection, the connection is closed, otherwise is
     * returned to the pool. In any case always remember to close open connections to avoid
     * connections leaking. Connections should be closed in the finally clause to be able to close
     * them even when an Exception is raised.
     * 
     * @throws SQLException
     */
    public void releaseConnection() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
            synchronized (ConnectionManager.class) {
                nOpen--;
            }
        }
        conn = null;
        if (nOpen > 0) {
            conf.log("releaseConnection() nOpen=" + nOpen, AppConfig.INFO);
        }
    }

    /**
     * Close the Connection if it was forgot open, when the Object is garbage collected. DO NOT RELY
     * ON THIS! Always close your own connetions. Objects in a running Java program might not get
     * garbage collected and the connection left open indefinitely. Also, do not call finalize()
     * directly, use releaseConnection() instead. In case finalize is forced to close a connection
     * will write to stdout the following message: "*** ERROR: Db.finalize() - OPEN CONNECTION"
     */
    @Override
    protected void finalize() {
        if (conn != null) {
            conf.log("Db.finalize() - OPEN CONNECTION", AppConfig.ERROR);
        }
        try {
            releaseConnection();
        } catch (SQLException ex) {
            conf.log(ex.toString(), AppConfig.ERROR);
        }
    }

    public Connection getConnection(boolean usePool) throws SQLException {
        if (conn != null && !conn.isClosed()) {
            return conn;
        }
        if (usePool) {
            return getPoolableConnection();
        }
        return getStandaloneConnection();
    }

    /**
     * Return the number of currently open connections. Useful to check that all opened connections
     * have been closed.
     * 
     * @return number of currently opened connections.
     */
    public static int getNumberOfOpenConnections() {
        return nOpen;
    }

    public void commitTransaction() throws SQLException {
        conf.log(this + " " + conn + ".commitTransaction()", AppConfig.INFO);
        conn.commit();
    }

    public void rollbackTransaction() throws SQLException {
        conf.log(this + " " + conn + ".rollbackTransaction()", AppConfig.INFO);
        conn.rollback();
    }

}
