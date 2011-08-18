package org.obliquid.util;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

import org.apache.log4j.Logger;
import org.obliquid.config.AppConfig;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.MemcachedClient;

/**
 * MemCached implementation of OCache interface. spy.memcached is declared as
 * thread safe, so we don't have to worry about multiple threads using the same
 * client at the same time. Requires a running memcached server.
 * 
 */
public final class MyMemCache implements OCache {

        /** A prefix to avoid id clashes from different applications. */
        private final String namespace;

        /** An array of MemcachedClient. */
        private MemcachedClient[] m = null;

        /** How many clients to use. */
        private static final int MAX_CONN = 4;

        /** Generates pseudo-random integers. */
        private Random random = new Random();

        /** A logger instance. */
        private static final Logger LOG = Logger.getLogger(MyMemCache.class);

        /**
         * Private constructor creating maxConn clients (each one starts a
         * thread and opens a connection to memcached server).
         * 
         * @param namespaceIn
         *                namespace prefix to avoid conflicts
         * @param memcachedAddressAndPort
         *                on localhost it will be "127.0.0.1:"
         */
        private MyMemCache(final String namespaceIn, final String memcachedAddressAndPort) {
                namespace = namespaceIn;
                m = new MemcachedClient[MAX_CONN];
                for (int i = 0; i <= MAX_CONN - 1; i++) {
                        MemcachedClient c;
                        try {
                                c = new MemcachedClient(new BinaryConnectionFactory(),
                                                AddrUtil.getAddresses(memcachedAddressAndPort));
                        } catch (IOException ex) {
                                throw new RejectedExecutionException(ex);
                        }
                        m[i] = c;
                }
        }

        /**
         * Shut down all the memcached clients created. Call it after use, for
         * instance with a destruction callback from the Spring Framework.
         * http://static.springsource.org/spring/docs/3.0.x/spring-framework-
         * reference/html/beans.html #beans-factory-lifecycle-disposablebean
         */
        public synchronized void shutdown() {
                if (m == null) {
                        LOG.debug(" memcached: nothing to do, it wasn't started.");
                        return;
                }
                LOG.debug("Shutting down memcached");
                for (int i = 0; i <= MAX_CONN - 1; i++) {
                        if (m[i] != null) {
                                m[i].shutdown();
                        }
                }
        }

        @Override
        public Future<Boolean> set(final String key, final Object o) {
                int timeToLiveInSeconds = AppConfig.getInstance().getPropertyAsInt("memcachedTtl");
                Future<Boolean> result = getCache().set(namespace + key, timeToLiveInSeconds, o);
                return result;
        }

        @Override
        public Object get(final String key) {
                Object object = getCache().get(namespace + key);
                if (object == null) {
                        System.out.println("Cache MISS KEY: " + key);
                }
                return object;
        }

        @Override
        public Future<Boolean> delete(final String key) {
                return getCache().delete(namespace + key);
        }

        /**
         * Return a random MemcachedClient from the pool.
         * 
         * @return a MemcachedClient instance
         */
        private MemcachedClient getCache() {
                MemcachedClient c = null;
                int i = random.nextInt(MAX_CONN);
                c = m[i];
                return c;
        }

}
