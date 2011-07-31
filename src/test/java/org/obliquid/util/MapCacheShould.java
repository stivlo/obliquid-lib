package org.obliquid.util;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Test;

public class MapCacheShould {

        MapCache cache = new MapCache();
        
        @Test
        public void returnNullForNonExistingKey() {
                assertNull(cache.get("anykey"));
        }
        
        @Test
        public void deleteNonExistentKeyReturnsFalse() throws InterruptedException, ExecutionException {
                Future<Boolean> result = cache.delete("akey");
                assertFalse(result.get());
        }
        
        @Test
        public void beAbleToSetAndGetAndDeleteObjects() throws InterruptedException, ExecutionException {
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
        
        @Test
        public void returnFalseForNullKey() throws InterruptedException, ExecutionException {
                Future<Boolean> result = cache.set(null, "hello");
                assertFalse(result.get());
        }
        
}
