package org.obliquid.datatype;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Test;
import org.obliquid.datatype.impl.BooleanTypeImpl;

/**
 * Checking the scalar type Boolean.
 * 
 * @author stivlo
 * 
 */
public class BooleanTypeImplShould {

        /**
         * Check that String "N" is valid.
         */
        @Test
        public final void considerStringNValid() {
                BooleanType myType = new BooleanTypeImpl();
                assertTrue(myType.isTheStringValid("N"));
        }

        /**
         * Check that String "Y" is valid.
         */
        @Test
        public final void considerStringYValid() {
                BooleanType myType = new BooleanTypeImpl();
                assertTrue(myType.isTheStringValid("Y"));
        }

        /**
         * Check that String "y" isn't valid.
         */
        @Test
        public final void considerStringyInvalid() {
                BooleanType myType = new BooleanTypeImpl();
                assertFalse(myType.isTheStringValid("y"));
        }

        /**
         * If I assign the String "Y" to a BooleanType, getData() will return
         * true.
         */
        @Test
        public final void getStringYasTrue() {
                BooleanType myType = new BooleanTypeImpl();
                myType.setDataFromString("Y");
                assertTrue(myType.getData());
        }

        /**
         * If I assign the String "N" to a BooleanType, getAsBoolean() will
         * return false.
         */
        @Test
        public final void getStringNasFalse() {
                BooleanType myType = new BooleanTypeImpl();
                myType.setDataFromString("N");
                assertFalse(myType.getData());
        }

        /**
         * If I try to assign the Character 'n' to a BooleanType an
         * IllegalArgumentException is thrown.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void throwExceptionWhenStringnIsSet() {
                BooleanType myType = new BooleanTypeImpl();
                myType.setDataFromString("n");
        }

        /**
         * If I set true, I expect to get true from getData().
         */
        @Test
        public final void returnTrueWhenTrueWasSet() {
                BooleanType myType = new BooleanTypeImpl();
                myType.setData(true);
                assertTrue(myType.getData());
        }

        /**
         * If I set false, I expect to get false from getAsBoolean().
         */
        @Test
        public final void returnFalseWhenFalseWasSet() {
                BooleanType myType = new BooleanTypeImpl();
                myType.setData(false);
                assertFalse(myType.getData());
        }

        /**
         * The null String is not valid, but should not throw exceptions.
         */
        @Test
        public final void isValidForNullStringIsFalse() {
                BooleanType myType = new BooleanTypeImpl();
                String nullString = null;
                assertFalse(myType.isTheStringValid(nullString));
        }

        /**
         * Can't format null, throw exception.
         */
        @Test(expected = IllegalStateException.class)
        public final void formatDataForNullValueThrowsException() {
                BooleanType myType = new BooleanTypeImpl();
                myType.formatData(Locale.getDefault());
        }

        /**
         * formatData for true returns "true".
         */
        @Test
        public final void formatBooleanTrue() {
                BooleanType myType = new BooleanTypeImpl();
                myType.setData(true);
                assertEquals("true", myType.formatData(Locale.getDefault()));
                assertEquals("true", myType.toString());
        }

        /**
         * formatData for false returns "false".
         */
        @Test
        public final void formatBooleanFalse() {
                BooleanType myType = new BooleanTypeImpl();
                myType.setData(false);
                assertEquals("false", myType.formatData(Locale.getDefault()));
                assertEquals("false", myType.toString());
        }

        /**
         * Throw an exception for getData() with null state.
         */
        @Test(expected = IllegalStateException.class)
        public final void throwAnExceptionWhenDataIsNull() {
                BooleanType myType = new BooleanTypeImpl();
                myType.getData();
        }

        /**
         * Boolean null is not valid.
         */
        @Test
        public final void booleanNullIsNotValid() {
                Boolean value = null;
                BooleanType myType = new BooleanTypeImpl();
                assertFalse(myType.isValid(value));
        }

        /**
         * Boolean true is valid.
         */
        @Test
        public final void booleanTrueIsValid() {
                BooleanType myType = new BooleanTypeImpl();
                assertTrue(myType.isValid(Boolean.TRUE));
        }

        /**
         * Boolean false is valid.
         */
        @Test
        public final void booleanFalseIsValid() {
                BooleanType myType = new BooleanTypeImpl();
                assertTrue(myType.isValid(Boolean.FALSE));
        }

        /**
         * Throw Exception when a null String is passed.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void setNullStringThrowsException() {
                String value = null;
                BooleanType myType = new BooleanTypeImpl();
                myType.setDataFromString(value);
        }

        /**
         * Throw Exception When a null Boolean is passed.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void setNullBooleanThrowsException() {
                Boolean value = null;
                BooleanType myType = new BooleanTypeImpl();
                myType.setData(value);
        }

}
