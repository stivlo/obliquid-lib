package org.obliquid.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Manage interactions with database.
 * 
 * @author stivlo
 */
public interface MetaDb {

        /**
         * Execute a raw query. Remember to close the created ResultSet and
         * Statement by calling the method closeResultSetAndStatement().
         * 
         * @param sql
         *                the query to execute
         * @return a ResultSet object
         * @throws SQLException
         *                 in case of connection problems or with the query
         */
        ResultSet executeRawQuery(String sql) throws SQLException;

        /**
         * Get a Connection, either from the pool or stand-alone, or from the
         * internal instance variable holding a Connection. In order to use this
         * method to get a connection from the pool, you've to extend this class
         * and override getDataSource Method. In the case of a stand-alone
         * connection, you've to extend this class and override getDriver(),
         * getUrl(), getUsername(), getPassword(). A good rule should be to not
         * mix stand-alone connections and connection pooling in the same
         * program execution.
         * 
         * @param usePool
         *                use a connection from the pool if usePool is true,
         *                otherwise open a normal BB connection
         * @return a Connection to the DB that should be used only to handle
         *         special cases, normally methods from this class should be
         *         enough for common DB operations.
         * @throws SQLException
         *                 in case of problems
         */
        Connection getConnection(boolean usePool) throws SQLException;

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
         *                 in case of problems
         */
        Connection getConnection() throws SQLException;

        /**
         * Release a connection. If it's a stand-alone Connection, the
         * connection is closed, otherwise is returned to the pool. In any case
         * always remember to close open connections to avoid connections
         * leaking. Connections should be closed in the finally clause to be
         * able to close them even when an Exception is raised. It doesn't throw
         * SQLException any more, since if it would, there would be nothing we
         * could do about it, so throwing it is pointless.
         */
        void releaseConnection();

        /**
         * Executes a SQL statement.
         * 
         * @param sql
         *                must be an SQL INSERT, UPDATE or DELETE statement or
         *                an SQL statement that doesn't return anything.
         * @return rowCount number of rows affected
         * @throws SQLException
         *                 in case of problems
         */
        int execute(String sql) throws SQLException;

        /**
         * Executes a SQL statement.
         * 
         * @param sql
         *                must be an SQL INSERT, UPDATE or DELETE statement or
         *                an SQL statement that doesn't return anything.
         * @param param
         *                List with IN parameters to be set in the query
         * @return rowCount number of rows affected
         * @throws SQLException
         *                 in case of problems
         */
        int execute(String sql, List<?> param) throws SQLException;

        /**
         * Executes a SQL statement.
         * 
         * @param sql
         *                must be an SQL INSERT, UPDATE or DELETE statement or
         *                an SQL statement that doesn't return anything.
         * @param param
         *                String array with IN parameters to be set in the query
         * @return rowCount number of rows affected
         * @throws SQLException
         *                 in case of problems
         */
        int execute(String sql, String[] param) throws SQLException;

        /**
         * Executes a SQL statement.
         * 
         * @param <T>
         *                the generic type
         * @param sql
         *                must be an SQL INSERT, UPDATE or DELETE statement or
         *                an SQL statement that doesn't return anything.
         * @param param
         *                IN parameter to be set in the query
         * @return rowCount number of rows affected
         * @throws SQLException
         *                 in case of problems
         */
        <T> int execute(String sql, T param) throws SQLException;

        /**
         * Execute a SQL statement.
         * 
         * @param sql
         *                must be an SQL INSERT, UPDATE or DELETE statement or
         *                an SQL statement that doesn't return anything.
         * @param param
         *                HashMap with IN parameters to be set in the query. The
         *                values of the HashMap will be used and the keys not
         *                considered.
         * @return rowCount number of rows affected
         * @throws SQLException
         *                 in case of problems
         */
        int execute(String sql, Map<String, Object> param) throws SQLException;

        /**
         * Execute a SQL statement.
         * 
         * @param sql
         *                must be an SQL INSERT, UPDATE or DELETE statement or
         *                an SQL statement that doesn't return anything.
         * @param param
         *                the parameter to be passed in the query
         * @return rowCount number of rows affected
         * @throws SQLException
         *                 in case of problems
         */
        int execute(String sql, int[] param) throws SQLException;

