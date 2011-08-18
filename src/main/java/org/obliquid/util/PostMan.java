package org.obliquid.util;

/**
 * Interface to send an email. Use ClientFactory to create an instance.
 * 
 * @author stivlo
 * 
 */
public interface PostMan {

        /**
         * Set the From: email.
         * 
         * @param from
         *                an email address
         */
        void setFrom(String from);

        /**
         * Get the From: email.
         * 
         * @return from email address
         */
        String getFrom();

        /**
         * Set the To: email, can be a comma separated list of email addresses.
         * 
         * @param to
         *                an email address or a comma separated list of email
         *                addresses
         */
        void setTo(String to);

        /**
         * Get the To: email, can be a comma separated list of email addresses.
         * 
         * @return to email address
         */
        String getTo();

        /**
         * Set the Subject of the email.
         * 
         * @param subject
         *                the subject of the email
         */
        void setSubject(String subject);

        /**
         * Set the Subject of the email.
         * 
         * @return the subject
         */
        String getSubject();

        /**
         * Set the message body.
         * 
         * @param message
         *                the email message in plain text
         */
        void setBody(String message);

        /**
         * Get the message body.
         * 
         * @return message body
         */
        String getBody();

        /**
         * Send the message.
         */
        void send();

}
