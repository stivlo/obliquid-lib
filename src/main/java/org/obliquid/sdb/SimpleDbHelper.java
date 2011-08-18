package org.obliquid.sdb;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.obliquid.config.AppConfig;

import org.obliquid.helpers.SqlHelper;
import org.obliquid.helpers.StringHelper;

import com.xerox.amazonws.sdb.Domain;
import com.xerox.amazonws.sdb.Item;
import com.xerox.amazonws.sdb.QueryWithAttributesResult;
import com.xerox.amazonws.sdb.SDBException;
import com.xerox.amazonws.sdb.SimpleDB;
import com.xerox.amazonws.sdb.ItemAttribute;

/**
 * Implementation of the PersistenceInterface for Amazon SimpleDb with Typica.
 * 
 * @author stivlo
 * 
 */
public class SimpleDbHelper implements PersistenceInterface {

        /**
         * A Typica SimpleDB instance. This class provides an interface with the
         * Amazon SDB service. It provides high level methods for listing and
         * creating and deleting domains.
         */
        private final SimpleDB sdb;

        /**
         * This class provides an interface with the Amazon SDB service. It
         * provides methods for listing and deleting items.
         */
        private Domain domain;

        /**
         * A list of ItemAttribute.
         */
        private List<ItemAttribute> attributes;

        /**
         * This class provides an interface with the Amazon SDB service. It
         * provides methods for listing items and adding/removing attributes.
         */
        private Item item;

        /** The primary key. */
        private String key;

        /** The current query. */
        private String currentQuery;

        /** The order by clause. */
        private String orderBy;

        /** The result of a query with attributes. */
        private QueryWithAttributesResult queryResult;

        /** A record buffer. */
        private Map<String, List<ItemAttribute>> buffer;

        /** An array with all the (primary) keys in the buffer. */
        private Object[] bufferKeys;

        /** Whether exception should be propagated. */
        private boolean enableExceptions = true;

        /**
         * Position inside the buffer, how many items were retrieved in this
         * batch.
         */
        private int bufferPosition;

        /** Position inside the query results. */
        private int position;

        /** Total size of the buffer (elements). */
        private int bufferSize;

        /** Limit to max limit results. */
        private int limit;

        /** Whether to use consistent reads or not. */
        private boolean consistent = false;

        /**
         * How many items to load per time, this doesn't limit the total results
         * of a query because all result can be retrieved reissuing the same
         * query with a token. The method next() implements this behaviour.
         */
        private static final int ITEMS_PER_TIME = 50;

        /** How many times to retry before giving up. */
        private static final int MAX_RETRIES = 5;

        /**
         * Default Constructor.
         */
        public SimpleDbHelper() {
                AppConfig conf = AppConfig.getInstance();
                boolean isSecure = true;
                sdb = new SimpleDB(conf.getProperty("awsAccessKey"), conf.getProperty("awsSecretKey"),
                                isSecure);
                sdb.setMaxRetries(MAX_RETRIES);
                attributes = new ArrayList<ItemAttribute>();
                bufferKeys = null;
                limit = 0;
                position = 0;
                bufferPosition = 0;
                bufferSize = 0;
        }

        /**
         * Set the domain to operate on.
         * 
         * @param domainName
         *                the domain to operate on
         * @throws ConnectException
         *                 in case of problems
         */
        @Override
        public final void setDomain(final String domainName) throws ConnectException {
                attributes.clear();
                try {
                        domain = sdb.getDomain(domainName);
                } catch (SDBException ex) {
                        throw new ConnectException(ex.getMessage());
                }
        }

        /**
         * Get the record Id.
         * 
         * @return the record Id
         */
        @Override
        public final String getKey() {
                return key;
        }

        /**
         * Set the id of the object to operate on, existing or new.
         * 
         * @param id
         *                the primary key
         * @throws ConnectException
         *                 in case of problems
         */
        @Override
        public final void setKey(final String id) throws ConnectException {
                try {
                        this.key = id;
                        item = domain.getItem(id);
                } catch (SDBException ex) {
                        throw new ConnectException(ex.getMessage());
                }
        }

