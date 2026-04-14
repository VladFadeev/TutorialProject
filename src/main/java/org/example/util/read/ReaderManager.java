package org.example.util.read;

public class ReaderManager {
    private static Reader<?> READER;

    public static Reader<?> getReader() {
        return READER;
    }
}
