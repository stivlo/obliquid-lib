package org.obliquid.datatype.impl;

import java.util.List;
import java.util.Locale;

import org.apache.commons.validator.EmailValidator;
import org.obliquid.datatype.EmailList;
import org.obliquid.datatype.strategy.ListOfStringStrategy;

/**
 * Hold and validate a comma separated list of email addresses (spaces allowed).
 * 
 * @author stivlo
 */
public class EmailListImpl implements EmailList {

        /**
         * Universal serial identifier.
         */
        private static final long serialVersionUID = 1L;

        /**
         * The list of String Strategy. (Strategy Pattern)
         */
        private ListOfStringStrategy listStrategy = new ListOfStringStrategy();

        /**
         * Check whether the email list is valid.
         * 
         * @param emailCsv
         *                the email list
         * @return true if valid
         */
        @Override
        public final boolean isTheStringValid(final String emailCsv) {
                if (emailCsv == null) {
                        return false;
                }
                if (emailCsv.trim().length() == 0) {
                        return true;
                }
                String[] emails = emailCsv.split(",");
                for (String email : emails) {
                        email = email.trim();
                        if (email.length() == 0) {
                                return true;
                        }
                        if (!EmailValidator.getInstance().isValid(email)) {
                                return false;
                        }
                }
                return true;
        }

        /**
         * Return the email list as a comma separated list of email, without
         * spaces.
         * 
         * @param locale
         *                locale to use
         * @return a CSV list of emails
         * @throws IllegalStateException
         *                 if the data wasn't set
         */
        @Override
        public final String formatData(final Locale locale) throws IllegalStateException {
                return listStrategy.formatData(locale);
        }

        /**
         * Return the email list.
         * 
         * @return the email list.
         * @throws IllegalStateException
         *                 if the data wasn't set
         */
        @Override
        public final List<String> getData() throws IllegalStateException {
                return listStrategy.getData();
        }

        /**
         * Set the data as a CSV email list.
         * 
         * @param emailCsv
         *                a list of CSV emails, with or without spaces between
         *                them.
         * @throws IllegalArgumentException
         *                 in case the argument isn't valid
         */
        @Override
        public final void setDataFromString(final String emailCsv) throws IllegalArgumentException {
                if (!isTheStringValid(emailCsv)) {
                        throw new IllegalArgumentException();
                }
                listStrategy.setDataFromString(emailCsv);
        }

        @Override
        public final void setData(final List<String> theList) throws IllegalArgumentException {
                if (!isValid(theList)) {
                        throw new IllegalArgumentException();
                }
                listStrategy.setData(theList);
        }

        @Override
        public final boolean isValid(final List<String> list) {
                boolean valid;
                if (list == null) {
                        return false;
                }
                for (String email : list) {
                        valid = EmailValidator.getInstance().isValid(email);
                        if (!valid) {
                                return false;
                        }
                }
                return true;
        }

        @Override
        public final boolean isAssigned() {
                return listStrategy.isAssigned();
        }

}
