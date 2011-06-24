package org.obliquid.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.obliquid.helpers.ArrayHelper;
import org.obliquid.helpers.SqlHelper;
import org.obliquid.helpers.StringHelper;

/**
 * Manage interactions with database.
 * 
 * @author stivlo
 */
public class MetaDb {

    /** Holds the connection */
    private final ConnectionManager connectionManager;

    /** Db Connection */
    private Connection conn = null;

    /** Statement for raw Query */
    private Statement statement;

    /** ResultSet for raw Query */
    private ResultSet res;

    /**
     * Create a new instance of Db
     * 
     */
    protected MetaDb() {
        connectionManager = new ConnectionManager();
    }

    /**
     * Execute a raw query. Remember to close the created ResultSet and Statement by calling the
     * method closeResultSetAndStatement()
     * 
     * @return a ResultSet object
     * @throws SQLException
     */
    public ResultSet executeRawQuery(String sql) throws SQLException {
        statement = null;
        res = null;
        statement = conn.createStatement();
        res = statement.executeQuery(sql);
        return res;
    }

    /**
     * Close ResulSet and Statement after a raw query. Exception are trapped.
     */
    public void closeResultSetAndStatement() {
        SqlHelper.close(statement);
        SqlHelper.close(res);
    }

    /**
     * Get a Connection, either from the pool or standalone, or from the internal instance variable
     * holding a Connection. In order to use this method to get a connection from the pool, you've
     * to extend this class and override getDataSource Method. In the case of a standalone
     * connection, you've to extend this class and override getDriver(), getUrl(), getUsername(),
     * getPassword(). A good rule should be to not mix standalone connections and connection pooling
     * in the same program execution.
     * 
     * @param usePool
     *            use a connection from the pool if usePool is true, otherwise open a normal db
     *            connnection
     * @return a Connection to the Db that should be used only to handle special cases, normally
     *         methods from this class should be enough for common Db operations.
     * @throws SQLException
     */
    public Connection getConnection(boolean usePool) throws SQLException {
        conn = connectionManager.getConnection(usePool);
        return conn;
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
        conn = connectionManager.getConnection();
        return conn;
    }

