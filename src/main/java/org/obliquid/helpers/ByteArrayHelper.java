package org.obliquid.helpers;

import java.util.ArrayList;
import java.util.List;

/**
 * Analyze and manipulate byte arrays: search, replace, toArray
 * 
 * @author stivlo
 */
public class ByteArrayHelper {

    /**
     * Search rawData for a 'search' byte sequence
     * 
     * @return false if the 'search' byte sequence is not contained in rawData, true if one or more
     *         byte sequences is found
     */
    public static boolean search(final byte[] rawData, final byte[] search) {
        int posInSearch = 0;
        for (int i = 0; i < rawData.length; i++) {
            if (rawData[i] != search[posInSearch]) {
                if (posInSearch > 0) {
                    //found part only, reset posInSearch
                    posInSearch = 0;
                }
            } else {
                posInSearch++;
                if (posInSearch == search.length) {
                    return true; //found all!
                }
            }
        }
        return false; //if we arrive here we never found the search sequence
    }

    /**
     * Replace all occurences of 'search' byte sequences with 'replace' byte sequence in rawData
     * 
     * @param rawData
     *            the input array
     * @param search
     *            the byte array to search
     * @param replace
     *            the byte array to replace
     * @return a new byte array with all 'search' byte sequences replaced by 'replace' byte sequence
     */
    public static byte[] replace(byte[] rawData, byte[] search, byte[] replace) {
        List<Byte> newBytes = new ArrayList<Byte>();
        byte curByte;
        int posInSearch = 0;
        for (int i = 0; i < rawData.length; i++) {
            curByte = rawData[i];
            //System.out.println("> " + curByte + " " + (char) curByte);
            if (curByte != search[posInSearch]) {
                if (posInSearch > 0) {
                    //System.out.print(" found part only ");
                    addBytesToList(newBytes, search, posInSearch);
                    posInSearch = 0;
                }
                newBytes.add(curByte);
                //System.out.print(" ADD(" + curByte + ") ");
            } else {
                posInSearch++;
                //System.out.print(" found " + posInSearch + " still checking... ");
                if (posInSearch == search.length) {
                    //System.out.print(" found all! ");
                    addBytesToList(newBytes, replace);
                    posInSearch = 0;
                }
            }
            //System.out.println();
        }
        return toArray(newBytes);
    }

    /**
     * Add the first howMany bytes from bytesToAdd to byteArray
     * 
     * @param byteArray
     *            the List to modify
     * @param bytesToAdd
     *            the bytes to be added from 0 to howMany - 1
     * @param howMany
     *            how many bytes to add
     */
    private static void addBytesToList(final List<Byte> byteArray, final byte[] bytesToAdd, final int howMany) {
        for (int i = 0; i < howMany; i++) {
            byteArray.add(bytesToAdd[i]);
            //System.out.print(" ADD (" + bytesToAdd[i] + ") ");
        }
    }

    /**
     * Add the all the bytes from bytesToAdd to byteArray
     * 
     * @param byteArray
     *            the List to modify
     * @param bytesToAdd
     *            the bytes to be added from 0 to howMany - 1
     * @param howMany
     *            how many bytes to add
     */
    private static void addBytesToList(final List<Byte> byteArray, final byte[] bytesToAdd) {
        addBytesToList(byteArray, bytesToAdd, bytesToAdd.length);
    }

    /**
     * Convert a List of Byte into an array of byte
     * 
     * @param original
     *            the List<Byte>
     * @return the resulting byte[]
     */
    public static byte[] toArray(List<Byte> original) {
        byte[] result = new byte[original.size()];
        int pos = 0;
        for (Byte aByte : original) {
            result[pos] = aByte;
            pos++;
        }
        return result;
    }

}
