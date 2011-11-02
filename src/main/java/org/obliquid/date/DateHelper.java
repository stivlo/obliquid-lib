package org.obliquid.date;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.MutableDateTime;
import org.joda.time.ReadableDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Helper class for Dates, Java Dates, Joda Dates and ISO Dates in the String
 * form yyyy-MM-dd.
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
        public static final int DECEMBER = 12;

        /** Length of an ISO Date in characters. */
        public static final int ISO_DATE_LENGTH = 10;

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
                return DateBuilder.buildIsoFormatForJodaDate().print(aDate);
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
         * Return the number of days between two ReadableDates, considering only
         * the date part and not the time part.
         * 
         * @param fromWhen
         *                start date
         * @param untilWhen
         *                end date
         * @return the number of days
         */
        public static int computeDaysBetween(final ReadableDateTime fromWhen,
                        final ReadableDateTime untilWhen) {
                LocalDate fromWhenLd = DateBuilder.buildLocalDateFromReadableDateTime(fromWhen);
                LocalDate untilWhenLd = DateBuilder.buildLocalDateFromReadableDateTime(untilWhen);
                return Days.daysBetween(fromWhenLd, untilWhenLd).getDays();
        }

        /**
         * Return the number of days between two ISO Dates.
         * 
         * @param isoFromWhen
         *                start date as ISO date yyyy-MM-dd
         * @param isoUntilWhen
         *                end date as ISO date yyyy-MM-dd
         * @return the number of days
         */
        public static int computeDaysBetween(final String isoFromWhen, final String isoUntilWhen) {
                ReadableDateTime jodaFromWhen = DateBuilder.buildReadableDateTimeFromIsoDate(isoFromWhen);
                ReadableDateTime jodaUntilWhen = DateBuilder.buildReadableDateTimeFromIsoDate(isoUntilWhen);
                return Days.daysBetween(jodaFromWhen, jodaUntilWhen).getDays();
        }

        /**
         * Return the number of days between two Java Dates, considering only
         * the date part and not the time part.
         * 
         * @param fromWhen
         *                as JavaDate
         * @param untilWhen
         *                as JavaDate
         * @return the number of days
         */
        public static int computeDaysBetween(Date fromWhen, Date untilWhen) {
                LocalDate jodaFromWhen = DateBuilder.buildLocalDateFromJavaDate(fromWhen);
                LocalDate jodaUntilWhen = DateBuilder.buildLocalDateFromJavaDate(untilWhen);
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
                MutableDateTime nextDay = DateBuilder.buildReadableDateTimeFromIsoDate(isoDate)
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
                MutableDateTime nextDay = DateBuilder.buildReadableDateTimeFromIsoDate(isoDate)
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

        /**
         * Return a copy of startDate plus the specified number of days.
         * 
         * @param startDate
         *                the starting date
         * @param days
         *                the amount of days to add, may be negative
         * @return a new Date obtained by startDate plus the requested days
         */
        public static Date plusDays(final Date startDate, final int days) {
                LocalDate destDate = DateBuilder.buildLocalDateFromJavaDate(startDate).plusDays(days);
                return DateBuilder.buildJavaDateFromLocalDate(destDate);
        }

        /**
         * Return a copy of startDate minus the specified number of days.
         * 
         * @param startDate
         *                the starting date
         * @param days
         *                the amount of days to subtract, may be negative
         * @return a new Date obtained by startDate minus the requested days
         */
        public static Date minusDays(final Date startDate, final int days) {
                LocalDate destDate = DateBuilder.buildLocalDateFromJavaDate(startDate).minusDays(days);
                return DateBuilder.buildJavaDateFromLocalDate(destDate);
        }

        /**
         * Return a copy of the max of the two supplied dates.
         * 
         * @param javaDate1
         *                the first date
         * @param javaDate2
         *                the second date
         * @return the max Date between the two
         */
        public static Date max(Date javaDate1, Date javaDate2) {
                if (javaDate1.after(javaDate2)) {
                        return new Date(javaDate1.getTime());
                }
                return new Date(javaDate2.getTime());
        }

}
