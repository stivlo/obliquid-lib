package org.obliquid.helpers;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.obliquid.db.DbNull;

/**
 * SQL related utilities: build a prepared statement, methods to close a
 * ResultSet or Statement checking for null and Exceptions, methods to extract
 * values from a ResultSet including blobs.
 * 
 * @author stivlo
 */
public final class SqlHelper {

        /**
         * No public default constructor, because is a Utility class.
         */
        private SqlHelper() {
                //utility class
        }

        /**
         * Set an Object in the Statement.
         * 
         * @param stmt
         *                the PreparedStatement
         * @param pos
         *                the position in the statement
         * @param value
         *                value to be set, can be a special object instance of
         *                DBNull to set null, we need that to preserve the type
         * @throws SQLException
         *                 when there are DB problems
         */
        public static void setObjectInStatement(final PreparedStatement stmt, final int pos,
                        final Object value) throws SQLException {
                if (value instanceof DbNull) {
                        stmt.setNull(pos, ((DbNull) value).getFieldType());
                } else if (value != null) {
                        stmt.setObject(pos, value);
                } else {
                        //this is a trick because if value is null we've lost the type information. it 
                        //should work on MySQL and doesn't work on Oracle. Use DbNull class to save null 
                        //value in a portable way
                        stmt.setNull(pos, Types.VARCHAR);
                }
        }

        /**
         * Extract the field name in the name part of a query when using AS.
         * Examples: "input" => "output": "id_hosting" => "id_hosting",
         * "h.address" => "h.address", "COUNT(*) AS how_many" => "how_many",
         * "surname " => "surname"
         * 
         * @param expression
         *                the SQL field part
         * @return field name
         */
        public static String extractFieldName(final String expression) {
                String as = " AS ";
                Locale locale = new Locale("en", "US");
                int asPos = expression.toUpperCase(locale).lastIndexOf(as);
                if (asPos == -1) {
                        return expression.trim();
                }
                return expression.substring(asPos + as.length()).trim();
        }

        /**
         * Close a ResultSet the safe way.
         * 
         * @param rs
         *                ResultSet to be closed
         */
        public static void close(final ResultSet rs) {
                if (rs == null) {
                        return;
                }
                try {
                        rs.close();
                } catch (SQLException ex) {
                        ex.printStackTrace();
                }
        }

        /**
         * Close a Statement the safe way.
         * 
         * @param sm
         *                Statement to be closed
         */
        public static void close(final Statement sm) {
                if (sm == null) {
                        return;
                }
                try {
                        sm.close();
                } catch (SQLException ex) {
                        ex.printStackTrace();
                }
        }

        /**
         * Extract a matrix as an List of List from a ResultSet.
         * 
         * @param res
         *                the ResultSet
         * @return all results as a matrix
         * @throws SQLException
         *                 when there are DB problems
         */
        public static List<List<Object>> extractMatrixFromResultSet(final ResultSet res) throws SQLException {
                List<Object> row;
                List<List<Object>> matrix = new ArrayList<List<Object>>();
                int numCols = getColumnCount(res);
                while (res.next()) {
                        row = extractRowFromResultSet(res, numCols);
                        matrix.add(row);
                }
                return matrix;
        }

        /**
         * Extract a row as an ArrayList of Object from a ResultSet.
         * 
         * @param res
         *                the ResultSet
         * @param numCols
         *                number of columns in the ResultSet that we want to
         *                extract
         * @return a new List Object with the content extracted
         * @throws SQLException
         *                 when there are DB problems.
         */
        public static List<Object> extractRowFromResultSet(final ResultSet res, final int numCols)
                        throws SQLException {
                ArrayList<Object> row;
                row = new ArrayList<Object>();
                res.getRow();
                for (int i = 1; i <= numCols; i++) {
                        row.add(res.getObject(i));
                }
                return row;
        }

        /**
         * Return the number of columns in a ResultSet.
         * 
         * @param res
         *                the ResultSet to examine
         * @return number of columns
         * @throws SQLException
         *                 when there are DB problems
         */
        public static int getColumnCount(final ResultSet res) throws SQLException {
                ResultSetMetaData rsmd;
                int numCols;
                rsmd = res.getMetaData();
                numCols = rsmd.getColumnCount();
                return numCols;
        }

        /**
         * Build the WHERE and ORDER BY clause for Simple DB. Amazon Simple DB
         * requires that columns appearing in the ORDER BY also appear in the
         * WHERE clause
         * 
         * @param orderBy
         *                the ORDER BY string. Examples: "id", "id DESC"
         * @return WHERE and ORDER BY clause. Examples "id" =>
         *         " WHERE id>='' ORDER BY id", "id DESC" =>
         *         " WHERE id>='' ORDER BY id DESC"
         */
        public static String buildWhereAndOrderByForSdb(final String orderBy) {
                String[] part = orderBy.split(" ");
                String result = " WHERE " + part[0] + ">='' ORDER BY " + orderBy;
                return result;
        }

        /**
         * Build a new PreparedStatement from the SQL clause and the parameters.
         * 
         * @param conn
         *                DB connection
         * @param sql
         *                parametric SQL
         * @param param
         *                parameters to be substituted
         * @return a new PreparedStatement object
         * @throws SQLException
         *                 when there are DB problems
         */
        public static PreparedStatement buildPreparedStatement(final Connection conn, final String sql,
                        final List<?> param) throws SQLException {
                PreparedStatement stmt;
                stmt = conn.prepareStatement(sql);
                Iterator<?> it = param.iterator();
                for (int paramPosition = 1; it.hasNext(); paramPosition++) {
                        SqlHelper.setObjectInStatement(stmt, paramPosition, it.next());
                }
                return stmt;
        }

        /**
         * Fetch a Blob from the current row of the ResultSet. next() should
         * have been called already.
         * 
         * @param res
         *                the ResultSet to use
         * @param fieldName
         *                the name of the field containing the Blob
         * @return the blob as a byte array
         * @throws SQLException
         *                 when there are DB problems
         */
        public static byte[] fetchBlobFromCurrentRowInResulSet(final ResultSet res, final String fieldName)
                        throws SQLException {
                byte[] response = null;
                Blob blob = res.getBlob(fieldName);
                response = blob.getBytes(1L, (int) blob.length());
                return response;
        }

        /**
         * Fetch a Blob from the current row of the ResultSet. next() should
         * have been called already.
         * 
         * @param res
         *                the ResultSet to use
         * @param fieldPos
         *                the position of the field containing the Blob
         * @return the blob as a byte array
         * @throws SQLException
         *                 when there are DB problems
         */
        public static byte[] fetchBlobFromCurrentRowInResulSet(final ResultSet res, final int fieldPos)
                        throws SQLException {
                byte[] response = null;
                Blob blob = res.getBlob(fieldPos);
                response = blob.getBytes(1L, (int) blob.length());
                return response;
        }

}
