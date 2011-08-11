package org.obliquid.datatype.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.obliquid.datatype.DataType;

/**
 * Implement basic List of String DataType behaviour for reuse. (Strategy
 * Pattern)
 * 
 * @author stivlo
 * 
 */
public class ListOfStringStrategy implements DataType<List<String>> {

        /**
         * The list of Strings.
         */
        private final List<String> list = new ArrayList<String>();

        @Override
        public final String formatData(final Locale locale) throws IllegalStateException {
                StringBuilder builder = new StringBuilder();
                boolean first = true;
                for (String email : list) {
                        if (!first) {
                                builder.append(",");
                        }
                        builder.append(email);
                        first = false;
                }
                return builder.toString();
        }

        @Override
        public final List<String> getData() throws IllegalStateException {
                return list;
        }

        @Override
        public final void setDataFromString(final String emailCsv) throws IllegalArgumentException {
                if (emailCsv == null) {
                        throw new IllegalArgumentException("The list can't be null");
                }
                String[] emails = emailCsv.split(",");
                list.clear();
                for (String email : emails) {
                        email = email.trim();
                        if (email.length() == 0) {
                                continue;
                        }
                        list.add(email);
                }
        }

        @Override
        public final void setData(final List<String> theData) throws IllegalArgumentException {
                if (theData == null) {
                        throw new IllegalArgumentException("The list can't be null");
                }
                //force defensive copy, since our list is final we can't forget.
                list.clear();
                for (String email : theData) {
                        email = email.trim();
                        list.add(email);
                }
        }

}
