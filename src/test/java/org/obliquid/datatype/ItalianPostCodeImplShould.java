package org.obliquid.datatype;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Test;
import org.obliquid.datatype.impl.ItalianPostCodeImpl;

/**
 * Class under test: ItalianPostCodeImpl.
 * 
 * @author stivlo
 * 
 */
public class ItalianPostCodeImplShould {

        /**
         * Check that the post code "03429" is valid.
         */
        @Test
        public final void beValidForFiveDigits() {
                ItalianPostCode postCode = new ItalianPostCodeImpl();
                assertTrue(postCode.isValid("03429"));
        }

        /**
         * Null is not valid.
         */
        @Test
        public final void notValidForNull() {
                ItalianPostCode postCode = new ItalianPostCodeImpl();
                assertFalse(postCode.isValid(null));
        }

        /**
         * Four digits are not valid.
         */
        @Test
        public final void notValidForFourDigits() {
                ItalianPostCode postCode = new ItalianPostCodeImpl();
                assertFalse(postCode.isValid("0329"));
        }

        /**
         * Five letters are not valid.
         */
        @Test
        public final void notValidForFiveLetters() {
                ItalianPostCode postCode = new ItalianPostCodeImpl();
                assertFalse(postCode.isValid("AXGVZ"));
        }

        /**
         * If there is a letter, is not valid, even if the others are digits and
         * the length is correct.
         */
        @Test
        public final void notValidWithALetter() {
                ItalianPostCode postCode = new ItalianPostCodeImpl();
                assertFalse(postCode.isTheStringValid("0123A"));
        }

        /**
         * Setting a null Post code throws Exception.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void settingANullPostCodeThrowsException() {
                ItalianPostCode postCode = new ItalianPostCodeImpl();
                postCode.setData(null);
        }

        /**
         * Using setDataFromString() with a null Post code throws Exception.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void setDataFromStringWithANullPostCodeThrowsException() {
                ItalianPostCode postCode = new ItalianPostCodeImpl();
                postCode.setData(null);
        }

        /**
         * Setting a wrong post code throws exception.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void settingAWrongPostCodeThrowsException() {
                ItalianPostCode postCode = new ItalianPostCodeImpl();
                postCode.setData("A342");
        }

        /**
         * Setting and retrieving a correct post code.
         */
        @Test
        public final void settingAndRetrievingACorrectPostCode() {
                String code = "23431";
                ItalianPostCode postCode = new ItalianPostCodeImpl();
                postCode.setData(code);
                assertEquals(code, postCode.getData());
                assertEquals(code, postCode.formatData(Locale.getDefault()));
        }

        /**
         * Setting and retrieving a correct post code with setDataFromString().
         */
        @Test
        public final void setDataFromStrinAndRetrievingACorrectPostCode() {
                String code = "00321";
                ItalianPostCode postCode = new ItalianPostCodeImpl();
                postCode.setDataFromString(code);
                assertEquals(code, postCode.getData());
                assertEquals(code, postCode.formatData(Locale.getDefault()));
        }

        /**
         * getData without setData() throws Exception.
         */
        @Test(expected = IllegalStateException.class)
        public final void getWithoutSetThrowsException() {
                ItalianPostCode postCode = new ItalianPostCodeImpl();
                postCode.getData();
        }

        /**
         * formatData without setData throws Exception.
         */
        @Test(expected = IllegalStateException.class)
        public final void formatWithoutSetThrowsException() {
                ItalianPostCode postCode = new ItalianPostCodeImpl();
                postCode.formatData(Locale.getDefault());
        }

}