        /**
         * Executes the specified SELECT query, fetch the value from the first
         * column of the first row of the result set into a given variable and
         * then frees the result set.
         * 
         * @param sql
         *                the SELECT query statement to be executed.
         * @return the fetched value in a Java Object.
         * @throws SQLException
         *                 in case of problems
         */
        Object selectField(String sql) throws SQLException;

        /**
         * Executes the specified prepared query, fetch the value from the first
         * column of the first row of the result set into a given variable and
         * then frees the result set.
         * 
         * @param sql
         *                the SELECT query statement to be executed.
         * @param param
         *                List with IN parameters to be set in the query
         * @return the fetched value in a Java Object or null
         * @throws SQLException
         *                 in case of problems
         */
        Object selectField(String sql, List<?> param) throws SQLException;

        /**
         * Executes the specified prepared query, fetch the value from the first
         * column of the first row of the result and then frees the result set.
         * 
         * @param sql
         *                the SELECT query statement to be executed.
         * @param param
         *                String array with IN parameters to be set in the query
         * @return the fetched value in an Object
         * @throws SQLException
         *                 in case of problems
         */
        Object selectField(String sql, String[] param) throws SQLException;

        /**
         * Executes the specified prepared query, fetch the value from the first
         * column of the first row of the result and then frees the result set.
         * 
         * @param sql
         *                the SELECT query statement to be executed.
         * @param param
         *                integer array with IN parameters to be set in the
         *                query
         * @return the fetched value in an Object
         * @throws SQLException
         *                 in case of problems
         */
        Object selectField(String sql, int[] param) throws SQLException;

        /**
         * Executes the specified prepared query, fetch the value from the first
         * column of the first row of the result and then frees the result set.
         * 
         * @param <T>
         *                the generic type to be used
         * @param sql
         *                the SELECT query statement to be executed.
         * @param param
         *                a String IN parameter to be set in the query
         * @return the fetched value in an Object
         * @throws SQLException
         *                 in case of problems
         */
        <T> Object selectField(String sql, T param) throws SQLException;

        /**
         * Executes the specified query, fetch the first row of the result set
         * row, frees the result set and returns the fetched row in a List.
         * 
         * @param sql
         *                the SELECT query statement to be executed.
         * @return an List populated with the Objects contained in the fetched
         *         row or null if no rows were fetched.
         * @throws SQLException
         *                 in case of problems
         */
        List<Object> selectRow(String sql) throws SQLException;

        /**
         * Executes the specified query, fetch the first row of the result set
         * row, frees the result set and returns the fetched row in a List.
         * 
         * @param sql
         *                the SELECT query statement to be executed.
         * @param param
         *                List with IN parameters to be set in the query
         * @return a List populated with the Objects contained in the fetched
         *         row or null if no rows were fetched.
         * @throws SQLException
         *                 in case of problems
         */
        List<Object> selectRow(String sql, List<?> param) throws SQLException;

        /**
         * Executes the specified query, fetch the first row of the result set
         * row, frees the result set and returns the fetched row in a List.
         * 
         * @param sql
         *                the SELECT query statement to be executed.
         * @param param
         *                an integer IN parameters to be set in the query
         * @return a List populated with the Objects contained in the fetched
         *         row or null if no rows were fetched.
         * @throws SQLException
         *                 in case of problems
         */
        List<Object> selectRow(String sql, int param) throws SQLException;

        /**
         * Executes the specified query, fetch the first row of the result set
         * row, frees the result set and returns the fetched row in a List.
         * 
         * @param sql
         *                the SELECT query statement to be executed.
         * @param param
         *                an String IN parameters to be set in the query
         * @return a List populated with the Objects contained in the fetched
         *         row or null if no rows were fetched.
         * @throws SQLException
         *                 in case of problems
         */
        List<Object> selectRow(String sql, String param) throws SQLException;

        /**
         * Executes the specified query, fetch the first row of the result set
         * row, frees the result set and returns the fetched row in a List.
         * 
         * @param sql
         *                the SELECT query statement to be executed.
         * @param param
         *                String array with IN parameters to be set in the query
         * @return a List populated with the Objects contained in the fetched
         *         row or null if no rows were fetched.
         * @throws SQLException
         *                 in case of problems
         */
        List<Object> selectRow(String sql, String[] param) throws SQLException;

