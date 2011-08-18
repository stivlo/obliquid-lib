package org.obliquid.sdb;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.obliquid.helpers.SqlHelper;
import org.obliquid.helpers.StringHelper;

import org.obliquid.util.ClientFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.BatchPutAttributesRequest;
import com.amazonaws.services.simpledb.model.CreateDomainRequest;
import com.amazonaws.services.simpledb.model.DeleteAttributesRequest;
import com.amazonaws.services.simpledb.model.DeleteDomainRequest;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.ReplaceableItem;
import com.amazonaws.services.simpledb.model.SelectRequest;
import com.amazonaws.services.simpledb.model.SelectResult;

/**
 * Implementation of the PersistenceInterface for Amazon SimpleDb with the
 * official Amazon SDK.
 * 
 * @author stivlo
 */
public class AmazonSdbHelper implements PersistenceInterface {

        /** An AmazonSimpleDB instance. */
        private final AmazonSimpleDB sdb;

        /** The domain to operate on. */
        private String domain;

        /** The primary key. */
        private String key;

        /** A list of attributes. */
        private final List<ReplaceableAttribute> attributes;

        /** */
        private final List<ReplaceableItem> items;

        /** The current query. */
        private String currentQuery;

        /** Order by clause. */
        private String orderBy = "";

        /** Result of a Select query. */
        private SelectResult selectResult;

        /** The buffer. */
        private List<Item> buffer;

        /** How many rows per time to fetch. */
        private int limit;

        /** The position in the query (on the total resultset). */
        private int position;

        /** The position in the buffer. */
        private int bufferPosition;

        /** The buffer size. */
        private int bufferSize;

        /** Whether to enable exceptions. */
        private boolean enableExceptions = true;

        /** Whether to use consistent reads or not. */
        private boolean consistent = false;

        /**
         * How many items to load per time, this doesn't limit the total results
         * of a query because all result can be retrieved reissuing the same
         * query with a token. The method next() implements this behaviour.
         */
        private static final int ITEMS_PER_TIME = 50;

        /**
         * Default constructor.
         * 
         * @throws ConnectException
         *                 in case of problems
         */
        public AmazonSdbHelper() throws ConnectException {
                try {
                        sdb = ClientFactory.createSimpleDbClient();
                        attributes = new ArrayList<ReplaceableAttribute>();
                        items = new ArrayList<ReplaceableItem>();
                        limit = 0;
                        position = 0;
                        bufferPosition = 0;
                        bufferSize = 0;
                } catch (NoSuchElementException ex) {
                        throw new ConnectException(ex.getMessage());
                }
        }

        @Override
        public final void setDomain(final String domainIn) throws ConnectException {
                attributes.clear();
                domain = domainIn;
        }

        @Override
        public final void setKey(final String newKey) throws ConnectException {
                key = newKey;
        }

        @Override
        public final String getKey() {
                return key;
        }

        /**
         * Remove an attribute from the current attributes in memory.
         * 
         * @param name
         *                the name of the attribute to be removed
         */
        private void removeAttribute(final String name) {
                ReplaceableAttribute curAttribute;
                for (Iterator<ReplaceableAttribute> it = attributes.iterator(); it.hasNext();) {
                        curAttribute = it.next();
                        if (curAttribute.getName().equals(name)) {
                                it.remove();
                        }
                }
        }

        /**
         * Add an attribute, but only if value is not empty.
         * 
         * @param name
         *                the name of the attribute
         * @param value
         *                the value
         */
        private void addAttribute(final String name, final String value) {
                if (value == null) {
                        return;
                }
                attributes.add(new ReplaceableAttribute(name, value, true));
        }

        @Override
        public final void setAttribute(final String name, final String value) {
                removeAttribute(name);
                addAttribute(name, value);
        }

        @Override
        public final void setAttribute(final String name, final Date valueAsDate) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String valueAsString = sdf.format(valueAsDate);
                setAttribute(name, valueAsString);
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
        public final void setAttribute(final String name, final Collection<String> values) {
                removeAttribute(name);
                for (String value : values) {
                        addAttribute(name, value);
                }
        }

        @Override
        public final void setAttribute(final String name, final String[] values) {
                removeAttribute(name);
                for (String value : values) {
                        addAttribute(name, value);
                }
        }

