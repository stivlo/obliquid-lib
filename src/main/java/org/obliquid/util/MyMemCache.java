package org.obliquid.util;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

import org.obliquid.config.AppConfig;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.MemcachedClient;

/**
 * Creates a connection pool of MemCached clients. Based on
 * http://sacharya.com/using-memcached-with-java/ spy.memcached is declared as thread safe, so we
 * don't have to worry about multiple threads using the same client at the same time.
 * 
 * Modified by stivlo
 */
public class MyMemCache {

    private static final String NAMESPACE = "sherd";
    private static MyMemCache instance = null;
    private static MemcachedClient[] m = null;
    private static final int maxConn = 2;
    private static Random random = new Random();

    /**
     * Private constructor creating maxConn clients (each one starts a thread and opens a connection
     * to memcached server)
     */
    private MyMemCache() {
        init();
    }

    private static void init() {
        m = new MemcachedClient[maxConn];
        for (int i = 0; i <= maxConn - 1; i++) {
            MemcachedClient c;
            try {
                c = new MemcachedClient(new BinaryConnectionFactory(),
                        AddrUtil.getAddresses("127.0.0.1:11211"));
            } catch (IOException ex) {
                throw new RejectedExecutionException(ex);
            }
            m[i] = c;
        }
    }

    /**
     * Shut down all the memcached clients created
     */
    public static synchronized void shutDown() {
        if (m == null) {
            System.out.println(" nothing to do, it wasn't started.");
            return;
        }
        for (int i = 0; i <= maxConn - 1; i++) {
            if (m[i] != null) {
                m[i].shutdown();
            }
        }
    }

    /**
     * Get an instance of the singleton
     * 
     * @return a random instance from the pool
     */
    public static synchronized MyMemCache getInstance() {
        if (instance == null) {
            System.out.println("Creating a new instance");
            instance = new MyMemCache();
        }
        return instance;
    }

    /**
     * Set a value in the cache
     * 
     * @param key
     *            a plain text key
     * @param o
     *            the object to be set
     * @return a Future Boolean to check whether the operation was successful
     */
    public Future<Boolean> set(String key, final Object o) {
        int memcachedTtl = AppConfig.getInstance().getPropertyAsInt("memcachedTtl"); //time to live in seconds
        Future<Boolean> result = getCache().set(NAMESPACE + key, memcachedTtl, o);
        return result;
    }

    /**
     * Get a value from the cache
     * 
     * @param key
     *            the key to look up
     * @return the Object retrieved from the cache
     */
    public Object get(String key) {
        Object object = getCache().get(NAMESPACE + key);
        if (object == null) {
            System.out.println("Cache MISS KEY: " + key);
        }
        return object;
    }

    /**
     * Delete an object from the cache.
     * 
     * @param key
     *            the key to be removed
     * @return a Future Boolean to check whether the operation was successful
     */
    public Future<Boolean> delete(String key) {
        return getCache().delete(NAMESPACE + key);
    }

    /**
     * Return a random MemcachedClient from the pool
     * 
     * @return a MemcachedClient instance
     */
    private MemcachedClient getCache() {
        MemcachedClient c = null;
        int i = random.nextInt(maxConn);
        c = m[i];
        return c;
    }
}
