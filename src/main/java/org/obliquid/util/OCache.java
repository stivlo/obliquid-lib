package org.obliquid.util;

import java.util.concurrent.Future;

/**
 * Obliquid Cache Interface
 * 
 * @author stivlo
 */
public interface OCache {

        /**
         * Set a value in the cache
         * 
         * @param key
         *                a plain text key
         * @param o
         *                the object to be set
         * @return a Future Boolean to check whether or not the operation was performed
         */
        public abstract Future<Boolean> set(String key, final Object o);

        /**
         * Get a value from the cache
         * 
         * @param key
         *                the key to look up
         * @return the Object retrieved from the cache
         */
        public abstract Object get(String key);

        /**
         * Delete an object from the cache.
         * 
         * @param key
         *                the key to be removed
         * @return a Future Boolean to check whether the operation was successful
         */
        public abstract Future<Boolean> delete(String key);

}