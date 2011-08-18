package org.obliquid.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.MissingResourceException;

/**
 * Application configuration, allows to keep different configurations for
 * different servers in the same code base, so I can publish over and over to
 * without worrying about messing the configurations. Extend this class and name
 * the new class with the host name having the first letter upper case. Subclass
 * will be looked for in the package org.obliquid.config. - These are the
 * defaults and can be overridden by calling setPackage() and setClass()
 * methods. This kind of system serves well my purposes, because I want to be
 * able to keep distinct configurations for the different servers, and I write
 * these configurations by hand. If needed you can just subclass and read the
 * configuration from a property file instead.
 */
public abstract class AppConfig {

        /** The configuration package and class. */
        private static String configPackage, configClass;

        /** The only instance of the singleton. */
        private static volatile AppConfig singleton = null;

        /** Constructor. */
        protected AppConfig() {
                //no initialisation
        }

        /**
         * Set the package for the configuration class. default
         * "org.obliquid.config"
         * 
         * @param configPackageIn
         *                the package name
         */
        public static void setPackage(final String configPackageIn) {
                AppConfig.configPackage = configPackageIn;
        }

        /**
         * Set the configuration class. Default host name with first letter
         * upper case.
         * 
         * @param configClassIn
         *                the configuration class, for example: "UnitTest"
         */
        public static void setClass(final String configClassIn) {
                AppConfig.configClass = configClassIn;
        }

        /**
         * Get a the only instance of the class, it's thread safe.
         * 
         * @return an instance of AppConfig.
         */
        public static AppConfig getInstance() {
                if (singleton != null) {
                        return singleton;
                }
                synchronized (AppConfig.class) {
                        if (singleton != null) { //check again inside synchronized
                                return singleton;
                        }
                        if (configPackage == null) {
                                configPackage = "org.obliquid.config"; //default
                        }
                        if (configClass == null) {
                                configClass = AppConfig.getClassName(); //default
                        }
                        String qualifiedClassName = configPackage + "." + configClass;
                        String errMsg = "Error while trying to load configuration class "
                                        + qualifiedClassName + " ";
                        try {
                                singleton = (AppConfig) Class.forName(qualifiedClassName).newInstance();
                        } catch (InstantiationException ex) {
                                throw new MissingResourceException(errMsg, "AppConfig", qualifiedClassName);
                        } catch (IllegalAccessException ex) {
                                throw new MissingResourceException(errMsg, "AppConfig", qualifiedClassName);
                        } catch (ClassNotFoundException ex) {
                                throw new MissingResourceException(errMsg, "AppConfig", qualifiedClassName);
                        }
                }
                return singleton;
        }

        /**
         * Get the class of the configuration object with class name same as the
         * hostname with uppercase first letter, and the rest lower case.
         * 
         * @return class name of the configuration object
         */
        private static String getClassName() {
                Locale locale = new Locale("en", "US");
                String hostname = getHostName().toLowerCase(locale);
                hostname = hostname.substring(0, 1).toUpperCase(locale) + hostname.substring(1);
                return hostname;
        }

        /**
         * Return the host name of the current machine.
         * 
         * @return the host name
         */
        private static String getHostName() {
                try {
                        String hostname = InetAddress.getLocalHost().getHostName();
                        return normalizeHostname(hostname);
                } catch (UnknownHostException e) {
                        return "unknown";
                }
        }

        /**
         * Normalise the host name getting only the part before the dot if there
         * is one.
         * 
         * @param hostname
         *                the hostname
         * @return normalised host name
         */
        private static String normalizeHostname(final String hostname) {
                if (hostname == null) {
                        return "";
                }
                String[] part = hostname.split("\\.");
                return part[0];
        }

        /**
         * Get a property as String.
         * 
         * @param propertyName
         *                the property name
         * @return a String value
         */
        public abstract String getProperty(String propertyName);

        /**
         * Set a property, use with caution, mostly for testing.
         * 
         * @param propertyName
         *                name of the property
         * @param propertyValue
         *                value of the property
         */
        public abstract void setProperty(String propertyName, String propertyValue);

        /**
         * Get a property as int.
         * 
         * @param name
         *                the property name
         * @return an int value
         */
        public final int getPropertyAsInt(final String name) {
                return Integer.parseInt(getProperty(name));
        }

        /**
         * Get a property as boolean.
         * 
         * @param name
         *                the property name
         * @return a boolean value
         */
        public final boolean getPropertyAsBoolean(final String name) {
                String value = getProperty(name);
                if (value.equals("true")) {
                        return true;
                }
                if (value.equals("false")) {
                        return false;
                }
                throw new IllegalArgumentException("Property '" + name
                                + "' should be either 'true' or 'false'");
        }

        /**
         * Extract the DB name from URL.
         * 
         * @return the database name configured
         */
        public final String getDataBaseName() {
                final int dbNamePosition = 3;
                return getProperty("dbUrl").split("/")[dbNamePosition].split("\\?")[0];
        }

}
