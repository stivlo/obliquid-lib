package org.obliquid.datatype;

import java.io.Serializable;
import java.util.Date;

/**
 * Hold and validate a date in ISO format yyyy-MM-dd.
 * 
 * @author stivlo
 * 
 */
public interface IsoDate extends DataType<Date>, DataTypeValidator<Date>, Serializable {

        /**
         * Computes the number of days in the year of the date of this object.
         * 
         * @return number of days in the year (either 365 or 366)
         */
        int daysInTheYear();

        //just combine the interfaces

}
