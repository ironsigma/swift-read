package com.izylab.swift.util;

import java.util.List;

/**
 * Line Tokenizer.
 */
public final class Tokenizer {
    /** Non-instantiable utility class. */
    private Tokenizer() {
        /* empty */
    }

    /**
     * Is white space char.
     * @param ch Character to test
     * @return true if char is white space, false otherwise
     */
    private static boolean isWhitespace(final char ch) {
        return ch == ' ' || ch == '\t';
    }

    /**
     * Tokenize a line into a list.
     * @param tokenList List to insert items
     * @param line Line to tokenize
     */
    public static void tokenize(final List<String> tokenList,
            final String line) {

        int index;
        char ch;

        // clear the list
        tokenList.clear();

        // find first non-white character
        int start = 0;
        for (index = 0; index < line.length(); index++) {
            ch = line.charAt(index);
            if (!isWhitespace(ch)) {
                start = index;
                break;
            }
        }

        // find last non-white character
        int end = 0;
        for (index = line.length() - 1; index > start; index--) {
            ch = line.charAt(index);
            if (!isWhitespace(ch)) {
                end = index;
                break;
            }
        }

        // tokenize
        boolean foundWhiteSpace = false;
        int prevIndex = start;
        for (index = start; index < end; index++) {
            ch = line.charAt(index);
            if (isWhitespace(ch)) {
                // if there text behind the index add token
                if (prevIndex != index) {
                    tokenList.add(line.substring(prevIndex, index));
                }
                // in white space keep increasing prev index
                prevIndex = index + 1;
                foundWhiteSpace = true;
                continue;
            }
            if (foundWhiteSpace) {
                if (prevIndex == index) {
                    foundWhiteSpace = false;
                    continue;
                }
                tokenList.add(line.substring(prevIndex, index));
                foundWhiteSpace = false;
            }
        }

        // if something left over, capture it
        if (prevIndex != index) {
            tokenList.add(line.substring(prevIndex, index + 1));
        }

        String token;
        String[] split;
        int insertIdx;
        for (int i = tokenList.size() - 1; i >= 0; i--) {
            token = tokenList.get(i);
            if (token.contains("--")) {
                insertIdx = i;
                tokenList.remove(i);
                while (!token.equals("--") && token.contains("--")) {
                    split = token.split("--", 2);
                    token = split[1];
                    tokenList.add(insertIdx, split[0]);
                    tokenList.add(insertIdx + 1, "--");
                    insertIdx += 2;
                }
                tokenList.add(insertIdx, token);
            }
        }
    }
}
