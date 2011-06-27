package org.obliquid.db;

/**
 * Represents a DB Field. It's Immutable.
 * 
 * @author stivlo
 */
public class DbField {

    private String name, type;

    /**
     * Builds an Immutable Field
     * 
     * @param name
     *            field name
     * @param type
     *            field type including size information such as "VARCHAR(20)"
     */
    public DbField(String name, String type) {
        this.name = name;
        this.type = type.split("\\(")[0];
    }

    /**
     * Get the database field name
     * 
     * @return field name
     */
    public Object getName() {
        return name;
    }

    /**
     * Get the database field type without size
     * 
     * @return field type, such as VARCHAR
     */
    public Object getType() {
        return type;
    }

}
