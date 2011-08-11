package org.obliquid.datatype;

import java.util.List;

/**
 * Hold and validate a comma separated list of email addresses (spaces allowed).
 * 
 * @author stivlo
 */
public interface EmailList extends DataType<List<String>>, DataTypeValidator<List<String>> {

        //just combining interfaces

}
