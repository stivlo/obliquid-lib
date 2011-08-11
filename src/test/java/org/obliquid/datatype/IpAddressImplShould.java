package org.obliquid.datatype;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.Test;
import org.obliquid.datatype.impl.IpAddressImpl;

/**
 * Unit test for class IpAddress.
 * 
 * @author stivlo
 */
public class IpAddressImplShould {

        /**
         * A wrong IP is not valid.
         */
        @Test
        public final void aWrongIpIsNotValid() {
                IpAddress ipAddress = new IpAddressImpl();
                String wrongIp = "321.12.1.2";
                assertFalse(ipAddress.isValid(wrongIp));
        }

        /**
         * A correct IP is valid.
         */
        @Test
        public final void aCorrectIpIsValid() {
                IpAddress ipAddress = new IpAddressImpl();
                String correctIp = "82.89.90.1";
                assertTrue(ipAddress.isValid(correctIp));
        }

        /**
         * isTheStringValid is actually the same as isValid().
         */
        @Test
        public final void isTheStringValid() {
                IpAddress ipAddress = new IpAddressImpl();
                String correctIp = "81.83.230.1";
                assertTrue(ipAddress.isTheStringValid(correctIp));
        }

        /**
         * A null String isn't valid.
         */
        @Test
        public final void aNullStringIsNotValid() {
                IpAddress ipAddress = new IpAddressImpl();
                assertFalse(ipAddress.isValid(null));
        }

        /**
         * Getting without setting throws Exception.
         */
        @Test(expected = IllegalStateException.class)
        public final void gettingWithoutSettingThrowsException() {
                IpAddress ipAddress = new IpAddressImpl();
                ipAddress.getData();
        }

        /**
         * Getting without setting throws Exception.
         */
        @Test(expected = IllegalStateException.class)
        public final void formatDataWithoutSettingThrowsException() {
                IpAddress ipAddress = new IpAddressImpl();
                ipAddress.formatData(Locale.getDefault());
        }

        /**
         * Setting null throws Exception.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void settingNullThrowsException() {
                IpAddress ipAddress = new IpAddressImpl();
                ipAddress.setData(null);
        }

        /**
         * Setting empty String throws Exception.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void settingEmptyStringThrowsException() {
                IpAddress ipAddress = new IpAddressImpl();
                ipAddress.setData("");
        }

        /**
         * Setting and Getting a valid Ip should work. Formatting should do
         * nothing.
         */
        @Test()
        public final void settingAndGetAValidIp() {
                String validIp = "127.0.0.1";
                IpAddress ipAddress = new IpAddressImpl();
                ipAddress.setData(validIp);
                assertEquals(validIp, ipAddress.getData());
                assertEquals(validIp, ipAddress.formatData(Locale.getDefault()));
        }

        /**
         * setDataFromString() is actually the same as setData().
         */
        @Test()
        public final void settingDataFromString() {
                String validIp = "192.168.254.101";
                IpAddress ipAddress = new IpAddressImpl();
                ipAddress.setDataFromString(validIp);
                assertEquals(validIp, ipAddress.getData());
                assertEquals(validIp, ipAddress.formatData(Locale.getDefault()));
        }

}
