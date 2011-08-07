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
 * MemCached implementation of OCache interface. spy.memcached is declared as thread safe, so we don't
 * have to worry about multiple threads using the same client at the same time. Requires a running
 * memcached server.
 * 
 */
public class MyMemCache implements OCache {

        private final String namespace;
        private MemcachedClient[] m = null;
        private final int maxConn = 4;
        private Random random = new Random();
        private Logger log = Logger.getLogger(MyMemCache.class);

        /**
         * Private constructor creating maxConn clients (each one starts a thread and opens a connection
         * to memcached server)
         * 
         * @param namespace
         *                namespace prefix to avoid conflicts
         * @param memcachedAddressAndPort
         *                on localhost it will be "127.0.0.1:"
         */
        private MyMemCache(String namespace, String memcachedAddressAndPort) {
                this.namespace = namespace;
                m = new MemcachedClient[maxConn];
                for (int i = 0; i <= maxConn - 1; i++) {
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
         * Shut down all the memcached clients created. Call it after use, for instance with a
         * destruction callback from the Spring Framework.
         * http://static.springsource.org/spring/docs/3.0.x/spring-framework-reference/html/beans.html
         * #beans-factory-lifecycle-disposablebean
         */
        public synchronized void shutdown() {
                if (m == null) {
                        log.debug(" memcached: nothing to do, it wasn't started.");
                        return;
                }
                log.debug("Shutting down memcached");
                for (int i = 0; i <= maxConn - 1; i++) {
                        if (m[i] != null) {
                                m[i].shutdown();
                        }
                }
        }

        @Override
        public Future<Boolean> set(String key, final Object o) {
                int timeToLiveInSeconds = AppConfig.getInstance().getPropertyAsInt("memcachedTtl");
                Future<Boolean> result = getCache().set(namespace + key, timeToLiveInSeconds, o);
                return result;
        }

        @Override
        public Object get(String key) {
                Object object = getCache().get(namespace + key);
                if (object == null) {
                        System.out.println("Cache MISS KEY: " + key);
                }
                return object;
        }

        @Override
        public Future<Boolean> delete(String key) {
                return getCache().delete(namespace + key);
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
