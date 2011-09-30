package org.obliquid.helpers;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

/**
 * Unit test for class StringHelper.
 * 
 * @author stivlo
 * 
 */
public class StringHelperShould {

        /**
         * A test String.
         */
        private static final String STRING_TO_HASH = "it's a really fine day";

        /**
         * Test SHA-1 for a valid String.
         */
        @Test
        public final void computeSha1OfString() {
                String sha1Hash = StringHelper.computeSha1OfString(STRING_TO_HASH);
                assertEquals("b4aadff1f039adb3f88bd84ceaa56e0eb62e1ba7", sha1Hash);
                //stackoverflow.com 7605217
                sha1Hash = StringHelper.computeSha1OfString("abcdef12");
                assertEquals("d253e3bd69ce1e7ce6074345fd5faa1a3c2e89ef", sha1Hash);
        }

        /**
         * SHA-1 for null should throw exception.
         */
        @Test(expected = NullPointerException.class)
        public final void computeSha1OfStringShouldThrowExceptionForNullArgument() {
                StringHelper.computeSha1OfString(null);
        }

        /**
         * Test MD5 for a valid String.
         */
        @Test
        public final void computeMd5OfString() {
                String md5Hash = StringHelper.computeMd5OfString(STRING_TO_HASH);
                assertEquals("a40b928eea9b71984fbf3beee15a83e8", md5Hash);
        }

        /**
         * MD5 for null should throw exception.
         */
        @Test(expected = NullPointerException.class)
        public final void computeMd5OfStringShouldThrowExceptionForNullArgument() {
                StringHelper.computeMd5OfString(null);
        }

        /**
         * MD5 for an input stream.
         * 
         * @throws IOException
         *                 when there are problems reading the stream
         */
        @Test
        public final void computeMd5OfInputStream() throws IOException {
                String md5Hash = StringHelper.computeMd5OfInputStream(new ByteArrayInputStream(
                                STRING_TO_HASH
                                                .getBytes("UTF-8")));
                assertEquals("a40b928eea9b71984fbf3beee15a83e8", md5Hash);
        }

        /**
         * When the input stream is null an exception is thrown.
         * 
         * @throws IOException
         *                 when the stream can't be read
         */
        @Test(expected = NullPointerException.class)
        public final void computeMd5OfInputStreamShouldThrowExceptionForNullArgument() throws IOException {
                StringHelper.computeMd5OfInputStream(null);
        }

        /**
         * Repeat a String 3 times.
         */
        @Test
        public final void repeatAString() {
                final int times = 3;
                String sentence = "very good";
                String result = StringHelper.repeat(sentence, times);
                assertEquals("very goodvery goodvery good", result);

        }

        /**
         * Repeat a String 0 times.
         */
        @Test
        public final void repeatAStringZeroTimesShouldReturnAnEmptyString() {
                String sentence = "any";
                assertEquals("", StringHelper.repeat(sentence, 0));
        }

        /**
         * Repeat a String a negative amount of times.
         */
        @Test
        public final void repeatAStringNegativeTimesShouldReturnAnEmptyString() {
                String sentence = "heya";
                assertEquals("", StringHelper.repeat(sentence, -1));
        }

        /**
         * Repeat a String 3 times with separator.
         */
        @Test
        public final void repeatWithSeparator() {
                final int multiplier = 3;
                String sentence = "very good";
                String result = StringHelper.repeatWithSeparator(sentence, multiplier, ", ");
                assertEquals("very good, very good, very good", result);
        }

        /**
         * Repeat a string 0 times with separator.
         */
        @Test
        public final void repeatWithSeparatorZeroTimesShouldReturnAnEmptyString() {
                String sentence = "very good";
                assertEquals("", StringHelper.repeatWithSeparator(sentence, 0, ", "));
        }

        /**
         * Repeat with separator negative times should return an empty string.
         */
        @Test
        public final void repeatWithSeparatorNegativeTimesShouldReturnAnEmptyString() {
                String sentence = "very good";
                assertEquals("", StringHelper.repeatWithSeparator(sentence, -1, ", "));
        }

        /**
         * Testing quote.
         */
        @Test
        public final void quote() {
                final int length = 3;
                final int numberToBeQuoted = 55;
                String result = StringHelper.quote("l'altro giorno");
                assertEquals("'l''altro giorno'", result);
                result = StringHelper.quote(numberToBeQuoted, length);
                assertEquals("'055'", result);
        }

        /**
         * Quoting null should return 'NULL'.
         */
        @Test
        public final void quoteNullShouldReturnTheStringNULL() {
                assertEquals("NULL", StringHelper.quote(null));
        }

