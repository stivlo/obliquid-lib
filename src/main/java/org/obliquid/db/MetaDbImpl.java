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

import org.obliquid.config.AppConfig;
import org.obliquid.helpers.ArrayHelper;
import org.obliquid.helpers.SqlHelper;
import org.obliquid.helpers.StringHelper;

/**
 * Manage interactions with database.
 * 
 * @author stivlo
 */
public class MetaDbImpl implements MetaDb {

    /** Holds the connection - one MetaDb Object one ConnectionManager Object, one Connection Object */
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
    public MetaDbImpl() {
        connectionManager = new ConnectionManager();
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#executeRawQuery(java.lang.String)
     */
    @Override
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

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#getConnection(boolean)
     */
    @Override
    public Connection getConnection(boolean usePool) throws SQLException {
        conn = connectionManager.getConnection(usePool);
        return conn;
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#getConnection()
     */
    @Override
    public Connection getConnection() throws SQLException {
        conn = connectionManager.getConnection();
        return conn;
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#releaseConnection()
     */
    @Override
    public void releaseConnection() {
        conn = null;
        try {
            connectionManager.releaseConnection();
        } catch (SQLException ex) {
            AppConfig.getInstance().log(ex.getMessage(), AppConfig.ERROR);
        }
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#execute(java.lang.String)
     */
    @Override
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

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#execute(java.lang.String, java.util.List)
     */
    @Override
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

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#execute(java.lang.String, java.lang.String[])
     */
    @Override
    public int execute(String sql, String[] param) throws SQLException {
        ArrayList<Object> arList = ArrayHelper.buildArrayList(param);
        return execute(sql, arList);
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#execute(java.lang.String, T)
     */
    @Override
    public <T> int execute(String sql, T param) throws SQLException {
        ArrayList<Object> arList = new ArrayList<Object>();
        arList.add(param);
        return execute(sql, arList);
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#execute(java.lang.String, java.util.Map)
     */
    @Override
    public int execute(String sql, Map<String, Object> param) throws SQLException {
        ArrayList<Object> arList = ArrayHelper.buildArrayList(param);
        return execute(sql, arList);
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#execute(java.lang.String, int[])
     */
    @Override
    public int execute(String sql, int[] param) throws SQLException {
        ArrayList<Object> arList = ArrayHelper.buildArrayList(param);
        return execute(sql, arList);
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectField(java.lang.String)
     */
    @Override
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

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectField(java.lang.String, java.util.List)
     */
    @Override
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

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectField(java.lang.String, java.lang.String[])
     */
    @Override
    public Object selectField(String sql, String[] param) throws SQLException {
        ArrayList<Object> arList = ArrayHelper.buildArrayList(param);
        return selectField(sql, arList);
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectField(java.lang.String, int[])
     */
    @Override
    public Object selectField(String sql, int[] param) throws SQLException {
        ArrayList<Object> arList = ArrayHelper.buildArrayList(param);
        return selectField(sql, arList);
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectField(java.lang.String, T)
     */
    @Override
    public <T> Object selectField(String sql, T param) throws SQLException {
        ArrayList<Object> arList = new ArrayList<Object>();
        arList.add(param);
        return selectField(sql, arList);
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectRow(java.lang.String)
     */
    @Override
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

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectRow(java.lang.String, java.util.List)
     */
    @Override
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

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectRow(java.lang.String, int)
     */
    @Override
    public List<Object> selectRow(String sql, int param) throws SQLException {
        List<Object> arList = new ArrayList<Object>();
        arList.add(param);
        return selectRow(sql, arList);
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectRow(java.lang.String, java.lang.String)
     */
    @Override
    public List<Object> selectRow(String sql, String param) throws SQLException {
        ArrayList<Object> arList = new ArrayList<Object>();
        arList.add(param);
        return selectRow(sql, arList);
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectRow(java.lang.String, java.lang.String[])
     */
    @Override
    public List<Object> selectRow(String sql, String[] param) throws SQLException {
        ArrayList<Object> arList = ArrayHelper.buildArrayList(param);
        return selectRow(sql, arList);
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectRow(java.lang.String, int[])
     */
    @Override
    public List<Object> selectRow(String sql, int[] param) throws SQLException {
        ArrayList<Object> arList = ArrayHelper.buildArrayList(param);
        return selectRow(sql, arList);
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectColumn(java.lang.String)
     */
    @Override
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

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectColumnToStringArray(java.lang.String)
     */
    @Override
    public String[] selectColumnToStringArray(String sql) throws SQLException {
        List<Object> res = selectColumn(sql);
        String[] ar = new String[res.size()];
        for (int i = 0; i < res.size(); i++) {
            ar[i] = (String) res.get(i);
        }
        return ar;
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectColumnToIntArray(java.lang.String)
     */
    @Override
    public int[] selectColumnToIntArray(String sql) throws SQLException {
        List<Object> res = selectColumn(sql);
        int[] ar = new int[res.size()];
        for (int i = 0; i < res.size(); i++) {
            ar[i] = (Integer) res.get(i);
        }
        return ar;
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectColumn(java.lang.String, int[])
     */
    @Override
    public List<Object> selectColumn(String sql, int[] param) throws SQLException {
        ArrayList<Object> arList = ArrayHelper.buildArrayList(param);
        return selectColumn(sql, arList);
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectColumn(java.lang.String, int)
     */
    @Override
    public List<Object> selectColumn(String sql, int param) throws SQLException {
        ArrayList<Object> arList = new ArrayList<Object>();
        arList.add(param);
        return selectColumn(sql, arList);
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectColumn(java.lang.String, java.util.List)
     */
    @Override
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

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectAll(java.lang.String)
     */
    @Override
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

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectAll(java.lang.String, java.util.List)
     */
    @Override
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

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectAll(java.lang.String, java.lang.String[])
     */
    @Override
    public List<List<Object>> selectAll(String sql, String[] param) throws SQLException {
        ArrayList<Object> arList = ArrayHelper.buildArrayList(param);
        return selectAll(sql, arList);
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectAll(java.lang.String, int[])
     */
    @Override
    public List<List<Object>> selectAll(String sql, int[] param) throws SQLException {
        ArrayList<Object> arList = ArrayHelper.buildArrayList(param);
        return selectAll(sql, arList);
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectAll(java.lang.String, T)
     */
    @Override
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

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#insertDelayed(java.lang.String, java.util.Map)
     */
    @Override
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

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#insert(java.lang.String, java.util.Map)
     */
    @Override
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

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#update(java.lang.String, java.util.Map, java.util.Map)
     */
    @Override
    public int update(String tablename, Map<String, Object> fields, Map<String, Object> priKeys)
            throws SQLException {
        String sql = "UPDATE " + tablename + " SET " + StringHelper.implodeAndQuote("=?, ", fields, "`")
                + "=? WHERE " + StringHelper.implode("=? AND ", priKeys) + "=?";
        ArrayList<Object> arList = ArrayHelper.buildArrayList(fields, priKeys);
        return execute(sql, arList);
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#update(java.lang.String, java.util.Map, java.lang.String, T)
     */
    @Override
    public <T> int update(String tablename, Map<String, Object> fields, String priName, T priValue)
            throws SQLException {
        Map<String, Object> priKeys = new HashMap<String, Object>();
        priKeys.put(priName, priValue);
        return update(tablename, fields, priKeys);
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectRow(java.lang.String[], java.lang.String)
     */
    @Override
    public List<Object> selectRow(String[] fields, String query) throws SQLException {
        String sql = "SELECT " + StringHelper.implode(", ", fields) + " " + query;
        List<Object> row = selectRow(sql);
        return row;
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectRow(java.lang.String[], java.lang.String, java.util.List)
     */
    @Override
    public List<Object> selectRow(String[] fields, String query, List<?> param) throws SQLException {
        String sql = "SELECT " + StringHelper.implode(", ", fields) + " " + query;
        List<Object> row = selectRow(sql, param);
        return row;
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectRow(java.lang.String[], java.lang.String, int)
     */
    @Override
    public List<Object> selectRow(String[] fields, String query, int param) throws SQLException {
        String sql = "SELECT " + StringHelper.implode(", ", fields) + " " + query;
        List<Object> row = selectRow(sql, param);
        return row;
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectRowHashMap(java.lang.String[], java.lang.String)
     */
    @Override
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

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectRowHashMap(java.lang.String[], java.lang.String, java.util.List)
     */
    @Override
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

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectRowHashMap(java.lang.String[], java.lang.String, int)
     */
    @Override
    public Map<String, Object> selectRowHashMap(String[] fields, String query, int param) throws SQLException {
        ArrayList<Object> arList = new ArrayList<Object>();
        arList.add(param);
        return selectRowHashMap(fields, query, arList);
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectRowHashMap(java.util.List, java.lang.String, int)
     */
    @Override
    public Map<String, Object> selectRowHashMap(List<String> fields, String query, int param)
            throws SQLException {
        return selectRowHashMap(fields.toArray(new String[0]), query, param);
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectRowHashMap(java.lang.String[], java.lang.String, java.lang.String)
     */
    @Override
    public Map<String, Object> selectRowHashMap(String[] fields, String query, String param)
            throws SQLException {
        ArrayList<Object> arList = new ArrayList<Object>();
        arList.add(param);
        return selectRowHashMap(fields, query, arList);
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectRowHashMap(java.lang.String[], java.lang.String, int[])
     */
    @Override
    public Map<String, Object> selectRowHashMap(String[] fields, String query, int[] param)
            throws SQLException {
        ArrayList<Object> paramList = ArrayHelper.buildArrayList(param);
        return selectRowHashMap(fields, query, paramList);
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectAll(java.lang.String[], java.lang.String)
     */
    @Override
    public List<List<Object>> selectAll(String[] fields, String query) throws SQLException {
        String sql = "SELECT " + StringHelper.implodeAndQuote(", ", fields, "`") + " " + query;
        List<List<Object>> matrix = selectAll(sql);
        return matrix;
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectAll(java.util.List, java.lang.String)
     */
    @Override
    public List<List<Object>> selectAll(List<String> fields, String sql) throws SQLException {
        return selectAll(fields.toArray(new String[0]), sql);
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectAll(java.lang.String[], java.lang.String, java.util.List)
     */
    @Override
    public List<List<Object>> selectAll(String[] fields, String query, List<?> param) throws SQLException {
        String sql = "SELECT " + StringHelper.implode(", ", fields) + " " + query;
        List<List<Object>> matrix = selectAll(sql, param);
        return matrix;
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectAll(java.lang.String[], java.lang.String, int)
     */
    @Override
    public List<List<Object>> selectAll(String[] fields, String query, int param) throws SQLException {
        String sql = "SELECT " + StringHelper.implode(", ", fields) + " " + query;
        List<List<Object>> matrix = selectAll(sql, param);
        return matrix;
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectAllHashMap(java.util.List, java.lang.String)
     */
    @Override
    public List<Map<String, Object>> selectAllHashMap(List<String> fields, String query) throws SQLException {
        return selectAllHashMap(fields.toArray(new String[0]), query);
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectAllHashMap(java.lang.String[], java.lang.String)
     */
    @Override
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

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectAllHashMap(java.lang.String[], java.lang.String, java.util.ArrayList)
     */
    @Override
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

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectAllHashMap(java.lang.String[], java.lang.String, int[])
     */
    @Override
    public List<Map<String, Object>> selectAllHashMap(String[] fields, String sql, int[] param)
            throws SQLException {
        ArrayList<Object> arList = ArrayHelper.buildArrayList(param);
        return selectAllHashMap(fields, sql, arList);
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectAllHashMap(java.lang.String[], java.lang.String, T)
     */
    @Override
    public <T> List<Map<String, Object>> selectAllHashMap(String[] fields, String sql, T param)
            throws SQLException {
        ArrayList<Object> arList = new ArrayList<Object>();
        arList.add(param);
        return selectAllHashMap(fields, sql, arList);
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#autoCommitTransactions(boolean)
     */
    @Override
    public void autoCommitTransactions(boolean autoCommit) throws SQLException {
        conn.setAutoCommit(autoCommit);
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#commitTransaction()
     */
    @Override
    public void commitTransaction() throws SQLException {
        connectionManager.commitTransaction();
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#rollbackTransaction()
     */
    @Override
    public void rollbackTransaction() throws SQLException {
        connectionManager.rollbackTransaction();
    }

    /* (non-Javadoc)
     * @see org.obliquid.db.MetaDb#selectBlobField(java.lang.String)
     */
    @Override
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
