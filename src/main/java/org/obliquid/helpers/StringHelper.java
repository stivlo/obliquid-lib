package org.obliquid.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

/**
 * Utility functions for String: SHA1, MD5, random string, ellipsis, escape,
 * implode, quote, repeat, substr, truncate, ucFirst, zeroPad.
 * 
 * @author stivlo
 */
public final class StringHelper {

        /**
         * Lower case Hex Digits.
         */
        private static final String HEX_DIGITS = "0123456789abcdef";

        /**
         * Byte mask.
         */
        private static final int BYTE_MSK = 0xFF;

        /**
         * Hex digit mask.
         */
        private static final int HEX_DIGIT_MASK = 0xF;

        /**
         * Number of bits per Hex digit (4).
         */
        private static final int HEX_DIGIT_BITS = 4;

        /**
         * Helper class.
         */
        private StringHelper() {
                //all methods are static
        }

        /**
         * Compute the SHA-1 digest of a String and return the bytes in
         * hexadecimal format.
         * 
         * @param message
         *                the UTF-8 String to be encoded
         * @return a SHA-1 hash
         * @throws UnsupportedOperationException
         *                 in the case the VM doesn't support UTF-8 which could
         *                 be caused by a VM bug, you shouldn't bother catching
         *                 this exception
         * @throws NullPointerException
         *                 if the String to be encoded is null
         */
        public static String computeSha1OfString(final String message) throws UnsupportedOperationException,
                        NullPointerException {
                try {
                        return computeSha1OfByteArray(message.getBytes(("UTF-8")));
                } catch (UnsupportedEncodingException ex) {
                        throw new UnsupportedOperationException(ex);
                }
        }

