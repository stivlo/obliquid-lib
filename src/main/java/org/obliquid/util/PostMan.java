package org.obliquid.util;

/**
 * Interface to send an email. Use ClientFactory to create an instance.
 * 
 * @author stivlo
 * 
 */
public interface PostMan {

    /**
     * Set the From: email
     * 
     * @param from
     */
    void setFrom(String from);

    /**
     * Get the From: email
     * 
     * @return
     */
    String getFrom();

    /**
     * Set the To: email, can be a comma separated list of email addresses
     * 
     * @param to
     */
    void setTo(String to);

    /**
     * Get the To: email, can be a comma separated list of email addresses
     * 
     * @return
     */
    String getTo();

    /**
     * Set the Subject of the email
     * 
     * @param subject
     */
    void setSubject(String subject);

    /**
     * Set the Subject of the email
     * 
     * @return the subject
     */
    String getSubject();

    /**
     * Set the message body
     * 
     * @param message
     */
    void setBody(String message);

    /**
     * Get the message body
     * 
     * @param message
     */
    String getBody();

    /**
     * Send the message
     */
    void send();

}
