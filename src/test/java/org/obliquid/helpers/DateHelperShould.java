package org.obliquid.helpers;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Test;

public class DateHelperShould {

    @Test
    public void getCurrentTimeStampInSeconds() {
        assertTrue(DateHelper.computeCurrentTimeStampInSeconds() > 0);
    }

    @Test
    public void getCurrentTimeStampinMs() {
        assertTrue(DateHelper.computeCurrentTimeStampInMs() > 0);
    }

    @Test
    public void getCurrentDate() {
        DateHelper.computeCurrentDateTime();
    }

    @Test
    public void formatIsoDateFromDateTime() {
        assertEquals("2011-07-26", DateHelper.formatIsoDate(new DateTime(2011, 7, 26, 22, 56, 0, 0)));
    }

    @Test
    public void formatIsoDateFromJavaDate() {
        String isoDate = "2009-09-24";
        assertEquals(isoDate, DateHelper.formatIsoDate(DateHelper.buildJavaDateFromIsoDate(isoDate)));
        isoDate = "2011-02-09";
        assertEquals(isoDate, DateHelper.formatIsoDate(DateHelper.buildJavaDateFromIsoDate(isoDate)));
    }

    @Test
    public void getCurrentDateAsIso() {
        assertEquals(10, DateHelper.formatCurrentDateAsIso().length());
    }

    @Test
    public void computeIsoDateWhenhowManyDaysAgoIsZeroIsToday() {
        assertEquals(DateHelper.formatCurrentDateAsIso(), DateHelper.computeIsoDateOfDaysAgo(0));
    }

    @Test
    public void computeIsoDateWhenhowManyDaysAgoIsOneIsBeforeToday() {
        assertTrue(DateHelper.formatCurrentDateAsIso().compareTo(DateHelper.computeIsoDateOfDaysAgo(1)) > 0);
    }

    @Test
    public void computeNextDay() {
        assertEquals("2012-01-01", DateHelper.computeNextDayInIsoFormat("2011-12-31"));
    }

    @Test
    public void computePreviousDay() {
        assertEquals("2011-12-31", DateHelper.computePreviousDayInIsoFormat("2012-01-01"));
    }

    @Test
    public void daysBetweenTheSameDateIsZero() {
        assertEquals(
                0,
                DateHelper.computeDaysBetween(DateHelper.computeCurrentDateTime(),
                        DateHelper.computeCurrentDateTime()));
    }

    @Test
    public void daysBetweenTheseTwoDaysAreTwo() {
        assertEquals(2, DateHelper.computeDaysBetween(
                DateHelper.buildReadableDateTimeFromIsoDate("2011-12-01"),
                DateHelper.buildReadableDateTimeFromIsoDate("2011-12-03")));
    }

    @Test
    public void formatItShortDate() {
        assertEquals("03/06/11",
                DateHelper.formatShortItDate(DateHelper.buildReadableDateTimeFromIsoDate("2011-06-03")));
    }

    @Test
    public void getCurrentYear() {
        assertEquals(Calendar.getInstance().get(Calendar.YEAR), DateHelper.computeCurrentYear());
    }

    @Test
    public void getEndOfCurrentYear() {
        String endOfYear = Calendar.getInstance().get(Calendar.YEAR) + "-12-31";
        assertEquals(DateHelper.buildReadableDateTimeFromIsoDate(endOfYear),
                DateHelper.buildReadableDateTimeOfTheEndOfCurrentYear());
    }

