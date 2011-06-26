package org.obliquid.helpers;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.obliquid.helpers.StringHelper;

/**
 * Recursive dump of Collections and Arrays. I wrote this long ago, should revisit to use generics
 * and maybe reflection, but not a priority, only if need arises, for the moment it's good enough.
 * 
 * @author stivlo
 */
public class RecursiveDump {

    private RecursiveDump() {
        //empty constructor
    }

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

    public static String dump(final String[][] ar) {
        StringBuilder res = new StringBuilder();
        res.append("Array(\n");
        for (int i = 0; i < ar.length; i++) {
            res.append(dump(ar[i], 2));
            if (i + 1 < ar.length)
                res.append(",\n");
            else
                res.append("\n");
        }
        res.append(")\n");
        return res.toString();
    }

    public static String dump(final String[] ar) {
        return dump(ar, 0);
    }

    public static String dump(final Integer[] ar) {
        StringBuilder res = new StringBuilder();
        res.append("Array( ");
        for (int i = 0; i < ar.length; i++) {
            res.append("[").append(ar[i]).append("] ");
        }
        res.append(")");
        return res.toString();
    }

    public static String dump(final int[] ar) {
        StringBuilder res = new StringBuilder();
        res.append("Array( ");
        for (int i = 0; i < ar.length; i++) {
            res.append("[").append(ar[i]).append("] ");
        }
        res.append(")");
        return res.toString();
    }

    @SuppressWarnings("unchecked")
    private static String dump(final Object obj, final int indent) {
        if (obj == null) {
            return "null";
        }
        if (obj instanceof ArrayList) {
            return StringHelper.repeat(" ", indent) + dump((ArrayList<Object>) obj, indent);
        } else if (obj instanceof HashMap) {
            return StringHelper.repeat(" ", indent) + dump((HashMap<Object, Object>) obj, indent);
        } else if (obj instanceof String[]) {
            return dump((String[]) obj);
        } else {
            return obj + "";
        }
    }

    private static String dump(final List<Object> ar, final int indent) {
        StringBuilder res = new StringBuilder();
        Object obj;
        if (ar == null)
            return "null";
        res.append("Array\n").append(StringHelper.repeat(" ", indent)).append("(\n");
        for (int i = 0; i < ar.size(); i++) {
            obj = ar.get(i);
            res.append(StringHelper.repeat(" ", indent + 4)).append("[").append(i).append("] => ")
                    .append(dump(obj, indent + 4)).append("\n");
        }
        res.append(StringHelper.repeat(" ", indent)).append(")\n");
        return res.toString();
    }

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
            res.append(StringHelper.repeat(" ", indent + 4)).append("[").append(dump(key, indent + 4))
                    .append("] => ").append(dump(value, indent + 4)).append("\n");
        }
        res.append(StringHelper.repeat(" ", indent)).append(")\n");
        return res.toString();
    }

    private static String dump(final Enumeration<Object> myEnum, final int indent) {
        StringBuilder res = new StringBuilder();
        Object elem;
        while (myEnum.hasMoreElements()) {
            elem = myEnum.nextElement();
            res.append(StringHelper.repeat(" ", indent + 4)).append("[").append(dump(elem, indent + 4))
                    .append("]\n");
        }
        return res.toString();
    }

    public static String dump(final Map<String, Object> hm) {
        return dump(hm, 0);
    }

    public static String dump(final List<Object> ar) {
        return dump(ar, 0);
    }

    public static String dump(final ArrayList<String[]> ar) {
        if (ar == null) {
            return "null";
        }
        StringBuilder res = new StringBuilder();
        res.append("[\n");
        for (int i = 0; i < ar.size(); i++) {
            res.append(dump(ar.get(i), 2));
        }
        res.append("]\n");
        return res.toString();
    }

    public static String dump(final Enumeration<Object> myEnum) {
        return dump(myEnum, 0);
    }

}
