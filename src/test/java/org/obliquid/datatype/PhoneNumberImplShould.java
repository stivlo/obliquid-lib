package org.obliquid.datatype;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.Test;
import org.obliquid.datatype.impl.PhoneNumberImpl;

/**
 * Class under test: PhoneNumber.
 * 
 * @author stivlo
 * 
 */
public class PhoneNumberImplShould {

        /**
         * A phone number containing letters isn't valid.
         */
        @Test
        public final void aPhoneNumberWithLettersIsntValid() {
                PhoneNumber phone = new PhoneNumberImpl();
                assertFalse(phone.isValid("3234a"));
                assertFalse(phone.isTheStringValid("3234a"));
        }

        /**
         * A phone number of three digits isn't valid.
         */
        @Test
        public final void aPhoneNumberWithThreeDigitsIsntValid() {
                PhoneNumber phone = new PhoneNumberImpl();
                assertFalse(phone.isValid("322"));
                assertFalse(phone.isTheStringValid("322"));
        }

        /**
         * A phone number containing numbers, dots, hyphens and parenthesis with
         * 6 digits is valid.
         */
        @Test
        public final void aPhoneNumberWithDotsHyphensAndParensIsValid() {
                PhoneNumber phone = new PhoneNumberImpl();
                assertTrue(phone.isValid("83-32.(0)1"));
                assertTrue(phone.isTheStringValid("83-32.(0)1"));
        }

        /**
         * Getting without setting throws exception.
         */
        @Test(expected = IllegalStateException.class)
        public final void gettingWithoutSettingThrowsException() {
                PhoneNumber phone = new PhoneNumberImpl();
                phone.getData();
        }

        /**
         * Formatting without setting throws exception.
         */
        @Test(expected = IllegalStateException.class)
        public final void formattingWithoutSettingThrowsExeption() {
                PhoneNumber phone = new PhoneNumberImpl();
                phone.formatData(Locale.getDefault());
        }

        /**
         * Setting should normalise.
         */
        @Test
        public final void settingShouldNormalise() {
                PhoneNumber phone = new PhoneNumberImpl();
                phone.setData("3249.3-21(0) 3");
                assertEquals("324932103", phone.getData());
        }

}
