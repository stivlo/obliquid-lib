package org.obliquid.util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * A synchronous Future implementations. I need this for MapCache, that is a
 * in-memory Map, so fast that creating a thread would be an unnecessary
 * overhead. Because the computation is already done, the implementation is very
 * simple.
 * 
 * @author stivlo
 * 
 */
public class FakeFutureBoolean implements Future<Boolean> {

        /**
         * Result of the computation that is known at instantiation time.
         */
        private final boolean computationResult;

        /**
         * Build a new immutatable FakeFutureBoolean with the result already
         * known.
         * 
         * @param result
         *                the result already known
         */
        public FakeFutureBoolean(final boolean result) {
                this.computationResult = result;
        }

        @Override
        public final boolean cancel(final boolean mayInterruptIfRunning) {
                return false; // task can't be cancelled: already completed
        }

        @Override
        public final Boolean get() throws InterruptedException, ExecutionException {
                return computationResult;
        }

        @Override
        public final Boolean get(final long timeout, final TimeUnit unit) throws InterruptedException,
                        ExecutionException, TimeoutException {
                return computationResult;
        }

        @Override
        public final boolean isCancelled() {
                return false; // not cancelled, in fact it can't be cancelled
        }

        @Override
        public final boolean isDone() {
                return true; // always already done after construction
        }

}
