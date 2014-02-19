package com.izylab.swift.file;

/**
 * File reader interface.
 */
public interface FileReader {
    /**
     * See if more word(s) are available.
     * @return true if data is still available, false otherwise.
     */
    boolean hasNext();

    /**
     * Get the next set of word(s) to display.
     * @return words to display.
     */
    String getNext();

    /**
     * Get the current position in the file.
     * @return the position.
     */
    Position getPosition();

    /**
     * Set the current position.
     * @param position the position to set.
     */
    void setPosition(Position position);

    /**
     * Close the file reader.
     */
    void close();
}
