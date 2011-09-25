package org.obliquid.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Helper class for Arrays and Collections: build HashMap from two arrays, build
 * an ArrayList from one or two Arrays.
 * 
 * @author stivlo
 * 
 */
public final class ArrayHelper {

        /**
         * Utility class.
         */
        private ArrayHelper() {

        }

        /**
         * Build an ArrayList from a HashMap.
         * 
         * @param param
         *                HashMap with values to be used to populate our
         *                ArrayList
         * @return an ArrayList Container
         */
        public static ArrayList<Object> buildArrayList(final Map<String, Object> param) {
                ArrayList<Object> arList = new ArrayList<Object>();
                Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
                while (it.hasNext()) {
                        arList.add((it.next()).getValue());
                }
                return arList;
        }

        /**
         * Builds an ArrayList from an array of integer.
         * 
         * @param param
         *                an array of integer
         * @return an ArrayList Container populated with Integer Objects.
         */
        public static ArrayList<Object> buildArrayList(final int[] param) {
                ArrayList<Object> arList = new ArrayList<Object>();
                for (int i = 0; i < param.length; i++) {
                        arList.add(param[i]);
                }
                return arList;
        }

        /**
         * Builds an ArrayList from two HashMap. First all the values from
         * param1 are added (values), after all elements from param2 (primary
         * keys).
         * 
         * @param param1
         *                HashMap with values to be used to populate our
         *                ArrayList
         * @param param2
         *                HashMap with values to be used to populate our
         *                ArrayList
         * @return an ArrayList Container
         */
        public static ArrayList<Object> buildArrayList(final Map<String, Object> param1,
                        final Map<String, Object> param2) {
                ArrayList<Object> arList = new ArrayList<Object>();
                Iterator<Map.Entry<String, Object>> it;
                it = param1.entrySet().iterator();
                while (it.hasNext()) {
                        Object value = (it.next()).getValue();
                        if (value != null && value.equals("true")) {
                                value = "Y";
                        } else if (value != null && value.equals("false")) {
                                value = "N";
                        }
                        arList.add(value);
                }
                it = param2.entrySet().iterator();
                while (it.hasNext()) {
                        arList.add((it.next()).getValue());
                }
                return arList;
        }

        /**
         * Build an ArrayList from an array of String.
         * 
         * @param param
         *                an Array of String
         * @return an ArrayList Container populated with String Objects.
         */
        public static ArrayList<Object> buildArrayList(final String[] param) {
                ArrayList<Object> arList = new ArrayList<Object>();
                for (int i = 0; i < param.length; i++) {
                        arList.add(param[i]);
                }
                return arList;
        }

        /**
         * Build an HashMap from two arrays, the first one is an array with
         * keys, the second one with values. The two arrays must have the same
         * size. No check is done for now.
         * 
         * @param key
         *                an array of keys
         * @param val
         *                an array of values
         * @return an HashMap
         */
        public static HashMap<String, String> buildHashMap(final String[] key, final String[] val) {
                HashMap<String, String> map = new HashMap<String, String>();
                for (int i = 0; i < key.length; i++) {
                        map.put(key[i], val[i]);
                }
                return map;
        }

}
