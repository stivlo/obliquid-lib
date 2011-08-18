package org.obliquid.helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.MutableDateTime;
import org.joda.time.ReadableDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Helper class for Dates, Java Dates, Joda Dates and ISO Dates in the String
 * form yyyy-MM-dd. If this class grows too big (say more than 800 lines I will
 * consider splitting it in a JavaDateHelper, JodaDateHelper and IsoDateHelper.
 * 
 * The methods implemented aren't mean to be comprehensive, it contains the
 * functions I need in my development, they give me a simple interface that it's
 * easier to memorise without going into the details of different
 * implementations of Java, Joda and ISO Dates.
 * 
 * Joda Dates are great, but in real life I've to deal with Java Dates and ISO
 * Dates too.
 * 
 * @author stivlo
 */
public final class DateHelper {

        /** How many milliseconds in a seconds. */
        private static final int MS_IN_ONE_SEC = 1000;

        /** The month of December. */
        private static final int DECEMBER = 12;

        /** Utility class. */
        private DateHelper() {
                //all methods are static
        }

        /**
         * Return the current time stamp in seconds since Unix Epoch
         * (01/01/1970). Timestamp is in UTC and so doesn't depend on the time
         * zone.
         * 
         * @return the current timestamp in seconds
         */
        public static long computeCurrentTimeStampInSeconds() {
                Calendar cal = new GregorianCalendar();
                long timeStampNow = cal.getTimeInMillis() / MS_IN_ONE_SEC;
                return timeStampNow;
        }

        /**
         * Return the current time stamp in MilliSeconds since Unix Epoch
         * (01/01/1970). Timestamp is in UTC and so doesn't depend on the time
         * zone.
         * 
         * @return current time stamp in milliseconds
         */
        public static long computeCurrentTimeStampInMs() {
                Calendar cal = new GregorianCalendar();
                long timeStampNow = cal.getTimeInMillis();
                return timeStampNow;
        }

        /**
         * Return the current Joda DateTime.
         * 
         * @return the Date of today
         */
        public static DateTime computeCurrentDateTime() {
                DateTime today = new DateTime();
                return today;
        }

        /**
         * Format a Joda ReadableDateTime in ISO short format as in yyyy-MM-dd.
         * 
         * @param aDate
         *                a Joda ReadableDateTime
         * @return the formatted date
         */
        public static String formatIsoDate(final ReadableDateTime aDate) {
                return buildIsoFormatForJodaDate().print(aDate);
        }

        /**
         * Simple ISO Format for Joda Dates. I need to build a new one each time
         * because is not thread safe.
         * 
         * @return a DateTimeFormatter instance
         */
        private static DateTimeFormatter buildIsoFormatForJodaDate() {
                return DateTimeFormat.forPattern("yyyy-MM-dd");
        }

        /**
         * Format a Java Date in ISO short format as in yyyy-MM-dd.
         * 
         * @param aDate
         *                a Java Date
         * @return the formatted date
         */
        public static String buildIsoDateFromJavaDate(final Date aDate) {
                Calendar cal = new GregorianCalendar();
                cal.setTime(aDate);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH) + 1;
                int day = cal.get(Calendar.DAY_OF_MONTH);
                return String.format("%04d-%02d-%02d", year, month, day);
        }

        /**
         * Return the current Date formatted in ISO short format as in
         * yyyy-MM-dd.
         * 
         * @return today's date formatted
         */
        public static String formatCurrentDateAsIso() {
                return formatIsoDate(computeCurrentDateTime());
        }

        /**
         * Format the provided date in Italian short format.
         * 
         * @param aDate
         *                date to be formatted
         * @return formatted date
         */
        public static String formatShortItDate(final ReadableDateTime aDate) {
                return DateTimeFormat.shortDate().withLocale(Locale.ITALIAN).print(aDate);
        }

        /**
         * Return the current Year on four digits, as 2011.
         * 
         * @return the current year
         */
        public static int computeCurrentYear() {
                ReadableDateTime today = new DateTime();
                return today.getYear();
        }

        /**
         * Return the date at the end of the current year. i.e. 31/Dec/YYYY
         * 
         * @return a date at the end of the current year
         */
        public static ReadableDateTime buildReadableDateTimeOfTheEndOfCurrentYear() {
                final int lastDayOfDecember = 31;
                ReadableDateTime endOfYear = new DateMidnight(computeCurrentYear(), DECEMBER,
                                lastDayOfDecember);
                return endOfYear;
        }

        /**
         * Return a Joda DateTime at the end of the month in the current year.
         * example 30/Jun/YYYY
         * 
         * @param month
         *                the month numeral (1=January)
         * @return a DateTime at the end of the month
         */
        public static ReadableDateTime buildReadableDateTimeOfTheEndOfMonthInTheCurrentYear(final int month) {
                DateMidnight firstOfMonth = new DateMidnight(computeCurrentYear(), month, 1);
                return firstOfMonth.dayOfMonth().withMaximumValue();
        }

        /**
         * Return a Joda DateTime at the first of the month in the current year.
         * example 01/June/YYYY
         * 
         * @param month
         *                the month numeral (1=January)
         * @return a DateTime at the first of the month
         */
        public static ReadableDateTime buildReadableDateTimeOfTheBeginningOfMonthInTheCurrentYear(
                        final int month) {
                ReadableDateTime firstOfMonth = new DateMidnight(computeCurrentYear(), month, 1);
                return firstOfMonth;
        }

        /**
         * Build a ReadableDateTime from an isoDate.
         * 
         * @param isoDate
         *                a String in the format yyyy-MM-dd
         * @return a ReadableDateTime
         */
        public static ReadableDateTime buildReadableDateTimeFromIsoDate(final String isoDate) {
                return buildIsoFormatForJodaDate().parseDateTime(isoDate);
        }

        /**
         * Build a LocalDate from an isoDate.
         * 
         * @param isoDate
         *                a string in the format yyyy-MM-dd
         * @return a LocalDate
         */
        public static LocalDate buildLocalDateFromIsoDate(final String isoDate) {
                ReadableDateTime dateTime = buildIsoFormatForJodaDate().parseDateTime(isoDate);
                LocalDate date = new LocalDate(dateTime.getYear(), dateTime.getMonthOfYear(),
                                dateTime.getDayOfMonth());
                return date;
        }

        /**
         * Build a LocalDate from a Java Date.
         * 
         * @param javaDate
         *                the Java Date to use as source
         * @return a LocalDate
         */
        public static LocalDate buildLocalDateFromJavaDate(final Date javaDate) {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(javaDate);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1; //month is 0 based in Java Date
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                return new LocalDate(year, month, day);
        }

        /**
         * Build a Java Date from a ISO Date String.
         * 
         * @param isoDate
         *                a String in the format yyyy-MM-dd
         * @return a Java Date
         */
        public static Date buildJavaDateFromIsoDate(final String isoDate) {
                Date date;
                try {
                        date = buildIsoFormatForJavaDate().parse(isoDate);
                } catch (ParseException ex) {
                        throw new IllegalArgumentException(ex);
                }
                return date;
        }

        /**
         * Simple ISO Format for Java Dates.
         * 
         * @return a new SimpleDateFormat (every time a new instance)
         */
        private static DateFormat buildIsoFormatForJavaDate() {
                return new SimpleDateFormat("yyyy-MM-dd");
        }

        /**
         * Build a ReadableDateTime from javaDate.
         * 
         * @param javaDate
         *                a java.util.Date Object
         * @return a ReadableDateTime
         */
        public static ReadableDateTime buildReadableDateTimeFromJavaDate(final Date javaDate) {
                return new DateTime(javaDate);
        }

        /**
         * Return the number of FULL days between two ReadableDates.
         * 
         * @param fromWhen
         *                start date
         * @param untilWhen
         *                end date
         * @return the number of full days
         */
        public static int computeDaysBetween(final ReadableDateTime fromWhen,
                        final ReadableDateTime untilWhen) {
                return Days.daysBetween(fromWhen, untilWhen).getDays();
        }

        /**
         * Return the number of FULL days between two ISO Dates.
         * 
         * @param isoFromWhen
         *                start date
         * @param isoUntilWhen
         *                end date
         * @return the number of full days
         */
        public static int computeDaysBetween(final String isoFromWhen, final String isoUntilWhen) {
                ReadableDateTime jodaFromWhen = DateHelper.buildReadableDateTimeFromIsoDate(isoFromWhen);
                ReadableDateTime jodaUntilWhen = DateHelper.buildReadableDateTimeFromIsoDate(isoUntilWhen);
                return Days.daysBetween(jodaFromWhen, jodaUntilWhen).getDays();
        }

        /**
         * Get the ISO date of howManyDays ago. With howManyDays=0 should return
         * todays date.
         * 
         * @param howManyDays
         *                how many days to go back in the past
         * @return ISO date of howManyDays ago
         */
        public static String computeIsoDateOfDaysAgo(final int howManyDays) {
                ReadableDateTime today = new DateTime();
                MutableDateTime until = today.toMutableDateTime();
                until.addDays(-howManyDays);
                return DateHelper.formatIsoDate(until);
        }

        /**
         * Compute how many days there are in the year contained in the isoDate.
         * 
         * @param isoDate
         *                the date from which I'll extract the year
         * @return the number of days, should be either 365 or 366
         */
        public static int computeDaysInTheYear(final String isoDate) {
                int year = DateHelper.extractYear(isoDate);
                return computeDaysInTheYear(year);
        }

        /**
         * Compute how many days there are in the year.
         * 
         * @param year
         *                the year to consider
         * @return the number of days, should be either 365 or 366
         */
        public static int computeDaysInTheYear(final int year) {
                String isoFromWhen = year + "-01-01";
                String isoUntilWhen = year + "-12-31";
                return DateHelper.computeDaysBetween(isoFromWhen, isoUntilWhen) + 1;
        }

        /**
         * Get the next day of the specified ISO date.
         * 
         * @param isoDate
         *                a String representing a date in ISO format, for
         *                example 2011-12-10
         * @return the day after the date
         */
        public static String computeNextDayInIsoFormat(final String isoDate) {
                MutableDateTime nextDay = DateHelper.buildReadableDateTimeFromIsoDate(isoDate)
                                .toMutableDateTime();
                nextDay.addDays(1);
                return DateHelper.formatIsoDate(nextDay);
        }

        /**
         * Get the previous day of the specified ISO date.
         * 
         * @param isoDate
         *                a String representing a date in ISO format, for
         *                example 2011-12-10
         * @return the day before the date
         */
        public static String computePreviousDayInIsoFormat(final String isoDate) {
                MutableDateTime nextDay = DateHelper.buildReadableDateTimeFromIsoDate(isoDate)
                                .toMutableDateTime();
                nextDay.addDays(-1);
                return DateHelper.formatIsoDate(nextDay);
        }

        /**
         * Extract the year part from a Java date, such as 2011.
         * 
         * @param javaDate
         *                the Date to consider
         * @return the year
         */
        public static int extractYear(final Date javaDate) {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(javaDate);
                return calendar.get(Calendar.YEAR);
        }

        /**
         * Extract the day part from a ISO date.
         * 
         * @param isoDate
         *                the ISO Date to consider
         * @return the day of the month
         */
        public static int extractDay(final String isoDate) {
                DateTimeFormatter parser = DateTimeFormat.forPattern("yyyy-MM-dd");
                DateTime date = parser.parseDateTime(isoDate);
                int year = date.getDayOfMonth();
                return year;
        }

        /**
         * Extract the month part from a ISO date.
         * 
         * @param isoDate
         *                the ISO Date to consider
         * @return the month of the year, such as 1 for January and 12 for
         *         December
         */
        public static int extractMonth(final String isoDate) {
                DateTimeFormatter parser = DateTimeFormat.forPattern("yyyy-MM-dd");
                DateTime date = parser.parseDateTime(isoDate);
                int year = date.getMonthOfYear();
                return year;
        }

        /**
         * Extract the year part from a ISO date, such as 2011.
         * 
         * @param isoDate
         *                the ISO Date to consider
         * @return the year
         */
        public static int extractYear(final String isoDate) {
                DateTimeFormatter parser = DateTimeFormat.forPattern("yyyy-MM-dd");
                DateTime date = parser.parseDateTime(isoDate);
                int year = date.getYear();
                return year;
        }

        /**
         * Extract the month part from a Java date.
         * 
         * @param javaDate
         *                the Date to consider
         * @return the month
         */
        public static int extractMonth(final Date javaDate) {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(javaDate);
                return calendar.get(Calendar.MONTH) + 1;
        }

        /**
         * Extract the day part from a Java Date.
         * 
         * @param javaDate
         *                the Date to consider
         * @return the day
         */
        public static int extractDay(final Date javaDate) {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(javaDate);
                return calendar.get(Calendar.DAY_OF_MONTH);
        }

        /**
         * Compare only the date part of two Java Dates. Implementation adapted
         * from Jorn answer on stackoverflow.com #1439779
         * 
         * @param firstDate
         *                the first date to compare
         * @param secondDate
         *                the second date to compare
         * @return a negative integer if firstDate precedes secondDate, zero if
         *         they are the same, a positive integer if firstDate follow
         *         secondDate.
         */
        public static int compareOnlyTheDatePart(final Date firstDate, final Date secondDate) {
                Calendar c1 = new GregorianCalendar();
                Calendar c2 = new GregorianCalendar();
                c1.setTime(firstDate);
                c2.setTime(secondDate);
                if (c1.get(Calendar.YEAR) != c2.get(Calendar.YEAR)) {
                        return c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
                }
                if (c1.get(Calendar.MONTH) != c2.get(Calendar.MONTH)) {
                        return c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
                }
                return c1.get(Calendar.DAY_OF_MONTH) - c2.get(Calendar.DAY_OF_MONTH);
        }

}
