package org.obliquid.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.obliquid.config.AppConfig;

/**
 * Hold one Connection, don't use this class directly, it's used by MetaDB, it's
 * not a public class.
 * 
 * @author stivlo
 * 
 */
class ConnectionManager {

        /** Number of open connections, or pool connections we're using. */
        private static volatile int nOpen = 0;

        /** DB Connection. */
        private Connection conn = null;

        /**
         * Configuration parameters.
         */
        private final String driver, url, username, password, dataSourceName;

        /**
         * Whether to use a connection pool or not.
         */
        private final boolean usePool;

        /** A reference to application configuration. */
        private final AppConfig conf;

        /** A JNDI reference to DataSource. */
        private static volatile DataSource ds = null;

        /** Log4j instance. */
        private static final Logger LOG = Logger.getLogger(ConnectionManager.class);

        /**
         * Default constructor.
         */
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
         *                 in case the data source is missing
         */
        protected String getDataSourceName() throws SQLException {
                if (dataSourceName == null || dataSourceName.length() == 0) {
                        String msg = "Define Config parameter getDataSourceName()";
                        LOG.error(msg);
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
         *                 in case the driver is missing
         */
        protected String getDriver() throws SQLException {
                if (driver == null || driver.length() == 0) {
                        String msg = "Define parameter getDriver()";
                        LOG.error(msg);
                        throw new SQLException(msg);
                }
                return driver;
        }

        /**
         * Return the connection URL.
         * 
         * <pre>
         *  MySQL: "jdbc:mysql://<HOST>[:<PORT>]/<DB>"
         *  cTree: "jdbc:ctree:<PORT>@<HOST>:<DB>"
         *  Derby: "jdbc:derby://<HOST>[:<PORT>]/<DB>"
         * </pre>
         * 
         * @return a String with the connection URL
         * @throws SQLException
         *                 in case the parameter is missing
         */
        public String getUrl() throws SQLException {
                if (url == null || url.length() == 0) {
                        String msg = "Define parameter getUrl()";
                        LOG.error(msg);
                        throw new SQLException(msg);
                }
                return url;
        }

        /**
         * Return database username.
         * 
         * @return a String with the username
         * @throws SQLException
         *                 in case the username is missing
         */
        protected String getUsername() throws SQLException {
                if (username == null || username.length() == 0) {
                        String msg = "Define parameter getUsername()";
                        LOG.error(msg);
                        throw new SQLException(msg);
                }
                return username;
        }

        /**
         * Return database password. Please define JFig parameter
         * 'database.password'.
         * 
         * @return a String with the password.
         * @throws SQLException
         *                 in case the password is missing
         */
        protected String getPassword() throws SQLException {
                if (password == null || password.length() == 0) {
                        String msg = "Define parameter getPassword()";
                        LOG.error(msg);
                        throw new SQLException(msg);
                }
                return password;
        }

        /**
         * Get a Connection either from the pool or stand-alone, as specified in
         * JFig parameter database.usePool ("true"/"false"), or from the
         * internal instance variable holding a Connection. Default: false In
         * order to use this method, you've to extend this class and override
         * getDataSourceName() method.
         * 
         * @return a Connection to the DB that should be used only to handle
         *         special cases, normally methods from this class should be
         *         enough for common DB operations.
         * @throws SQLException
         *                 in case the connection fails
         */
        public Connection getConnection() throws SQLException {
                if (conn != null && !conn.isClosed()) {
                        return conn;
                }
                if (usePool) {
                        return getPoolableConnection();
                }
                return getStandaloneConnection();
        }

        /**
         * Really get a new stand-alone Connection.
         * 
         * @return a Connection
         * @throws SQLException
         *                 when the connection can't be established
         */
        private Connection getStandaloneConnection() throws SQLException {
                try {
                        Class.forName(getDriver());
                        // System.out.println("DriverManager.getConnection( "+getUrl()+", "+getUsername()
                        //+", "+getPassword())+")");
                        conn = DriverManager.getConnection(getUrl(), getUsername(), getPassword());
                } catch (Exception ex) {
                        throw new SQLException(ex);
                }
                synchronized (ConnectionManager.class) {
                        nOpen++;
                }
                LOG.info("getStandaloneConnection() nOpen=" + nOpen);
                // Thread.dumpStack();
                return conn;
        }

        /**
         * Really get a new Pool-able Connection.
         * 
         * @return a pool-able Connection
         * @throws SQLException
         *                 in case the connection can't be established
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
                LOG.info("getPoolableConnection() nOpen=" + nOpen);
                return conn;
        }

        /**
         * Release a connection. If it's a stand-alone Connection, the
         * connection is closed, otherwise is returned to the pool. In any case
         * always remember to close open connections to avoid connections
         * leaking. Connections should be closed in the finally clause to be
         * able to close them even when an Exception is raised.
         * 
         * @throws SQLException
         *                 in case of problems
         */
        public void releaseConnection() throws SQLException {
                if (conn != null && !conn.isClosed()) {
                        conn.close();
                        synchronized (ConnectionManager.class) {
                                nOpen--;
                        }
                }
                conn = null;
                LOG.info("releaseConnection() nOpen=" + nOpen);
        }

        /**
         * Close the Connection if it was forgot open, when the Object is
         * garbage collected. DO NOT RELY ON THIS! Always close your own
         * connections. Objects in a running Java program might not get garbage
         * collected and the connection left open indefinitely. Also, do not
         * call finalise() directly, use releaseConnection() instead. In case
         * finalise is forced to close a connection will write to stdout the
         * following message: "*** ERROR: Db.finalize() - OPEN CONNECTION"
         */
        @Override
        protected void finalize() {
                if (conn != null) {
                        LOG.error("Db.finalize() - OPEN CONNECTION");
                }
                try {
                        releaseConnection();
                } catch (SQLException ex) {
                        LOG.error(ex.toString());
                }
        }

        /**
         * Get a connection.
         * 
         * @param usePoolIn
         *                whether to use pool
         * @return a Connection
         * @throws SQLException
         *                 in case the connection couldn't be established
         */
        public Connection getConnection(final boolean usePoolIn) throws SQLException {
                if (conn != null && !conn.isClosed()) {
                        return conn;
                }
                if (usePoolIn) {
                        return getPoolableConnection();
                }
                return getStandaloneConnection();
        }

        /**
         * Return the number of currently open connections. Useful to check that
         * all opened connections have been closed.
         * 
         * @return number of currently opened connections.
         */
        public static int getNumberOfOpenConnections() {
                return nOpen;
        }

        /**
         * Commit the current transaction.
         * 
         * @throws SQLException
         *                 in case of problems
         */
        public void commitTransaction() throws SQLException {
                LOG.info(this + " " + conn + ".commitTransaction()");
                conn.commit();
        }

        /**
         * Roll back the current transaction.
         * 
         * @throws SQLException
         *                 in case of problems.
         */
        public void rollbackTransaction() throws SQLException {
                LOG.info(this + " " + conn + ".rollbackTransaction()");
                conn.rollback();
        }

}
