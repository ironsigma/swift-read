package com.izylab.swift.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

/**
 * TimerNotification class.
 */
public final class TimerNotifier implements Runnable {
    /** Logger. */
    private static final Logger LOG = Logger.getLogger(TimerNotifier.class);

    /** Milliseconds in a second. */
    private static final int MILLIS_IN_SECONDS = 1000000;

    /** Nanoseconds in a seconds. */
    private static final long NANOS_IN_SECOND = 1000000000;

    /** Seconds in a minute. */
    private static final int SECONDS_IN_MINUTE = 60;

    /** One second. */
    private static final long DEFAULT_NOTIFICATIONS_PER_SECOND = 1;

    /** Listener list. */
    private List<TimerNotifierListener> listenerList
            = new ArrayList<TimerNotifierListener>();

    /** Last notification time. */
    private long lastNotificationTime = System.nanoTime();

    /** Additional delay. */
    private long delayNext = 0;

    /** Running flag. */
    private boolean running = false;

    /** Thread. */
    private Thread thread;

    /** Lock for start/stop. */
    private Lock lock = new ReentrantLock();

    /** Condition for start/stop. */
    private Condition notSuspended = lock.newCondition();

    /** Notify flag. */
    private boolean suspend = false;

    /** Optimal notifications per second. */
    private long optimalNotificationsPerSecond
            = NANOS_IN_SECOND / DEFAULT_NOTIFICATIONS_PER_SECOND;

    /**
     * Set notification interval.
     * @param interval number of notifications per minute
     */
    public void setInterval(final int interval) {
        optimalNotificationsPerSecond = NANOS_IN_SECOND
                / (interval / SECONDS_IN_MINUTE);
    }

    /**
     * Delay next notification.
     * @param millis Additional ms to delay next notification
     */
    public void delayNext(final long millis) {
        this.delayNext = millis;
    }

    /**
     * Start timer.
     */
    public void start() {
        if (!running) {
            suspend = false;
            running = true;

            thread = new Thread(this);
            thread.start();
        }

        if (suspend) {
            suspend = false;
            lock.lock();
            try {
                notSuspended.signal();
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * Shutdown the timer.
     */
    public void shutdown() {
        running = false;
        if (suspend) {
            suspend = false;
            lock.lock();
            try {
                notSuspended.signal();
            } finally {
                lock.unlock();
            }
        }
        try {
            if (thread != null) {
                thread.join();
            }
        } catch (InterruptedException e) {
            LOG.warn("Interrupted before rejoining timer thread.");
        } finally {
            thread = null;
        }
    }

    /**
     * Stop timer.
     */
    public void stop() {
        suspend = true;
    }

    /**
     * Is timer suspended.
     * @return true if suspended, false otherwise.
     */
    public boolean isRunning() {
        return running && !suspend;
    }

    /**
     * Add a timer listener.
     * @param listener Listener.
     */
    public void addListener(final TimerNotifierListener listener) {
        listenerList.add(listener);
    }

    @Override
    public void run() {
        try {
            while (running) {
                // keep track of how much time it takes to notify
                lastNotificationTime = System.nanoTime();

                // notify listeners
                for (TimerNotifierListener listener : listenerList) {
                    listener.intervalElapsed();
                }

                // sleep until next notification
                Thread.sleep((lastNotificationTime - System.nanoTime()
                        + optimalNotificationsPerSecond) / MILLIS_IN_SECONDS);

                // do additional delay
                synchronized (this) {
                    if (delayNext != 0) {
                        long delay = delayNext;
                        delayNext = 0;
                        Thread.sleep(delay);
                    }
                }

                    // check suspend flag
                lock.lock();
                try {
                    while (suspend) {
                        notSuspended.await();
                    }
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException ex) {
            LOG.warn("Timer notifications interrupted");
            running = false;
        }
        for (TimerNotifierListener listener : listenerList) {
            listener.timerStopped();
        }
    }

}
