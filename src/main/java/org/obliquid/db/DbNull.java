package org.obliquid.db;

/**
 * Represents a NULL value in a database field, and holds the type from
 * java.sql.Types.
 */
public class DbNull {

        /** Field Type code. */
        private int fieldType = 0;

        /**
         * Constructs an empty DbNull Object.
         */
        public DbNull() {
                //empty constructor
        }

        /**
         * Construct a DbNull Object with type information.
         * 
         * @param theFieldType
         *                one of the type constants defined in java.sql.Types
         */
        public DbNull(final int theFieldType) {
                fieldType = theFieldType;
        }

        /**
         * Gets the field type as in java.sql.Types.
         * 
         * @return an integer representing the SQL type
         */
        public final int getFieldType() {
                return fieldType;
        }

        /**
         * Sets the field type as in java.sql.Types.
         * 
         * @param theFieldType
         *                one of the type constants defined in java.sql.Types
         */
        public final void setFieldType(final int theFieldType) {
                fieldType = theFieldType;
        }

}
