package org.obliquid.datatype;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.Test;
import org.obliquid.datatype.impl.EmailListImpl;

/**
 * Check EmailList class.
 * 
 * @author stivlo
 * 
 */
public class EmailListImplShould {

        /**
         * An empty String is valid, and so is a String with just spaces.
         */
        @Test
        public final void aStringWithZeroEmailIsValid() {
                EmailList list = new EmailListImpl();
                assertTrue(list.isTheStringValid(""));
                assertTrue(list.isTheStringValid("  "));
                assertTrue(list.isTheStringValid(" ,  "));
        }

        /**
         * A String with one email is valid.
         */
        @Test
        public final void aStringWithOneEmailIsValid() {
                EmailList list = new EmailListImpl();
                assertTrue(list.isTheStringValid("stivlo@example.com"));
        }

        /**
         * A wrong email isn't valid.
         */
        @Test
        public final void aWrongEmailIsntValid() {
                EmailList list = new EmailListImpl();
                assertFalse(list.isTheStringValid("xx"));
        }

        /**
         * Two comma separated emails are valid.
         */
        @Test
        public final void aStringWithTwoEmailsIsValid() {
                EmailList list = new EmailListImpl();
                assertTrue(list.isTheStringValid("stivlo@example.com, anotherone@example.com"));
                assertTrue(list.isTheStringValid("stivlo@example.com,anotherone@example.com "));
        }

        /**
         * A list with one email is valid.
         */
        @Test
        public final void aListWithOneEmailIsValid() {
                EmailList list = new EmailListImpl();
                List<String> myList = new ArrayList<String>();
                myList.add("stefano@example.com ");
                assertTrue(list.isValid(myList));
        }

        /**
         * isTheStringValid is actually the same as isValid.
         */
        @Test
        public final void isTheStringValid() {
                EmailList list = new EmailListImpl();
                assertTrue(list.isTheStringValid("stefano@example.com "));
        }

        /**
         * A list with zero email is valid.
         */
        @Test
        public final void aListWithZeroEmailIsValid() {
                EmailList list = new EmailListImpl();
                List<String> myList = new ArrayList<String>();
                assertTrue(list.isValid(myList));
        }

        /**
         * A null list isn't valid.
         */
        @Test
        public final void aNullListIsntValid() {
                EmailList list = new EmailListImpl();
                List<String> myList = null;
                assertFalse(list.isValid(myList));
        }

        /**
         * A null String isn't valid.
         */
        @Test
        public final void aNullStringIsntValid() {
                EmailList list = new EmailListImpl();
                String myString = null;
                assertFalse(list.isTheStringValid(myString));
        }

        /**
         * A list with two emails is valid.
         */
        @Test
        public final void aListWithTwoEmailIsValid() {
                EmailList list = new EmailListImpl();
                List<String> myList = new ArrayList<String>();
                myList.add("appo@example.com ");
                myList.add("   ttt@example.net  ");
                assertTrue(list.isValid(myList));
        }

        /**
         * If one email is wrong, the list is not valid.
         */
        @Test
        public final void ifOneEmailIsWrongTheListIsNotValid() {
                EmailList list = new EmailListImpl();
                assertFalse(list.isTheStringValid("stivlo@example.c, anotherone@example.com"));
                assertFalse(list.isTheStringValid("stivlo@example.com,anotheroneexample.com, "));
        }

        /**
         * Throws Exception when setting null String.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void throwsExceptionWhenSettingNullString() {
                String emailCsv = null;
                EmailList list = new EmailListImpl();
                list.setDataFromString(emailCsv);
        }

        /**
         * Throws Exception when setting null List.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void throwsExceptionWhenSettingNullList() {
                List<String> stringList = null;
                EmailList list = new EmailListImpl();
                list.setData(stringList);
        }

        /**
         * Set an email and get it back.
         */
        @Test
        public final void setAnEmailAndGetItBack() {
                String email = "appo@example.org";
                EmailList list = new EmailListImpl();
                list.setDataFromString(email);
                assertEquals(email, list.formatData(Locale.getDefault()));
                List<String> retList = list.getData();
                assertEquals(1, retList.size());
                assertEquals(email, retList.get(0));
        }

        /**
         * Set an email with leading and trailing spaces and get it back without
         * them.
         */
        @Test
        public final void setAnEmailWithLeadingAndTrailingSpaces() {
                String email = "     appo@example.net     ";
                EmailList list = new EmailListImpl();
                list.setDataFromString(email);
                assertEquals(email.trim(), list.formatData(Locale.getDefault()));
                List<String> retList = list.getData();
                assertEquals(1, retList.size());
                assertEquals(email.trim(), retList.get(0));
        }

        /**
         * Set an email with trailing comma and get it back without.
         */
        @Test
        public final void setAnEmailWithTrailingComma() {
                String email = "     appo@example.org ,    ";
                String fixedEmail = "appo@example.org";
                EmailList list = new EmailListImpl();
                list.setDataFromString(email);
                assertEquals(fixedEmail, list.formatData(Locale.getDefault()));
                List<String> retList = list.getData();
                assertEquals(1, retList.size());
                assertEquals(fixedEmail, retList.get(0));
        }

        /**
         * Set an empty email or with spaces only.
         */
        @Test
        public final void setAnEmptyEmail() {
                EmailList list = new EmailListImpl();
                list.setDataFromString("");
                assertEquals("", list.formatData(Locale.getDefault()));
                assertEquals(0, list.getData().size());
                list.setDataFromString("   ");
                assertEquals("", list.formatData(Locale.getDefault()));
                assertEquals(0, list.getData().size());
                list.setDataFromString("  ,  ");
                assertEquals("", list.formatData(Locale.getDefault()));
                assertEquals(0, list.getData().size());
        }

        /**
         * Set a list of two emails and get them back trimmed.
         */
        @Test
        public final void setAListWithTwoEmails() {
                EmailList list = new EmailListImpl();
                List<String> myList = new ArrayList<String>();
                myList.add("  yupi-du@example.com ");
                myList.add(" another@example.net  ");
                list.setData(myList);
                checkTheList(list);
        }

        /**
         * Set a String with two emails.
         */
        @Test
        public final void setAStringWithTwoEmails() {
                EmailList list = new EmailListImpl();
                list.setDataFromString(" yupi-du@example.com,another@example.net");
                checkTheList(list);
                list.setDataFromString(" , yupi-du@example.com, another@example.net, ");
                checkTheList(list);
        }

        /**
         * Throw exception if one email in the String is wrong.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void throwExceptionfOneEmailInTheStringIsWrong() {
                EmailList list = new EmailListImpl();
                list.setDataFromString(" appo@example.net, ciao@ciao");
        }

        /**
         * Throw exception if one email in the List is wrong.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void throwExceptionfOneEmailInTheListIsWrong() {
                EmailList list = new EmailListImpl();
                List<String> emails = new ArrayList<String>();
                emails.add(" appo@example.net");
                emails.add(" ciao@ciao ");
                list.setData(emails);
        }

        /**
         * Private method two check if a list of two emails is set up correctly.
         * 
         * @param list
         *                an EmailList object set up with the two email to test
         */
        private void checkTheList(final EmailList list) {
                List<String> myList = list.getData();
                assertEquals(2, myList.size());
                assertEquals("yupi-du@example.com", myList.get(0));
                assertEquals("another@example.net", myList.get(1));
                assertEquals("yupi-du@example.com,another@example.net", list.formatData(Locale.getDefault()));

        }

}