        @Override
        public final void save() throws ConnectException {
                items.clear();
                items.add(new ReplaceableItem(key, attributes));
                //System.out.println("Saving in domain " + domain + " " + items.size() + " items");
                //for (ReplaceableAttribute attribute : attributes) {
                //    System.out.println("    " + attribute.getName() + "=" + attribute.getValue());
                //}
                try {
                        sdb.batchPutAttributes(new BatchPutAttributesRequest(domain, items));
                } catch (AmazonClientException ex) {
                        throw new ConnectException();
                }
                attributes.clear();
        }

        @Override
        public final String getAttributeAsString(final String name) throws NoSuchElementException {
                for (ReplaceableAttribute attribute : attributes) {
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
                for (ReplaceableAttribute attribute : attributes) {
                        if (attribute.getName().equals(name)) {
                                values.add(attribute.getValue());
                        }
                }
                return values;
        }

        @Override
        public final void loadAll() throws ConnectException {
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
        public final void consistentQuery(final String sql) throws ConnectException {
                consistent = true;
                query(sql, null);
        }

        @Override
        public final void query(final String sql) throws ConnectException {
                query(sql, null);
        }

        /**
         * Get the next batch of results of a query.
         * 
         * @param sql
         *                the original query
         * @param nextToken
         *                the next token
         * @throws ConnectException
         *                 in case of problems
         */
        public final void query(final String sql, final String nextToken) throws ConnectException {
                currentQuery = sql; //we need it to get the next batch of results
                try {
                        SelectRequest selectRequest = new SelectRequest(sql, consistent);
                        if (nextToken != null) {
                                selectRequest.setNextToken(nextToken);
                        }
                        selectResult = sdb.select(selectRequest);
                        buffer = selectResult.getItems();
                        position = 0;
                        bufferPosition = 0;
                        bufferSize = buffer.size();
                } catch (AmazonClientException ex) {
                        throw new ConnectException(ex.getMessage());
                }
        }

        @Override
        public final String next() {
                Item currentItem = buffer.get(bufferPosition);
                key = currentItem.getName();
                attributes.clear();
                List<Attribute> currentAttributes = currentItem.getAttributes();
                for (Attribute anAttribute : currentAttributes) {
                        attributes.add(new ReplaceableAttribute(anAttribute.getName(), anAttribute
                                        .getValue(), true));
                }
                position++;
                bufferPosition++;
                return key;
        }

        @Override
        public final boolean hasNext() throws ConnectException {
                if (bufferSize == 0 || (limit != 0 && position >= limit)) {
                        return false;
                }
                if (bufferPosition < bufferSize) {
                        return true;
                }
                String nextToken = selectResult.getNextToken();
                if (nextToken == null || nextToken.trim().length() == 0) {
                        return false;
                }
                query(currentQuery, nextToken);
                return hasNext();
        }

        @Override
        public final String generateNewId() throws ConnectException {
                return UUID.randomUUID().toString();
        }

        @Override
        public final String[] getAttributeAsStringArray(final String name) {
                Collection<String> values = getAttributeAsCollection(name);
                return values.toArray(new String[values.size()]);
        }

        @Override
        public final List<String> listDomains() throws ConnectException {
                return sdb.listDomains().getDomainNames();
        }

        @Override
        public final void createDomain(final String domainName) throws ConnectException {
                sdb.createDomain(new CreateDomainRequest(domainName));
        }

        @Override
        public final void deleteDomain(final String domainName) throws ConnectException {
                sdb.deleteDomain(new DeleteDomainRequest(domainName));
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
        public final void deleteByKey(final String keyIn) throws ConnectException {
                DeleteAttributesRequest request = new DeleteAttributesRequest(domain, keyIn);
                try {
                        sdb.deleteAttributes(request);
                } catch (AmazonServiceException ex) {
                        throw new ConnectException(ex.getMessage());
                } catch (AmazonClientException ex) {
                        throw new ConnectException(ex.getMessage());
                }
        }

        @Override
        public final String toString() {
                StringBuffer sb = new StringBuffer();
                for (ReplaceableAttribute attribute : attributes) {
                        sb.append(attribute.getName());
                        sb.append(": ");
                        sb.append(attribute.getValue());
                        sb.append("\n");
                }
                return sb.toString();
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
