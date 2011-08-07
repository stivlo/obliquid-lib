package org.obliquid.datatype;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.joda.time.ReadableDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.obliquid.helpers.DateHelper;

/**
 * Hold and validate a date in ISO format yyyy-MM-dd
 * 
 * @author stivlo
 * 
 */
public class IsoDate extends DataType {

    private static final long serialVersionUID = 1L;

    /**
     * Check a date in ISO 8601 format, formatted as yyyy-MM-dd
     * 
     */
    @Override
    public boolean isValid(String isoDate) {
        if (isoDate == null || isoDate.length() != 10) {
            return false;
        }
        String datePattern = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
        try {
            formatter.parse(isoDate);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Format the date as a long date
     */
    @Override
    public String getFormattedString(Locale locale) {
        DateTimeFormatter formatter = DateTimeFormat.longDate().withLocale(locale);
        return formatter.print(getDateTime());
    }

    public ReadableDateTime getDateTime() {
        //DateTimeFormatter parser = DateTimeFormat.forPattern("yyyy-MM-dd");
        //DateTime date = parser.parseDateTime(getRawString());        
        return DateHelper.buildReadableDateTimeFromIsoDate(getRawString());
    }

    /**
     * Compute the number of days from the date inside the object to the date specified as parameter
     * 
     * @param dateString
     *            an iso date in the format yyyy-MM-dd
     * @return number of days between the dates
     */
    public int daysUntil(String dateString) {
        return DateHelper.computeDaysBetween(getRawString(), dateString);
    }

    /**
     * Computes the number of days in the year of the date of this object
     * 
     * @return number of days in the year (either 365 or 366)
     */
    public int daysInTheYear() {
        return DateHelper.computeDaysInTheYear(getRawString());
    }

}