    /**
     * Release a connection. If it's a standalone Connection, the connection is closed, otherwise is
     * returned to the pool. In any case always remember to close open connections to avoid
     * connections leaking. Connections should be closed in the finally clause to be able to close
     * them even when an Exception is raised. It doesn't throw SQLException any more, since if it
     * would, there would be nothing we could do about it, so throwing it is pointless.
     */
    public void releaseConnection() {
        conn = null;
        try {
            connectionManager.releaseConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Executes a SQL statement.
     * 
     * @param sql
     *            must be an SQL INSERT, UPDATE or DELETE statement or an SQL statement that doesn't
     *            return anything.
     * @return rowCount number of rows affected
     * @throws SQLException
     */
    public int execute(String sql) throws SQLException {
        Statement stmt = null;
        int rowCount = 0;
        try {
            stmt = conn.createStatement();
            rowCount = stmt.executeUpdate(sql);
        } finally {
            SqlHelper.close(stmt);
        }
        return rowCount;
    }

    /**
     * Executes a SQL statement.
     * 
     * @param sql
     *            must be an SQL INSERT, UPDATE or DELETE statement or an SQL statement that doesn't
     *            return anything.
     * @param param
     *            List with IN parameters to be set in the query
     * @return rowCount number of rows affected
     * @throws SQLException
     */
    public int execute(String sql, List<?> param) throws SQLException {
        PreparedStatement statement = null;
        int rowCount = 0;
        try {
            statement = SqlHelper.buildPreparedStatement(conn, sql, param);
            rowCount = statement.executeUpdate();
        } finally {
            SqlHelper.close(statement);
        }
        return rowCount;
    }

    /**
     * Executes a SQL statement.
     * 
     * @param sql
     *            must be an SQL INSERT, UPDATE or DELETE statement or an SQL statement that doesn't
     *            return anything.
     * @param param
     *            String array with IN parameters to be set in the query
     * @return rowCount number of rows affected
     * @throws SQLException
     */
    public int execute(String sql, String[] param) throws SQLException {
        ArrayList<Object> arList = ArrayHelper.buildArrayList(param);
        return execute(sql, arList);
    }

    /**
     * Executes a SQL statement.
     * 
     * @param sql
     *            must be an SQL INSERT, UPDATE or DELETE statement or an SQL statement that doesn't
     *            return anything.
     * @param param
     *            IN parameter to be set in the query
     * @return rowCount number of rows affected
     * @throws SQLException
     */
    public <T> int execute(String sql, T param) throws SQLException {
        ArrayList<Object> arList = new ArrayList<Object>();
        arList.add(param);
        return execute(sql, arList);
    }

    /**
     * Execute a SQL statement.
     * 
     * @param sql
     *            must be an SQL INSERT, UPDATE or DELETE statement or an SQL statement that doesn't
     *            return anything.
     * @param param
     *            HashMap with IN parameters to be set in the query. The values of the HashMap will
     *            be used and the keys not considered.
     * @return rowCount number of rows affected
     * @throws SQLException
     */
    public int execute(String sql, Map<String, Object> param) throws SQLException {
        ArrayList<Object> arList = ArrayHelper.buildArrayList(param);
        return execute(sql, arList);
    }

    /**
     * Execute a SQL statement.
     * 
     * @param sql
     *            must be an SQL INSERT, UPDATE or DELETE statement or an SQL statement that doesn't
     *            return anything.
     * @param param
     * @return rowCount number of rows affected
     * @throws SQLException
     */
    public int execute(String sql, int[] param) throws SQLException {
        ArrayList<Object> arList = ArrayHelper.buildArrayList(param);
        return execute(sql, arList);
    }

    /**
     * Executes the specified SELECT query, fetch the value from the first column of the first row
     * of the result set into a given variable and then frees the result set.
     * 
     * @param sql
     *            the SELECT query statement to be executed.
     * @return the fetched value in a Java Object.
     * @throws SQLException
     */
    public Object selectField(String sql) throws SQLException {
        Statement stmt = null;
        ResultSet res = null;
        Object field = null;
        try {
            stmt = conn.createStatement();
            res = stmt.executeQuery(sql);
            if (res.next()) {
                field = res.getObject(1);
            } else {
                throw new SQLException("*** Empty ResultSet ***");
            }
        } finally {
            SqlHelper.close(stmt);
            SqlHelper.close(res);
        }
        return field;
    }

    /**
     * Executes the specified prepared query, fetch the value from the first column of the first row
     * of the result set into a given variable and then frees the result set.
     * 
     * @param sql
     *            the SELECT query statement to be executed.
     * @param param
     *            List with IN parameters to be set in the query
     * @return the fetched value in a Java Object or null
     * @throws SQLException
     */
    public Object selectField(String sql, List<?> param) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet res = null;
        Object field = null;
        try {
            stmt = SqlHelper.buildPreparedStatement(conn, sql, param);
            res = stmt.executeQuery();
            if (res.next()) {
                field = res.getObject(1);
            } else {
                return null;
            }
        } finally {
            SqlHelper.close(stmt);
            SqlHelper.close(res);
        }
        return field;
    }

    /**
     * Executes the specified prepared query, fetch the value from the first column of the first row
     * of the result and then frees the result set.
     * 
     * @param sql
     *            the SELECT query statement to be executed.
     * @param param
     *            String array with IN parameters to be set in the query
     * @return the fetched value in an Object
     * @throws SQLException
     */
    public Object selectField(String sql, String[] param) throws SQLException {
        ArrayList<Object> arList = ArrayHelper.buildArrayList(param);
        return selectField(sql, arList);
    }

    /**
     * Executes the specified prepared query, fetch the value from the first column of the first row
     * of the result and then frees the result set.
     * 
     * @param sql
     *            the SELECT query statement to be executed.
     * @param param
     *            int array with IN parameters to be set in the query
     * @return the fetched value in an Object
     * @throws SQLException
     */
    public Object selectField(String sql, int[] param) throws SQLException {
        ArrayList<Object> arList = ArrayHelper.buildArrayList(param);
        return selectField(sql, arList);
    }

    /**
     * Executes the specified prepared query, fetch the value from the first column of the first row
     * of the result and then frees the result set.
     * 
     * @param sql
     *            the SELECT query statement to be executed.
     * @param param
     *            a String IN parameter to be set in the query
     * @return the fetched value in an Object
     * @throws SQLException
     */
    public <T> Object selectField(String sql, T param) throws SQLException {
        ArrayList<Object> arList = new ArrayList<Object>();
        arList.add(param);
        return selectField(sql, arList);
    }

    /**
     * Executes the specified query, fetch the first row of the result set row, frees the result set
     * and returns the fetched row in a List.
     * 
     * @param sql
     *            the SELECT query statement to be executed.
     * @return an List populated with the Objects contained in the fetched row or null if no rows
     *         were fetched.
     * @throws SQLException
     */
    public List<Object> selectRow(String sql) throws SQLException {
        Statement statement = null;
        ResultSet res = null;
        List<Object> row = null;
        try {
            statement = conn.createStatement();
            res = statement.executeQuery(sql);
            int numCols = SqlHelper.getColumnCount(res);
            if (res.next()) {
                row = SqlHelper.extractRowFromResultSet(res, numCols);
            }
        } finally {
            SqlHelper.close(statement);
            SqlHelper.close(res);
        }
        return row;
    }

    /**
     * Executes the specified query, fetch the first row of the result set row, frees the result set
     * and returns the fetched row in a List.
     * 
     * @param sql
     *            the SELECT query statement to be executed.
     * @param param
     *            List with IN parameters to be set in the query
     * @return a List populated with the Objects contained in the fetched row or null if no rows
     *         were fetched.
     * @throws SQLException
     */
    public List<Object> selectRow(String sql, List<?> param) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet res = null;
        List<Object> row = null;
        try {
            stmt = SqlHelper.buildPreparedStatement(conn, sql, param);
            res = stmt.executeQuery();
            int numCols = SqlHelper.getColumnCount(res);
            if (res.next()) {
                row = SqlHelper.extractRowFromResultSet(res, numCols);
            }
        } finally {
            SqlHelper.close(res);
            SqlHelper.close(stmt);
        }
        return row;
    }

