package org.obliquid.scripts;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.obliquid.db.DbField;
import org.obliquid.db.FieldIteratorBuilder;
import org.obliquid.db.HasDb;
import org.obliquid.db.PriKeyLister;
import org.obliquid.db.TableIteratorBuilder;

public class RecodeDb extends HasDb {

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
     * Reocode all the text fields of a table
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
                recodeField(table, priKeys, aField.getName());
            }
        }
    }

    private void recodeField(String table, List<String> priKeys, String fieldName) throws SQLException {
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
            db.update(table, fieldAndValue, priKeyAndValue);
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
    private Map<String, Object> recodeFieldAndStoreInMap(String fieldName, Object content) {
        Map<String, Object> text = new HashMap<String, Object>();
        text.put(fieldName, "*" + (String) content);
        return text;
    }

    public static void main(String[] arg) throws SQLException {
        RecodeDb instance = new RecodeDb();
        try {
            instance.getDb();
            instance.recodeDb();
        } finally {
            instance.releaseConnection();
        }
    }

}
