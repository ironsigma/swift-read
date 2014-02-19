package com.izylab.swift.file;

/**
 * Text File Position.
 */
public final class TextFilePosition implements Position {

    /** Current position. */
    private long currentWord = 0;

    /** Increment the current word position by one. */
    public void increment() {
        currentWord++;
    }

    /**
     * Set the current word position.
     * @param currentWord new position
     */
    public void setCurrentWord(final long currentWord) {
        this.currentWord = currentWord;
    }

    /**
     * Return the current location.
     * @return the current word position
     */
    public long getCurrentWord() {
        return currentWord;
    }

    @Override
    public String toString() {
        return "at word " + currentWord;
    }

}
