package org.obliquid.datatype;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.Test;
import org.obliquid.datatype.impl.UrlTypeImpl;

/**
 * Class under test: UrlType.
 * 
 * @author stivlo
 * 
 */
public class UrlTypeImplShould {

        /**
         * An URL without protocol isn't valid.
         */
        @Test
        public final void anUrlWithoutProtocolIsntValid() {
                String exampleUrl = "www.example.com";
                UrlType url = new UrlTypeImpl();
                assertFalse(url.isValid(exampleUrl));
                assertFalse(url.isTheStringValid(exampleUrl));
        }

        /**
         * An URL with protocol but not a valid host part isn't valid.
         */
        @Test
        public final void anUrlWithProtocolButWithoutAValidHostPartIsntValid() {
                String exampleUrl = "http://www";
                UrlType url = new UrlTypeImpl();
                assertFalse(url.isValid(exampleUrl));
                assertFalse(url.isTheStringValid(exampleUrl));
        }

        /**
         * An URL with protocol and valid host part is valid.
         */
        @Test
        public final void anUrlWithProtocolAndValidHostPartIsvalid() {
                String exampleUrl = "http://www.example.com";
                UrlType url = new UrlTypeImpl();
                assertTrue(url.isValid(exampleUrl));
                assertTrue(url.isTheStringValid(exampleUrl));
        }

        /**
         * An empty URL isn't valid.
         */
        @Test
        public final void anEmptyUrlIsntValid() {
                UrlType url = new UrlTypeImpl();
                assertFalse(url.isValid(""));
                assertFalse(url.isTheStringValid(""));
        }

        /**
         * A null URL isn't valid.
         */
        @Test
        public final void aNullUrlIsntValid() {
                UrlType url = new UrlTypeImpl();
                assertFalse(url.isValid(null));
                assertFalse(url.isTheStringValid(null));
        }

        /**
         * Getting without setting throws exception.
         */
        @Test(expected = IllegalStateException.class)
        public final void gettingWithoutSettingThrowsException() {
                UrlType url = new UrlTypeImpl();
                url.getData();
        }

        /**
         * formatData without setting throws exception.
         */
        @Test(expected = IllegalStateException.class)
        public final void formatDataWithoutSettingThrowsException() {
                UrlType url = new UrlTypeImpl();
                url.formatData(Locale.getDefault());
        }

        /**
         * Setting and retrieving a valid URL.
         */
        @Test
        public final void settingAndRetrievingAValidUrl() {
                String urlString = "http://www.example.org/adak";
                UrlType url = new UrlTypeImpl();
                url.setData(urlString);
                assertEquals(urlString, url.getData());
                assertEquals(urlString, url.formatData(Locale.getDefault()));
        }

        /**
         * Setting an empty URL throws exception.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void settingAnEmptyUrlThrowsException() {
                UrlType url = new UrlTypeImpl();
                url.setData("");
        }

        /**
         * setDataFromString with an empty URL throws exception.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void setDataFromStringWithAnEmptyUrlThrowsException() {
                UrlType url = new UrlTypeImpl();
                url.setDataFromString("");
        }

        /**
         * Setting a null URL throws exception.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void settingANullUrlThrowsException() {
                UrlType url = new UrlTypeImpl();
                url.setData(null);
        }

        /**
         * setDataFromString with a null URL throws exception.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void setDataFromStringWithANullUrlThrowsException() {
                UrlType url = new UrlTypeImpl();
                url.setDataFromString(null);
        }

}
