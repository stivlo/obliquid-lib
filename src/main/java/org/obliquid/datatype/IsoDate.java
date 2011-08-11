package org.obliquid.datatype;

import java.util.Date;

/**
 * Hold and validate a date in ISO format yyyy-MM-dd.
 * 
 * @author stivlo
 * 
 */
public interface IsoDate extends DataType<Date>, DataTypeValidator<Date> {

        //just combine the interfaces

}
