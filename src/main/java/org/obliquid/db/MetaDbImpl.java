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

import org.apache.log4j.Logger;
import org.obliquid.helpers.ArrayHelper;
import org.obliquid.helpers.SqlHelper;
import org.obliquid.helpers.StringHelper;

/**
 * Manage interactions with database.
 * 
 * @author stivlo
 */
public class MetaDbImpl implements MetaDb {

        /**
         * Holds the connection - one MetaDb Object one ConnectionManager
         * Object, one Connection Object.
         */
        private final ConnectionManager connectionManager;

        /** DB Connection. */
        private Connection conn = null;

        /** Statement for raw Query. */
        private Statement statement;

        /** ResultSet for raw Query. */
        private ResultSet res;

        /** Log4j instance. */
        private static final Logger LOG = Logger.getLogger(ConnectionManager.class);

        /**
         * Create a new instance of DB.
         * 
         */
        public MetaDbImpl() {
                connectionManager = new ConnectionManager();
        }

        @Override
        public final ResultSet executeRawQuery(final String sql) throws SQLException {
                statement = null;
                res = null;
                statement = conn.createStatement();
                res = statement.executeQuery(sql);
                return res;
        }

        @Override
        public final void closeResultSetAndStatement() {
                SqlHelper.close(statement);
                SqlHelper.close(res);
        }

        @Override
        public final Connection getConnection(final boolean usePool) throws SQLException {
                conn = connectionManager.getConnection(usePool);
                return conn;
        }

        @Override
        public final Connection getConnection() throws SQLException {
                conn = connectionManager.getConnection();
                return conn;
        }

        @Override
        public final void releaseConnection() {
                conn = null;
                try {
                        connectionManager.releaseConnection();
                } catch (SQLException ex) {
                        LOG.error(ex.getMessage());
                }
        }

