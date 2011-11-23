package org.obliquid.date;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.joda.time.LocalDate;
import org.junit.Test;

/**
 * Class under test: DateBuilder.
 * 
 * @author stivlo
 * 
 */
public class DateBuilderShould {

        /**
         * Format ISO Date from Java Date.
         */
        @Test
        public final void formatIsoDateFromJavaDate() {
                String isoDate = "2009-09-24";
                assertEquals(isoDate, DateBuilder.buildIsoDateFromJavaDate(DateBuilder
                                .buildJavaDateFromIsoDate(isoDate)));
                isoDate = "2011-02-09";
                assertEquals(isoDate, DateBuilder.buildIsoDateFromJavaDate(DateBuilder
                                .buildJavaDateFromIsoDate(isoDate)));
        }

        /**
         * Get the end of the current year.
         */
        @Test
        public final void getEndOfCurrentYear() {
                String endOfYear = Calendar.getInstance().get(Calendar.YEAR) + "-12-31";
                assertEquals(DateBuilder.buildReadableDateTimeFromIsoDate(endOfYear),
                                DateBuilder.buildReadableDateTimeOfTheEndOfCurrentYear());
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
                assertEquals(DateBuilder.buildReadableDateTimeFromIsoDate(year + "-06-" + lastDay),
                                DateBuilder.buildReadableDateTimeOfTheEndOfMonthInTheCurrentYear(month));
        }

        /**
         * Get the beginning of month in the current year.
         */
        @Test
        public final void getBeginningOfMonthInTheCurrentYear() {
                final int month = 4;
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                assertEquals(DateBuilder.buildReadableDateTimeFromIsoDate(year + "-04-01"),
                                DateBuilder.buildReadableDateTimeOfTheBeginningOfMonthInTheCurrentYear(month));
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
                assertEquals(DateBuilder.buildReadableDateTimeFromIsoDate(isoDate),
                                DateBuilder.buildReadableDateTimeFromJavaDate(javaDate));
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
                LocalDate date = DateBuilder.buildLocalDateFromIsoDate(isoDate);
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
                Date date = DateBuilder.buildJavaDateFromIsoDate(isoDate);
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
                DateBuilder.buildJavaDateFromIsoDate("wrongDate");
        }

        /**
         * Throw an exception when building a local date from an invalid ISO
         * date.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void throwAnExceptionWhenBuildingLocalDateFromInvalidIsoDate() {
                DateBuilder.buildLocalDateFromIsoDate("wrongDate");
        }

        /**
         * Throw an exception when building a ReadableDateTime from an invalid
         * ISO date.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void throwAnExceptionWhenBuildingReadableDateTimeFromInvalidIsoDate() {
                DateBuilder.buildReadableDateTimeFromIsoDate("wrongDate");
        }

        /**
         * Build a LocalDateTime from a Java Date.
         */
        @Test
        public final void buildLocalDateFromJavaDate() {
                final int year = 2007;
                final int month = 11;
                final int day = 29;
                Date javaDate = DateBuilder.buildJavaDateFromIsoDate("2007-11-29");
                LocalDate localDate = DateBuilder.buildLocalDateFromJavaDate(javaDate);
                assertEquals(year, localDate.getYear());
                assertEquals(month, localDate.getMonthOfYear());
                assertEquals(day, localDate.getDayOfMonth());
        }

}
