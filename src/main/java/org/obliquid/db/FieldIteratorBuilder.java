package org.obliquid.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Provides an Iterator for all the fields of the specified table. Example Usage:
 * 
 * <pre>
 * //if you've already a db connection use the other constructor
 * FieldIteratorBuilder builder = new FieldIteratorBuilder();
 * builder.getDb(); //open the db connection
 * Iterator&lt;DbField&gt; it = builder.fieldIterator(&quot;my_table&quot;);
 * builder.releaseConnection(); //we can close it already 
 * while (it.hasNext()) {
 *     DbField field = it.next();
 * }
 * </pre>
 * 
 * @author stivlo
 */
public class FieldIteratorBuilder extends HasDb {

    public FieldIteratorBuilder() {
        super();
    }

    public FieldIteratorBuilder(MetaDb db) {
        super();
    }

    /**
     * Build a Field list for a table
     * 
     * @param tableName
     *            the table requested
     * @return a Field List
     * @throws SQLException
     */
    public Iterator<DbField> fieldIterator(String tableName) throws SQLException {
        List<DbField> fieldList = new ArrayList<DbField>();
        List<List<Object>> allFields = db.selectAll("DESCRIBE " + tableName);
        for (List<Object> fieldRawData : allFields) {
            DbField aField = new DbField((String) fieldRawData.get(0), (String) fieldRawData.get(1));
            fieldList.add(aField);
        }
        return fieldList.iterator();
    }

}
