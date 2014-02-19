package com.izylab.swift.file;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.izylab.swift.util.Tokenizer;

/**
 * Text file reader.
 */
public final class TextFileReader implements FileReader {
    /** File scanner. */
    private Scanner scanner;

    /** File name. */
    private String fileName;

    /** Reader position. */
    private TextFilePosition position = new TextFilePosition();

    /** Data. */
    private List<String> data = new ArrayList<String>();

    /** Iterator index. */
    private int index = 0;

    /**
     * Constructor.
     * @param file File to load
     */
    public TextFileReader(final String file) {
        fileName = file;
    }

    /** Get additional data from the scanner. */
    private void fetchData() {
        if (scanner == null) {
            scanner = new Scanner(getClass().getResourceAsStream(fileName));
            position.setCurrentWord(0L);
        }
        if (!scanner.hasNextLine()) {
            data.clear();
        }
        while (scanner.hasNextLine()) {
            Tokenizer.tokenize(data, scanner.nextLine());
            if (data.size() != 0) {
                break;
            }
        }
        index = 0;
    }

    @Override
    public boolean hasNext() {
        if (data.size() == 0 || index == data.size()) {
            fetchData();
        }
        return data.size() != 0;
    }

    @Override
    public String getNext() {
        if (index + 1 > data.size()) {
            fetchData();
        }
        index++;
        position.increment();
        return data.get(index - 1);
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void close() {
        if (scanner != null) {
            scanner.close();
        }
        position.setCurrentWord(0L);
    }

    @Override
    public void setPosition(final Position position) {
        close();
        while (hasNext()
                && ((TextFilePosition) position).getCurrentWord() > this.position.getCurrentWord()) {
            getNext();
        }
    }

}