    /**
     * Executes the specified query, fetch the first row of the result set row, frees the result set
     * and returns the fetched row in a List.
     * 
     * @param sql
     *            the SELECT query statement to be executed.
     * @param param
     *            an int IN parameters to be set in the query
     * @return a List populated with the Objects contained in the fetched row or null if no rows
     *         were fetched.
     * @throws SQLException
     */
    public List<Object> selectRow(String sql, int param) throws SQLException {
        List<Object> arList = new ArrayList<Object>();
        arList.add(param);
        return selectRow(sql, arList);
    }

    /**
     * Executes the specified query, fetch the first row of the result set row, frees the result set
     * and returns the fetched row in a List.
     * 
     * @param sql
     *            the SELECT query statement to be executed.
     * @param param
     *            an String IN parameters to be set in the query
     * @return a List populated with the Objects contained in the fetched row or null if no rows
     *         were fetched.
     * @throws SQLException
     */
    public List<Object> selectRow(String sql, String param) throws SQLException {
        ArrayList<Object> arList = new ArrayList<Object>();
        arList.add(param);
        return selectRow(sql, arList);
    }

    /**
     * Executes the specified query, fetch the first row of the result set row, frees the result set
     * and returns the fetched row in an ArrayList.
     * 
     * @param sql
     *            the SELECT query statement to be executed.
     * @param param
     *            String array with IN parameters to be set in the query
     * @return a List populated with the Objects contained in the fetched row or null if no rows
     *         were fetched.
     * @throws SQLException
     */
    public List<Object> selectRow(String sql, String[] param) throws SQLException {
        ArrayList<Object> arList = ArrayHelper.buildArrayList(param);
        return selectRow(sql, arList);
    }

    /**
     * Executes the specified query, fetch the first row of the result set row, frees the result set
     * and returns the fetched row in an ArrayList.
     * 
     * @param sql
     *            the SQL statement to be executed
     * @param param
     *            an array of int parameters
     * @return a row of results
     * @throws SQLException
     */
    public List<Object> selectRow(String sql, int[] param) throws SQLException {
        ArrayList<Object> arList = ArrayHelper.buildArrayList(param);
        return selectRow(sql, arList);
    }

    /**
     * Executes the specified query, fetch the first column of all the rows of the result set, frees
     * the result set and returns the fetched column in an ArrayList.
     * 
     * @param sql
     *            the sql statement to be executed
     * 
     * @return a column of results
     * @throws SQLException
     */
    public List<Object> selectColumn(String sql) throws SQLException {
        Statement stmt = null;
        ResultSet res = null;
        ArrayList<Object> column = new ArrayList<Object>();
        try {
            stmt = conn.createStatement();
            res = stmt.executeQuery(sql);
            while (res.next()) {
                res.getRow();
                column.add(res.getObject(1));
            }
        } finally {
            SqlHelper.close(res);
            SqlHelper.close(stmt);
        }
        return column;
    }