        /**
         * Remove an attribute from the current attributes in memory.
         * 
         * @param name
         *                the name of the attribute to be removed
         */
        private void removeAttribute(final String name) {
                ItemAttribute curAttribute;
                for (Iterator<ItemAttribute> it = attributes.iterator(); it.hasNext();) {
                        curAttribute = it.next();
                        if (curAttribute.getName().equals(name)) {
                                it.remove();
                        }
                }
        }

        /**
         * Replace or create an attribute in the current object.
         * 
         * @param name
         *                attribute's name
         * @param value
         *                attribute's value
         */
        @Override
        public final void setAttribute(final String name, final String value) {
                removeAttribute(name);
                attributes.add(new ItemAttribute(name, value, true));
        }

        @Override
        public final void setAttribute(final String name, final boolean value) {
                String valueString;
                if (value) {
                        valueString = "Y";
                } else {
                        valueString = "N";
                }
                setAttribute(name, valueString);
        }

        @Override
        public final void setAttribute(final String name, final Date valueAsDate) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String valueAsString = sdf.format(valueAsDate);
                setAttribute(name, valueAsString);
        }

        @Override
        public final void setAttribute(final String name, final Collection<String> values) {
                removeAttribute(name);
                for (String value : values) {
                        attributes.add(new ItemAttribute(name, value, true));
                }
        }

        @Override
        public final void setAttribute(final String name, final String[] values) {
                removeAttribute(name);
                for (String value : values) {
                        attributes.add(new ItemAttribute(name, value, true));
                }
        }

        /**
         * Save changes.
         * 
         * @throws ConnectException
         *                 in case of problems
         */
        @Override
        public final void save() throws ConnectException {
                try {
                        item.putAttributes(attributes);
                        attributes.clear();
                } catch (SDBException ex) {
                        throw new ConnectException(ex.getMessage());
                }
        }

        @Override
        public final void loadAll() throws ConnectException {
                this.limit = 0; //unlimited
                String sql = "SELECT * FROM " + domain;
                if (orderBy.length() > 0) {
                        sql += SqlHelper.buildWhereAndOrderByForSdb(orderBy);
                }
                sql += " LIMIT " + ITEMS_PER_TIME;
                query(sql);
        }

        @Override
        public final void loadAll(final int limitIn) throws ConnectException {
                limit = limitIn;
                loadAll();
        }

        @Override
        public final String generateNewId() throws ConnectException {
                return UUID.randomUUID().toString();
        }

        @Override
        public final String getAttributeAsString(final String name) throws NoSuchElementException {
                for (ItemAttribute attribute : attributes) {
                        if (attribute.getName().equals(name)) {
                                return attribute.getValue();
                        }
                }
                if (enableExceptions) {
                        throw new NoSuchElementException("Attribute '" + name + "' not found");
                }
                return null;
        }

        @Override
        public final int getAttributeAsInt(final String name) throws NoSuchElementException {
                String value = getAttributeAsString(name);
                return Integer.parseInt(value);
        }

        @Override
        public final long getAttributeAsLong(final String name) {
                String value = getAttributeAsString(name);
                return Long.parseLong(value);
        }

        @Override
        public final boolean getAttributeAsBoolean(final String name) throws NoSuchElementException {
                String value = getAttributeAsString(name);
                if (value == null) {
                        return false;
                }
                return value.equalsIgnoreCase("Y");
        }

        @Override
        public final BigDecimal getAttributeAsBigDecimal(final String name) throws NoSuchElementException {
                String value = getAttributeAsString(name);
                return new BigDecimal(value);
        }

        @Override
        public final Collection<String> getAttributeAsCollection(final String name)
                        throws NoSuchElementException {
                List<String> values = new ArrayList<String>();
                for (ItemAttribute attribute : attributes) {
                        if (attribute.getName().equals(name)) {
                                values.add(attribute.getValue());
                        }
                }
                return values;
        }

        @Override
        public final String[] getAttributeAsStringArray(final String name) {
                Collection<String> values = getAttributeAsCollection(name);
                return values.toArray(new String[values.size()]);
        }

        @Override
        public final boolean hasNext() throws ConnectException {
                if (limit != 0 && position >= limit) {
                        return false;
                }
                if (bufferPosition < bufferSize || bufferSize == 0) {
                        return true;
                }
                String nextToken = queryResult.getNextToken();
                if (nextToken == null || nextToken.trim().length() == 0) {
                        return false;
                }
                query(currentQuery, nextToken);
                return hasNext();
        }

        @Override
        public final String next() {
                key = (String) bufferKeys[bufferPosition];
                attributes = buffer.get(key);
                position++;
                bufferPosition++;
                return key;
        }

        @Override
        public final void consistentQuery(final String sql) throws ConnectException {
                consistent = true;
                query(sql, null);
        }

        /**
         * Execute a custom query, with eventual consistency.
         * 
         * @param sql
         *                the query to execute
         * @throws ConnectException
         *                 in case of connection problems
         */
        @Override
        public final void query(final String sql) throws ConnectException {
                consistent = false;
                query(sql, null);
        }

        /**
         * Get the next batch of results for a query.
         * 
         * @param sql
         *                the query to execute
         * @param nextToken
         *                the token to get the next batch of results
         * @throws ConnectException
         *                 in case of connection problems
         */
        private void query(final String sql, final String nextToken) throws ConnectException {
                try {
                        currentQuery = sql; //we need it to get the next batch of results
                        queryResult = domain.selectItems(sql, nextToken, consistent);
                        buffer = queryResult.getItems();
                        bufferKeys = buffer.keySet().toArray();
                        position = 0;
                        bufferPosition = 0;
                        bufferSize = buffer.size();
                        //System.out.println("boxUsage: " + queryResult.getBoxUsage());
                } catch (SDBException ex) {
                        throw new ConnectException(ex.getMessage() + "\n         " + sql);
                }
        }

        /**
         * List the available domains.
         * 
         * @return a list of Strings representing domain names
         * @throws ConnectException
         *                 in case of connection problems
         */
        @Override
        public final List<String> listDomains() throws ConnectException {
                List<String> domainList = new ArrayList<String>();
                try {
                        List<Domain> domainListTypica = sdb.listDomains().getDomainList();
                        for (Domain aDomain : domainListTypica) {
                                domainList.add(aDomain.getName());
                        }
                } catch (SDBException e) {
                        throw new ConnectException();
                }
                return domainList;
        }

        @Override
        public final void createDomain(final String domainName) throws ConnectException {
                try {
                        sdb.createDomain(domainName);
                } catch (SDBException ex) {
                        throw new ConnectException(ex.getMessage());
                }
        }

        @Override
        public final void deleteDomain(final String domainName) throws ConnectException {
                try {
                        sdb.deleteDomain(domainName);
                } catch (SDBException ex) {
                        throw new ConnectException(ex.getMessage());
                }
        }

        @Override
        public final void deleteByKey(final String keyIn) throws ConnectException {
                throw new UnsupportedOperationException();
        }

        @Override
        public final void loadByKey(final String keyIn) throws ConnectException, NoSuchElementException {
                String sql = "SELECT * FROM " + domain + " WHERE itemName() = " + StringHelper.quote(keyIn);
                query(sql);
                if (hasNext()) {
                        next();
                } else {
                        throw new NoSuchElementException();
                }
        }

        @Override
        public final void setOrderBy(final String orderByIn) {
                if (orderByIn != null) {
                        orderBy = orderByIn;
                } else {
                        orderBy = "";
                }

        }

        @Override
        public final void enableExceptions(final boolean enable) {
                enableExceptions = enable;
        }

}
