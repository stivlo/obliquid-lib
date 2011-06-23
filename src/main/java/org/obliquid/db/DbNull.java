package org.obliquid.db;

/**
 * Represents a NULL value in a database field, and holds the type from java.sql.Types
 */
public class DbNull {

    private int fieldType = 0;

    /**
     * Constructs an empty DbNull Object
     */
    public DbNull() {
        //empty constructor
    }

    /**
     * Construct a DbNull Object with type information
     * 
     * @param fieldType
     *            one of the type constants defined in java.sql.Types
     */
    public DbNull(int fieldType) {
        this.fieldType = fieldType;
    }

    /**
     * Gets the field type as in java.sql.Types
     * 
     * @return an integer representing the sql type
     */
    public int getFieldType() {
        return fieldType;
    }

    /**
     * Sets the field type as in java.sql.Types
     * 
     * @param fieldType
     *            one of the type constants defined in java.sql.Types
     */
    public void setFieldType(int fieldType) {
        this.fieldType = fieldType;
    }

}
