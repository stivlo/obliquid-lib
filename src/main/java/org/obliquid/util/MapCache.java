package org.obliquid.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * In memory Map implementation of the OCache interface. Should not be used for
 * production, only for testing when memcache is not available.
 * 
 * @author stivlo
 * 
 */
public class MapCache implements OCache {

        /** Our cache buffer. */
        private final Map<String, Object> store = new HashMap<String, Object>(256);

        @Override
        public final Future<Boolean> set(final String key, final Object object) {
                boolean result = false;
                if (key != null) {
                        try {
                                store.put(key, object);
                                result = true;
                        } catch (NullPointerException ex) {
                                result = false;
                        } catch (IllegalArgumentException ex) {
                                result = false;
                        }
                }
                return new FakeFutureBoolean(result);
        }

        @Override
        public final Object get(final String key) {
                return store.get(key);
        }

        @Override
        public final Future<Boolean> delete(final String key) {
                Object value = store.remove(key);
                return new FakeFutureBoolean(value != null);
        }

}
