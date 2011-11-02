package org.obliquid.date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Calendar;
import java.util.Date;
import org.joda.time.DateTime;
import org.junit.Test;
import org.obliquid.date.DateHelper;
import org.obliquid.util.StopWatch;

/**
 * Class under test: DateHelper.
 * 
 * @author stivlo
 * 
 */
public class DateHelperShould {

        /**
         * Get the current time stamp in seconds.
         */
        @Test
        public final void getCurrentTimeStampInSeconds() {
                assertTrue(DateHelper.computeCurrentTimeStampInSeconds() > 0);
        }

        /**
         * Get the current time stamp in milliseconds.
         */
        @Test
        public final void getCurrentTimeStampinMs() {
                assertTrue(DateHelper.computeCurrentTimeStampInMs() > 0);
        }

        /**
         * Get the current Joda DateTime.
         */
        @Test
        public final void getCurrentDate() {
                assertEquals(new DateTime().getDayOfYear(), DateHelper.computeCurrentDateTime()
                                .getDayOfYear());
        }

        /**
         * Format an ISO Date from Joda DateTime.
         */
        @Test
        public final void formatIsoDateFromDateTime() {
                final int year = 2011, month = 7, day = 26, hour = 22, minute = 56;
                assertEquals("2011-07-26",
                                DateHelper.formatIsoDate(new DateTime(year, month, day, hour, minute, 0, 0)));
        }

        /**
         * Get the current Date as ISO.
         */
        @Test
        public final void getCurrentDateAsIso() {
                final int expectedLength = 10;
                assertEquals(expectedLength, DateHelper.formatCurrentDateAsIso().length());
        }

        /**
         * Compute ISO Date: when how many days ago is zero is today.
         */
        @Test
        public final void computeIsoDateWhenHowManyDaysAgoIsZeroIsToday() {
                assertEquals(DateHelper.formatCurrentDateAsIso(), DateHelper.computeIsoDateOfDaysAgo(0));
        }

        /**
         * Compute ISO Date: when how many days ago is one is before today.
         */
        @Test
        public final void computeIsoDateWhenhowManyDaysAgoIsOneIsBeforeToday() {
                assertTrue(DateHelper.formatCurrentDateAsIso().compareTo(
                                DateHelper.computeIsoDateOfDaysAgo(1)) > 0);
        }

        /**
         * Compute the next day in ISO format.
         */
        @Test
        public final void computeNextDay() {
                assertEquals("2012-01-01", DateHelper.computeNextDayInIsoFormat("2011-12-31"));
        }

        /**
         * Compute the previous day in ISO format.
         */
        @Test
        public final void computePreviousDay() {
                assertEquals("2011-12-31", DateHelper.computePreviousDayInIsoFormat("2012-01-01"));
        }

        /**
         * Days between the same date is zero.
         */
        @Test
        public final void daysBetweenTheSameDateIsZero() {
                assertEquals(0, DateHelper.computeDaysBetween(DateHelper.computeCurrentDateTime(),
                                DateHelper.computeCurrentDateTime()));
        }

        /**
         * Days between these two dates should be two.
         */
        @Test
        public final void daysBetweenTheseTwoDaysAreTwo() {
                assertEquals(2, DateHelper.computeDaysBetween(
                                DateBuilder.buildReadableDateTimeFromIsoDate("2011-12-01"),
                                DateBuilder.buildReadableDateTimeFromIsoDate("2011-12-03")));
        }

        /**
         * Format an italian Short date.
         */
        @Test
        public final void formatItShortDate() {
                assertEquals("03/06/11", DateHelper.formatShortItDate(DateBuilder
                                .buildReadableDateTimeFromIsoDate("2011-06-03")));
        }

        /**
         * Get the current year.
         */
        @Test
        public final void getCurrentYear() {
                assertEquals(Calendar.getInstance().get(Calendar.YEAR), DateHelper.computeCurrentYear());
        }

        /**
         * Extract the year from a Java Date.
         */
        @Test
        public final void extractYear() {
                final int year = 2009;
                Date javaDate = DateBuilder.buildJavaDateFromIsoDate("2009-12-09");
                assertEquals(year, DateHelper.extractYear(javaDate));
        }

