package org.obliquid.datatype;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Check Username class.
 * 
 * @author stivlo
 * 
 */
public class UsernameShould {

        /** The class under test. */
        private Username user = new Username();

        /** Store and retrieve a valid username. */
        @Test
        public final void storeAValidUsername() {
                user.set("valid");
                assertEquals("valid", user.getData());
                assertEquals("", user.getMessage());
        }

}
