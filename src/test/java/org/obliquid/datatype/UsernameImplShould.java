package org.obliquid.datatype;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.Test;
import org.obliquid.datatype.impl.UsernameImpl;

/**
 * Check Username class.
 * 
 * @author stivlo
 * 
 */
public class UsernameImplShould {

        /** Store and retrieve a valid username. */
        @Test
        public final void storeAValidUsername() {
                Username user = new UsernameImpl();
                user.setData("stivlo");
                assertEquals("stivlo", user.getData());
                assertEquals("stivlo", user.formatData(Locale.getDefault()));
        }

        /** A null username isn't valid. */
        @Test
        public final void aNullUsernameIsntValid() {
                Username user = new UsernameImpl();
                assertFalse(user.isValid(null));
                assertFalse(user.isTheStringValid(null));
        }

        /** An empty username isn't valid. */
        @Test
        public final void anEmptyUsernameIsntValid() {
                Username user = new UsernameImpl();
                assertFalse(user.isValid(""));
                assertFalse(user.isTheStringValid(""));
        }

        /** A two letters username isn't valid. */
        @Test
        public final void aTwoLettersUsernameIsntValid() {
                Username user = new UsernameImpl();
                assertFalse(user.isValid("ab"));
                assertFalse(user.isTheStringValid("ab"));
        }

        /** A three letters username is valid. */
        @Test
        public final void aThreeLettersUsernameIsValid() {
                Username user = new UsernameImpl();
                assertTrue(user.isValid("abc"));
                assertTrue(user.isTheStringValid("abc"));
        }

        /** An username ending with $ isn't valid. */
        @Test
        public final void anUsernameEndingWithDollarIsntValid() {
                Username user = new UsernameImpl();
                assertFalse(user.isValid("abc$"));
                assertFalse(user.isTheStringValid("abc$"));
        }

        /** An username starting with $ isn't valid. */
        @Test
        public final void anUsernameStartingWithDollarIsntValid() {
                Username user = new UsernameImpl();
                assertFalse(user.isValid("$abc"));
                assertFalse(user.isTheStringValid("$abc"));
        }

        /** Getting without setting throws exception. */
        @Test(expected = IllegalStateException.class)
        public final void gettingWithoutSettingThrowsException() {
                Username user = new UsernameImpl();
                user.getData();
        }

        /** Formatting without setting throws exception. */
        @Test(expected = IllegalStateException.class)
        public final void formattingWithoutSettingThrowsException() {
                Username user = new UsernameImpl();
                user.formatData(Locale.getDefault());
        }

        /** Setting a null username throws exception. */
        @Test(expected = IllegalArgumentException.class)
        public final void settingANullUsernameThrowsException() {
                Username user = new UsernameImpl();
                user.setData(null);
        }

        /** setFromString a null username throws exception. */
        @Test(expected = IllegalArgumentException.class)
        public final void setFromStringANullUsernameThrowsException() {
                Username user = new UsernameImpl();
                user.setDataFromString(null);
        }

        /** Setting an empty username throws exception. */
        @Test(expected = IllegalArgumentException.class)
        public final void settingAnEmptyUsernameThrowsException() {
                Username user = new UsernameImpl();
                user.setData("");
        }

        /** setFromString an empty username throws exception. */
        @Test(expected = IllegalArgumentException.class)
        public final void setFromStringAnEmptyUsernameThrowsException() {
                Username user = new UsernameImpl();
                user.setDataFromString("");
        }

        /** Setting a two letters username throws exception. */
        @Test(expected = IllegalArgumentException.class)
        public final void settingATwoLettersUsernameThrowsException() {
                Username user = new UsernameImpl();
                user.setData("ab");
        }

        /** setFromString a two letters username throws exception. */
        @Test(expected = IllegalArgumentException.class)
        public final void setFromStringATwoLettersUsernameThrowsException() {
                Username user = new UsernameImpl();
                user.setData("ab");
        }

        /** Setting and getting a valid username. */
        @Test
        public final void settingAngGettingAValidUsername() {
                Username user = new UsernameImpl();
                user.setData("abcd");
                assertEquals("abcd", user.getData());
        }

        /** Setting and formatting a valid username. */
        @Test
        public final void settingAndFromattingAValidUsername() {
                Username user = new UsernameImpl();
                user.setDataFromString("abcd");
                assertEquals("abcd", user.formatData(Locale.getDefault()));
        }

}