        /**
         * Executes the specified query, fetch the first row of the result set
         * row, frees the result set and returns the fetched row in a List.
         * 
         * @param sql
         *                the SQL statement to be executed
         * @param param
         *                an array of integer parameters
         * @return a row of results
         * @throws SQLException
         *                 in case of problems
         */
        List<Object> selectRow(String sql, int[] param) throws SQLException;

        /**
         * Executes the specified query, fetch the first column of all the rows
         * of the result set, frees the result set and returns the fetched
         * column in a List.
         * 
         * @param sql
         *                the SQL statement to be executed
         * 
         * @return a column of results
         * @throws SQLException
         *                 in case of problems
         */
        List<Object> selectColumn(String sql) throws SQLException;

        /**
         * Executes the specified query, fetch the first column of all the rows
         * of the result set, frees the result set and returns the fetched
         * column in an array of String.
         * 
         * @param sql
         *                the SQL statement to be executed
         * @return a column of results
         * @throws SQLException
         *                 in case of problems
         */
        String[] selectColumnToStringArray(String sql) throws SQLException;

        /**
         * Executes the specified query, fetch the first column of all the rows
         * of the result set, frees the result set and returns the fetched
         * column in an array of integer.
         * 
         * @param sql
         *                the SQL statement to be executed
         * @return a column of results
         * @throws SQLException
         *                 in case of problems
         */
        int[] selectColumnToIntArray(String sql) throws SQLException;

        /**
         * Executes the specified query, fetch the first column of all the rows
         * of the result set, frees the result set and returns the fetched
         * column in a List.
         * 
         * @param sql
         *                the SQL statement to be executed
         * @param param
         *                an array of integer to be substituted as parameters
         * @return a column of results
         * @throws SQLException
         *                 in case of problems
         */
        List<Object> selectColumn(String sql, int[] param) throws SQLException;

        /**
         * Executes the specified query, fetch the first column of all the rows
         * of the result set, frees the result set and returns the fetched
         * column in a List.
         * 
         * @param sql
         *                the SQL statement to be executed
         * @param param
         *                the parameter to be passed to SQL
         * @return a List of Object
         * @throws SQLException
         *                 in case of problems
         */
        List<Object> selectColumn(String sql, int param) throws SQLException;

        /**
         * Executes the specified query, fetch the first column of all the rows
         * of the result set, frees the result set and returns the fetched
         * column in a List.
         * 
         * @param sql
         *                the SQL statement to be executed
         * @param param
         *                a List of IN parameters to be set in the query
         * @return a column of results
         * @throws SQLException
         *                 in case of problems
         */
        List<Object> selectColumn(String sql, List<?> param) throws SQLException;

        /**
         * Executes the specified SELECT query, fetch all rows of the result set
         * row into a given two dimension List Object and then frees the result
         * set.
         * 
         * @param sql
         *                the SELECT query statement to be executed.
         * @return a List populated with rows fetched. Each row is a List Object
         *         populated with the Objects contained in the row. If no rows
         *         were fetched, queryAll() returns null.
         * @throws SQLException
         *                 in case of problems
         */
        List<List<Object>> selectAll(String sql) throws SQLException;

        /**
         * Executes the specified query, fetch all rows of the result set row
         * into a given two dimension List Object and then frees the result set.
         * 
         * @param sql
         *                the SELECT query statement to be executed
         * @param param
         *                List with IN parameters to be set in the query
         * @return an List populated with rows fetched. Each row is an List
         *         Object populated with the Objects contained in the row. If no
         *         rows were fetched, queryAll() returns null.
         * @throws SQLException
         *                 in case of problems
         */
        List<List<Object>> selectAll(String sql, List<?> param) throws SQLException;

        /**
         * Executes the specified SELECT query, fetch all rows of the result set
         * row into a given two dimension List Object and then frees the result
         * set.
         * 
         * @param sql
         *                the SELECT query statement to be executed
         * @param param
         *                String array with IN parameters to be set in the query
         * @return an List populated with rows fetched. Each row is an List
         *         Object populated with the Objects contained in the row. If no
         *         rows were fetched, queryAll() returns null.
         * @throws SQLException
         *                 in case of problems
         */
        List<List<Object>> selectAll(String sql, String[] param) throws SQLException;

        /**
         * selectAll for an integer array.
         * 
         * @param sql
         *                the SQL to be executed
         * @param param
         *                an integer array parameter.
         * @return all rows of results as a matrix
         * @throws SQLException
         *                 in case of problems
         */
        List<List<Object>> selectAll(String sql, int[] param) throws SQLException;

