package org.obliquid.util;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Test;

/**
 * Class under test MapCache.
 * 
 * @author stivlo
 * 
 */
public class MapCacheShould {

        /**
         * A MapCache instance.
         */
        private MapCache cache = new MapCache();

        /**
         * When asking a non existing key, null will be returned.
         */
        @Test
        public final void returnNullForNonExistingKey() {
                assertNull(cache.get("anykey"));
        }

        /**
         * Trying to delete a key that doesn't exists returns false.
         * 
         * @throws InterruptedException
         *                 Thrown when a thread is waiting, sleeping, or
         *                 otherwise occupied, and the thread is interrupted,
         *                 either before or during the activity.
         * @throws ExecutionException
         *                 Exception thrown when attempting to retrieve the
         *                 result of a task that aborted by throwing an
         *                 exception.
         */
        @Test
        public final void deleteNonExistentKeyReturnsFalse() throws ExecutionException, InterruptedException {
                Future<Boolean> result = cache.delete("akey");
                assertFalse(result.get());
        }

        /**
         * Setting and Getting and Deleting Objects.
         * 
         * @throws InterruptedException
         *                 Thrown when a thread is waiting, sleeping, or
         *                 otherwise occupied, and the thread is interrupted,
         *                 either before or during the activity.
         * @throws ExecutionException
         *                 Exception thrown when attempting to retrieve the
         *                 result of a task that aborted by throwing an
         *                 exception.
         */
        @Test
        public final void beAbleToSetAndGetAndDeleteObjects() throws InterruptedException,
                        ExecutionException {
                String key = "key";
                String testObject = "Hello, this is a test object";
                Future<Boolean> result = cache.set(key, testObject);
                assertTrue(result.get());
                assertEquals(testObject, cache.get(key));
                result = cache.delete(key);
                assertTrue(result.get());
                result = cache.delete(key);
                assertFalse(result.get());
        }

        /**
         * Trying to set with a null key returns false.
         * 
         * @throws InterruptedException
         *                 Thrown when a thread is waiting, sleeping, or
         *                 otherwise occupied, and the thread is interrupted,
         *                 either before or during the activity.
         * @throws ExecutionException
         *                 Exception thrown when attempting to retrieve the
         *                 result of a task that aborted by throwing an
         *                 exception.
         */
        @Test
        public final void returnFalseForNullKey() throws InterruptedException, ExecutionException {
                Future<Boolean> result = cache.set(null, "hello");
                assertFalse(result.get());
        }

}
