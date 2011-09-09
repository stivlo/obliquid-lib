package org.obliquid.util;

import java.util.Arrays;
import java.util.List;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

/**
 * A class to send emails using Amazon Web Services. To instantiate it use
 * ClientFactory.
 * 
 * @author stivlo
 * 
 */
class AmazonPostMan implements PostMan {

        /** Mail headers and body. */
        private String from, to, subject, body;

        @Override
        public void setFrom(final String fromIn) {
                from = fromIn;
        }

        @Override
        public String getFrom() {
                return from;
        }

        @Override
        public void setTo(final String toIn) {
                to = toIn;
        }

        @Override
        public String getTo() {
                return to;
        }

        @Override
        public void setSubject(final String subjectIn) {
                subject = subjectIn;
        }

        @Override
        public String getSubject() {
                return subject;
        }

        /**
         * Set the message body.
         * 
         * @param message
         *                the message body
         */
        @Override
        public void setBody(final String message) {
                this.body = message;
        }

        @Override
        public String getBody() {
                return body;
        }

        /**
         * Actually send the email.
         */
        @Override
        public void send() {
                AmazonSimpleEmailService service = ClientFactory.createSimpleEmailServiceClient();
                Destination destination = new Destination(getToAsList());
                Body amazonBody = new Body();
                amazonBody.setText(new Content(getBody()));
                Message message = new Message(new Content(getSubject()), amazonBody);
                SendEmailRequest request = new SendEmailRequest(getFrom(), destination, message);
                service.sendEmail(request);
        }

        /**
         * Get a list of addressees (to be used in to: header).
         * 
         * @return a list of addresses
         */
        private List<String> getToAsList() {
                return Arrays.asList(getTo().split(","));
        }

}
