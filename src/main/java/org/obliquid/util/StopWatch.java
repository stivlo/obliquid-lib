package org.obliquid.util;

/**
 * Simple Stop Watch with seconds and milliseconds timings and sleep functions
 * wrapped to ignore exceptions.
 * 
 * @author stivlo
 */
public class StopWatch {

        /**
         * Start time for the stop watch.
         */
        private long startTime;

        /** How many milliseconds in a second (1000). */
        private static final int MS_IN_A_SEC = 1000;

        /**
         * Create a new StopWatch and reset the timer.
         */
        public StopWatch() {
                reset();
        }

        /**
         * Reset the StopWatch to zero milliseconds.
         */
        private void reset() {
                startTime = System.currentTimeMillis();
        }

        /**
         * Compute the elapsed time since Object creation or last reset().
         * 
         * @return elapsed time in milliseconds
         */
        public final long computeElapsedMillisSeconds() {
                return System.currentTimeMillis() - startTime;
        }

        /**
         * Compute the elapsed time since Object creation or last reset().
         * 
         * @return elapsed time in seconds
         */
        public final long computeElapsedSeconds() {
                return (System.currentTimeMillis() - startTime) / MS_IN_A_SEC;
        }

        /**
         * Sleep for a number of seconds, masking InterruptedException.
         * 
         * @param seconds
         *                number of seconds to sleep
         */
        public static final void sleepSeconds(final int seconds) {
                // CHECKSTYLE:OFF
                try {
                        Thread.sleep(seconds * MS_IN_A_SEC);
                } catch (InterruptedException ex) {
                        //we don't care about interruptions
                }
                // CHECKSTYLE:ON
        }

        /**
         * Sleep for a number of milliseconds, masking InterruptedException.
         * 
         * @param milliSeconds
         *                number of milliseconds sleep
         */
        public static final void sleepMilliSeconds(final int milliSeconds) {
                // CHECKSTYLE:OFF
                try {
                        Thread.sleep(milliSeconds);
                } catch (InterruptedException ex) {
                        //we don't care about interruptions
                }
                // CHECKSTYLE:ON
        }

}
