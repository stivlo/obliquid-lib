package org.obliquid.sdb;

import java.io.IOException;
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
 * Implementation of the PersistenceInterface for Amazon SimpleDb with Typica
 * 
 * @author stivlo
 * 
 */
public class SimpleDbHelper implements PersistenceInterface {

    private final SimpleDB sdb;
    private Domain domain;
    private List<ItemAttribute> attributes;
    private Item item;
    private String key;

    private String currentQuery;
    private String orderBy;
    private QueryWithAttributesResult queryResult;
    private Map<String, List<ItemAttribute>> buffer;
    private Object[] bufferKeys;
    boolean enableExceptions = true;

    /** Position inside the buffer, how many items were retrieved in this batch */
    private int bufferPosition;

    /** Position inside the query results */
    private int position;

    /** Total size of the buffer (elements) */
    private int bufferSize;

    /** Limit to max limit results */
    private int limit;

    /** Whether to use consistent reads or not */
    private boolean consistent = false;

    /**
     * How many items to load per time, this doesn't limit the total results of a query because all
     * result can be retrieved reissuing the same query with a token. The method next() implements
     * this behavior.
     */
    private static final int itemsPerTime = 50;

    public SimpleDbHelper() throws IOException {
        AppConfig conf = AppConfig.getInstance();
        boolean isSecure = true;
        sdb = new SimpleDB(conf.getProperty("awsAccessKey"), conf.getProperty("awsSecretKey"), isSecure);

        sdb.setMaxRetries(5); //default 5
        attributes = new ArrayList<ItemAttribute>();
        bufferKeys = null;
        limit = 0;
        position = 0;
        bufferPosition = 0;
        bufferSize = 0;
    }

    /**
     * Set the domain to operate on
     * 
     * @param domainName
     * @throws SDBException
     */
    @Override
    public void setDomain(final String domainName) throws ConnectException {
        attributes.clear();
        try {
            domain = sdb.getDomain(domainName);
        } catch (SDBException ex) {
            throw new ConnectException(ex.getMessage());
        }
    }

    /**
     * Get the record Id
     */
    @Override
    public String getKey() {
        return key;
    }

    /**
     * Set the id of the object to operate on, existing or new
     * 
     * @param id
     * @throws SDBException
     */
    @Override
    public void setKey(final String id) throws ConnectException {
        try {
            this.key = id;
            item = domain.getItem(id);
        } catch (SDBException ex) {
            throw new ConnectException(ex.getMessage());
        }
    }

    /**
     * Remove an attribute from the current attributes in memory
     * 
     * @param name
     *            the name of the attribute to be removed
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
     * Replace or create an attribute in the current object
     * 
     * @param name
     * @param value
     */
    @Override
    public void setAttribute(final String name, final String value) {
        removeAttribute(name);
        attributes.add(new ItemAttribute(name, value, true));
    }

    @Override
    public void setAttribute(final String name, final boolean value) {
        String valueString;
        if (value) {
            valueString = "Y";
        } else {
            valueString = "N";
        }
        setAttribute(name, valueString);
    }

    @Override
    public void setAttribute(final String name, final Date valueAsDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String valueAsString = sdf.format(valueAsDate);
        setAttribute(name, valueAsString);
    }

    @Override
    public void setAttribute(final String name, final Collection<String> values) {
        removeAttribute(name);
        for (String value : values) {
            attributes.add(new ItemAttribute(name, value, true));
        }
    }

    @Override
    public void setAttribute(final String name, final String[] values) {
        removeAttribute(name);
        for (String value : values) {
            attributes.add(new ItemAttribute(name, value, true));
        }
    }

    /**
     * Save changes
     * 
     * @throws SDBException
     */
    @Override
    public void save() throws ConnectException {
        try {
            item.putAttributes(attributes);
            attributes.clear();
        } catch (SDBException ex) {
            throw new ConnectException(ex.getMessage());
        }
    }

    @Override
    public void loadAll() throws ConnectException {
        this.limit = 0; //unlimited
        String sql = "SELECT * FROM " + domain;
        if (orderBy.length() > 0) {
            sql += SqlHelper.buildWhereAndOrderByForSdb(orderBy);
        }
        sql += " LIMIT " + itemsPerTime;
        query(sql);
    }

    @Override
    public void loadAll(int limit) throws ConnectException {
        this.limit = limit;
        loadAll();
    }

    @Override
    public String generateNewId() throws ConnectException {
        return UUID.randomUUID().toString();
    }

    @Override
    public String getAttributeAsString(final String name) throws NoSuchElementException {
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
    public int getAttributeAsInt(final String name) throws NoSuchElementException {
        String value = getAttributeAsString(name);
        return Integer.parseInt(value);
    }

    @Override
    public long getAttributeAsLong(final String name) {
        String value = getAttributeAsString(name);
        return Long.parseLong(value);
    }

    @Override
    public boolean getAttributeAsBoolean(String name) throws NoSuchElementException {
        String value = getAttributeAsString(name);
        if (value == null) {
            return false;
        }
        return value.equalsIgnoreCase("Y");
    }

    @Override
    public BigDecimal getAttributeAsBigDecimal(final String name) throws NoSuchElementException {
        String value = getAttributeAsString(name);
        return new BigDecimal(value);
    }

    @Override
    public Collection<String> getAttributeAsCollection(final String name) throws NoSuchElementException {
        List<String> values = new ArrayList<String>();
        for (ItemAttribute attribute : attributes) {
            if (attribute.getName().equals(name)) {
                values.add(attribute.getValue());
            }
        }
        return values;
    }

    @Override
    public String[] getAttributeAsStringArray(final String name) {
        Collection<String> values = getAttributeAsCollection(name);
        return values.toArray(new String[values.size()]);
    }

    @Override
    public boolean hasNext() throws ConnectException {
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
    public String next() {
        key = (String) bufferKeys[bufferPosition];
        attributes = buffer.get(key);
        position++;
        bufferPosition++;
        return key;
    }

    @Override
    public void consistentQuery(final String sql) throws ConnectException {
        consistent = true;
        query(sql, null);
    }

    /**
     * Execute a custom query, with eventual consistency
     * 
     * @param sql
     * @throws ConnectException
     */
    @Override
    public void query(final String sql) throws ConnectException {
        consistent = false;
        query(sql, null);
    }

    private void query(final String sql, String nextToken) throws ConnectException {
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
     * List the available domains
     * 
     * @return a list of Strings representing domain names
     * @throws ConnectException
     */
    @Override
    public List<String> listDomains() throws ConnectException {
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
    public void createDomain(final String domainName) throws ConnectException {
        try {
            sdb.createDomain(domainName);
        } catch (SDBException ex) {
            throw new ConnectException(ex.getMessage());
        }
    }

    @Override
    public void deleteDomain(final String domainName) throws ConnectException {
        try {
            sdb.deleteDomain(domainName);
        } catch (SDBException ex) {
            throw new ConnectException(ex.getMessage());
        }
    }

    @Override
    public void deleteByKey(final String key) throws ConnectException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void loadByKey(final String key) throws ConnectException, NoSuchElementException {
        String sql = "SELECT * FROM " + domain + " WHERE itemName() = " + StringHelper.quote(key);
        query(sql);
        if (hasNext()) {
            next();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void setOrderBy(String orderBy) {
        if (orderBy != null) {
            this.orderBy = orderBy;
        } else {
            this.orderBy = "";
        }

    }

    @Override
    public void enableExceptions(boolean enable) {
        enableExceptions = enable;
    }

}
