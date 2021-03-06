package org.obliquid.sdb;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Interface for persistence in value-data storage.
 * 
 * @author stivlo
 */
public interface PersistenceInterface {

        /**
         * Set the domain to operate on (and clears the list of attributes).
         * 
         * @param domain
         *                the domain name
         * @throws ConnectException
         *                 in case of problems
         */
        void setDomain(final String domain) throws ConnectException;

        /**
         * Enable NoSuchElementExceptions or not (default enabled).
         * 
         * @param enable
         *                true enable exception, false disable them
         */
        void enableExceptions(boolean enable);

        /**
         * Set the key of the object to operate on, existing or new.
         * 
         * @param key
         *                the object key
         * @throws ConnectException
         *                 in case of problems
         */
        void setKey(final String key) throws ConnectException;

        /**
         * Get the key of the object in memory.
         * 
         * @return the current key
         */
        String getKey();

        /**
         * Set an attribute in the current object, previous values of the
         * attribute already in the db are overwritten. Also, if the attribute
         * already exist in memory, the value will be overwritten.
         * 
         * @param name
         *                attribute name
         * @param value
         *                attribute String value
         */
        void setAttribute(final String name, final String value);

        /**
         * Set an attribute in the current object, previous values of the
         * attribute already in the DB are overwritten. Also, if the attribute
         * already exist in memory, the value will be overwritten.
         * 
         * @param name
         *                attribute name
         * @param value
         *                attribute Date value
         */
        void setAttribute(final String name, final Date value);

        /**
         * Set an attribute in the current object with a boolean value, previous
         * values of the attribute already in the DB are overwritten. The value
         * will be saved in the store as a string, either "Y" or "N". Also, if
         * the attribute already exists in memory, the value will be
         * overwritten.
         * 
         * @param name
         *                attribute name
         * @param value
         *                attribute boolean value
         */
        void setAttribute(final String name, final boolean value);

        /**
         * Set an attribute with multiple values, previous values of the
         * attributes already in the DB are overwritten. Also, if the attribute
         * already exists in memory, the value will be overwritten.
         * 
         * @param name
         *                attribute name
         * @param values
         *                a list of attribute values
         */
        void setAttribute(final String name, final Collection<String> values);

        /**
         * Set an attribute with multiple values, previous values of the
         * attributes already in the DB are overwritten. Also, if the attribute
         * already exists in memory, the value will be overwritten.
         * 
         * @param name
         *                attribute name
         * @param values
         *                a list of attribute values
         */
        void setAttribute(final String name, final String[] values);

        /**
         * Save changes.
         * 
         * @throws ConnectException
         *                 when there are problem saving
         */
        void save() throws ConnectException;

        /**
         * Get an attribute from the current object. For multi-value attributes
         * only the first value found will be returned.
         * 
         * @param name
         *                name of the attribute
         * @return the attribute as a String
         * @throws NoSuchElementException
         *                 when the attribute can't be found
         */
        String getAttributeAsString(final String name) throws NoSuchElementException;

        /**
         * Get an attribute from the current object and convert it to an int.
         * 
         * @param name
         *                name of the attribute
         * @return the attribute as an int
         * @throws NoSuchElementException
         *                 when the attribute can't be found
         */
        int getAttributeAsInt(final String name) throws NoSuchElementException;

        /**
         * Get an attribute from the current object and convert it to a long.
         * 
         * @param name
         *                name of the attribute
         * @return the attribute as an long
         * @throws NoSuchElementException
         *                 when the attribute can't be found
         */
        long getAttributeAsLong(final String name) throws NoSuchElementException;

        /**
         * Get an attribute from the current object and convert it to a Boolean.
         * The attribute is supposed to contain either 'Y' or 'N'. If the
         * attribute is not set a NoSuchElementException is thrown, unless when
         * enableExceptions() is set to false, if the attribute doesn't exists,
         * false is returned.
         * 
         * @param name
         *                the attribute as boolean
         * @return true if the attribute contains 'Y' ignoring case, false
         *         otherwise
         * @throws NoSuchElementException
         *                 when the attribute can't be found
         */
        boolean getAttributeAsBoolean(final String name) throws NoSuchElementException;

