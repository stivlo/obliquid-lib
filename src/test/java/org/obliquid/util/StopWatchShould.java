package org.obliquid.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Class under test: StopWatch.
 * 
 * @author stivlo
 * 
 */
public class StopWatchShould {

        /**
         * When we say sleep for 457 milliseconds it actually means sleep for
         * 457 milliseconds or more. Normally, the timing is very precise but
         * could change under different load conditions, so I don't know how to
         * define an upper bound, that could make the test fail, without
         * reasons.
         */
        @Test
        public final void computeElapsedMilliSeconds() {
                final int sleepMs = 457;
                StopWatch watch = new StopWatch();
                StopWatch.sleepMilliSeconds(sleepMs);
                assertTrue(watch.computeElapsedMillisSeconds() >= sleepMs);
        }

        /**
         * Check that the computeElapsedSeconds() method works.
         */
        @Test
        public final void computeElapasedSeconds() {
                StopWatch watch = new StopWatch();
                StopWatch.sleepSeconds(1);
                assertTrue(watch.computeElapsedSeconds() >= 1);
        }

}
