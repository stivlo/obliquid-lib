package org.obliquid.datatype.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.obliquid.datatype.IsoDate;
import org.obliquid.datatype.strategy.IsoDateStrategy;
import org.obliquid.helpers.DateHelper;

/**
 * Hold and validate a date in ISO format yyyy-MM-dd.
 * 
 * @author stivlo
 * 
 */
public class IsoDateImpl implements IsoDate {

        /**
         * IsoDate Strategy.
         */
        private IsoDateStrategy isoDateStrategy = new IsoDateStrategy();

        /**
         * How long is an ISO date 'yyyy-MM-dd'.
         */
        private final int isoDateLength = 10;

        /**
         * Check a date in ISO 8601 format, formatted as "yyyy-MM-dd".
         * 
         * @param isoDate
         *                a date in ISO format
         * @return true if is valid
         */
        @Override
        public final boolean isTheStringValid(final String isoDate) {
                if (isoDate == null || isoDate.length() != isoDateLength) {
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

        @Override
        public final boolean isValid(final Date theData) {
                return theData != null;
        }

        /**
         * Format the date as a long Date.
         * 
         * @param locale
         *                the locale to use.
         * @return a formatted Date in the specified Locale.
         */
        @Override
        public final String formatData(final Locale locale) {
                return isoDateStrategy.formatData(locale);
        }

        /**
         * Compute the number of days from the date inside the object to the
         * date specified as parameter.
         * 
         * @param dateString
         *                an ISO date in the format yyyy-MM-dd
         * @return number of days between the dates
         */
        //        private final int daysUntil(final String dateString) {
        //                //return DateHelper.computeDaysBetween(getData(), dateString);
        //        }

        /**
         * Computes the number of days in the year of the date of this object.
         * 
         * @return number of days in the year (either 365 or 366)
         */
        //        public int daysInTheYear() {
        //                return DateHelper.computeDaysInTheYear(getData());
        //        }

        /**
         * Return the date stored.
         * 
         * @return the date
         * @throws IllegalStateException
         *                 if the date wasn't set
         */
        @Override
        public final Date getData() throws IllegalStateException {
                return isoDateStrategy.getData();
        }

        @Override
        public final void setDataFromString(final String isoDate) throws IllegalArgumentException {
                isoDateStrategy.setDataFromString(isoDate);
        }

        @Override
        public final void setData(final Date theDate) throws IllegalArgumentException {
                isoDateStrategy.setData(theDate);
        }

        @Override
        public final String toString() {
                return DateHelper.formatIsoDate(isoDateStrategy.getData());
        }

}