        /**
         * Get an attribute from the current object and convert it to a
         * BigDecimal.
         * 
         * @param name
         *                name of the attribute
         * @return the attribute as an BigDecimal
         * @throws NoSuchElementException
         *                 when the attribute can't be found
         */
        BigDecimal getAttributeAsBigDecimal(final String name) throws NoSuchElementException;

        /**
         * Get a multi value attribute and return it as a Collection of String.
         * If the attribute is not multi-valued, it will be returned a
         * Collection with a single element
         * 
         * @param name
         *                name of the attribute
         * @return the values for the attribute
         * @throws NoSuchElementException
         *                 when the attribute can't be found
         */
        Collection<String> getAttributeAsCollection(final String name) throws NoSuchElementException;

        /**
         * Get a multi value attribute and return it as an array of String. If
         * the attribute is not multi-valued, it will be returned as an array
         * with a single element
         * 
         * @param name
         *                name of the attribute
         * @return the values for the attribute
         * @throws NoSuchElementException
         *                 when the attribute can't be found
         */
        String[] getAttributeAsStringArray(final String name) throws NoSuchElementException;

        /**
         * Load all items from a domain, use the methods hasNext(), next() and
         * getAttributeAsXXX to retrieve them.
         * 
         * @throws ConnectException
         *                 when there are problems loading the items
         */
        void loadAll() throws ConnectException;

        /**
         * Load all items from a domain, use the methods hasNext(), next() and
         * getAttributeAsXXX to retrieve them.
         * 
         * @param limit
         *                limit the results to the first limit values
         * 
         * @throws ConnectException
         *                 when ther are problems loading the items
         */
        void loadAll(int limit) throws ConnectException;

        /**
         * Execute a custom query with eventual consistency.
         * 
         * @param sql
         *                custom SQL SELECT
         * @throws ConnectException
         *                 when there are problems executing the query
         */
        void query(final String sql) throws ConnectException;

        /**
         * Execute a custom SQL query with read consistency.
         * 
         * @param sql
         *                custom SQL SELECT
         * @throws ConnectException
         *                 when there are problems executing the query
         */
        void consistentQuery(final String sql) throws ConnectException;

        /**
         * Load the next item.
         * 
         * @return the item key
         */
        String next();

        /**
         * Check if there is a next item.
         * 
         * @return true in case there are
         * @throws ConnectException
         *                 when there are problems
         */
        boolean hasNext() throws ConnectException;

        /**
         * Generate a new Id based on the currentTimeStampMs, applications may
         * have a better way to generate an id.
         * 
         * @return the new id
         * @throws ConnectException
         *                 in case of problems
         */
        String generateNewId() throws ConnectException;

        /**
         * Create a new domain.
         * 
         * @param domainName
         *                name for the new domain
         * @throws ConnectException
         *                 in case of problems
         */
        void createDomain(final String domainName) throws ConnectException;

        /**
         * Delete a domain.
         * 
         * @param domainName
         *                name of the domain to delete
         * @throws ConnectException
         *                 in case of problems
         */
        void deleteDomain(final String domainName) throws ConnectException;

        /**
         * List all available domains.
         * 
         * @return all available domains as a List of String
         * @throws ConnectException
         *                 in case of problems
         */
        List<String> listDomains() throws ConnectException;

        /**
         * Delete an item with the specified primary key.
         * 
         * @param key
         *                the primary key
         * @throws ConnectException
         *                 in case of problems
         */
        void deleteByKey(final String key) throws ConnectException;

        /**
         * Load an item by the primary key.
         * 
         * @param key
         *                the primary key (itemName)
         * @throws ConnectException
         *                 in case of technical problems
         * @throws NoSuchElementException
         *                 in case the primary key can't be found
         */
        void loadByKey(final String key) throws ConnectException, NoSuchElementException;

        /**
         * Optionally set the ORDER BY string, including text after the ORDER BY
         * clause.
         * 
         * @param orderBy
         *                the ORDER BY string. Examples: "id", "id DESC"
         */
        void setOrderBy(final String orderBy);

}
