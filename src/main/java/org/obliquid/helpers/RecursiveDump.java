package org.obliquid.helpers;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Recursive dump of Collections and Arrays. I wrote this long ago, should
 * revisit to use generics and maybe reflection, but not a priority, only if
 * need arises, for the moment it's good enough.
 * 
 * @author stivlo
 */
public final class RecursiveDump {

        /** How many characters to indent. */
        private static final int INDENT = 4;

        /** Default constructor. */
        private RecursiveDump() {
                //empty constructor
        }

        /**
         * Dump an array of String, with a certain indentation.
         * 
         * @param ar
         *                the array of String to dump
         * @param indent
         *                how many characters to indent
         * @return a String representation of the String array
         */
        public static String dump(final String[] ar, final int indent) {
                StringBuilder res = new StringBuilder();
                if (indent > 0) {
                        res.append(StringHelper.repeat(" ", indent));
                }
                if (ar == null) {
                        res.append("null");
                        return res.toString();
                }
                res.append("[ ");
                for (int i = 0; i < ar.length; i++) {
                        res.append("'").append(ar[i]).append("' ");
                }
                res.append("]");
                return res.toString();
        }

        /**
         * Dump a Matrix of String (Array of Array).
         * 
         * @param ar
         *                the matrix of String
         * @return a String representation
         */
        public static String dump(final String[][] ar) {
                StringBuilder res = new StringBuilder();
                res.append("Array(\n");
                for (int i = 0; i < ar.length; i++) {
                        res.append(dump(ar[i], INDENT));
                        if (i + 1 < ar.length) {
                                res.append(",\n");
                        } else {
                                res.append("\n");
                        }
                }
                res.append(")\n");
                return res.toString();
        }

        /**
         * Dump an array of String.
         * 
         * @param ar
         *                the array of String to dump
         * @return a String representation of the String array
         */
        public static String dump(final String[] ar) {
                return dump(ar, 0);
        }

        /**
         * Dump an array of Integer.
         * 
         * @param ar
         *                the array of Integer to dump
         * @return a String representation of the Integer array
         */
        public static String dump(final Integer[] ar) {
                StringBuilder res = new StringBuilder();
                res.append("Array( ");
                for (int i = 0; i < ar.length; i++) {
                        res.append("[").append(ar[i]).append("] ");
                }
                res.append(")");
                return res.toString();
        }

        /**
         * Dump an array of int.
         * 
         * @param ar
         *                the array of int to dump
         * @return a String representation of the int array
         */
        public static String dump(final int[] ar) {
                StringBuilder res = new StringBuilder();
                res.append("Array( ");
                for (int i = 0; i < ar.length; i++) {
                        res.append("[").append(ar[i]).append("] ");
                }
                res.append(")");
                return res.toString();
        }

        /**
         * Dump an Object, indented of a certain amount of characters.
         * 
         * @param obj
         *                the object to be dumped
         * @param indent
         *                how many characters to indent
         * @return a String representation of the Object
         */
        @SuppressWarnings("unchecked")
        private static String dump(final Object obj, final int indent) {
                if (obj == null) {
                        return "null";
                }
                if (obj instanceof ArrayList) {
                        return StringHelper.repeat(" ", indent) + dump((ArrayList<Object>) obj, indent);
                } else if (obj instanceof HashMap) {
                        return StringHelper.repeat(" ", indent)
                                        + dump((HashMap<Object, Object>) obj, indent);
                } else if (obj instanceof String[]) {
                        return dump((String[]) obj);
                } else {
                        return obj + "";
                }
        }

        /**
         * Dump a List of Object, indented by a certain amount of characters.
         * 
         * @param ar
         *                the List of Object to dump
         * @param indent
         *                how many characters to indent
         * @return a String representation of the List of Object
         */
        private static String dump(final List<Object> ar, final int indent) {
                StringBuilder res = new StringBuilder();
                Object obj;
                if (ar == null) {
                        return "null";
                }
                res.append("Array\n").append(StringHelper.repeat(" ", indent)).append("(\n");
                for (int i = 0; i < ar.size(); i++) {
                        obj = ar.get(i);
                        res.append(StringHelper.repeat(" ", indent + INDENT)).append("[").append(i)
                                        .append("] => ").append(dump(obj, indent + INDENT)).append("\n");
                }
                res.append(StringHelper.repeat(" ", indent)).append(")\n");
                return res.toString();
        }

        /**
         * Dump an HashMap, indented by a certain amount of characters.
         * 
         * @param hm
         *                the HashMap to be dumped
         * @param indent
         *                how many characters to indent
         * @return a String representation of the HashMap
         */
        private static String dump(final Map<Object, Object> hm, final int indent) {
                StringBuilder res = new StringBuilder();
                Object key, value;
                if (hm == null) {
                        return "HashMap: null\n";
                }
                Set<Object> keys = hm.keySet();
                res.append("HashMap\n").append(StringHelper.repeat(" ", indent)).append("(\n");
                for (Iterator<Object> it = keys.iterator(); it.hasNext();) {
                        key = it.next();
                        value = hm.get(key);
                        res.append(StringHelper.repeat(" ", indent + INDENT)).append("[")
                                        .append(dump(key, indent + INDENT)).append("] => ")
                                        .append(dump(value, indent + INDENT)).append("\n");
                }
                res.append(StringHelper.repeat(" ", indent)).append(")\n");
                return res.toString();
        }

        /**
         * Dump an Enumeration indented a certain amount of characters.
         * 
         * @param myEnum
         *                the Enumeration to dump
         * @param indent
         *                how many characters to indent
         * @return a String representation of the Enumeration
         */
        private static String dump(final Enumeration<Object> myEnum, final int indent) {
                StringBuilder res = new StringBuilder();
                Object elem;
                while (myEnum.hasMoreElements()) {
                        elem = myEnum.nextElement();
                        res.append(StringHelper.repeat(" ", indent + INDENT)).append("[")
                                        .append(dump(elem, indent + INDENT)).append("]\n");
                }
                return res.toString();
        }

        /**
         * Dump an HashMap.
         * 
         * @param hm
         *                the HashMap to dump
         * @return a String representation of the HashMap
         */
        public static String dump(final Map<String, Object> hm) {
                return dump(hm, 0);
        }

        /**
         * Dump a List of Object.
         * 
         * @param ar
         *                a List of Object
         * @return a String representation of the List.
         */
        public static String dump(final List<Object> ar) {
                return dump(ar, 0);
        }

        /**
         * Dump an ArrayList of String[].
         * 
         * @param ar
         *                an ArrayList of String[]
         * @return a String representation of the ArrayList
         */
        public static String dump(final ArrayList<String[]> ar) {
                if (ar == null) {
                        return "null";
                }
                StringBuilder res = new StringBuilder();
                res.append("[\n");
                for (int i = 0; i < ar.size(); i++) {
                        res.append(dump(ar.get(i), INDENT));
                }
                res.append("]\n");
                return res.toString();
        }

        /**
         * Dump an Enumeration.
         * 
         * @param myEnum
         *                the enumeration to be dumped
         * @return a String representation of the enumeration
         */
        public static String dump(final Enumeration<Object> myEnum) {
                return dump(myEnum, 0);
        }

}