    @Test
    public void getEndOfMonthInTheCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        calendar.set(year, Calendar.JUNE, 1);
        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        assertEquals(DateHelper.buildReadableDateTimeFromIsoDate(year + "-06-" + lastDay),
                DateHelper.buildReadableDateTimeOfTheEndOfMonthInTheCurrentYear(6));
    }

    @Test
    public void getBeginningOfMonthInTheCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        assertEquals(DateHelper.buildReadableDateTimeFromIsoDate(year + "-04-01"),
                DateHelper.buildReadableDateTimeOfTheBeginningOfMonthInTheCurrentYear(4));
    }

    @Test
    public void buildReadableDateTimeFromJavaDate() throws ParseException {
        String isoDate = "2009-08-22";
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date javaDate = formatter.parse(isoDate);
        assertEquals(DateHelper.buildReadableDateTimeFromIsoDate(isoDate),
                DateHelper.buildReadableDateTimeFromJavaDate(javaDate));
    }

    @Test
    public void buildLocalDateFromIsoDate() {
        String isoDate = "2011-11-23";
        LocalDate date = DateHelper.buildLocalDateFromIsoDate(isoDate);
        assertEquals(2011, date.getYear());
        assertEquals(11, date.getMonthOfYear());
        assertEquals(23, date.getDayOfMonth());
    }

    @Test
    public void buildJavaDateFromIsoDate() throws ParseException {
        String isoDate = "2010-09-18";
        Date date = DateHelper.buildJavaDateFromIsoDate(isoDate);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        assertEquals(2010, calendar.get(Calendar.YEAR));
        assertEquals(9, calendar.get(Calendar.MONTH) + 1); //months start from 0, oh my.
        assertEquals(18, calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwAnExceptionWhenBuildingJavaDateFromInvalidIsoDate() {
        DateHelper.buildJavaDateFromIsoDate("wrongDate");
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwAnExceptionWhenBuildingLocalDateFromInvalidIsoDate() {
        DateHelper.buildLocalDateFromIsoDate("wrongDate");
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwAnExceptionWhenBuildingReadableDateTimeFromInvalidIsoDate() {
        DateHelper.buildReadableDateTimeFromIsoDate("wrongDate");
    }

    @Test
    public void buildLocalDateFromJavaDate() {
        Date javaDate = DateHelper.buildJavaDateFromIsoDate("2007-11-29");
        LocalDate localDate = DateHelper.buildLocalDateFromJavaDate(javaDate);
        assertEquals(2007, localDate.getYear());
        assertEquals(11, localDate.getMonthOfYear());
        assertEquals(29, localDate.getDayOfMonth());
    }

    @Test
    public void extractYear() {
        Date javaDate = DateHelper.buildJavaDateFromIsoDate("2009-12-09");
        assertEquals(2009, DateHelper.extractYear(javaDate));
    }

    @Test
    public void extractMonth() {
        Date javaDate = DateHelper.buildJavaDateFromIsoDate("2009-12-09");
        assertEquals(12, DateHelper.extractMonth(javaDate));
    }

    @Test
    public void extractDay() {
        Date javaDate = DateHelper.buildJavaDateFromIsoDate("2009-12-09");
        assertEquals(9, DateHelper.extractDay(javaDate));
    }

    @Test
    public void compareOnlyTheDatePartShouldReturnANegativeIntWhenFirstDateIsOlder1() {
        Date firstDate = DateHelper.buildJavaDateFromIsoDate("2010-02-01");
        Date secondDate = DateHelper.buildJavaDateFromIsoDate("2011-02-02");
        assertTrue(DateHelper.compareOnlyTheDatePart(firstDate, secondDate) < 0);
    }

    @Test
    public void compareOnlyTheDatePartShouldReturnANegativeIntWhenFirstDateIsOlder2() {
        Date firstDate = DateHelper.buildJavaDateFromIsoDate("2011-01-01");
        Date secondDate = DateHelper.buildJavaDateFromIsoDate("2011-02-02");
        assertTrue(DateHelper.compareOnlyTheDatePart(firstDate, secondDate) < 0);
    }

    @Test
    public void compareOnlyTheDatePartShouldReturnANegativeIntWhenFirstDateIsOlder3() {
        Date firstDate = DateHelper.buildJavaDateFromIsoDate("2011-02-01");
        Date secondDate = DateHelper.buildJavaDateFromIsoDate("2011-02-02");
        assertTrue(DateHelper.compareOnlyTheDatePart(firstDate, secondDate) < 0);
    }

    /**
     * Unless this test is run a second before midnight, it shouldn't fail.
     */
    @Test
    public void compareOnlyTheDatePartShouldReturnZeroWhenTheDatesAreTheSame() {
        Date firstDate = new Date();
        StopWatch.sleepSeconds(1);
        Date secondDate = new Date();
        assertTrue(DateHelper.compareOnlyTheDatePart(firstDate, secondDate) == 0);
    }

    @Test
    public void compareOnlyTheDatePartShouldReturnAPositiveIntWhenFirstDateIsNewer1() {
        Date firstDate = DateHelper.buildJavaDateFromIsoDate("2012-01-01");
        Date secondDate = DateHelper.buildJavaDateFromIsoDate("2011-02-02");
        assertTrue(DateHelper.compareOnlyTheDatePart(firstDate, secondDate) > 0);
    }

    @Test
    public void compareOnlyTheDatePartShouldReturnAPositiveIntWhenFirstDateIsNewer2() {
        Date firstDate = DateHelper.buildJavaDateFromIsoDate("2011-03-01");
        Date secondDate = DateHelper.buildJavaDateFromIsoDate("2011-02-02");
        assertTrue(DateHelper.compareOnlyTheDatePart(firstDate, secondDate) > 0);
    }

    @Test
    public void compareOnlyTheDatePartShouldReturnAPositiveIntWhenFirstDateIsNewer3() {
        Date firstDate = DateHelper.buildJavaDateFromIsoDate("2011-02-03");
        Date secondDate = DateHelper.buildJavaDateFromIsoDate("2011-02-02");
        assertTrue(DateHelper.compareOnlyTheDatePart(firstDate, secondDate) > 0);
    }

}
