package org.obliquid.helpers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Test;
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
         * Format ISO Date from Java Date.
         */
        @Test
        public final void formatIsoDateFromJavaDate() {
                String isoDate = "2009-09-24";
                assertEquals(isoDate, DateHelper.buildIsoDateFromJavaDate(DateHelper
                                .buildJavaDateFromIsoDate(isoDate)));
                isoDate = "2011-02-09";
                assertEquals(isoDate, DateHelper.buildIsoDateFromJavaDate(DateHelper
                                .buildJavaDateFromIsoDate(isoDate)));
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
                                DateHelper.buildReadableDateTimeFromIsoDate("2011-12-01"),
                                DateHelper.buildReadableDateTimeFromIsoDate("2011-12-03")));
        }

        /**
         * Format an italian Short date.
         */
        @Test
        public final void formatItShortDate() {
                assertEquals("03/06/11", DateHelper.formatShortItDate(DateHelper
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
         * Get the end of the current year.
         */
        @Test
        public final void getEndOfCurrentYear() {
                String endOfYear = Calendar.getInstance().get(Calendar.YEAR) + "-12-31";
                assertEquals(DateHelper.buildReadableDateTimeFromIsoDate(endOfYear),
                                DateHelper.buildReadableDateTimeOfTheEndOfCurrentYear());
        }

        /**
         * Get end of month in the current year.
         */
        @Test
        public final void getEndOfMonthInTheCurrentYear() {
                final int month = 6;
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                calendar.set(year, Calendar.JUNE, 1);
                int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                assertEquals(DateHelper.buildReadableDateTimeFromIsoDate(year + "-06-" + lastDay),
                                DateHelper.buildReadableDateTimeOfTheEndOfMonthInTheCurrentYear(month));
        }

        /**
         * Get the beginning of month in the current year.
         */
        @Test
        public final void getBeginningOfMonthInTheCurrentYear() {
                final int month = 4;
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                assertEquals(DateHelper.buildReadableDateTimeFromIsoDate(year + "-04-01"),
                                DateHelper.buildReadableDateTimeOfTheBeginningOfMonthInTheCurrentYear(month));
        }

        /**
         * Build a ReadableDateTime from ISO Date.
         * 
         * @throws ParseException
         *                 in case of parsing problems
         */
        @Test
        public final void buildReadableDateTimeFromIsoDate() throws ParseException {
                String isoDate = "2009-08-22";
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date javaDate = formatter.parse(isoDate);
                assertEquals(DateHelper.buildReadableDateTimeFromIsoDate(isoDate),
                                DateHelper.buildReadableDateTimeFromJavaDate(javaDate));
        }

        /**
         * Build a LocalDate from ISO date.
         */
        @Test
        public final void buildLocalDateFromIsoDate() {
                final int year = 2011;
                final int month = 11;
                final int day = 23;
                String isoDate = "2011-11-23";
                LocalDate date = DateHelper.buildLocalDateFromIsoDate(isoDate);
                assertEquals(year, date.getYear());
                assertEquals(month, date.getMonthOfYear());
                assertEquals(day, date.getDayOfMonth());
        }

        /**
         * Build a Java Date from ISO Date.
         * 
         * @throws ParseException
         *                 in case of parsing problems
         */
        @Test
        public final void buildJavaDateFromIsoDate() throws ParseException {
                final int year = 2010;
                final int month = 9;
                final int day = 18;
                String isoDate = "2010-09-18";
                Date date = DateHelper.buildJavaDateFromIsoDate(isoDate);
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date);
                assertEquals(year, calendar.get(Calendar.YEAR));
                assertEquals(month, calendar.get(Calendar.MONTH) + 1); //months start from 0, oh my.
                assertEquals(day, calendar.get(Calendar.DAY_OF_MONTH));
        }

        /**
         * Throw an exception when building a Java Date from an invalid ISO
         * date.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void throwAnExceptionWhenBuildingJavaDateFromInvalidIsoDate() {
                DateHelper.buildJavaDateFromIsoDate("wrongDate");
        }

        /**
         * Throw an exception when building a local date from an invalid ISO
         * date.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void throwAnExceptionWhenBuildingLocalDateFromInvalidIsoDate() {
                DateHelper.buildLocalDateFromIsoDate("wrongDate");
        }

        /**
         * Throw an exception when building a ReadableDateTime from an invalid
         * ISO date.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void throwAnExceptionWhenBuildingReadableDateTimeFromInvalidIsoDate() {
                DateHelper.buildReadableDateTimeFromIsoDate("wrongDate");
        }

        /**
         * Build a LocalDateTime from a Java Date.
         */
        @Test
        public final void buildLocalDateFromJavaDate() {
                final int year = 2007;
                final int month = 11;
                final int day = 29;
                Date javaDate = DateHelper.buildJavaDateFromIsoDate("2007-11-29");
                LocalDate localDate = DateHelper.buildLocalDateFromJavaDate(javaDate);
                assertEquals(year, localDate.getYear());
                assertEquals(month, localDate.getMonthOfYear());
                assertEquals(day, localDate.getDayOfMonth());
        }

        /**
         * Extract the year from a Java Date.
         */
        @Test
        public final void extractYear() {
                final int year = 2009;
                Date javaDate = DateHelper.buildJavaDateFromIsoDate("2009-12-09");
                assertEquals(year, DateHelper.extractYear(javaDate));
        }

        /**
         * Extract the month from a Java Date.
         */
        @Test
        public final void extractMonth() {
                final int month = 12;
                Date javaDate = DateHelper.buildJavaDateFromIsoDate("2009-12-09");
                assertEquals(month, DateHelper.extractMonth(javaDate));
        }

        /**
         * Extract the day from a Java Date.
         */
        @Test
        public final void extractDay() {
                final int day = 9;
                Date javaDate = DateHelper.buildJavaDateFromIsoDate("2009-12-09");
                assertEquals(day, DateHelper.extractDay(javaDate));
        }

        /**
         * Compare only the date part should return a negative int when the
         * first date is older. Test 1.
         */
        @Test
        public final void compareOnlyTheDatePartShouldReturnANegativeIntWhenFirstDateIsOlder1() {
                Date firstDate = DateHelper.buildJavaDateFromIsoDate("2010-02-01");
                Date secondDate = DateHelper.buildJavaDateFromIsoDate("2011-02-02");
                assertTrue(DateHelper.compareOnlyTheDatePart(firstDate, secondDate) < 0);
        }

        /**
         * Compare only the date part should return a negative int when the
         * first date is older. Test 2.
         */
        @Test
        public final void compareOnlyTheDatePartShouldReturnANegativeIntWhenFirstDateIsOlder2() {
                Date firstDate = DateHelper.buildJavaDateFromIsoDate("2011-01-01");
                Date secondDate = DateHelper.buildJavaDateFromIsoDate("2011-02-02");
                assertTrue(DateHelper.compareOnlyTheDatePart(firstDate, secondDate) < 0);
        }

        /**
         * Compare only the date part should return a negative int when the
         * first date is older. Test 3.
         */
        @Test
        public final void compareOnlyTheDatePartShouldReturnANegativeIntWhenFirstDateIsOlder3() {
                Date firstDate = DateHelper.buildJavaDateFromIsoDate("2011-02-01");
                Date secondDate = DateHelper.buildJavaDateFromIsoDate("2011-02-02");
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
                Date firstDate = DateHelper.buildJavaDateFromIsoDate("2012-01-01");
                Date secondDate = DateHelper.buildJavaDateFromIsoDate("2011-02-02");
                assertTrue(DateHelper.compareOnlyTheDatePart(firstDate, secondDate) > 0);
        }

        /**
         * Compare only the date part should return a positive int when the
         * first date is newer. Test 2.
         */
        @Test
        public final void compareOnlyTheDatePartShouldReturnAPositiveIntWhenFirstDateIsNewer2() {
                Date firstDate = DateHelper.buildJavaDateFromIsoDate("2011-03-01");
                Date secondDate = DateHelper.buildJavaDateFromIsoDate("2011-02-02");
                assertTrue(DateHelper.compareOnlyTheDatePart(firstDate, secondDate) > 0);
        }

        /**
         * Compare only the date part should return a positive int when the
         * first date is newer. Test 3.
         */
        @Test
        public final void compareOnlyTheDatePartShouldReturnAPositiveIntWhenFirstDateIsNewer3() {
                Date firstDate = DateHelper.buildJavaDateFromIsoDate("2011-02-03");
                Date secondDate = DateHelper.buildJavaDateFromIsoDate("2011-02-02");
                assertTrue(DateHelper.compareOnlyTheDatePart(firstDate, secondDate) > 0);
        }

}
