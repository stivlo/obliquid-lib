package org.obliquid.helpers;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

/**
 * Unit test for class StringHelper
 * 
 * @author stivlo
 * 
 */
public class StringHelperShould {

    private final static String stringToHash = "it's a really fine day";

    @Test
    public void computeSha1OfString() {
        String sha1Hash = StringHelper.computeSha1OfString(stringToHash);
        assertEquals("b4aadff1f039adb3f88bd84ceaa56e0eb62e1ba7", sha1Hash);
    }

    @Test(expected = NullPointerException.class)
    public void computeSha1OfStringShouldThrowExceptionForNullArgument() {
        StringHelper.computeSha1OfString(null);
    }

    @Test
    public void computeMd5OfString() {
        String md5Hash = StringHelper.computeMd5OfString(stringToHash);
        assertEquals("a40b928eea9b71984fbf3beee15a83e8", md5Hash);
    }

    @Test(expected = NullPointerException.class)
    public void computeMd5OfStringShouldThrowExceptionForNullArgument() {
        StringHelper.computeMd5OfString(null);
    }

    @Test
    public void computeMd5OfInputStream() throws IOException {
        String md5Hash = StringHelper.computeMd5OfInputStream(new ByteArrayInputStream(stringToHash
                .getBytes("UTF-8")));
        assertEquals("a40b928eea9b71984fbf3beee15a83e8", md5Hash);
    }

    @Test(expected = NullPointerException.class)
    public void computeMd5OfInputStreamShouldThrowExceptionForNullArgument() throws IOException {
        StringHelper.computeMd5OfInputStream(null);
    }

    @Test
    public void repeatAString() {
        String sentence = "very good";
        String result = StringHelper.repeat(sentence, 3);
        assertEquals("very goodvery goodvery good", result);

    }

    @Test
    public void repeatAStringZeroTimesShouldReturnAnEmptyString() {
        String sentence = "any";
        assertEquals("", StringHelper.repeat(sentence, 0));
    }

    @Test
    public void repeatAStringNegativeTimesShouldReturnAnEmptyString() {
        String sentence = "heya";
        assertEquals("", StringHelper.repeat(sentence, -1));
    }

    @Test
    public void repeatWithSeparator() {
        String sentence = "very good";
        String result = StringHelper.repeatWithSeparator(sentence, 3, ", ");
        assertEquals("very good, very good, very good", result);
    }

    @Test
    public void repeatWithSeparatorZeroTimesShouldReturnAnEmptyString() {
        String sentence = "very good";
        assertEquals("", StringHelper.repeatWithSeparator(sentence, 0, ", "));
    }

    @Test
    public void repeatWithSeparatorNegativeTimesShouldReturnAnEmptyString() {
        String sentence = "very good";
        assertEquals("", StringHelper.repeatWithSeparator(sentence, -1, ", "));
    }

    @Test
    public void quote() {
        String result = StringHelper.quote("l'altro giorno");
        assertEquals("'l''altro giorno'", result);
        result = StringHelper.quote(55, 3);
        assertEquals("'055'", result);
    }

    @Test
    public void quoteNullShouldReturnTheStringNULL() {
        assertEquals("NULL", StringHelper.quote(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void zeroPad() {
        StringHelper.zeroPad(5555, 3);
    }

    /*
     * @Test public void doZeroPadBigDecimal() { String result = StringHelper.zeroPad1(new
     * BigDecimal("1.2"), 5, 2); assertEquals("01.20", result); }
     * 
     * @Test(expected = IllegalArgumentException.class) public void
     * refuseToDoZeroPadBigNegativeDecimal() { StringHelper.zeroPad1(new BigDecimal("-1.2"), 4, 2);
     * }
     */

    @Test
    public void implode() {
        String result = StringHelper.implode(", ", new int[] {});
        assertEquals("", result);
        result = StringHelper.implode(", ", new int[] { 1, 2 });
        assertEquals("1, 2", result);
        result = StringHelper.implode(", ", new String[] { "a", "b" });
        assertEquals("a, b", result);
        HashMap<String, Object> ingredients = new HashMap<String, Object>();
        ingredients.put("spaghetti", "100g");
        ingredients.put("sauce", "150g");
        result = StringHelper.implode(", ", ingredients);
        assertEquals("spaghetti, sauce", result);
        List<String> pieces = new ArrayList<String>();
        pieces.add("uno");
        pieces.add("due");
        assertEquals("uno, due", StringHelper.implode(", ", pieces));
    }

    @Test
    public void truncate() {
        String result = StringHelper.truncate("my string", 3);
        assertEquals("my ", result);
        result = StringHelper.truncate("short", 20);
        assertEquals("short", result);
    }

    @Test
    public void subStr() {
        String input = "abcd";
        assertEquals("bc", StringHelper.substr(input, 1, 2));
        assertEquals("", StringHelper.substr(null, 1, 2));
        assertEquals("cd", StringHelper.substr(input, -2, 2));
        assertEquals("d", StringHelper.substr(input, 3, 4));
        assertEquals("ab", StringHelper.substr(input, -20, 2));
        assertEquals("", StringHelper.substr(input, 20, 2));
        assertEquals("cd", StringHelper.substr(input, 2, 20));
    }

    @Test(expected = IllegalArgumentException.class)
    public void subStrShouldThrowExceptionWhenLengthIsNegative() {
        StringHelper.substr("abcd", 1, -2);
    }

    @Test
    public void extractFieldNameTest() {
        HashMap<String, String> ingredients = new HashMap<String, String>();
        ingredients.put("spaghetti", "100g");
        ingredients.put("sauce", "150g");
        String result = StringHelper.implodeAndQuote(",", ingredients, "'");
        assertEquals("'spaghetti','sauce'", result);
    }

    @Test
    public void computeRandomStringTest() {
        for (int i = 0; i < 20; i++) {
            assertEquals(i, StringHelper.computeRandomString(i).length());
        }
    }

    @Test
    public void ucFirstTest() {
        assertEquals("Una Vita", StringHelper.ucFirst("una Vita"));
        assertEquals("Una Vita", StringHelper.ucFirst("Una Vita"));
    }

    @Test
    public void implodeAndQuoteTest() {
        String fields[] = { "uno", "due" };
        assertEquals("\"uno\", \"due\"", StringHelper.implodeAndQuote(", ", fields, "\""));
    }

    @Test
    public void implementEllipsis() {
        assertEquals("0122345789", StringHelper.ellipsis("0122345789", 10));
        assertEquals("0122345789", StringHelper.ellipsis("0122345789", 11));
        assertEquals("012234578&hellip;", StringHelper.ellipsis("0122345789", 9));
        assertEquals("&hellip;", StringHelper.ellipsis("0122345789", 0));
        assertEquals("0&hellip;", StringHelper.ellipsis("0122345789", 1));
        assertEquals("", StringHelper.ellipsis("", 1));
        assertEquals("", StringHelper.ellipsis("", 0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void refuseToProcessNegativeMaxLengthInEllipsis() {
        StringHelper.ellipsis("", -1);
    }

}
