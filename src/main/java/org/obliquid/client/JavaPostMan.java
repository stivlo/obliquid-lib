package org.obliquid.client;

import java.util.Date;
import java.util.concurrent.RejectedExecutionException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * A class to send email with JavaMail. To instantiate it use ClientFactory.
 * 
 * @author stivlo
 */
class JavaPostMan implements PostMan {

    private String from, to, subject, body;

    @Override
    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public String getFrom() {
        return from;
    }

    @Override
    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public String getTo() {
        return to;
    }

    @Override
    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public void setBody(String message) {
        this.body = message;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public void send() {
        Message emailMessage = new MimeMessage(ClientFactory.createJavaMailSession());
        try {
            emailMessage.setFrom(new InternetAddress(getFrom()));
            emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(getTo(), false));
            emailMessage.setSubject(getSubject());
            emailMessage.setSentDate(new Date());
            emailMessage.setText(getBody());
            Transport.send(emailMessage);
        } catch (AddressException ex) {
            throw new RejectedExecutionException(ex);
        } catch (MessagingException ex) {
            throw new RejectedExecutionException(ex);
        }
    }

}
