package org.obliquid.helpers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Class under test: Dump.
 * 
 * @author stivlo
 * 
 */
public class DumpShould {

        /**
         * Dump a String array.
         */
        @Test
        public final void dumpAStringArray() {
                String[] test = { "a", "b", "third" };
                String expected = "String[] {\n    [0] = \"a\"\n    [1] = \"b\"\n    [2] = \"third\"\n}\n";
                String computed = Dump.the(test);
                assertEquals(expected, computed);
        }

        /**
         * Dump a List of String.
         */
        @Test
        public final void dumpAListOfString() {
                List<String> list = new ArrayList<String>();
                list.add("a");
                list.add("b");
                list.add("third");
                String expected = "List {\n    [0] = \"a\"\n    [1] = \"b\"\n    [2] = \"third\"\n}\n";
                String computed = Dump.the(list);
                assertEquals(expected, computed);
        }

        /**
         * Dump a List of integer.
         */
        @Test
        public final void dumpAListOfInt() {
                List<Integer> list = new ArrayList<Integer>();
                list.add(1);
                list.add(5);
                list.add(2091);
                String expected = "List {\n    [0] = 1\n    [1] = 5\n    [2] = 2091\n}\n";
                String computed = Dump.the(list);
                assertEquals(expected, computed);
        }

}
