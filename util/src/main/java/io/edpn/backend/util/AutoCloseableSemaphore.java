package io.edpn.backend.util;

import java.util.concurrent.Semaphore;

public class AutoCloseableSemaphore implements AutoCloseable {
    private final Semaphore semaphore;

    public AutoCloseableSemaphore(int permits) {
        this.semaphore = new Semaphore(permits);
    }

    public void acquire() throws InterruptedException {
        semaphore.acquire();
    }

    public void release() {
        semaphore.release();
    }

    @Override
    public void close() {
        semaphore.release();
    }
}