        /**
         * queryAll for Object.
         * 
         * @param <T>
         *                the generic type
         * @param sql
         *                the SQL statement to be executed
         * @param param
         *                a parameter to be substituted
         * @return all rows of results as a matrix
         * @throws SQLException
         *                 in case of problems
         */
        <T> List<List<Object>> selectAll(String sql, T param) throws SQLException;

        /**
         * Build a SQL Insert Statement for the table specified using the
         * contents of the HashMap as data, using the INSERT DELAYED statement.
         * 
         * @param tablename
         *                name of the database table
         * @param fields
         *                HashMap with fields names and their values
         * @return the query executed
         * @throws SQLException
         *                 in case of problems
         */
        String insertDelayed(String tablename, Map<String, Object> fields) throws SQLException;

        /**
         * Build a SQL Insert Statement for the table specified using the
         * contents of the HashMap as data.
         * 
         * @param tablename
         *                name of the database table
         * @param fields
         *                HashMap with fields names and their values
         * @return the query executed
         * @throws SQLException
         *                 in case of problems
         */
        String insert(String tablename, Map<String, Object> fields) throws SQLException;

        /**
         * Build a simple SQL Update Statement for table tablename using the
         * contents of the HashMap as data.
         * 
         * @param tablename
         *                name of the database table
         * @param fields
         *                HashMap with fields names and their values
         * @param priKeys
         *                HashMap with primary key(s)
         * @return rowCount number of rows affected
         * @throws SQLException
         *                 in case of problems
         */
        int update(String tablename, Map<String, Object> fields, Map<String, Object> priKeys)
                        throws SQLException;

        /**
         * Build a simple SQL Update Statement for the table specified using the
         * contents of the HashMap as data.
         * 
         * @param <T>
         *                generic type
         * @param tablename
         *                name of the database table
         * @param fields
         *                HashMap with fields names and their values
         * @param priName
         *                Name of the primary key
         * @param priValue
         *                Object with primary key
         * @return rowCount number of rows affected
         * @throws SQLException
         *                 in case of problems
         */
        <T> int update(String tablename, Map<String, Object> fields, String priName, T priValue)
                        throws SQLException;

        /**
         * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma
         * separated list of fields built from the String Array.
         * 
         * @param fields
         *                the field names
         * @param query
         *                the rest of the query
         * @return a List with the results, or null
         * @throws SQLException
         *                 in case of problems
         */
        List<Object> selectRow(String[] fields, String query) throws SQLException;

        /**
         * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma
         * separated list of fields built from the String Array.
         * 
         * @param fields
         *                the field names
         * @param query
         *                the rest of the query
         * @param param
         *                a List of query parameters
         * @return a List with the results, or null
         * @throws SQLException
         *                 in case of problems
         */
        List<Object> selectRow(String[] fields, String query, List<?> param) throws SQLException;

        /**
         * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma
         * separated list of fields built from the String Array.
         * 
         * @param fields
         *                the field names
         * @param query
         *                the rest of the query
         * @param param
         *                a integer parameter
         * @return a List with the results, or null
         * @throws SQLException
         *                 in case of problems
         */
        List<Object> selectRow(String[] fields, String query, int param) throws SQLException;

        /**
         * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma
         * separated list of fields built from the String Array. It supports AS
         * to rename fields/give names to expressions.
         * 
         * @param fields
         *                the fields of the SELECT statement
         * @param query
         *                the rest of the query starting from FROM (included)
         * @return an HashMap with the results, or null
         * @throws SQLException
         *                 in case of problems
         */
        Map<String, Object> selectRowHashMap(String[] fields, String query) throws SQLException;

        /**
         * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma
         * separated list of fields built from the String Array. It supports AS
         * to rename fields/give names to expressions.
         * 
         * @param fields
         *                the fields of the SELECT statement
         * @param query
         *                the rest of the query starting from FROM (included)
         * @param param
         *                a List of IN parameters to be set in the query
         * @return an HashMap with the results, or null
         * @throws SQLException
         *                 in case of problems
         */
        Map<String, Object> selectRowHashMap(String[] fields, String query, List<?> param)
                        throws SQLException;

