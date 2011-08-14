package org.obliquid.datatype;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Locale;

import org.junit.Test;
import org.obliquid.datatype.impl.IsoDateImpl;

/**
 * Unit test the Class IsoDate.
 * 
 * @author stivlo
 * 
 */
public class IsoDateImplShould {

        /**
         * An Empty Date isn't valid.
         */
        @Test
        public final void anEmptyDateIsntValid() {
                IsoDate isoDate = new IsoDateImpl();
                assertFalse(isoDate.isTheStringValid(""));
        }

        /**
         * The Date "2011-12-05" is valid.
         */
        @Test
        public final void aValidIsoDateIsValid() {
                IsoDate isoDate = new IsoDateImpl();
                assertTrue(isoDate.isTheStringValid("2011-12-05"));
        }

        /**
         * The Date "201a-12-05" isn't valid.
         */
        @Test
        public final void aWrongIsoDateIsntValid() {
                IsoDate isoDate = new IsoDateImpl();
                assertFalse(isoDate.isTheStringValid("201a-12-05"));
        }

        /**
         * A Null Date isn't valid.
         */
        @Test
        public final void aNullDateIsntValid() {
                IsoDate isoDate = new IsoDateImpl();
                String date = null;
                assertFalse(isoDate.isTheStringValid(date));
        }

        /**
         * Format Data US Style.
         */
        @Test
        public final void formatDataUsTest() {
                IsoDate isoDate = new IsoDateImpl();
                isoDate.setDataFromString("2011-02-22");
                assertEquals("February 22, 2011", isoDate.formatData(Locale.US));
                assertEquals("2011-02-22", isoDate.toString());
        }

        /**
         * Format Data UK Style.
         */
        @Test
        public final void formatDataUkTest() {
                IsoDate isoDate = new IsoDateImpl();
                isoDate.setDataFromString("2011-02-22");
                assertEquals("22 February 2011", isoDate.formatData(Locale.UK));
                assertEquals("2011-02-22", isoDate.toString());
        }

        /**
         * Format Data IT Style.
         */
        @Test
        public final void formatDataItTest() {
                IsoDate isoDate = new IsoDateImpl();
                isoDate.setDataFromString("2011-02-22");
                assertEquals("22 febbraio 2011", isoDate.formatData(Locale.ITALY));
                assertEquals("2011-02-22", isoDate.toString());
        }

        /**
         * Getting without setting throws Exception.
         */
        @Test(expected = IllegalStateException.class)
        public final void gettingWithoutSettingThrowsException() {
                IsoDate isoDate = new IsoDateImpl();
                isoDate.getData();
        }

        /**
         * Setting a null date causes Exception.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void setNullDateThrowsException() {
                IsoDate isoDate = new IsoDateImpl();
                isoDate.setData(null);
        }

        /**
         * Setting a null ISO date String causes Exception.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void setNullStringThrowsException() {
                IsoDate isoDate = new IsoDateImpl();
                isoDate.setDataFromString(null);
        }

        /**
         * Setting an ISO Date.
         */
        @Test
        public final void settingAnIsoDate() {
                IsoDate isoDate = new IsoDateImpl();
                isoDate.setDataFromString("2011-07-21");
                assertEquals("2011-07-21", isoDate.toString());
        }

        /**
         * Setting a wrong ISO Date.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void settingAWrongIsoDate() {
                IsoDate isoDate = new IsoDateImpl();
                isoDate.setDataFromString("201--07-21");
        }

        /**
         * null Date isn't valid.
         */
        @Test
        public final void nullDateIsntValid() {
                IsoDate isoDate = new IsoDateImpl();
                assertFalse(isoDate.isValid(null));
        }

        /**
         * Any other Date is valid.
         */
        @Test
        public final void anyOtherDateIsValid() {
                IsoDate isoDate = new IsoDateImpl();
                assertTrue(isoDate.isValid(new Date()));
        }

        /**
         * Setting and retrieving a Date.
         */
        @Test
        public final void settingAndRetrievingADate() {
                Date now = new Date();
                IsoDate isoDate = new IsoDateImpl();
                isoDate.setData(now);
                assertEquals(now, isoDate.getData());
        }

        //        @Test
        //        public void daysInTheYearTest() {
        //                IsoDate isoDate = new IsoDate();
        //                isoDate.setData("2011-02-22");
        //                assertEquals(365, isoDate.daysInTheYear());
        //                isoDate.setData("2008-02-22");
        //                assertEquals(366, isoDate.daysInTheYear());
        //        }

        //        @Test
        //        public void computeDaysUntil() {
        //                IsoDate isoDate = new IsoDate();
        //                isoDate.setData("2010-11-01");
        //                assertEquals(5, isoDate.daysUntil("2010-11-06"));
        //        }

}
