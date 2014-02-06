package com.izylab.swift.file;

/**
 * File reader interface.
 */
public interface FileReader {
    /**
     * Load a file.
     * @param file File to load
     */
    void loadFile(String file);

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
}
