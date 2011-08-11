package org.obliquid.datatype.strategy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import org.joda.time.ReadableDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.obliquid.datatype.DataType;
import org.obliquid.helpers.DateHelper;

/**
 * Implement IsoDate Data Type behaviour for reuse. (Strategy Pattern)
 * 
 * @author stivlo
 * 
 */
public class IsoDateStrategy implements DataType<Date> {

        /** The value held. */
        private Date date;

        /** Check an ISO Date in the format yyyy-MM-dd only. */
        private Pattern regexp = Pattern.compile("^([0-9]{4})-(1[0-2]|0[1-9])-(3[0-1]|[1-2][0-9]|0[1-9])$");

        /**
         * ISO Date parser.
         */
        private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        @Override
        public final String formatData(final Locale locale) throws IllegalStateException {
                DateTimeFormatter dtFormatter = DateTimeFormat.longDate().withLocale(locale);
                return dtFormatter.print(getDateTime());
        }

        @Override
        public final Date getData() throws IllegalStateException {
                if (date == null) {
                        throw new IllegalStateException();
                }
                return new Date(date.getTime());
        }

        @Override
        public final void setDataFromString(final String isoDate) throws IllegalArgumentException {
                if (isoDate == null) {
                        throw new IllegalArgumentException("ISO Date can't be null");
                }
                if (!regexp.matcher(isoDate).find()) {
                        throw new IllegalArgumentException("Date '" + isoDate + "' not in ISO format");
                }
                try {
                        date = formatter.parse(isoDate);
                } catch (ParseException ex) {
                        throw new IllegalArgumentException("Problem parsing Date '" + isoDate);
                }

        }

        @Override
        public final void setData(final Date theDate) throws IllegalArgumentException {
                if (theDate == null) {
                        throw new IllegalArgumentException("Date can't be null");
                }
                date = new Date(theDate.getTime());
        }

        /**
         * Build a Joda ReadableDateTime from the internal Date.
         * 
         * @return a Joda ReadableDateTime
         */
        private ReadableDateTime getDateTime() {
                return DateHelper.buildReadableDateTimeFromJavaDate(getData());
        }

}
