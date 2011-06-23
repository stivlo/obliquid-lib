package org.obliquid.helpers;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

/**
 * Analyze Apache .htaccess file: listAuthorizedAddresses(), buildAllowFrom()
 * 
 * @author stivlo
 * 
 */
public class HtaccessHelper {

    /**
     * Analyze an .htaccess file and list IP Addresses in allow from directive (case insensitive).
     * Syntax: "Allow from 192.168,1.104 192.168.1.205"
     * 
     * @param fileName
     *            name of the file to be analyzed
     * @return a list of the IP addresses on the same line of allow from or an empty array in case
     *         "allow from" could not be found. If .htaccess is not found return an empty array too.
     */
    public static String[] listAuthorizedAddresses(final String fileName) {
        String line = findAllowFromLine(fileName);
        if (line.length() == 0) {
            return new String[0];
        }
        line = line.replaceAll("allow\\s*from", "").trim();
        return line.split("\\s+");
    }

    /**
     * Build a String of IP addresses separated by a space from an array of authorized IPs
     * 
     * @param authorizedAddresses
     *            an array of IP addresses to be authorized in String form.
     * @return a String with all IP addresses separated by a space
     */
    public static String buildAllowFrom(final String[] authorizedAddresses) {
        StringBuilder sb = new StringBuilder();
        if (authorizedAddresses.length > 0) {
            sb.append("    Allow from");
        }
        for (int i = 0; i < authorizedAddresses.length; i++) {
            sb.append(" ");
            sb.append(authorizedAddresses[i]);
        }
        return sb.toString();
    }

    /**
     * Analize an .htaccess file and return the "Allow from line" (case insensitive).
     * 
     * @param fileName
     *            the .htaccess file to be analyzed
     * @return the "Allow from" line or the empty String if it couldn't be found (i.e. when
     *         .htaccess file couldn't be found or the file was found but didn't contain that
     *         statement. In case the file contains more than one "Allow from" line, only the first
     *         one is returned.
     */
    protected static String findAllowFromLine(final String fileName) {
        String line = null;
        boolean found = false;
        LineIterator lines = null;
        try {
            lines = FileUtils.lineIterator(new File(fileName));
            while (lines.hasNext()) {
                line = lines.next().toLowerCase();
                if (line.indexOf("allow") != -1 && line.indexOf("from") != -1) {
                    found = true;
                    break;
                }
            }
        } catch (IOException e) {
            //in case of exception we force found = false
            found = false;
        } finally {
            System.out.println("lines " + lines);
            if (lines != null) {
                System.out.println("close");
                lines.close();
            }
        }
        if (found) {
            return line;
        }
        return "";
    }

}
