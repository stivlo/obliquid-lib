package org.obliquid.util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * A synchronous Future implementations. I need this for MapCache, that is a in-memory Map, so fast that
 * creating a thread would be an unnecessary overhead. Because the computation is already done, the
 * implementation is very simple.
 * 
 * @author stivlo
 * 
 */
public class FakeFutureBoolean implements Future<Boolean> {

        private final boolean computationResult;

        public FakeFutureBoolean(boolean result) {
                this.computationResult = result;
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
                return false; // task can't be cancelled: already completed
        }

        @Override
        public Boolean get() throws InterruptedException, ExecutionException {
                return computationResult;
        }

        @Override
        public Boolean get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException,
                        TimeoutException {
                return computationResult;
        }

        @Override
        public boolean isCancelled() {
                return false; // not cancelled, in fact it can't be cancelled
        }

        @Override
        public boolean isDone() {
                return true; // always already done after construction
        }

}
