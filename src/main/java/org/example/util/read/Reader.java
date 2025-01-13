package org.example.util.read;

import org.example.util.exception.ReaderException;

import java.util.List;

public abstract class Reader<T> {
    protected Class<T> clazz;

    public Reader(Class<T> clazz) {
        this.clazz = clazz;
    }

    /***
     * Reads object T from file
     *
     * @param fileName file name
     * @return object T
     * @throws ReaderException if necessary
     */
    public abstract T readFromFile(String fileName) throws ReaderException;

    /***
     * Reads List of objects T from file
     *
     * @param fileName file name
     * @return List of objects T
     * @throws ReaderException if necessary
     */
    public abstract List<T> readListFromFile(String fileName) throws ReaderException;
}
