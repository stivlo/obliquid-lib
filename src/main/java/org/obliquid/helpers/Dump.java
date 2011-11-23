package org.obliquid.helpers;

import java.util.List;

/**
 * Deep dump of Collections and arrays.
 * 
 * @author stivlo
 * 
 */
public final class Dump {

        /** How many characters to indent. */
        private static final int INDENT = 4;

        /**
         * Class composed of static methods.
         */
        private Dump() {
                //utility class
        }

        /**
         * Build a String composed by size spaces.
         * 
         * @param size
         *                the length of the resulting String
         * @return the String used for indentation
         */
        private static String indent(final int size) {
                return StringHelper.repeat(" ", size);
        }

        /**
         * Dump a String Array for visual inspection, with initial indent.
         * 
         * @param var
         *                the array
         * @param initialIndent
         *                how many spaces to indent
         * @return a String representing the String Array content
         */
        private static String the(final String[] var, final int initialIndent) {
                String indent1 = indent(initialIndent);
                String indent2 = indent(initialIndent + INDENT);
                StringBuffer out = new StringBuffer(indent1).append("String[] {\n");
                for (int i = 0; i < var.length; i++) {
                        out.append(indent2).append('[').append(i).append("] = \"").append(var[i])
                                        .append("\"\n");
                }
                out.append(indent1).append("}\n");
                return out.toString();
        }

        /**
         * Dump a String Array for visual inspection.
         * 
         * @param var
         *                the variable to dump
         * @return a String representing the String Array content
         */
        public static String the(final String[] var) {
                return the(var, 0);
        }

        /**
         * Dump a List for visual inspection.
         * 
         * @param var
         *                the variable to dump
         * @return a String representing the List content
         */
        @SuppressWarnings("rawtypes")
        public static String the(final List var) {
                return the(var, 0);
        }

        /**
         * Dump a List for visual inspection with an initial indentation.
         * 
         * @param var
         *                the variable to dump
         * @param initialIndent
         *                the initial indentation
         * @return a String representing the list content
         */
        @SuppressWarnings("rawtypes")
        private static String the(final List var, final int initialIndent) {
                String indent1 = indent(initialIndent);
                String indent2 = indent(initialIndent + INDENT);
                StringBuffer out = new StringBuffer(indent1).append("List {\n");
                for (int i = 0; i < var.size(); i++) {
                        out.append(indent2).append('[').append(i).append("] = ").append(the(var.get(i)))
                                        .append("\n");
                }
                out.append(indent1).append("}\n");
                return out.toString();
        }

        /**
         * Dump an Object for visual inspection.
         * 
         * @param var
         *                the Object to be dumped
         * @return String representation of the object
         */
        public static String the(final Object var) {
                if (var instanceof String) {
                        return "\"" + var + "\"";
                }
                return var.toString();
        }

}