        /**
         * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma
         * separated list of fields built from the String Array. It supports AS
         * to rename fields/give names to expressions.
         * 
         * @param fields
         *                the fields of the SELECT statement
         * @param query
         *                the rest of the query starting from FROM (included)
         * @param param
         *                an integer IN parameters to be set in the query
         * @return an HashMap with the results, or null
         * @throws SQLException
         *                 in case of problems
         */
        Map<String, Object> selectRowHashMap(String[] fields, String query, int param) throws SQLException;

        /**
         * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma
         * separated list of fields built from the List. It supports AS to
         * rename fields/give names to expressions.
         * 
         * @param fields
         *                the fields of the SELECT statement
         * @param query
         *                the rest of the query starting from FROM (included)
         * @param param
         *                an int IN parameters to be set in the query
         * @return an HashMap with the results, or null
         * @throws SQLException
         *                 in case of problems
         */
        Map<String, Object> selectRowHashMap(List<String> fields, String query, int param)
                        throws SQLException;

        /**
         * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma
         * separated list of fields built from the List. It supports AS to
         * rename fields/give names to expressions.
         * 
         * @param fields
         *                the fields of the SELECT statement
         * @param query
         *                the rest of the query starting from FROM (included)
         * @param param
         *                an String IN parameters to be set in the query
         * @return an HashMap with the results, or null
         * @throws SQLException
         *                 in case of problems
         */
        Map<String, Object> selectRowHashMap(List<String> fields, String query, String param)
                        throws SQLException;

        /**
         * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma
         * separated list of fields built from the List. It supports AS to
         * rename fields/give names to expressions.
         * 
         * @param fields
         *                the fields of the SELECT statement
         * @param query
         *                the rest of the query starting from FROM (included)
         * @param param
         *                a String IN parameter to be set in the query
         * @return an HashMap with the results, or null
         * @throws SQLException
         *                 in case of problems
         */
        Map<String, Object> selectRowHashMap(String[] fields, String query, String param)
                        throws SQLException;

        /**
         * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma
         * separated list of fields built from the List. It supports AS to
         * rename fields/give names to expressions.
         * 
         * @param fields
         *                the fields of the SELECT statement
         * @param query
         *                the rest of the query starting from FROM (included)
         * @param param
         *                an array of int of IN parameters to be set in the
         *                query
         * @return an HashMap with the results, or null
         * @throws SQLException
         *                 in case of problems
         */
        Map<String, Object> selectRowHashMap(String[] fields, String query, int[] param) throws SQLException;

        /**
         * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma
         * separated list of fields built from the Array fields. For now it
         * doesn't recognise AS, but AS support can be added.
         * 
         * @param fields
         *                list of fields
         * @param query
         *                the body of the query (starting from FROM, included)
         * @return a List with the results, or null
         * @throws SQLException
         *                 in case of problems
         */
        List<List<Object>> selectAll(String[] fields, String query) throws SQLException;

        /**
         * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma
         * separated list of fields built from fields List. No AS support for
         * now.
         * 
         * @param fields
         *                list of fields
         * @param sql
         *                the body of the query (starting from FROM, included)
         * @return an List with the results, or null
         * @throws SQLException
         *                 in case of problems
         */
        List<List<Object>> selectAll(List<String> fields, String sql) throws SQLException;

        /**
         * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma
         * separated list of fields built from the List. For now it doesn't
         * recognise AS, but AS support can be added.
         * 
         * @param fields
         *                list of fields
         * @param query
         *                the body of the query (starting from FROM, included)
         * @param param
         *                a list of parameters
         * @return a List with the results, or null
         * @throws SQLException
         *                 in case of problems
         */
        List<List<Object>> selectAll(String[] fields, String query, List<?> param) throws SQLException;

        /**
         * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma
         * separated list of fields built from the List. For now it doesn't
         * recognise AS, but AS support can be added.
         * 
         * @param fields
         *                list of fields
         * @param query
         *                the body of the query (starting from FROM, included)
         * @param param
         *                a single integer parameter
         * @return a List with the results, or null
         * @throws SQLException
         *                 in case of problems
         */
        List<List<Object>> selectAll(String[] fields, String query, int param) throws SQLException;

        /**
         * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma
         * separated list of fields built from the List. It supports AS to
         * rename fields/give names to expressions.
         * 
         * @param fields
         *                a List of field names
         * @param query
         *                the rest of the query
         * @return a List of HashMap(s) with the results, or null
         * @throws SQLException
         *                 in case of problems
         */
        List<Map<String, Object>> selectAllHashMap(List<String> fields, String query) throws SQLException;

