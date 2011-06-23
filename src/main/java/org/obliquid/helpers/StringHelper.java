package org.obliquid.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

/**
 * Utility functions for String: SHA1, MD5, random string, ellipsis, escape, implode, quote, repeat,
 * substr, truncate, ucFirst, zeroPad
 * 
 * @author stivlo
 */
public class StringHelper {

    private static final String HEX_DIGITS = "0123456789abcdef";

    private StringHelper() {
        //all methods are static
    }

    /**
     * Compute the SHA-1 digest of a String and return the bytes in hexadecimal format
     * 
     * @param message
     *            the UTF-8 String to be encoded
     * @return a SHA-1 hash
     * @throws UnsupportedOperationException
     *             in the case the VM doesn't support UTF-8 which could be caused by a VM bug, you
     *             shouldn't bother catching this exception
     * @throws NullPointerException
     *             if the String to be encoded is null
     */
    public static String computeSha1OfString(final String message) {
        try {
            return computeSha1OfByteArray(message.getBytes(("UTF-8")));
        } catch (UnsupportedEncodingException ex) {
            throw new UnsupportedOperationException(ex);
        }
    }

    /**
     * Compute the SHA-1 digest of raw bytes and return the bytes in hexadecimal format
     * 
     * @param message
     *            the raw byte array to be encoded
     * @return a SHA-1 hash
     * @throws UnsupportedOperationException
     *             in the case SHA-1 MessageDigest is not supported, you shouldn't bother catching
     *             this exception
     */
    private static String computeSha1OfByteArray(final byte[] message) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(message);
            byte[] res = md.digest();
            return toHexString(res);
        } catch (NoSuchAlgorithmException ex) {
            throw new UnsupportedOperationException(ex);
        }
    }

    /**
     * Compute the md5 hash of a String. using the RSA Data Security, Inc. MD5 Message-Digest
     * Algorithm, and returns that hash. Throws UnsupportedOperationException in case of errors.
     * 
     * @param arg
     *            the UTF-8 String to be encoded
     * @return a 32-character hexadecimal number
     * @throws UnsupportedOperationException
     *             in the case the VM doesn't support UTF-8 or MD5, you shouldn't bother catching
     *             this exception
     */
    public static String computeMd5OfString(final String arg) {
        try {
            return computeMd5OfByteArray(arg.getBytes(("UTF-8")));
        } catch (UnsupportedEncodingException ex) {
            throw new UnsupportedOperationException(ex);
        }
    }

    /**
     * Compute the md5 hash of a byte array.
     * 
     * @param arg
     *            the raw byte array to be encoded
     * @return a 32-character hexadecimal number
     * @throws UnsupportedOperationException
     *             in the case MD5 MessageDigest is not supported, you shouldn't bother catching
     *             this exception
     */
    public static String computeMd5OfByteArray(final byte[] arg) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(arg);
            byte[] res = md.digest();
            return toHexString(res);
        } catch (NoSuchAlgorithmException ex) {
            throw new UnsupportedOperationException(ex);
        }
    }

    /**
     * Compute the md5 hash of a InputStream. In case of errors, returns an empty string. It takes
     * care of closing the InputStream
     * 
     * @param filename
     *            the file to be hashed
     * @return a 32-character hexadecimal number
     * @throws IOException
     */
    public static String computeMd5OfInputStream(final InputStream inputStream) throws IOException {
        byte[] b = md5AsBytes(inputStream);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            result.append(Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }

    /**
     * Compute a String in HexDigit from the input
     * 
     * @param byteArray
     *            a row byte array
     * @return a hex String
     */
    private static String toHexString(final byte[] byteArray) {
        StringBuilder sb = new StringBuilder(byteArray.length * 2);
        for (int i = 0; i < byteArray.length; i++) {
            int b = byteArray[i] & 0xFF;
            sb.append(HEX_DIGITS.charAt(b >>> 4)).append(HEX_DIGITS.charAt(b & 0xF));
        }
        return sb.toString();
    }

    /**
     * Compute the md5 hash of a File. In case of errors, returns a zero length array (file not
     * existent, not readable, ...), also takes care of closing the InputStream
     * 
     * @param file
     *            the file to be hashed
     * @return a 32-character hexadecimal number
     * @throws IOException
     *             when InputStream can't be read properly
     */
    private static byte[] md5AsBytes(final InputStream fis) throws IOException {
        MessageDigest complete;
        try {
            complete = computeMd5MessageDigest(fis);
        } catch (NoSuchAlgorithmException ex) {
            throw new UnsupportedOperationException(ex);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
                return new byte[0];
            }
        }
        return complete.digest();
    }

    /**
     * Compute the md5 hash of a InputStream
     * 
     * @param fis
     *            the InputStream
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    private static MessageDigest computeMd5MessageDigest(final InputStream fis) throws IOException,
            NoSuchAlgorithmException {
        int numRead;
        int chunk = 1024;
        MessageDigest complete = MessageDigest.getInstance("MD5");
        byte[] buffer = new byte[chunk];
        do {
            numRead = fis.read(buffer, 0, chunk);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);
        return complete;
    }

    /**
     * Repeat input multiplier times. multiplier has to be greater than or equal to 0. If the
     * multiplier is &lt;= 0, the function will return an empty string.
     * 
     * @param input
     *            the input String
     * @param multiplier
     *            how many times to repeat it
     * @return the resulting String
     */
    public static String repeat(final String input, final int multiplier) {
        if (multiplier <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < multiplier; i++) {
            sb.append(input);
        }
        return sb.toString();
    }

    /**
     * Return input repeated multiplier times, with separator in between. multiplier has to be
     * greater than or equal to 0. If the multiplier is &lt;= 0, the function will return an empty
     * string.
     * 
     * @param input
     *            the string to multiply
     * @param multiplier
     *            how many times to repeat it
     * @param separator
     *            a separator String to be inserted between repetitions
     * @return the resulting String
     */
    public static String repeatWithSeparator(final String input, final int multiplier, final String separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < multiplier; i++) {
            if (i > 0) {
                sb.append(separator);
            }
            sb.append(input);
        }
        return sb.toString();
    }

    /**
     * Quote a field for a SQL query. If the argument is null, the string "NULL" is returned.
     * 
     * @param arg
     *            the argument to be escaped and quoted
     * @return the original string quoted and escaped
     */
    public static <T> String quote(final T arg) {
        if (arg == null) {
            return "NULL";
        }
        return "'" + escape(arg.toString()) + "'";
    }

    /**
     * Quote and zeropad a field for a SQL query
     * 
     * @param arg
     * @param length
     * @return
     */
    public static String quote(final long arg, final int length) {
        return "'" + zeroPad(arg, length) + "'";
    }

    public static String escape(final String arg) {
        return arg.replaceAll("'", "''");
    }

    /**
     * Zero pad the argument until it reach the specified length
     * 
     * @param arg
     *            the argument to pad
     * @param length
     *            the desired length
     * @return a zero-padded string
     */
    public static String zeroPad(final long arg, final int length) {
        String valueString = "" + arg;
        if (valueString.length() > length) {
            throw new IllegalArgumentException("Long attribute overflow for " + valueString + " (length "
                    + valueString.length() + ")");
        }
        StringBuilder format = new StringBuilder("%0");
        format.append(length).append("d");
        return String.format(format.toString(), arg);
    }

    /**
     * Join array elements with a glue String.
     * 
     * @param glue
     * @param pieces
     * @return a String containing a String representation of all the array elements in the same
     *         order, with the glue String between each element.
     */
    public static String implode(final String glue, final int[] pieces) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pieces.length; i++) {
            if (i != 0) {
                sb.append(glue);
            }
            sb.append(pieces[i]);
        }
        return sb.toString();
    }

    /**
     * Join array elements with a glue String.
     * 
     * @param glue
     * @param pieces
     * @return a String containing a String representation of all the array elements in the same
     *         order, with the glue String between each element.
     */
    public static String implode(final String glue, final String[] pieces) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pieces.length; i++) {
            if (i != 0) {
                sb.append(glue);
            }
            sb.append(pieces[i]);
        }
        return sb.toString();
    }

    /**
     * Join collection elements with a glue String.
     * 
     * @param glue
     * @param pieces
     * @return a String containing a String representation of all the Collection elements, with the
     *         glue String between each element.
     */
    public static String implode(final String glue, final Collection<?> pieces) {
        StringBuilder sb = new StringBuilder();
        boolean firstElement = true;
        for (Object element : pieces) {
            if (!firstElement) {
                sb.append(glue);
            }
            sb.append(element);
            firstElement = false;
        }
        return sb.toString();
    }

    /**
     * Join keys elements of the HashMap with a glue String.
     * 
     * @param glue
     * @param pieces
     * @return a String containing a String representation of all the array elements in the same
     *         order, with the glue String between each element.
     */
    public static String implode(final String glue, final Map<String, Object> pieces) {
        String sep = null;
        StringBuilder sb = new StringBuilder();
        Iterator<Entry<String, Object>> it = pieces.entrySet().iterator();
        while (it.hasNext()) {
            if (sep != null) {
                sb.append(sep);
            }
            sb.append((it.next()).getKey());
            sep = glue;
        }
        return sb.toString();
    }

    /**
     * Join keys elements of the HashMap with a glue String and quote each one of them with a quote
     * string.
     * 
     * @param glue
     * @param pieces
     * @param quote
     * @return a String containing a String representation of all the array elements in the same
     *         order, with the glue String between each element.
     */
    public static String implodeAndQuote(final String glue, final HashMap<String, String> pieces,
            final String quote) {
        StringBuilder sb = new StringBuilder();
        String sep = "";
        Iterator<Entry<String, String>> it = pieces.entrySet().iterator();
        while (it.hasNext()) {
            sb.append(sep);
            sb.append(quote);
            sb.append((it.next()).getKey());
            sb.append(quote);
            sep = glue;
        }
        return sb.toString();
    }

    public static String implodeAndQuote(final String glue, final String[] pieces, final String quote) {
        StringBuilder sb = new StringBuilder();
        String sep = "";
        for (String piece : pieces) {
            sb.append(sep);
            sb.append(quote);
            sb.append(piece);
            sb.append(quote);
            sep = glue;
        }
        return sb.toString();
    }

    /**
     * A safer, smarter truncate that doesn't complain if length is greater than the string length.
     * 
     * @param str
     *            the input string
     * @param length
     *            how many chars to allow as a max
     * @return the truncated string (which can be the same of the original if it was already within
     *         length)
     * @throws IllegalArgumentException
     *             if length was negative
     */
    public static String truncate(final String str, final int length) {
        String out;
        if (length < 0) {
            throw new IllegalArgumentException("negative length is not allowed. (length=" + length + ")");
        }
        if (str.length() <= length) {
            out = str;
        } else {
            out = str.substring(0, length);
        }
        return out;
    }

    /**
     * Return part of a string.
     * 
     * @param str
     *            The input string.
     * @param startIndex
     *            If start is non-negative, the returned string will start at the start 'th position
     *            in string, counting from zero. For instance, in the string 'abcdef', the character
     *            at position 0 is 'a', the character at position 2 is 'c', and so forth. If start
     *            is negative, the returned string will start at the start 'th character from the
     *            end of string. If str is less than or equal to start characters long, the empty
     *            String will be returned.
     * @param length
     *            If length is positive, the string returned will contain at most length characters
     *            beginning from start (depending on the length of string).
     * @return the substring
     * @throws IllegalArgumentException
     *             when length is negative
     */
    public static String substr(String str, int startIndex, int length) {
        String out;
        int endIndex;
        if (length < 0) {
            throw new IllegalArgumentException("Length can't be negative.");
        }
        if (str == null || startIndex >= str.length()) {
            return "";
        }
        if (startIndex < 0) {
            startIndex = str.length() + startIndex; //we are subtracting since startIndex is negative
        }
        if (startIndex < 0) {
            startIndex = 0; //the previous if could have made startIndex negative
        }
        if (startIndex + length >= str.length()) {
            endIndex = str.length();
        } else {
            endIndex = startIndex + length;
        }
        out = str.substring(startIndex, endIndex);
        return out;
    }

    /**
     * Compute a random string composed of lowercase letters and numbers
     * 
     * @param length
     *            the length of the random String
     * @return a random String
     */
    public static String computeRandomString(final int length) {
        Random r = new Random(); // just create one and keep it around
        String alphabet = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(alphabet.charAt(r.nextInt(alphabet.length())));
        }
        String randomName = sb.toString();
        return randomName;
    }

    /**
     * Make a string's first character upper case
     * 
     * @param input
     * @return a String with the fist character of input capitalized, if that character is
     *         alphabetic.
     */
    public static String ucFirst(String input) {
        String firstLetter = input.substring(0, 1);
        String remainder = input.substring(1);
        String ucFirst = firstLetter.toUpperCase() + remainder;
        return ucFirst;
    }

    /**
     * Truncate a text after maxLength chars. If the text was longer than maxLength, three dots are
     * added (&hellip;).
     * 
     * @param input
     *            the input string
     * @return truncated string
     */
    public static String ellipsis(String input, int maxLength) {
        StringBuilder output = new StringBuilder(StringHelper.truncate(input, maxLength));
        if (input.length() > maxLength) {
            output.append("&hellip;");
        }
        return output.toString();
    }

}
