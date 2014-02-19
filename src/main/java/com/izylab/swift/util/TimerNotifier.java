package com.izylab.swift.util;

import java.util.ArrayList;
import java.util.List;

/**
 * TimerNotification class.
 */
public final class TimerNotifier extends Thread {
    /** Milliseconds in a second. */
    private static final int MILLIS_IN_SECONDS = 1000000;

    /** Nanoseconds in a seconds. */
    private static final long NANOS_IN_SECOND = 1000000000;

    /** Listener list. */
    private List<TimerNotifierListener> listenerList
            = new ArrayList<TimerNotifierListener>();

    /** Last notification time. */
    private long lastNotificationTime = System.nanoTime();

    /** Additional delay. */
    private long delayNext = 0;

    /** Running flag. */
    private boolean running = true;

    /** Optimal notifications per second. */
    private final long optimalNotificationsPerSecond;
    
    /**
     * Create a new timer.
     * @param interval Millisecond interval between notifications
     */
    public TimerNotifier(final int interval) {
        super();
        optimalNotificationsPerSecond = NANOS_IN_SECOND / interval;
    }

    /**
     * Delay next notification.
     * @param millis Additional ms to delay next notification
     */
    public void delayNext(final long millis) {
        this.delayNext = millis;
    }

    /** Stop notifications. */
    public void stopNotifications() {
        this.running = false;
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
                long now = System.nanoTime();
                lastNotificationTime = now;

                for (TimerNotifierListener listener : listenerList) {
                    listener.intervalElapsed();
                }

                Thread.sleep((lastNotificationTime - System.nanoTime()
                        + optimalNotificationsPerSecond) / MILLIS_IN_SECONDS);
                if (delayNext != 0) {
                    long delay = delayNext;
                    delayNext = 0;
                    Thread.sleep(delay);
                }
            }
        } catch (InterruptedException ex) {
            running = false;
        }
        for (TimerNotifierListener listener : listenerList) {
            listener.timerStopped();
        }
    }

}