        /**
         * zeroPad with too short length throws exception.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void zeroPad() {
                final int length = 3;
                final int value = 5555;
                StringHelper.zeroPad(value, length);
        }

        /*
         * @Test public void doZeroPadBigDecimal() { String result =
         * StringHelper.zeroPad1(new BigDecimal("1.2"), 5, 2);
         * assertEquals("01.20", result); }
         * 
         * @Test(expected = IllegalArgumentException.class) public void
         * refuseToDoZeroPadBigNegativeDecimal() { StringHelper.zeroPad1(new
         * BigDecimal("-1.2"), 4, 2); }
         */

        /**
         * Various tests with implode.
         */
        @Test
        public final void implode() {
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

        /**
         * Various tests with truncate.
         */
        @Test
        public final void truncate() {
                final int shortLen = 3;
                final int longLen = 20;
                String result = StringHelper.truncate("my string", shortLen);
                assertEquals("my ", result);
                result = StringHelper.truncate("short", longLen);
                assertEquals("short", result);
        }

        /**
         * Sub string tests: normal and with null.
         */
        @Test
        public final void substrNormalAndNull() {
                String input = "abcd";
                assertEquals("bc", StringHelper.substr(input, 1, 2));
                assertEquals("", StringHelper.substr(null, 1, 2));
        }

        /**
         * Sub string test with negative startIndex.
         */
        @Test
        public final void substrWithNegativeStartIndex() {
                final int startIndex = -2;
                String input = "abcd";
                assertEquals("cd", StringHelper.substr(input, startIndex, 2));
        }

        /**
         * Testing sub string with length exceeding the remaining part.
         */
        @Test
        public final void substrWithLengthExceedingRemainingPart() {
                final int startIndex = 3, length = 4;
                String input = "abcd";
                assertEquals("d", StringHelper.substr(input, startIndex, length));
        }

        /**
         * Testing sub string with start index exceeding the length of the
         * string: in this case it will start from the beginning.
         */
        @Test
        public final void substrWithHugeNegativeStartIndex() {
                final int startIndex = -20;
                String input = "abcd";
                assertEquals("ab", StringHelper.substr(input, startIndex, 2));
        }

        /**
         * Testing sub string with huge positive start index, in which case an
         * empty string will be returned.
         */
        @Test
        public final void substrWithHugePositiveStartIndex() {
                final int startIndex = 20;
                String input = "abcd";
                assertEquals("", StringHelper.substr(input, startIndex, 2));
        }

        /**
         * Testing sub string with huge length, in which case all the part that
         * is available will be returned.
         */
        @Test
        public final void substr() {
                final int length = 20;
                String input = "abcd";
                assertEquals("cd", StringHelper.substr(input, 2, length));
        }

        /**
         * Sub String should throw exception when length is negative.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void substrShouldThrowExceptionWhenLengthIsNegative() {
                final int length = -2;
                StringHelper.substr("abcd", 1, length);
        }

        /**
         * Testing implodeAndQuote() with HashMap.
         */
        @Test
        public final void implodeAndQuoteTest1() {
                HashMap<String, Object> ingredients = new HashMap<String, Object>();
                ingredients.put("spaghetti", "100g");
                ingredients.put("sauce", "150g");
                String result = StringHelper.implodeAndQuote(",", ingredients, "'");
                assertEquals("'spaghetti','sauce'", result);
        }

        /**
         * Testing implode and Quote with String array.
         */
        @Test
        public final void implodeAndQuoteTest2() {
                String[] fields = { "uno", "due" };
                assertEquals("\"uno\", \"due\"", StringHelper.implodeAndQuote(", ", fields, "\""));
        }

        /**
         * computeRandomString() test.
         */
        @Test
        public final void computeRandomStringTest() {
                final int times = 20;
                for (int i = 0; i < times; i++) {
                        assertEquals(i, StringHelper.computeRandomString(i).length());
                }
        }

        /**
         * Testing ucFirst().
         */
        @Test
        public final void ucFirstTest() {
                assertEquals("Una Vita", StringHelper.ucFirst("una Vita"));
                assertEquals("Una Vita", StringHelper.ucFirst("Una Vita"));
        }

        /**
         * Testing implementEllipsis().
         */
        @Test
        public final void implementEllipsis() {
                final int shortLen = 9, mediumLen = 10, longLen = 11;
                assertEquals("0122345789", StringHelper.ellipsis("0122345789", mediumLen));
                assertEquals("0122345789", StringHelper.ellipsis("0122345789", longLen));
                assertEquals("012234578&hellip;", StringHelper.ellipsis("0122345789", shortLen));
                assertEquals("&hellip;", StringHelper.ellipsis("0122345789", 0));
                assertEquals("0&hellip;", StringHelper.ellipsis("0122345789", 1));
                assertEquals("", StringHelper.ellipsis("", 1));
                assertEquals("", StringHelper.ellipsis("", 0));
        }

        /**
         * Testing negative max length for ellipsis().
         */
        @Test(expected = IllegalArgumentException.class)
        public final void refuseToProcessNegativeMaxLengthInEllipsis() {
                StringHelper.ellipsis("", -1);
        }

}
