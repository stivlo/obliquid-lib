package org.obliquid.helpers;

import java.lang.reflect.Field;

/**
 * Utility methods to help writing unit tests.
 * 
 * @author stivlo
 * 
 */
public class TestHelper {

        /**
         * Set a value in a field of the object bean, even if it's private.
         * 
         * @param bean
         *                the object where to set the field
         * @param fieldName
         *                name of the field
         * @param value
         *                value to put into the field
         * @throws IllegalArgumentException
         *                 in case the argument type is not correct
         * @throws IllegalAccessException
         *                 if access to the field is denied
         * @throws SecurityException
         *                 in case the security manager doesn't allow the
         *                 request
         * @throws NoSuchFieldException
         *                 in case the field couldn't be found
         */
        public static void setValue(Object bean, String fieldName, Object value)
                        throws IllegalArgumentException, IllegalAccessException,
                        SecurityException, NoSuchFieldException {
                Field privateVar = bean.getClass().getDeclaredField(fieldName);
                privateVar.setAccessible(true);
                privateVar.set(bean, value);
        }

}
