package org.obliquid.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.MutableDateTime;
import org.joda.time.ReadableDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Build various type of Dates, Java Dates, Joda Dates and ISO Dates in the
 * String form yyyy-MM-dd.
 * 
 * @author stivlo
 */
public class DateBuilder {

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
         * Return the date at the end of the current year. i.e. 31/Dec/YYYY
         * 
         * @return a date at the end of the current year
         */
        public static ReadableDateTime buildReadableDateTimeOfTheEndOfCurrentYear() {
                final int lastDayOfDecember = 31;
                ReadableDateTime endOfYear = new DateMidnight(DateHelper.computeCurrentYear(),
                                DateHelper.DECEMBER, lastDayOfDecember);
                return endOfYear;
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
         * Build a Java Date from a LocalDate.
         * 
         * @param localDate
         *                the local date to use as source
         * @return a Java Date
         */
        public static Date buildJavaDateFromLocalDate(final LocalDate localDate) {
                Calendar calendar = Calendar.getInstance();
                calendar.clear();
                //month is 0 based in Java Date
                calendar.set(localDate.getYear(), localDate.getMonthOfYear() - 1, localDate.getDayOfMonth());
                return calendar.getTime();
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
         * Build a LocalDate from a ReadableDateTime.
         * 
         * @param dtDate
         *                the ReadableDateTime to use as source
         * @return a LocalDate
         */
        public static LocalDate buildLocalDateFromReadableDateTime(final ReadableDateTime dtDate) {
                return new LocalDate(dtDate.getYear(), dtDate.getMonthOfYear(), dtDate.getDayOfMonth());
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
                ReadableDateTime firstOfMonth = new DateMidnight(DateHelper.computeCurrentYear(), month, 1);
                return firstOfMonth;
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
         * Build a MutableDateTime from javaDate.
         * 
         * @param javaDate
         *                a java.util.Date Object
         * @return a MutableDateTime
         */
        public static MutableDateTime buildMutableDateTimeFromJavaDate(final Date javaDate) {
                return new DateTime(javaDate).toMutableDateTime();
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
                DateMidnight firstOfMonth = new DateMidnight(DateHelper.computeCurrentYear(), month, 1);
                return firstOfMonth.dayOfMonth().withMaximumValue();
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
         * Simple ISO Format for Joda Dates. I need to build a new one each time
         * because is not thread safe.
         * 
         * @return a DateTimeFormatter instance
         */
        public static DateTimeFormatter buildIsoFormatForJodaDate() {
                return DateTimeFormat.forPattern("yyyy-MM-dd");
        }

}
