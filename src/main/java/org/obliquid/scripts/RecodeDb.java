package org.obliquid.scripts;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.obliquid.db.DbField;
import org.obliquid.db.FieldIteratorBuilder;
import org.obliquid.db.MetaDb;
import org.obliquid.db.MetaDbImpl;
import org.obliquid.db.PriKeyLister;
import org.obliquid.db.TableIteratorBuilder;

/**
 * This script will go through each and every text data in the DB and applies the recodeString()
 * function to it. In this implementation it will reverse every String in the db, so by running it
 * again is possible to have the data reversed back exactly as it was. Subclasses will override the
 * implementation of recodeString() to do something useful, such as StringHelper.recodePhp(). By
 * defaults it runs in dry run which you want to try first to be sure that all MySQL types needed
 * are supported.
 * 
 * @author stivlo
 */
public class RecodeDb {

    private MetaDb db;

    /** On the cautios side, we don't do any modifications unless specifically required */
    boolean dryRun = true;

    /**
     * Release a connection. If it's a standalone Connection, the connection is closed, otherwise is
     * returned to the pool.
     */
    public void releaseConnection() {
        if (db != null) {
            db.releaseConnection();
        }
    }

    public Connection getConnection() throws SQLException {
        return db.getConnection();
    }

    /**
     * Recode the current db
     * 
     * @throws SQLException
     */
    public void recodeDb() throws SQLException {
        Iterator<String> tableIterator = TableIteratorBuilder.tableIteratorWithAutoDb();
        while (tableIterator.hasNext()) {
            recodeTable(tableIterator.next());
        }
    }

    /**
     * Set a dry or production run. dryRun is default
     * 
     * @param dryRun
     *            set it to false to update the db
     */
    public void setDryRun(boolean dryRun) {
        this.dryRun = dryRun;
    }

    /**
     * Recode all the text fields of a table
     * 
     * @param table
     * @throws SQLException
     */
    private void recodeTable(String table) throws SQLException {
        Iterator<DbField> fieldIterator = FieldIteratorBuilder.fieldIteratorWithAutoDb(table);
        while (fieldIterator.hasNext()) {
            DbField aField = fieldIterator.next();
            if (aField.isText()) {
                List<String> priKeys = PriKeyLister.getPriKeysWithAutoDb(table);
                System.out.println("Recoding table " + table + " field " + aField.getName());
                recodeField(table, aField.getName(), priKeys);
            }
        }
    }

    private void recodeField(String table, String fieldName, List<String> priKeys) throws SQLException {
        System.out.println(table + " " + priKeys + " " + fieldName);
        List<String> fields = new ArrayList<String>();
        fields.add(fieldName);
        fields.addAll(priKeys);
        List<List<Object>> content = db.selectAll(fields, "FROM " + table);
        Iterator<List<Object>> rowIterator = content.iterator();
        while (rowIterator.hasNext()) {
            List<Object> row = rowIterator.next();
            Map<String, Object> fieldAndValue = recodeFieldAndStoreInMap(fieldName, row.get(0));
            row.remove(0); //remove the field that was used already to leave only the priKey values
            Map<String, Object> priKeyAndValue = storePrimaryKeyInMap(priKeys, row);
            if (!dryRun) {
                db.update(table, fieldAndValue, priKeyAndValue);
            }
        }
    }

    /**
     * Store the primary key names and values in a Map
     * 
     * @param priKeys
     *            the primary key field names
     * @param values
     *            the primary key values
     * @return a Map containing the priKeys as key and values as values
     */
    private Map<String, Object> storePrimaryKeyInMap(List<String> priKeys, List<Object> values) {
        assert priKeys.size() == values.size(); //should never block, but better be sure
        Map<String, Object> priKeyAndValue = new HashMap<String, Object>();
        for (int i = 0; i < priKeys.size(); i++) { //I loop the old-fashioned way to have the index
            priKeyAndValue.put(priKeys.get(i), values.get(i));
        }
        return priKeyAndValue;
    }

    /**
     * Recode our test field and package it in a Map with fieldName as Key and the content as value
     * 
     * @param fieldName
     *            the field name
     * @param content
     *            the value
     * @return a Map of field => content, with content recoded
     */
    private Map<String, Object> recodeFieldAndStoreInMap(final String fieldName, final Object content) {
        Map<String, Object> text = new HashMap<String, Object>();
        String fixedContent = recodeString((String) content);
        text.put(fieldName, fixedContent);
        return text;
    }

    /**
     * This is the method to be over-ridden by a child class to implement the required recoding
     * 
     * @param input
     * @return
     */
    protected String recodeString(String input) {
        return StringUtils.reverse(input);
    }

    public static void main(String[] arg) throws SQLException {
        RecodeDb instance = new RecodeDb();
        try {
            instance.db = new MetaDbImpl();
            instance.db.getConnection();
            instance.recodeDb();
        } finally {
            instance.releaseConnection();
        }
    }

}
