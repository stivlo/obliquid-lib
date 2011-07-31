package org.obliquid.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * In memory Map implementation of the OCache interface
 * 
 * @author stivlo
 * 
 */
public class MapCache implements OCache {

        private final Map<String, Object> store = new HashMap<String, Object>(256);

        @Override
        public Future<Boolean> set(String key, Object object) {
                boolean result;
                try {
                        store.put(key, object);
                        result = true;
                } catch (NullPointerException ex) {
                        result = false;
                } catch (IllegalArgumentException ex) {
                        result = false;
                }
                return new FakeFutureBoolean(result);
        }

        @Override
        public Object get(String key) {
                return store.get(key);
        }

        @Override
        public Future<Boolean> delete(String key) {
                Object value = store.remove(key);
                return new FakeFutureBoolean(value != null);
        }

}
