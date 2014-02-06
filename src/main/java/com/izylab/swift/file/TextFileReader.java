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

    /** Data. */
    private List<String> data = new ArrayList<String>();

    /** Iterator index. */
    private int index = 0;

    @Override
    public void loadFile(final String file) {
        scanner = new Scanner(getClass().getResourceAsStream(file));
    }

    /** Get additional data from the scanner. */
    private void fetchData() {
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
        if (data.size() == 0 || index == data.size() - 1) {
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
        return data.get(index - 1);
    }

}