        /**
         * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma
         * separated list of fields built from the List. It supports AS to
         * rename fields/give names to expressions.
         * 
         * @param fields
         *                a String array of field names
         * @param query
         *                the rest of the query
         * @return a List of HashMap(s) with the results, or null
         * @throws SQLException
         *                 in case of problems
         */
        List<Map<String, Object>> selectAllHashMap(String[] fields, String query) throws SQLException;

        /**
         * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma
         * separated list of fields built from the List. It supports AS to
         * rename fields/give names to expressions.
         * 
         * @param fields
         *                a String array of field names
         * @param query
         *                the rest of the query
         * @param param
         *                a List of parameters
         * @return a List of HashMap(s) with the results, or null
         * @throws SQLException
         *                 in case of problems
         */
        List<Map<String, Object>> selectAllHashMap(String[] fields, String query, List<?> param)
                        throws SQLException;

        /**
         * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma
         * separated list of fields built from the List. It supports AS to
         * rename fields/give names to expressions.
         * 
         * @param fields
         *                a String array of field names
         * @param query
         *                the rest of the query
         * @param param
         *                an array of integer parameters
         * @return an List of HashMap(s) with the results, or null
         * @throws SQLException
         *                 in case of problems
         */
        List<Map<String, Object>> selectAllHashMap(String[] fields, String query, int[] param)
                        throws SQLException;

        /**
         * Build a SELECT query as SELECT <fields> <query>. <fields> is a comma
         * separated list of fields built from the List. It supports AS to
         * rename fields/give names to expressions.
         * 
         * @param <T>
         *                the generic type
         * @param fields
         *                a String array of field names
         * @param query
         *                the rest of the query
         * @param param
         *                a parameter of type T
         * @return an List of HashMap(s) with the results, or null
         * @throws SQLException
         *                 in case of problems
         */
        <T> List<Map<String, Object>> selectAllHashMap(String[] fields, String query, T param)
                        throws SQLException;

        /**
         * Define whether database changes done on the database be automatically
         * committed. This function may also implicitly start or end a
         * transaction.
         * 
         * @param autoCommit
         *                is a boolean flag that indicates whether the database
         *                changes should be committed right after executing
         *                every query statement. If this argument is false a
         *                transaction implicitly started. Otherwise, if a
         *                transaction is in progress it is ended by committing
         *                any database changes that were pending.
         * @throws SQLException
         *                 in case of problems
         */
        void autoCommitTransactions(boolean autoCommit) throws SQLException;

        /**
         * Commit the database changes done during a transaction that is in
         * progress. This method may only be called when auto-committing is
         * disabled, otherwise it will fail. Therefore, a new transaction is
         * implicitly started after committing the pending changes.
         * 
         * @throws SQLException
         *                 in case of problems
         */
        void commitTransaction() throws SQLException;

        /**
         * Cancel any database changes done during a transaction that is in
         * progress. This function may only be called when auto-committing is
         * disabled, otherwise it will fail. Therefore, a new transaction is
         * implicitly started after cancelling the pending changes.
         * 
         * @throws SQLException
         *                 in case of problems
         */
        void rollbackTransaction() throws SQLException;

        /**
         * Execute the query an extract the blob field in row 1, column 1 (first
         * row, first column).
         * 
         * @param sql
         *                the SQL to be executed
         * @return the object as a byte[]
         * @throws SQLException
         *                 in case of problems
         */
        byte[] selectBlobField(String sql) throws SQLException;

        /**
         * Close ResulSet and Statement after a raw query. Exception are
         * trapped.
         */
        void closeResultSetAndStatement();

        /**
         * Creates a new Id to use in an INSERT statement. The returned Id won't
         * be returned again, even if it's not used.
         * 
         * @param tablename
         *                the name of the table for which we are requesting a
         *                new Id.
         * @return an Object of type Long containing an Id that we can use for
         *         an insert statement.
         * @throws SQLException
         *                 mostly could mean that the _sequence_<tablename>
         *                 table doesn't exists
         */
        int newIdUsingSequence(String tablename) throws SQLException;
        
        /** 
         * Set whether or not to use SELECT DISTINCT in queries (default: false).
         * @param distinctStatus if true use DISTINCT 
         */
        void setDistinct(boolean distinctStatus);

}