    /**
     * Executes the specified query, fetch the first column of all the rows of the result set, frees
     * the result set and returns the fetched column in an array of String.
     * 
     * @param sql
     *            the sql statement to be executed
     * @return a column of results
     * @throws SQLException
     */
    public String[] selectColumnToStringArray(String sql) throws SQLException {
        List<Object> res = selectColumn(sql);
        String[] ar = new String[res.size()];
        for (int i = 0; i < res.size(); i++) {
            ar[i] = (String) res.get(i);
        }
        return ar;
    }

    /**
     * Executes the specified query, fetch the first column of all the rows of the result set, frees
     * the result set and returns the fetched column in an array of int.
     * 
     * @param sql
     *            the sql statement to be executed
     * @return a column of results
     * @throws SQLException
     */
    public int[] selectColumnToIntArray(String sql) throws SQLException {
        List<Object> res = selectColumn(sql);
        int[] ar = new int[res.size()];
        for (int i = 0; i < res.size(); i++) {
            ar[i] = (Integer) res.get(i);
        }
        return ar;
    }

    /**
     * Executes the specified query, fetch the first column of all the rows of the result set, frees
     * the result set and returns the fetched column in a List.
     * 
     * @param sql
     *            the sql statement to be executed
     * @param param
     *            an array of int to be substituted as parameters
     * @return a column of results
     * @throws SQLException
     */
    public List<Object> selectColumn(String sql, int[] param) throws SQLException {
        ArrayList<Object> arList = ArrayHelper.buildArrayList(param);
        return selectColumn(sql, arList);
    }

    /**
     * Executes the specified query, fetch the first column of all the rows of the result set, frees
     * the result set and returns the fetched column in a List.
     * 
     * @param sql
     *            the sql statement to be executed
     * @param param
     * @return a List of Object
     * @throws SQLException
     */
    public List<Object> selectColumn(String sql, int param) throws SQLException {
        ArrayList<Object> arList = new ArrayList<Object>();
        arList.add(param);
        return selectColumn(sql, arList);
    }

