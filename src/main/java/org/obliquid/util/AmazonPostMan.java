package org.obliquid.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

/**
 * A class to send emails using Amazon Web Services. To instantiate it use ClientFactory.
 * 
 * @author stivlo
 * 
 */
class AmazonPostMan implements PostMan {

    private String from, to, subject, body;

    public AmazonPostMan() {
        //create only through factory class
    }

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

    /**
     * Set the message body
     * 
     * @param message
     */
    @Override
    public void setBody(String message) {
        this.body = message;
    }

    @Override
    public String getBody() {
        return body;
    }

    /**
     * Actually send the email
     * 
     * @throws IOException
     */
    @Override
    public void send() {
        AmazonSimpleEmailService service = ClientFactory.createSimpleEmailServiceClient();
        Destination destination = new Destination(getToAsList());
        Body body = new Body();
        body.setText(new Content(getBody()));
        Message message = new Message(new Content(getSubject()), body);
        SendEmailRequest request = new SendEmailRequest(getFrom(), destination, message);
        service.sendEmail(request);
    }

    private List<String> getToAsList() {
        return Arrays.asList(getTo().split(","));
    }

}
