package org.obliquid.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.MissingResourceException;

/**
 * Application configuration, allows to keep different configurations forr different servers in the
 * same codebase, so I can publish over and over to without worrying about messing the
 * configurations. Extend this class and name the new class with the hostname having the first
 * letter uppercase. Subclass will be looked for in the package org.obliquid.config. - These are the
 * defaults and can be overridden by calling setPackage() and setClass() methods. This kind of
 * system serves well my purposes, because I want to be able to keep distinct configurations for the
 * different servers, and I write these configurations by hand. If needed you can just subclass and
 * read the configuration from a property file instead.
 */
public abstract class AppConfig {

    public static final int DISABLED = 0, ERROR = 1, WARNING = 2, INFO = 3;

    private static String configPackage, configClass;

    private static volatile AppConfig singleton = null;

    protected AppConfig() {
        //no initialization
    }

    /**
     * Set the package for the configuration class. default "org.obliquid.config"
     * 
     * @param configPackage
     *            the package name
     */
    public static void setPackage(String configPackage) {
        AppConfig.configPackage = configPackage;
    }

    /**
     * Set the configuration class. Default hostname with first letter upper case.
     * 
     * @param configClass
     *            the config class, for example: "UnitTest"
     */
    public static void setClass(String configClass) {
        AppConfig.configClass = configClass;
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
            String errMsg = "Error while trying to load configuration class " + qualifiedClassName + " ";
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
     * Get the class of the configuration object with class name same as the hostname with uppercase
     * first letter, and the rest lower case.
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
     * Return the hostname of the current machine
     * 
     * @return the hostname
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
     * Normalize the hostname getting only the part before the dot if there is one
     * 
     * @param hostname
     * @return normalized hostname
     */
    private static String normalizeHostname(String hostname) {
        if (hostname == null) {
            return "";
        }
        String[] part = hostname.split("\\.");
        hostname = part[0];
        return hostname;
    }

    /**
     * Get a property as String
     * 
     * @param propertyName
     *            the property name
     * @return a String value
     */
    public abstract String getProperty(String propertyName);

    /**
     * Set a property, use with caution, mostly for testing.
     * 
     * @param propertyName
     *            name of the property
     * @param propertyValue
     *            value of the property
     */
    public abstract void setProperty(String propertyName, String propertyValue);

    /**
     * Get a property as int
     * 
     * @param name
     *            the property name
     * @return an int value
     */
    public int getPropertyAsInt(String name) {
        return Integer.parseInt(getProperty(name));
    }

    /**
     * Get a property as boolean
     * 
     * @param name
     *            the property name
     * @return a boolean value
     */
    public boolean getPropertyAsBoolean(String name) {
        String value = getProperty(name);
        if (value.equals("true")) {
            return true;
        }
        if (value.equals("false")) {
            return false;
        }
        throw new IllegalArgumentException("Property '" + name + "' should be either 'true' or 'false'");
    }

    /**
     * Extract the db name from URL
     * 
     * @return the database name configured
     */
    public String getDataBaseName() {
        return getProperty("dbUrl").split("/")[3].split("\\?")[0];
    }

}
