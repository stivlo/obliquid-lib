package org.obliquid.helpers;

import static org.junit.Assert.*;

import org.junit.Test;

public class SimpleStopWatchShould {

    /**
     * When we say sleep for 457 milliseconds it actually means sleep for 457 milliseconds or more.
     * Normally, the timing is very precise but could change under different load conditions, so I
     * don't know how to define an upper bound, that could make the test fail, without reasons.
     */
    @Test
    public void computeElapsedMilliSeconds() {
        StopWatch watch = new StopWatch();
        StopWatch.sleepMilliSeconds(457);
        assertTrue(watch.computeElapsedMillisSeconds() >= 457);
    }

    @Test
    public void computeElapasedSeconds() {
        StopWatch watch = new StopWatch();
        StopWatch.sleepSeconds(1);
        assertTrue(watch.computeElapsedSeconds() >= 1);
    }

}
