package org.obliquid.datatype;

/**
 * Hold and validate a relative path according to my rules (I admit only lower
 * case and upper case letters, numbers, hyphen, underscore, the dot and Unix
 * path separator.
 * 
 * @author stivlo
 * 
 */
public interface PathName extends DataType<String>, DataTypeValidator<String> {

        //just combines interfaces

}
