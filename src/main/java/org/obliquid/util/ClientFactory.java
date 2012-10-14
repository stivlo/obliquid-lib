package org.obliquid.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.obliquid.config.AppConfig;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;

/**
 * A factory class to create Clients, using the application configuration.
 * 
 * @author stivlo
 */
public final class ClientFactory {

        /** Utility class. */
        private ClientFactory() {

        }

        /**
         * Create an AmazonEC2 client properly configured.
         * 
         * @return an AmazonEC2 client
         */
        public static AmazonEC2 createElasticComputeCloudClient() {
                return new AmazonEC2Client(createAwsCredentials(), createClientConfiguration());
        }

        /**
         * Create an AmazonSimpleDB client properly configured.
         * 
         * @return an AmazonSimpleDB client
         */
        public static AmazonSimpleDB createSimpleDbClient() {
                return new AmazonSimpleDBClient(createAwsCredentials(), createClientConfiguration());
        }

        /**
         * Create an AmazonS3 client properly configured.
         * 
         * @return an AmazonS3 client
         */
        public static AmazonS3 createSimpleStorageServiceClient() {
                System.out.println("ClientFactory.createSimpleStorageServiceClient()");
                return new AmazonS3Client(createAwsCredentials(), createClientConfiguration());
        }
        
        /**
         * Creates an AmazonPostMan, in the future it should create either an Amazon PostMan,
         * or another type (JavaMail, Google), according to configuration.
         * @return an instance of PostMan
         */
        public static PostMan createPostMan() {
        	return new AmazonPostMan();
        }

        /**
         * Create an AmazonSimpleEmailService properly configured.
         * 
         * @return an AmazonSimpleEmailService
         */
        static AmazonSimpleEmailService createSimpleEmailServiceClient() {
                System.out.println("ClientFactory.createSimpleEmailServiceClient()");
                AmazonSimpleEmailService service = new AmazonSimpleEmailServiceClient(
                                createAwsCredentials(), createClientConfiguration());
                return service;
        }

        /**
         * Return a AWSCredentials Object for this application. The following
         * configuration properties are used: awsAccessKey, awsSecretKey.
         * 
         * @return AWSCredentials Object
         */
        public static AWSCredentials createAwsCredentials() {
                AppConfig appConf = AppConfig.getInstance();
                AWSCredentials credentials = new BasicAWSCredentials(appConf.getProperty("awsAccessKey"),
                                appConf.getProperty("awsSecretKey"));
                return credentials;
        }

        /**
         * Client configuration deals with proxies, the following configuration
         * properties are used: useProxy (true/false), if it's false proxy is
         * not used and is not necessary to set the other properties. The other
         * properties are proxyHost, proxyPort, proxyUsername, proxyPassword.
         * 
         * @return a ClientConfiguration instance
         */
        private static ClientConfiguration createClientConfiguration() {
                AppConfig appConf = AppConfig.getInstance();
                ClientConfiguration clientConf = new ClientConfiguration();
                if (appConf.getPropertyAsBoolean("useProxy")) {
                        clientConf.setProxyHost(appConf.getProperty("proxyHost"));
                        clientConf.setProxyPort(appConf.getPropertyAsInt("proxyPort"));
                        clientConf.setProxyUsername(appConf.getProperty("proxyUsername"));
                        clientConf.setProxyPassword(appConf.getProperty("proxyPassword"));
                }
                return clientConf;
        }

        /**
         * Return an Authenticator for Java Mail. It reads the following
         * properties: mail.smtp.user, mail.smtp.password
         * 
         * @return AWSCredentials Object
         */
        private static Authenticator createJavaMailSmtpCredentials() {
                final AppConfig appConf = AppConfig.getInstance();
                return new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(appConf.getProperty("mail.smtp.user"),
                                                appConf.getProperty("mail.smtp.password"));
                        }
                };
        }

        /**
         * Create a Java Mail Session Object, properly configured using the
         * application configuration. It reads the following properties:
         * mail.smtp.host, mail.smtp.port, mail.smtp.user, mail.smtp.auth
         * 
         * @return a Java Mail Session
         */
        static Session createJavaMailSession() {
                AppConfig appConf = AppConfig.getInstance();
                Properties sessionProperties = new Properties();
                sessionProperties.put("mail.smtp.host", appConf.getProperty("mail.smtp.host"));
                sessionProperties.put("mail.smtp.port", appConf.getPropertyAsInt("mail.smtp.port"));
                sessionProperties.put("mail.smtp.user", appConf.getProperty("mail.smtp.user"));
                sessionProperties.put("mail.smtp.auth", appConf.getProperty("mail.smtp.auth"));
                sessionProperties.put("mail.transport.protocol.rfc822", "smtp");
                Session session = Session.getInstance(sessionProperties, createJavaMailSmtpCredentials());
                return session;
        }

}