        @Override
        public final int execute(final String sql) throws SQLException {
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

        @Override
        public final int execute(final String sql, final List<?> param) throws SQLException {
                PreparedStatement st = null;
                int rowCount = 0;
                try {
                        st = SqlHelper.buildPreparedStatement(conn, sql, param);
                        rowCount = st.executeUpdate();
                } finally {
                        SqlHelper.close(st);
                }
                return rowCount;
        }

        @Override
        public final int execute(final String sql, final String[] param) throws SQLException {
                ArrayList<Object> arList = ArrayHelper.buildArrayList(param);
                return execute(sql, arList);
        }

        @Override
        public final <T> int execute(final String sql, final T param) throws SQLException {
                ArrayList<Object> arList = new ArrayList<Object>();
                arList.add(param);
                return execute(sql, arList);
        }

        @Override
        public final int execute(final String sql, final Map<String, Object> param) throws SQLException {
                ArrayList<Object> arList = ArrayHelper.buildArrayList(param);
                return execute(sql, arList);
        }

        @Override
        public final int execute(final String sql, final int[] param) throws SQLException {
                ArrayList<Object> arList = ArrayHelper.buildArrayList(param);
                return execute(sql, arList);
        }

        @Override
        public final Object selectField(final String sql) throws SQLException {
                Statement stmt = null;
                ResultSet result = null;
                Object field = null;
                try {
                        stmt = conn.createStatement();
                        result = stmt.executeQuery(sql);
                        if (result.next()) {
                                field = result.getObject(1);
                        } else {
                                throw new SQLException("*** Empty ResultSet ***");
                        }
                } finally {
                        SqlHelper.close(stmt);
                        SqlHelper.close(result);
                }
                return field;
        }

        @Override
        public final Object selectField(final String sql, final List<?> param) throws SQLException {
                PreparedStatement stmt = null;
                ResultSet result = null;
                Object field = null;
                try {
                        stmt = SqlHelper.buildPreparedStatement(conn, sql, param);
                        result = stmt.executeQuery();
                        if (result.next()) {
                                field = result.getObject(1);
                        } else {
                                return null;
                        }
                } finally {
                        SqlHelper.close(stmt);
                        SqlHelper.close(result);
                }
                return field;
        }

        @Override
        public final Object selectField(final String sql, final String[] param) throws SQLException {
                ArrayList<Object> arList = ArrayHelper.buildArrayList(param);
                return selectField(sql, arList);
        }

        @Override
        public final Object selectField(final String sql, final int[] param) throws SQLException {
                ArrayList<Object> arList = ArrayHelper.buildArrayList(param);
                return selectField(sql, arList);
        }

        @Override
        public final <T> Object selectField(final String sql, final T param) throws SQLException {
                ArrayList<Object> arList = new ArrayList<Object>();
                arList.add(param);
                return selectField(sql, arList);
        }

        @Override
        public final List<Object> selectRow(final String sql) throws SQLException {
                Statement stat = null;
                ResultSet result = null;
                List<Object> row = null;
                try {
                        stat = conn.createStatement();
                        result = stat.executeQuery(sql);
                        int numCols = SqlHelper.getColumnCount(result);
                        if (result.next()) {
                                row = SqlHelper.extractRowFromResultSet(result, numCols);
                        }
                } finally {
                        SqlHelper.close(stat);
                        SqlHelper.close(result);
                }
                return row;
        }

        @Override
        public final List<Object> selectRow(final String sql, final List<?> param) throws SQLException {
                PreparedStatement stmt = null;
                ResultSet result = null;
                List<Object> row = null;
                try {
                        stmt = SqlHelper.buildPreparedStatement(conn, sql, param);
                        result = stmt.executeQuery();
                        int numCols = SqlHelper.getColumnCount(result);
                        if (result.next()) {
                                row = SqlHelper.extractRowFromResultSet(result, numCols);
                        }
                } finally {
                        SqlHelper.close(result);
                        SqlHelper.close(stmt);
                }
                return row;
        }

        @Override
        public final List<Object> selectRow(final String sql, final int param) throws SQLException {
                List<Object> arList = new ArrayList<Object>();
                arList.add(param);
                return selectRow(sql, arList);
        }

        @Override
        public final List<Object> selectRow(final String sql, final String param) throws SQLException {
                ArrayList<Object> arList = new ArrayList<Object>();
                arList.add(param);
                return selectRow(sql, arList);
        }

        @Override
        public final List<Object> selectRow(final String sql, final String[] param) throws SQLException {
                ArrayList<Object> arList = ArrayHelper.buildArrayList(param);
                return selectRow(sql, arList);
        }

        @Override
        public final List<Object> selectRow(final String sql, final int[] param) throws SQLException {
                ArrayList<Object> arList = ArrayHelper.buildArrayList(param);
                return selectRow(sql, arList);
        }

        @Override
        public final List<Object> selectColumn(final String sql) throws SQLException {
                Statement stmt = null;
                ResultSet result = null;
                ArrayList<Object> column = new ArrayList<Object>();
                try {
                        stmt = conn.createStatement();
                        result = stmt.executeQuery(sql);
                        while (result.next()) {
                                result.getRow();
                                column.add(result.getObject(1));
                        }
                } finally {
                        SqlHelper.close(result);
                        SqlHelper.close(stmt);
                }
                return column;
        }

        @Override
        public final String[] selectColumnToStringArray(final String sql) throws SQLException {
                List<Object> result = selectColumn(sql);
                String[] ar = new String[result.size()];
                for (int i = 0; i < result.size(); i++) {
                        ar[i] = (String) result.get(i);
                }
                return ar;
        }

        @Override
        public final int[] selectColumnToIntArray(final String sql) throws SQLException {
                List<Object> result = selectColumn(sql);
                int[] ar = new int[result.size()];
                for (int i = 0; i < result.size(); i++) {
                        ar[i] = (Integer) result.get(i);
                }
                return ar;
        }

        @Override
        public final List<Object> selectColumn(final String sql, final int[] param) throws SQLException {
                ArrayList<Object> arList = ArrayHelper.buildArrayList(param);
                return selectColumn(sql, arList);
        }

        @Override
        public final List<Object> selectColumn(final String sql, final int param) throws SQLException {
                ArrayList<Object> arList = new ArrayList<Object>();
                arList.add(param);
                return selectColumn(sql, arList);
        }

        @Override
        public final List<Object> selectColumn(final String sql, final List<?> param) throws SQLException {
                PreparedStatement stmt = null;
                ResultSet result = null;
                ArrayList<Object> row = new ArrayList<Object>();
                try {
                        stmt = SqlHelper.buildPreparedStatement(conn, sql, param);
                        result = stmt.executeQuery();
                        while (result.next()) {
                                result.getRow();
                                row.add(result.getObject(1));
                        }
                } finally {
                        SqlHelper.close(result);
                        SqlHelper.close(stmt);
                }
                return row;
        }

        @Override
        public final List<List<Object>> selectAll(final String sql) throws SQLException {
                Statement st = null;
                ResultSet result = null;
                List<List<Object>> matrix = null;
                try {
                        st = conn.createStatement();
                        result = st.executeQuery(sql);
                        matrix = SqlHelper.extractMatrixFromResultSet(result);
                } finally {
                        SqlHelper.close(result);
                        SqlHelper.close(st);
                }
                return matrix;
        }

        @Override
        public final List<List<Object>> selectAll(final String sql, final List<?> param) throws SQLException {
                PreparedStatement stmt = null;
                ResultSet result = null;
                List<List<Object>> matrix = null;
                try {
                        stmt = SqlHelper.buildPreparedStatement(conn, sql, param);
                        result = stmt.executeQuery();
                        matrix = SqlHelper.extractMatrixFromResultSet(result);
                } finally {
                        SqlHelper.close(result);
                        SqlHelper.close(stmt);
                }
                return matrix;
        }

        @Override
        public final List<List<Object>> selectAll(final String sql, final String[] param)
                        throws SQLException {
                ArrayList<Object> arList = ArrayHelper.buildArrayList(param);
                return selectAll(sql, arList);
        }

        @Override
        public final List<List<Object>> selectAll(final String sql, final int[] param) throws SQLException {
                ArrayList<Object> arList = ArrayHelper.buildArrayList(param);
                return selectAll(sql, arList);
        }

        @Override
        public final <T> List<List<Object>> selectAll(final String sql, final T param) throws SQLException {
                ArrayList<Object> arList = new ArrayList<Object>();
                arList.add(param);
                return selectAll(sql, arList);
        }

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
        public final int newIdUsingSequence(final String tablename) throws SQLException {
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
         *                a mnemonic for the sequence. it's a good idea to use
         *                the same name as a table name, but it's not required.
         * @return an integer that is the next value to be used
         * @throws SQLException
         *                 when some DB problem happens
         */
        public final int newIdUsingSequenceNoCommit(final String sequenceName) throws SQLException {
                String sql = "SELECT nextval FROM sequence WHERE sequencename=? FOR UPDATE";
                List<Object> result = selectRow(sql, sequenceName);
                if (result == null) {
                        sql = "INSERT INTO equence(nextval, sequencename) VALUES (2, ?)";
                        execute(sql, sequenceName);
                        return 1;
                }
                Integer nextval = (Integer) result.get(0);
                sql = "UPDATE sequence SET nextval=nextval+1 WHERE sequencename=?";
                execute(sql, sequenceName);
                return nextval;
        }

        @Override
        public final String insertDelayed(final String tablename, final Map<String, Object> fields)
                        throws SQLException {
                final int initialBufferSize = 128;
                StringBuilder sb = new StringBuilder(initialBufferSize);
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

        @Override
        public final String insert(final String tablename, final Map<String, Object> fields)
                        throws SQLException {
                final int initialBufferSize = 128;
                String sql;
                StringBuilder sqlB = new StringBuilder(initialBufferSize);
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

        @Override
        public final int update(final String tablename, final Map<String, Object> fields,
                        final Map<String, Object> priKeys) throws SQLException {
                String sql = "UPDATE " + tablename + " SET "
                                + StringHelper.implodeAndQuote("=?, ", fields, "`") + "=? WHERE "
                                + StringHelper.implode("=? AND ", priKeys) + "=?";
                ArrayList<Object> arList = ArrayHelper.buildArrayList(fields, priKeys);
                return execute(sql, arList);
        }

        @Override
        public final <T> int update(final String tablename, final Map<String, Object> fields,
                        final String priName, final T priValue) throws SQLException {
                Map<String, Object> priKeys = new HashMap<String, Object>();
                priKeys.put(priName, priValue);
                return update(tablename, fields, priKeys);
        }

        @Override
        public final List<Object> selectRow(final String[] fields, final String query) throws SQLException {
                String sql = "SELECT " + StringHelper.implode(", ", fields) + " " + query;
                List<Object> row = selectRow(sql);
                return row;
        }

        @Override
        public final List<Object> selectRow(final String[] fields, final String query, final List<?> param)
                        throws SQLException {
                String sql = "SELECT " + StringHelper.implode(", ", fields) + " " + query;
                List<Object> row = selectRow(sql, param);
                return row;
        }

        @Override
        public final List<Object> selectRow(final String[] fields, final String query, final int param)
                        throws SQLException {
                String sql = "SELECT " + StringHelper.implode(", ", fields) + " " + query;
                List<Object> row = selectRow(sql, param);
                return row;
        }

        @Override
        public final Map<String, Object> selectRowHashMap(final String[] fields, final String query)
                        throws SQLException {
                Map<String, Object> result = new HashMap<String, Object>();
                String sql = "SELECT " + StringHelper.implode(", ", fields) + " " + query;
                List<Object> row = selectRow(sql);
                if (row == null) {
                        return null;
                }
                for (int i = 0; i < fields.length; i++) {
                        result.put(SqlHelper.extractFieldName(fields[i]), row.get(i));
                }
                return result;
        }

        @Override
        public final Map<String, Object> selectRowHashMap(final String[] fields, final String query,
                        final List<?> param) throws SQLException {
                Map<String, Object> result = new HashMap<String, Object>();
                String sql = "SELECT " + StringHelper.implode(", ", fields) + " " + query;
                List<Object> row = selectRow(sql, param);
                if (row == null) {
                        return null;
                }
                for (int i = 0; i < fields.length; i++) {
                        result.put(SqlHelper.extractFieldName(fields[i]), row.get(i));
                }
                return result;
        }

        @Override
        public final Map<String, Object> selectRowHashMap(final String[] fields, final String query,
                        final int param) throws SQLException {
                ArrayList<Object> arList = new ArrayList<Object>();
                arList.add(param);
                return selectRowHashMap(fields, query, arList);
        }

        @Override
        public final Map<String, Object> selectRowHashMap(final List<String> fields, final String query,
                        final int param) throws SQLException {
                return selectRowHashMap(fields.toArray(new String[0]), query, param);
        }

        @Override
        public final Map<String, Object> selectRowHashMap(final String[] fields, final String query,
                        final String param) throws SQLException {
                ArrayList<Object> arList = new ArrayList<Object>();
                arList.add(param);
                return selectRowHashMap(fields, query, arList);
        }

        @Override
        public final Map<String, Object> selectRowHashMap(final String[] fields, final String query,
                        final int[] param) throws SQLException {
                ArrayList<Object> paramList = ArrayHelper.buildArrayList(param);
                return selectRowHashMap(fields, query, paramList);
        }

        @Override
        public final List<List<Object>> selectAll(final String[] fields, final String query)
                        throws SQLException {
                String sql = "SELECT " + StringHelper.implodeAndQuote(", ", fields, "`") + " " + query;
                List<List<Object>> matrix = selectAll(sql);
                return matrix;
        }

        @Override
        public final List<List<Object>> selectAll(final List<String> fields, final String sql)
                        throws SQLException {
                return selectAll(fields.toArray(new String[0]), sql);
        }

        @Override
        public final List<List<Object>> selectAll(final String[] fields, final String query,
                        final List<?> param) throws SQLException {
                String sql = "SELECT " + StringHelper.implode(", ", fields) + " " + query;
                List<List<Object>> matrix = selectAll(sql, param);
                return matrix;
        }

        @Override
        public final List<List<Object>> selectAll(final String[] fields, final String query, final int param)
                        throws SQLException {
                String sql = "SELECT " + StringHelper.implode(", ", fields) + " " + query;
                List<List<Object>> matrix = selectAll(sql, param);
                return matrix;
        }

        @Override
        public final List<Map<String, Object>> selectAllHashMap(final List<String> fields, final String query)
                        throws SQLException {
                return selectAllHashMap(fields.toArray(new String[0]), query);
        }

        @Override
        public final List<Map<String, Object>> selectAllHashMap(final String[] fields, final String query)
                        throws SQLException {
                Map<String, Object> rowAsHashMap;
                List<Object> rowAsList;
                ArrayList<Map<String, Object>> result;
                String sql = "SELECT " + StringHelper.implode(", ", fields) + " " + query;
                List<List<Object>> matrix = selectAll(sql);
                if (matrix == null) {
                        return null;
                }
                result = new ArrayList<Map<String, Object>>();
                for (Iterator<List<Object>> it = matrix.iterator(); it.hasNext();) {
                        rowAsHashMap = new HashMap<String, Object>();
                        rowAsList = it.next();
                        for (int i = 0; i < fields.length; i++) {
                                rowAsHashMap.put(SqlHelper.extractFieldName(fields[i]), rowAsList.get(i));
                        }
                        result.add(rowAsHashMap);
                }
                return result;
        }

        @Override
        public final List<Map<String, Object>> selectAllHashMap(final String[] fields, final String query,
                        final List<?> param) throws SQLException {
                Map<String, Object> rowHM;
                List<Object> rowAL;
                ArrayList<Map<String, Object>> result;
                String sql = "SELECT " + StringHelper.implode(", ", fields) + " " + query;
                List<List<Object>> matrix = selectAll(sql, param);
                if (matrix == null) {
                        return null;
                }
                result = new ArrayList<Map<String, Object>>();
                for (Iterator<List<Object>> it = matrix.iterator(); it.hasNext();) {
                        rowHM = new HashMap<String, Object>();
                        rowAL = it.next();
                        for (int i = 0; i < fields.length; i++) {
                                rowHM.put(SqlHelper.extractFieldName(fields[i]), rowAL.get(i));
                        }
                        result.add(rowHM);
                }
                return result;
        }

        @Override
        public final List<Map<String, Object>> selectAllHashMap(final String[] fields, final String sql,
                        final int[] param) throws SQLException {
                ArrayList<Object> arList = ArrayHelper.buildArrayList(param);
                return selectAllHashMap(fields, sql, arList);
        }

        @Override
        public final <T> List<Map<String, Object>> selectAllHashMap(final String[] fields, final String sql,
                        final T param) throws SQLException {
                ArrayList<Object> arList = new ArrayList<Object>();
                arList.add(param);
                return selectAllHashMap(fields, sql, arList);
        }

        @Override
        public final void autoCommitTransactions(final boolean autoCommit) throws SQLException {
                conn.setAutoCommit(autoCommit);
        }

        @Override
        public final void commitTransaction() throws SQLException {
                connectionManager.commitTransaction();
        }

        @Override
        public final void rollbackTransaction() throws SQLException {
                connectionManager.rollbackTransaction();
        }

        @Override
        public final byte[] selectBlobField(final String sql) throws SQLException {
                ResultSet result = executeRawQuery(sql);
                byte[] immagine = null;
                if (!result.next()) {
                        throw new SQLException("No rows returned");
                }
                immagine = SqlHelper.fetchBlobFromCurrentRowInResulSet(result, 1);
                closeResultSetAndStatement();
                return immagine;
        }

}