        /**
         * Extract the month from a Java Date.
         */
        @Test
        public final void extractMonth() {
                final int month = 12;
                Date javaDate = DateBuilder.buildJavaDateFromIsoDate("2009-12-09");
                assertEquals(month, DateHelper.extractMonth(javaDate));
        }

        /**
         * Extract the day from a Java Date.
         */
        @Test
        public final void extractDay() {
                final int day = 9;
                Date javaDate = DateBuilder.buildJavaDateFromIsoDate("2009-12-09");
                assertEquals(day, DateHelper.extractDay(javaDate));
        }

        /**
         * Compare only the date part should return a negative int when the
         * first date is older. Test 1.
         */
        @Test
        public final void compareOnlyTheDatePartShouldReturnANegativeIntWhenFirstDateIsOlder1() {
                Date firstDate = DateBuilder.buildJavaDateFromIsoDate("2010-02-01");
                Date secondDate = DateBuilder.buildJavaDateFromIsoDate("2011-02-02");
                assertTrue(DateHelper.compareOnlyTheDatePart(firstDate, secondDate) < 0);
        }

        /**
         * Compare only the date part should return a negative int when the
         * first date is older. Test 2.
         */
        @Test
        public final void compareOnlyTheDatePartShouldReturnANegativeIntWhenFirstDateIsOlder2() {
                Date firstDate = DateBuilder.buildJavaDateFromIsoDate("2011-01-01");
                Date secondDate = DateBuilder.buildJavaDateFromIsoDate("2011-02-02");
                assertTrue(DateHelper.compareOnlyTheDatePart(firstDate, secondDate) < 0);
        }

        /**
         * Compare only the date part should return a negative int when the
         * first date is older. Test 3.
         */
        @Test
        public final void compareOnlyTheDatePartShouldReturnANegativeIntWhenFirstDateIsOlder3() {
                Date firstDate = DateBuilder.buildJavaDateFromIsoDate("2011-02-01");
                Date secondDate = DateBuilder.buildJavaDateFromIsoDate("2011-02-02");
                assertTrue(DateHelper.compareOnlyTheDatePart(firstDate, secondDate) < 0);
        }

        /**
         * Unless this test is run a second before midnight, it shouldn't fail.
         */
        @Test
        public final void compareOnlyTheDatePartShouldReturnZeroWhenTheDatesAreTheSame() {
                Date firstDate = new Date();
                StopWatch.sleepSeconds(1);
                Date secondDate = new Date();
                assertTrue(DateHelper.compareOnlyTheDatePart(firstDate, secondDate) == 0);
        }

        /**
         * Compare only the date part should return a positive int when the
         * first date is newer. Test 1.
         */
        @Test
        public final void compareOnlyTheDatePartShouldReturnAPositiveIntWhenFirstDateIsNewer1() {
                Date firstDate = DateBuilder.buildJavaDateFromIsoDate("2012-01-01");
                Date secondDate = DateBuilder.buildJavaDateFromIsoDate("2011-02-02");
                assertTrue(DateHelper.compareOnlyTheDatePart(firstDate, secondDate) > 0);
        }

        /**
         * Compare only the date part should return a positive int when the
         * first date is newer. Test 2.
         */
        @Test
        public final void compareOnlyTheDatePartShouldReturnAPositiveIntWhenFirstDateIsNewer2() {
                Date firstDate = DateBuilder.buildJavaDateFromIsoDate("2011-03-01");
                Date secondDate = DateBuilder.buildJavaDateFromIsoDate("2011-02-02");
                assertTrue(DateHelper.compareOnlyTheDatePart(firstDate, secondDate) > 0);
        }

        /**
         * Compare only the date part should return a positive int when the
         * first date is newer. Test 3.
         */
        @Test
        public final void compareOnlyTheDatePartShouldReturnAPositiveIntWhenFirstDateIsNewer3() {
                Date firstDate = DateBuilder.buildJavaDateFromIsoDate("2011-02-03");
                Date secondDate = DateBuilder.buildJavaDateFromIsoDate("2011-02-02");
                assertTrue(DateHelper.compareOnlyTheDatePart(firstDate, secondDate) > 0);
        }

}
