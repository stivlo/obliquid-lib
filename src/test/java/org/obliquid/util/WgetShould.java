package org.obliquid.util;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.RejectedExecutionException;

import org.junit.Test;

/**
 * Test of Wget class.
 * 
 * @author stivlo
 * 
 */
public class WgetShould {

        /**
         * Simple URL fetch test.
         */
        @Test
        public final void fetchAnUrl() {
                final int resultLength = 1406;
                String result = Wget.fetchUrl("http://www.stefanolocati.it/favicon.ico");
                assertEquals(resultLength, result.length());
        }

        /**
         * Throw IllegalArgumentException when the URL isn't valid.
         */
        @Test(expected = IllegalArgumentException.class)
        public final void throwAnExceptionWhenTheUrlIsNotValid() {
                Wget.fetchUrl("ehm");
        }

        /**
         * Throw an exception when the file wasn't found.
         */
        @Test(expected = RejectedExecutionException.class)
        public final void throwAnExceptionWhenTheFileWasntFound() {
                String res = Wget.fetchUrl("http://www.stefanolocati.it/missingPage");
                System.out.println("res = " + res);
        }

}
