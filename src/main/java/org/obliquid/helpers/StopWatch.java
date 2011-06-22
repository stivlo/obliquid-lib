package org.obliquid.helpers;

/**
 * Simple Stop Watch with seconds and milliseconds timings and sleep functions wrapped to ignore
 * exceptions.
 * 
 * @author stivlo
 */
public class StopWatch {

    private long startTime;

    /**
     * Create a new StopWatch and reset the timer.
     */
    public StopWatch() {
        reset();
    }

    /**
     * Reset the StopWatch to zero milli seconds
     */
    private void reset() {
        startTime = System.currentTimeMillis();
    }

    /**
     * Compute the elapsed time since Object creation or last reset()
     * 
     * @return elapsed time in milli seconds
     */
    public long computeElapsedMillisSeconds() {
        return System.currentTimeMillis() - startTime;
    }

    /**
     * Compute the elapsed time since Object creation or last reset()
     * 
     * @return elapsed time in seconds
     */
    public long computeElapsedSeconds() {
        return (System.currentTimeMillis() - startTime) / 1000;
    }

    /**
     * Sleep for a number of seconds, masking InterruptedException.
     * 
     * @param seconds
     *            number of seconds to sleep
     */
    public static void sleepSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ex) {
            //we don't care about interruptions
        }
    }

    /**
     * Sleep for a number of milli seconds, masking InterruptedException.
     * 
     * @param milliSeconds
     *            number of milli seconds sleep
     */
    public static void sleepMilliSeconds(int milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException ex) {
            //we don't care about interruptions
        }
    }

}