    /**
     * Executes the specified query, fetch the first column of all the rows of the result set, frees
     * the result set and returns the fetched column in a List.
     * 
     * @param sql
     *            the sql statement to be executed
     * @param param
     *            a List of IN parameters to be set in the query
     * @return a column of results
     * @throws SQLException
     */
    public List<Object> selectColumn(String sql, List<?> param) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet res = null;
        ArrayList<Object> row = new ArrayList<Object>();
        try {
            stmt = SqlHelper.buildPreparedStatement(conn, sql, param);
            res = stmt.executeQuery();
            while (res.next()) {
                res.getRow();
                row.add(res.getObject(1));
            }
        } finally {
            SqlHelper.close(res);
            SqlHelper.close(stmt);
        }
        return row;
    }

    /**
     * Executes the specified SELECT query, fetch all rows of the result set row into a given two
     * dimension ArrayList Object and then frees the result set.
     * 
     * @param sql
     *            the SELECT query statement to be executed.
     * @return an ArrayList populated with rows fetched. Each row is an ArrayList Object populated
     *         with the Objects contained in the row. If no rows were fetched, queryAll() returns
     *         null.
     * @throws SQLException
     */
    public List<List<Object>> selectAll(String sql) throws SQLException {
        Statement statement = null;
        ResultSet res = null;
        List<List<Object>> matrix = null;
        try {
            statement = conn.createStatement();
            res = statement.executeQuery(sql);
            matrix = SqlHelper.extractMatrixFromResultSet(res);
        } finally {
            SqlHelper.close(res);
            SqlHelper.close(statement);
        }
        return matrix;
    }

    /**
     * Executes the specified query, fetch all rows of the result set row into a given two dimension
     * ArrayList Object and then frees the result set.
     * 
     * @param sql
     *            the SELECT query statement to be executed
     * @param param
     *            List with IN parameters to be set in the query
     * @return an List populated with rows fetched. Each row is an ArrayList Object populated with
     *         the Objects contained in the row. If no rows were fetched, queryAll() returns null.
     * @throws SQLException
     */
    public List<List<Object>> selectAll(String sql, List<?> param) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet res = null;
        List<List<Object>> matrix = null;
        try {
            stmt = SqlHelper.buildPreparedStatement(conn, sql, param);
            res = stmt.executeQuery();
            matrix = SqlHelper.extractMatrixFromResultSet(res);
        } finally {
            SqlHelper.close(res);
            SqlHelper.close(stmt);
        }
        return matrix;
    }

    /**
     * Executes the specified SELECT query, fetch all rows of the result set row into a given two
     * dimension ArrayList Object and then frees the result set.
     * 
     * @param sql
     *            the SELECT query statement to be executed
     * @param param
     *            String array with IN parameters to be set in the query
     * @return an ArrayList populated with rows fetched. Each row is an ArrayList Object populated
     *         with the Objects contained in the row. If no rows were fetched, queryAll() returns
     *         null.
     * @throws SQLException
     */
    public List<List<Object>> selectAll(String sql, String[] param) throws SQLException {
        ArrayList<Object> arList = ArrayHelper.buildArrayList(param);
        return selectAll(sql, arList);
    }

    /**
     * selectAll for int[]
     * 
     * @param sql
     *            the sql to be executed
     * @param param
     *            an int parameter array
     * @return all rows of results as a matrix
     * @throws SQLException
     */
    public List<List<Object>> selectAll(String sql, int[] param) throws SQLException {
        ArrayList<Object> arList = ArrayHelper.buildArrayList(param);
        return selectAll(sql, arList);
    }

    /**
     * queryAll for Object
     * 
     * @param sql
     *            the sql statement to be executed
     * @param param
     *            a parameter to be substituted
     * @return all rows of results as a matrix
     * @throws SQLException
     */
    public <T> List<List<Object>> selectAll(String sql, T param) throws SQLException {
        ArrayList<Object> arList = new ArrayList<Object>();
        arList.add(param);
        return selectAll(sql, arList);
    }

    /**
     * Creates a new Id to use in an INSERT statement. The returned Id won't be returned again, even
     * if it's not used.
     * 
     * @param tablename
     *            the name of the table for which we are requesting a new Id.
     * @return an Object of type Long containing an Id that we can use for an insert statement.
     * @throws SQLException
     */
    public int newIdUsingSequence(String tablename) throws SQLException {
        String sql = "INSERT INTO _sequence_" + tablename + " VALUES (null)";
        execute(sql);
        Long id = (Long) selectField("SELECT LAST_INSERT_ID()");
        sql = "DELETE FROM _sequence_" + tablename + " WHERE sequence<" + id;
        execute(sql);
        return id.intValue();
    }

    /**
     * Returns a new id using sequences.
     * 
     * @param sequenceName
     *            a mnemonic for the sequence. it's a good idea to use the same name as a table
     *            name, but it's not required.
     * @return an integer that is the next val to be used
     * @throws SQLException
     */
    public int newIdUsingSequenceNoCommit(String sequenceName) throws SQLException {
        String sql = "SELECT nextval FROM sequence WHERE sequencename=? FOR UPDATE";
        List<Object> res = selectRow(sql, sequenceName);
        if (res == null) {
            sql = "INSERT INTO equence(nextval, sequencename) VALUES (2, ?)";
            execute(sql, sequenceName);
            return 1;
        }
        Integer nextval = (Integer) res.get(0);
        sql = "UPDATE sequence SET nextval=nextval+1 WHERE sequencename=?";
        execute(sql, sequenceName);
        return nextval;
    }

    /**
     * Build a SQL Insert Statement for table tablename using the contents of the HashMap as data,
     * using the INSERT DELAYED statement.
     * 
     * @param tablename
     *            name of the database table
     * @param fields
     *            HashMap with fields names and their values
     * @return the query executed
     * @throws SQLException
     */
    public String insertDelayed(String tablename, Map<String, Object> fields) throws SQLException {
        StringBuilder sb = new StringBuilder(128);
        sb.append("INSERT DELAYED INTO ");
        sb.append(tablename);
        sb.append("(");
        sb.append(StringHelper.implode(", ", fields));
        sb.append(") VALUES (");
        sb.append(StringHelper.repeatWithSeparator("?", fields.size(), ","));
        sb.append(")");
        String sql = sb.toString();
        execute(sql, fields);
        return sql;
    }

    /**
     * Build a SQL Insert Statement for table tablename using the contents of the HashMap as data.
     * 
     * @param tablename
     *            name of the database table
     * @param fields
     *            HashMap with fields names and their values
     * @return the query executed
     * @throws SQLException
     */
    public String insert(String tablename, Map<String, Object> fields) throws SQLException {
        String sql;
        StringBuilder sqlB = new StringBuilder(128);
        sqlB.append("INSERT INTO ");
        sqlB.append(tablename);
        sqlB.append("(");
        sqlB.append(StringHelper.implode(", ", fields));
        sqlB.append(") VALUES (");
        sqlB.append(StringHelper.repeatWithSeparator("?", fields.size(), ","));
        sqlB.append(")");
        sql = sqlB.toString();
        execute(sql, fields);
        return sql;
    }

    /**
     * Build a simple SQL Update Statement for table tablename using the contents of the HashMap as
     * data.
     * 
     * @param tablename
     *            name of the database table
     * @param fields
     *            HashMap with fields names and their values
     * @param priKeys
     *            HashMap with primary key(s)
     * @return rowCount number of rows affected
     * @throws SQLException
     */
    public int update(String tablename, Map<String, Object> fields, Map<String, Object> priKeys)
            throws SQLException {
        String sql = "UPDATE " + tablename + " SET " + StringHelper.implode("=?, ", fields) + "=? WHERE "
                + StringHelper.implode("=? AND ", priKeys) + "=?";
        ArrayList<Object> arList = ArrayHelper.buildArrayList(fields, priKeys);
        return execute(sql, arList);
    }

    /**
     * Build a simple SQL Update Statement for table tablename using the contents of the HashMap as
     * data.
     * 
     * @param tablename
     *            name of the database table
     * @param fields
     *            HashMap with fields names and their values
     * @param priName
     *            Name of the primary key
     * @param priValue
     *            Object with primary key
     * @return rowCount number of rows affected
     * @throws SQLException
     */
    public <T> int update(String tablename, Map<String, Object> fields, String priName, T priValue)
            throws SQLException {
        Map<String, Object> priKeys = new HashMap<String, Object>();
        priKeys.put(priName, priValue);
        return update(tablename, fields, priKeys);
    }

    /**
     * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma separated list of fields
     * built from the ArrayList.
     * 
     * @param fields
     * @param query
     * @return an ArrayList with the results, or null
     * @throws SQLException
     */
    public List<Object> selectRow(String[] fields, String query) throws SQLException {
        String sql = "SELECT " + StringHelper.implode(", ", fields) + " " + query;
        List<Object> row = selectRow(sql);
        return row;
    }

    /**
     * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma separated list of fields
     * built from the ArrayList.
     * 
     * @param fields
     * @param query
     * @param param
     * @return an ArrayList with the results, or null
     * @throws SQLException
     */
    public List<Object> selectRow(String[] fields, String query, List<?> param) throws SQLException {
        String sql = "SELECT " + StringHelper.implode(", ", fields) + " " + query;
        List<Object> row = selectRow(sql, param);
        return row;
    }

    public List<Object> selectRow(String[] fields, String query, int param) throws SQLException {
        String sql = "SELECT " + StringHelper.implode(", ", fields) + " " + query;
        List<Object> row = selectRow(sql, param);
        return row;
    }

    /**
     * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma separated list of fields
     * built from the ArrayList. It supports AS to rename fields/give names to expressions.
     * 
     * @param fields
     *            the fields of the SELECT statement
     * @param query
     *            the rest of the query starting from FROM (included)
     * @return an HashMap with the results, or null
     * @throws SQLException
     */
    public Map<String, Object> selectRowHashMap(String[] fields, String query) throws SQLException {
        Map<String, Object> res = new HashMap<String, Object>();
        String sql = "SELECT " + StringHelper.implode(", ", fields) + " " + query;
        List<Object> row = selectRow(sql);
        if (row == null) {
            return null;
        }
        for (int i = 0; i < fields.length; i++) {
            res.put(SqlHelper.extractFieldName(fields[i]), row.get(i));
        }
        return res;
    }

    /**
     * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma separated list of fields
     * built from the ArrayList. It supports AS to rename fields/give names to expressions.
     * 
     * @param fields
     *            the fields of the SELECT statement
     * @param query
     *            the rest of the query starting from FROM (included)
     * @param param
     *            a List of IN parameters to be set in the query
     * @return an HashMap with the results, or null
     * @throws SQLException
     */
    public Map<String, Object> selectRowHashMap(String[] fields, String query, List<?> param)
            throws SQLException {
        Map<String, Object> res = new HashMap<String, Object>();
        String sql = "SELECT " + StringHelper.implode(", ", fields) + " " + query;
        List<Object> row = selectRow(sql, param);
        if (row == null) {
            return null;
        }
        for (int i = 0; i < fields.length; i++) {
            res.put(SqlHelper.extractFieldName(fields[i]), row.get(i));
        }
        return res;
    }

    /**
     * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma separated list of fields
     * built from the ArrayList. It supports AS to rename fields/give names to expressions.
     * 
     * @param fields
     *            the fields of the SELECT statement
     * @param query
     *            the rest of the query starting from FROM (included)
     * @param param
     *            an int IN parameters to be set in the query
     * @return an HashMap with the results, or null
     * @throws SQLException
     */
    public Map<String, Object> selectRowHashMap(String[] fields, String query, int param) throws SQLException {
        ArrayList<Object> arList = new ArrayList<Object>();
        arList.add(param);
        return selectRowHashMap(fields, query, arList);
    }

    /**
     * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma separated list of fields
     * built from the ArrayList. It supports AS to rename fields/give names to expressions.
     * 
     * @param fields
     *            the fields of the SELECT statement
     * @param query
     *            the rest of the query starting from FROM (included)
     * @param param
     *            a String IN parameter to be set in the query
     * @return an HashMap with the results, or null
     * @throws SQLException
     */
    public Map<String, Object> selectRowHashMap(String[] fields, String query, String param)
            throws SQLException {
        ArrayList<Object> arList = new ArrayList<Object>();
        arList.add(param);
        return selectRowHashMap(fields, query, arList);
    }

    /**
     * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma separated list of fields
     * built from the ArrayList. It supports AS to rename fields/give names to expressions.
     * 
     * @param fields
     *            the fields of the SELECT statement
     * @param query
     *            the rest of the query starting from FROM (included)
     * @param param
     *            an array of int of IN parameters to be set in the query
     * @return an HashMap with the results, or null
     * @throws SQLException
     */
    public Map<String, Object> selectRowHashMap(String[] fields, String query, int[] param)
            throws SQLException {
        ArrayList<Object> paramList = ArrayHelper.buildArrayList(param);
        return selectRowHashMap(fields, query, paramList);
    }

    /**
     * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma separated list of fields
     * built from the ArrayList. For now it doesn't recognize AS, but AS support can be added.
     * 
     * @param fields
     *            list of fields
     * @param query
     *            the body of the query (starting from FROM, included)
     * @return an ArrayList with the results, or null
     * @throws SQLException
     */
    public List<List<Object>> selectAll(String[] fields, String query) throws SQLException {
        String sql = "SELECT " + StringHelper.implode(", ", fields) + " " + query;
        List<List<Object>> matrix = selectAll(sql);
        return matrix;
    }

    /**
     * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma separated list of fields
     * built from the ArrayList. For now it doesn't recognize AS, but AS support can be added.
     * 
     * @param fields
     *            list of fields
     * @param query
     *            the body of the query (starting from FROM, included)
     * @param param
     *            a list of parameters
     * @return an ArrayList with the results, or null
     * @throws SQLException
     */
    public List<List<Object>> selectAll(String[] fields, String query, List<?> param) throws SQLException {
        String sql = "SELECT " + StringHelper.implode(", ", fields) + " " + query;
        List<List<Object>> matrix = selectAll(sql, param);
        return matrix;
    }

    /**
     * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma separated list of fields
     * built from the ArrayList. For now it doesn't recognize AS, but AS support can be added.
     * 
     * @param fields
     *            list of fields
     * @param query
     *            the body of the query (starting from FROM, included)
     * @param param
     *            a single integer parameter
     * @return an ArrayList with the results, or null
     * @throws SQLException
     */
    public List<List<Object>> selectAll(String[] fields, String query, int param) throws SQLException {
        String sql = "SELECT " + StringHelper.implode(", ", fields) + " " + query;
        List<List<Object>> matrix = selectAll(sql, param);
        return matrix;
    }

    /**
     * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma separated list of fields
     * built from the ArrayList. It supports AS to rename fields/give names to expressions.
     * 
     * @param fields
     * @param query
     * @return an ArrayList of HashMap(s) with the results, or null
     * @throws SQLException
     */
    public List<Map<String, Object>> selectAllHashMap(String[] fields, String query) throws SQLException {
        Map<String, Object> rowAsHashMap;
        List<Object> rowAsList;
        ArrayList<Map<String, Object>> res;
        String sql = "SELECT " + StringHelper.implode(", ", fields) + " " + query;
        List<List<Object>> matrix = selectAll(sql);
        if (matrix == null) {
            return null;
        }
        res = new ArrayList<Map<String, Object>>();
        for (Iterator<List<Object>> it = matrix.iterator(); it.hasNext();) {
            rowAsHashMap = new HashMap<String, Object>();
            rowAsList = it.next();
            for (int i = 0; i < fields.length; i++) {
                rowAsHashMap.put(SqlHelper.extractFieldName(fields[i]), rowAsList.get(i));
            }
            res.add(rowAsHashMap);
        }
        return res;
    }

    /**
     * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma separated list of fields
     * built from the ArrayList. It supports AS to rename fields/give names to expressions.
     * 
     * @param fields
     * @param query
     * @param param
     * @return an ArrayList of HashMap(s) with the results, or null
     * @throws SQLException
     */
    public List<Map<String, Object>> selectAllHashMap(String[] fields, String query, ArrayList<?> param)
            throws SQLException {
        Map<String, Object> rowHM;
        List<Object> rowAL;
        ArrayList<Map<String, Object>> res;
        String sql = "SELECT " + StringHelper.implode(", ", fields) + " " + query;
        List<List<Object>> matrix = selectAll(sql, param);
        if (matrix == null) {
            return null;
        }
        res = new ArrayList<Map<String, Object>>();
        for (Iterator<List<Object>> it = matrix.iterator(); it.hasNext();) {
            rowHM = new HashMap<String, Object>();
            rowAL = it.next();
            for (int i = 0; i < fields.length; i++) {
                rowHM.put(SqlHelper.extractFieldName(fields[i]), rowAL.get(i));
            }
            res.add(rowHM);
        }
        return res;
    }

    public List<Map<String, Object>> selectAllHashMap(String[] fields, String sql, int[] param)
            throws SQLException {
        ArrayList<Object> arList = ArrayHelper.buildArrayList(param);
        return selectAllHashMap(fields, sql, arList);
    }

    /**
     * <pre>
     * public List&lt;Map&lt;String, Object&gt;&gt; selectAllHashMap(String[] fields, String query, int param)
     *         throws SQLException {
     *     ArrayList&lt;Object&gt; arList = new ArrayList&lt;Object&gt;();
     *     arList.add(param);
     *     return selectAllHashMap(fields, query, arList);
     * }
     * 
     * public List&lt;Map&lt;String, Object&gt;&gt; selectAllHashMap(String[] fields, String sql, String param)
     *         throws SQLException {
     *     ArrayList&lt;Object&gt; arList = new ArrayList&lt;Object&gt;();
     *     arList.add(param);
     *     return selectAllHashMap(fields, sql, arList);
     * }
     * @throws SQLException
     */
    public <T> List<Map<String, Object>> selectAllHashMap(String[] fields, String sql, T param)
            throws SQLException {
        ArrayList<Object> arList = new ArrayList<Object>();
        arList.add(param);
        return selectAllHashMap(fields, sql, arList);
    }

    /**
     * Define whether database changes done on the database be automatically committed. This
     * function may also implicitly start or end a transaction.
     * 
     * @param autoCommit
     *            is a boolean flag that indicates whether the database changes should be committed
     *            right after executing every query statement. If this argument is false a
     *            transaction implicitly started. Otherwise, if a transaction is in progress it is
     *            ended by committing any database changes that were pending.
     * @throws SQLException
     */
    public void autoCommitTransactions(boolean autoCommit) throws SQLException {
        conn.setAutoCommit(autoCommit);
    }

    /**
     * Commit the database changes done during a transaction that is in progress. This method may
     * only be called when auto-committing is disabled, otherwise it will fail. Therefore, a new
     * transaction is implicitly started after committing the pending changes.
     * 
     * @throws SQLException
     */
    public void commitTransaction() throws SQLException {
        connectionManager.commitTransaction();
    }

    /**
     * Cancel any database changes done during a transaction that is in progress. This function may
     * only be called when auto-committing is disabled, otherwise it will fail. Therefore, a new
     * transaction is implicitly started after canceling the pending changes.
     * 
     * @throws SQLException
     */
    public void rollbackTransaction() throws SQLException {
        connectionManager.rollbackTransaction();
    }

    /**
     * Execute the query an extract the blob field in row 1, column 1 (first row, first column)
     * 
     * @param sql
     *            the sql to be executed
     * @return the object as a byte[]
     * @throws SQLException
     */
    public byte[] selectBlobField(String sql) throws SQLException {
        ResultSet res = executeRawQuery(sql);
        byte[] immagine = null;
        if (!res.next()) {
            throw new SQLException("No rows returned");
        }
        immagine = SqlHelper.fetchBlobFromCurrentRowInResulSet(res, 1);
        closeResultSetAndStatement();
        return immagine;
    }

}
