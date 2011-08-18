package org.obliquid.util;

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

        @Override
        public void setBody(final String messageIn) {
                body = messageIn;
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
                        emailMessage.setRecipients(Message.RecipientType.TO,
                                        InternetAddress.parse(getTo(), false));
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