        /**
         * Compute the SHA-1 digest of raw bytes and return the bytes in
         * hexadecimal format.
         * 
         * @param message
         *                the raw byte array to be encoded
         * @return a SHA-1 hash
         * @throws UnsupportedOperationException
         *                 in the case SHA-1 MessageDigest is not supported, you
         *                 shouldn't bother catching this exception
         */
        private static String computeSha1OfByteArray(final byte[] message)
                        throws UnsupportedOperationException {
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
         * Compute the md5 hash of a String. using the RSA Data Security, Inc.
         * MD5 Message-Digest Algorithm, and returns that hash. Throws
         * UnsupportedOperationException in case of errors.
         * 
         * @param arg
         *                the UTF-8 String to be encoded
         * @return a 32-character hexadecimal number
         * @throws UnsupportedOperationException
         *                 in the case the VM doesn't support UTF-8 or MD5, you
         *                 shouldn't bother catching this exception
         */
        public static String computeMd5OfString(final String arg) throws UnsupportedOperationException {
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
         *                the raw byte array to be encoded
         * @return a 32-character hexadecimal number
         * @throws UnsupportedOperationException
         *                 in the case MD5 MessageDigest is not supported, you
         *                 shouldn't bother catching this exception
         */
        public static String computeMd5OfByteArray(final byte[] arg) throws UnsupportedOperationException {
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
         * Compute the md5 hash of a InputStream. In case of errors, returns an
         * empty string. It takes care of closing the InputStream
         * 
         * @param inputStream
         *                the InputStream to be hashed
         * @return a 32-character hexadecimal number
         * @throws IOException
         *                 when there are problem reading the stream
         */
        public static String computeMd5OfInputStream(final InputStream inputStream) throws IOException {
                final int base = 16;
                byte[] b = md5AsBytes(inputStream);
                StringBuilder result = new StringBuilder();
                for (int i = 0; i < b.length; i++) {
                        result.append(Integer.toString((b[i] & BYTE_MSK) + BYTE_MSK + 1, base).substring(1));
                }
                return result.toString();
        }

        /**
         * Compute a String in HexDigit from the input.
         * 
         * @param byteArray
         *                a row byte array
         * @return a hex String
         */
        private static String toHexString(final byte[] byteArray) {
                StringBuilder sb = new StringBuilder(byteArray.length * 2);
                for (int i = 0; i < byteArray.length; i++) {
                        int b = byteArray[i] & BYTE_MSK;
                        sb.append(HEX_DIGITS.charAt(b >>> HEX_DIGIT_BITS)).append(
                                        HEX_DIGITS.charAt(b & HEX_DIGIT_MASK));
                }
                return sb.toString();
        }

        /**
         * Compute the md5 hash of a File. In case of errors, returns a zero
         * length array (file not existent, not readable, ...), also takes care
         * of closing the InputStream
         * 
         * @param fis
         *                the input stream to be hashed
         * @return a 32-character hexadecimal number
         * @throws IOException
         *                 when InputStream can't be read properly
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
         * Compute the md5 hash of a InputStream.
         * 
         * @param fis
         *                the InputStream
         * @throws IOException
         *                 when there are problems reading the stream
         * @throws NoSuchAlgorithmException
         *                 should not bother to catch this exception
         * @return a MessageDigest
         */
        private static MessageDigest computeMd5MessageDigest(final InputStream fis) throws IOException,
                        NoSuchAlgorithmException {
                int numRead;
                final int chunk = 1024;
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
         * Repeat input multiplier times. multiplier has to be greater than or
         * equal to 0. If the multiplier is &lt;= 0, the function will return an
         * empty string.
         * 
         * @param input
         *                the input String
         * @param multiplier
         *                how many times to repeat it
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
         * Return input repeated multiplier times, with separator in between.
         * multiplier has to be greater than or equal to 0. If the multiplier is
         * &lt;= 0, the function will return an empty string.
         * 
         * @param input
         *                the string to multiply
         * @param multiplier
         *                how many times to repeat it
         * @param separator
         *                a separator String to be inserted between repetitions
         * @return the resulting String
         */
        public static String repeatWithSeparator(final String input, final int multiplier,
                        final String separator) {
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
         * Quote a field for a SQL query. If the argument is null, the string
         * "NULL" is returned.
         * 
         * @param <T>
         *                the type to quote
         * @param arg
         *                the argument to be escaped and quoted
         * @return the original string quoted and escaped
         */
        public static <T> String quote(final T arg) {
                if (arg == null) {
                        return "NULL";
                }
                return "'" + escape(arg.toString()) + "'";
        }

        /**
         * Quote and zero pad a field for a SQL query.
         * 
         * @param value
         *                the value to be quoted and zero padded
         * @param length
         *                the length of the zero-padded long, excluding the
         *                quotes
         * @return the quoted and zero-padded long value
         */
        public static String quote(final long value, final int length) {
                return "'" + zeroPad(value, length) + "'";
        }

        /**
         * Escape single quotes.
         * 
         * @param arg
         *                the String to escape
         * @return escaped String
         */
        public static String escape(final String arg) {
                return arg.replaceAll("'", "''");
        }

        /**
         * Zero pad the argument until it reach the specified length.
         * 
         * @param arg
         *                the argument to pad
         * @param length
         *                the desired length
         * @return a zero-padded string
         */
        public static String zeroPad(final long arg, final int length) {
                String valueString = "" + arg;
                if (valueString.length() > length) {
                        throw new IllegalArgumentException("Long attribute overflow for " + valueString
                                        + " (length " + valueString.length() + ")");
                }
                StringBuilder format = new StringBuilder("%0");
                format.append(length).append("d");
                return String.format(format.toString(), arg);
        }

        /**
         * Zero pad the decimal part of the argument until it reach the
         * specified scale and then zero pad the integer part until the whole
         * resulting string reach the specified totalLength (also the decimal
         * separator is counted).
         * 
         * @param value
         *                a BigDecimal to be zero-padded
         * @param totalLength
         *                the total length, including the decimal point and the
         *                decimal digits
         * @param scale
         *                number of digits after the decimal point
         * @return a zero-padded String representation
         * @throws IllegalArgumentException
         *                 when the BigDecimal is negative, since is not
         *                 supported
         */
        public static String zeroPad(final BigDecimal value, final int totalLength, final int scale)
                        throws IllegalArgumentException {
                return BdHelper.zeroPad(value, totalLength, scale);
        }

        /**
         * Join array elements with a glue String.
         * 
         * @param glue
         *                the separator
         * @param pieces
         *                the parts to join
         * @return a String containing a String representation of all the array
         *         elements in the same order, with the glue String between each
         *         element.
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
         *                the separator
         * @param pieces
         *                the parts to join
         * @return a String containing a String representation of all the array
         *         elements in the same order, with the glue String between each
         *         element.
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
         *                the separator
         * @param pieces
         *                the parts to join
         * @return a String containing a String representation of all the
         *         Collection elements, with the glue String between each
         *         element.
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
         *                the separator
         * @param pieces
         *                the parts to join
         * @return a String containing a String representation of all the array
         *         elements in the same order, with the glue String between each
         *         element.
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
         * Join keys elements of the HashMap with a glue String and quote each
         * one of them with a quote string. The values are ignored.
         * 
         * @param glue
         *                the connecting string, for instance a comma
         * @param pieces
         *                the parts to be join
         * @param quote
         *                the quote to use
         * @return a String containing a String representation of all the array
         *         elements in the same order, with the glue String between each
         *         element.
         */
        public static String implodeAndQuote(final String glue, final Map<String, Object> pieces,
                        final String quote) {
                StringBuilder sb = new StringBuilder();
                String sep = "";
                Iterator<Entry<String, Object>> it = pieces.entrySet().iterator();
                while (it.hasNext()) {
                        sb.append(sep).append(quote).append((it.next()).getKey()).append(quote);
                        sep = glue;
                }
                return sb.toString();
        }

        /**
         * Join keys elements of the String array with a glue Stringand quote
         * each one of them with a quote string.
         * 
         * @param glue
         *                the connecting string, for instance a comma
         * @param pieces
         *                the parts to be join
         * @param quote
         *                the quote to use
         * @return a String containing a String representation of all the array
         *         elements in the same order, with the glue String between each
         *         element.
         */
        public static String implodeAndQuote(final String glue, final String[] pieces, final String quote) {
                StringBuilder sb = new StringBuilder();
                String sep = "";
                for (String piece : pieces) {
                        sb.append(sep).append(quote).append(piece).append(quote);
                        sep = glue;
                }
                return sb.toString();
        }

        /**
         * A safer, smarter truncate that doesn't complain if length is greater
         * than the string length.
         * 
         * @param str
         *                the input string
         * @param length
         *                how many chars to allow as a max
         * @return the truncated string (which can be the same of the original
         *         if it was already within length)
         * @throws IllegalArgumentException
         *                 if length was negative
         */
        public static String truncate(final String str, final int length) throws IllegalArgumentException {
                String out;
                if (length < 0) {
                        throw new IllegalArgumentException("negative length is not allowed. (length="
                                        + length + ")");
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
         *                The input string.
         * @param startIndex
         *                If start is non-negative, the returned string will
         *                start at the start 'th position in string, counting
         *                from zero. For instance, in the string 'abcdef', the
         *                character at position 0 is 'a', the character at
         *                position 2 is 'c', and so forth. If start is negative,
         *                the returned string will start at the start 'th
         *                character from the end of string. If str is less than
         *                or equal to start characters long, the empty String
         *                will be returned.
         * @param length
         *                If length is positive, the string returned will
         *                contain at most length characters beginning from start
         *                (depending on the length of string).
         * @return the substring
         * @throws IllegalArgumentException
         *                 when length is negative
         */
        public static String substr(final String str, final int startIndex, final int length)
                        throws IllegalArgumentException {
                String out;
                int newStartIndex = startIndex, endIndex;
                if (length < 0) {
                        throw new IllegalArgumentException("Length can't be negative.");
                }
                if (str == null || newStartIndex >= str.length()) {
                        return "";
                }
                if (startIndex < 0) {
                        newStartIndex += str.length(); //we are subtracting since startIndex is negative
                }
                if (startIndex < 0) {
                        newStartIndex = 0; //the previous if could have made startIndex negative
                }
                if (newStartIndex + length >= str.length()) {
                        endIndex = str.length();
                } else {
                        endIndex = newStartIndex + length;
                }
                out = str.substring(newStartIndex, endIndex);
                return out;
        }

        /**
         * Compute a random string composed of lower case letters and numbers.
         * 
         * @param length
         *                the length of the random String
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
         * Make a string's first character upper case.
         * 
         * @param input
         *                the String to process
         * @return a String with the fist character of input capitalized, if
         *         that character is alphabetic.
         */
        public static String ucFirst(final String input) {
                String firstLetter = input.substring(0, 1);
                String remainder = input.substring(1);
                String ucFirst = firstLetter.toUpperCase() + remainder;
                return ucFirst;
        }

        /**
         * Truncate a text after maxLength chars. If the text was longer than
         * maxLength, three dots are added (&hellip;).
         * 
         * @param input
         *                the input string
         * @param maxLength
         *                the maximum String length to allow before truncating
         * @return truncated string
         */
        public static String ellipsis(final String input, final int maxLength) {
                StringBuilder output = new StringBuilder(StringHelper.truncate(input, maxLength));
                if (input.length() > maxLength) {
                        output.append("&hellip;");
                }
                return output.toString();
        }

        /**
         * Recode to correct UTF8 the wrong encoding that you obtain in the DB
         * when the PHP page has UTF8 Content-Type header, but the DB connection
         * uses Latin1 encoding.
         * 
         * @param bogusString
         *                the String to recode
         * 
         * @return a correctly encoded UTF8 string
         */
        public static String recodePhp(final String bogusString) {
                try {
                        return new String(bogusString.getBytes("LATIN1"));
                } catch (UnsupportedEncodingException ex) {
                        throw new UnsupportedOperationException(ex);
                }
        }

}
