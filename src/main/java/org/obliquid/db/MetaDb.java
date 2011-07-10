package org.obliquid.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface MetaDb {

    /**
     * Execute a raw query. Remember to close the created ResultSet and Statement by calling the
     * method closeResultSetAndStatement()
     * 
     * @return a ResultSet object
     * @throws SQLException
     */
    public abstract ResultSet executeRawQuery(String sql) throws SQLException;

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
    public abstract Connection getConnection(boolean usePool) throws SQLException;

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
    public abstract Connection getConnection() throws SQLException;

    /**
     * Release a connection. If it's a standalone Connection, the connection is closed, otherwise is
     * returned to the pool. In any case always remember to close open connections to avoid
     * connections leaking. Connections should be closed in the finally clause to be able to close
     * them even when an Exception is raised. It doesn't throw SQLException any more, since if it
     * would, there would be nothing we could do about it, so throwing it is pointless.
     */
    public abstract void releaseConnection();

    /**
     * Executes a SQL statement.
     * 
     * @param sql
     *            must be an SQL INSERT, UPDATE or DELETE statement or an SQL statement that doesn't
     *            return anything.
     * @return rowCount number of rows affected
     * @throws SQLException
     */
    public abstract int execute(String sql) throws SQLException;

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
    public abstract int execute(String sql, List<?> param) throws SQLException;

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
    public abstract int execute(String sql, String[] param) throws SQLException;

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
    public abstract <T> int execute(String sql, T param) throws SQLException;

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
    public abstract int execute(String sql, Map<String, Object> param) throws SQLException;

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
    public abstract int execute(String sql, int[] param) throws SQLException;

    /**
     * Executes the specified SELECT query, fetch the value from the first column of the first row
     * of the result set into a given variable and then frees the result set.
     * 
     * @param sql
     *            the SELECT query statement to be executed.
     * @return the fetched value in a Java Object.
     * @throws SQLException
     */
    public abstract Object selectField(String sql) throws SQLException;

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
    public abstract Object selectField(String sql, List<?> param) throws SQLException;

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
    public abstract Object selectField(String sql, String[] param) throws SQLException;

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
    public abstract Object selectField(String sql, int[] param) throws SQLException;

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
    public abstract <T> Object selectField(String sql, T param) throws SQLException;

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
    public abstract List<Object> selectRow(String sql) throws SQLException;

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
    public abstract List<Object> selectRow(String sql, List<?> param) throws SQLException;

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
    public abstract List<Object> selectRow(String sql, int param) throws SQLException;

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
    public abstract List<Object> selectRow(String sql, String param) throws SQLException;

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
    public abstract List<Object> selectRow(String sql, String[] param) throws SQLException;

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
    public abstract List<Object> selectRow(String sql, int[] param) throws SQLException;

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
    public abstract List<Object> selectColumn(String sql) throws SQLException;

    /**
     * Executes the specified query, fetch the first column of all the rows of the result set, frees
     * the result set and returns the fetched column in an array of String.
     * 
     * @param sql
     *            the sql statement to be executed
     * @return a column of results
     * @throws SQLException
     */
    public abstract String[] selectColumnToStringArray(String sql) throws SQLException;

    /**
     * Executes the specified query, fetch the first column of all the rows of the result set, frees
     * the result set and returns the fetched column in an array of int.
     * 
     * @param sql
     *            the sql statement to be executed
     * @return a column of results
     * @throws SQLException
     */
    public abstract int[] selectColumnToIntArray(String sql) throws SQLException;

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
    public abstract List<Object> selectColumn(String sql, int[] param) throws SQLException;

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
    public abstract List<Object> selectColumn(String sql, int param) throws SQLException;

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
    public abstract List<Object> selectColumn(String sql, List<?> param) throws SQLException;

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
    public abstract List<List<Object>> selectAll(String sql) throws SQLException;

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
    public abstract List<List<Object>> selectAll(String sql, List<?> param) throws SQLException;

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
    public abstract List<List<Object>> selectAll(String sql, String[] param) throws SQLException;

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
    public abstract List<List<Object>> selectAll(String sql, int[] param) throws SQLException;

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
    public abstract <T> List<List<Object>> selectAll(String sql, T param) throws SQLException;

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
    public abstract String insertDelayed(String tablename, Map<String, Object> fields) throws SQLException;

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
    public abstract String insert(String tablename, Map<String, Object> fields) throws SQLException;

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
    public abstract int update(String tablename, Map<String, Object> fields, Map<String, Object> priKeys)
            throws SQLException;

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
    public abstract <T> int update(String tablename, Map<String, Object> fields, String priName, T priValue)
            throws SQLException;

    /**
     * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma separated list of fields
     * built from the String Array.
     * 
     * @param fields
     * @param query
     * @return an ArrayList with the results, or null
     * @throws SQLException
     */
    public abstract List<Object> selectRow(String[] fields, String query) throws SQLException;

    /**
     * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma separated list of fields
     * built from the String Array.
     * 
     * @param fields
     * @param query
     * @param param
     * @return an ArrayList with the results, or null
     * @throws SQLException
     */
    public abstract List<Object> selectRow(String[] fields, String query, List<?> param) throws SQLException;

    public abstract List<Object> selectRow(String[] fields, String query, int param) throws SQLException;

    /**
     * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma separated list of fields
     * built from the String Array. It supports AS to rename fields/give names to expressions.
     * 
     * @param fields
     *            the fields of the SELECT statement
     * @param query
     *            the rest of the query starting from FROM (included)
     * @return an HashMap with the results, or null
     * @throws SQLException
     */
    public abstract Map<String, Object> selectRowHashMap(String[] fields, String query) throws SQLException;

    /**
     * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma separated list of fields
     * built from the String Array. It supports AS to rename fields/give names to expressions.
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
    public abstract Map<String, Object> selectRowHashMap(String[] fields, String query, List<?> param)
            throws SQLException;

    /**
     * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma separated list of fields
     * built from the String Array. It supports AS to rename fields/give names to expressions.
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
    public abstract Map<String, Object> selectRowHashMap(String[] fields, String query, int param)
            throws SQLException;

    /**
     * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma separated list of fields
     * built from the List. It supports AS to rename fields/give names to expressions.
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
    public abstract Map<String, Object> selectRowHashMap(List<String> fields, String query, int param)
            throws SQLException;

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
    public abstract Map<String, Object> selectRowHashMap(String[] fields, String query, String param)
            throws SQLException;

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
    public abstract Map<String, Object> selectRowHashMap(String[] fields, String query, int[] param)
            throws SQLException;

    /**
     * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma separated list of fields
     * built from the Array fields. For now it doesn't recognize AS, but AS support can be added.
     * 
     * @param fields
     *            list of fields
     * @param query
     *            the body of the query (starting from FROM, included)
     * @return an ArrayList with the results, or null
     * @throws SQLException
     */
    public abstract List<List<Object>> selectAll(String[] fields, String query) throws SQLException;

    /**
     * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma separated list of fields
     * built from fields List. No AS support for now.
     * 
     * @param fields
     *            list of fields
     * @param sql
     *            the body of the query (starting from FROM, included)
     * @return an ArrayList with the results, or null
     * @throws SQLException
     */
    public abstract List<List<Object>> selectAll(List<String> fields, String sql) throws SQLException;

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
    public abstract List<List<Object>> selectAll(String[] fields, String query, List<?> param)
            throws SQLException;

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
    public abstract List<List<Object>> selectAll(String[] fields, String query, int param)
            throws SQLException;

    /**
     * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma separated list of fields
     * built from the ArrayList. It supports AS to rename fields/give names to expressions.
     * 
     * @param fields
     * @param query
     * @return an ArrayList of HashMap(s) with the results, or null
     * @throws SQLException
     */
    public abstract List<Map<String, Object>> selectAllHashMap(List<String> fields, String query)
            throws SQLException;

    /**
     * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma separated list of fields
     * built from the ArrayList. It supports AS to rename fields/give names to expressions.
     * 
     * @param fields
     * @param query
     * @return an ArrayList of HashMap(s) with the results, or null
     * @throws SQLException
     */
    public abstract List<Map<String, Object>> selectAllHashMap(String[] fields, String query)
            throws SQLException;

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
    public abstract List<Map<String, Object>> selectAllHashMap(String[] fields, String query,
            ArrayList<?> param) throws SQLException;

    public abstract List<Map<String, Object>> selectAllHashMap(String[] fields, String sql, int[] param)
            throws SQLException;

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
    public abstract <T> List<Map<String, Object>> selectAllHashMap(String[] fields, String sql, T param)
            throws SQLException;

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
    public abstract void autoCommitTransactions(boolean autoCommit) throws SQLException;

    /**
     * Commit the database changes done during a transaction that is in progress. This method may
     * only be called when auto-committing is disabled, otherwise it will fail. Therefore, a new
     * transaction is implicitly started after committing the pending changes.
     * 
     * @throws SQLException
     */
    public abstract void commitTransaction() throws SQLException;

    /**
     * Cancel any database changes done during a transaction that is in progress. This function may
     * only be called when auto-committing is disabled, otherwise it will fail. Therefore, a new
     * transaction is implicitly started after canceling the pending changes.
     * 
     * @throws SQLException
     */
    public abstract void rollbackTransaction() throws SQLException;

    /**
     * Execute the query an extract the blob field in row 1, column 1 (first row, first column)
     * 
     * @param sql
     *            the sql to be executed
     * @return the object as a byte[]
     * @throws SQLException
     */
    public abstract byte[] selectBlobField(String sql) throws SQLException;

}