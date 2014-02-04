package com.izylab.swift.util;

/**
 * Timer Listener Interface.
 */
public interface TimerNotifierListener {
    /** Notify that interval has elapsed. */
    void intervalElapsed();

    /** Notify that timer has stopped. */
    void timerStopped();
}
