package org.example.util.read.impl;

import org.example.util.exception.ReaderException;
import org.example.util.read.Reader;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CLIReader<T> extends Reader<T> {
    private Scanner scanner = new Scanner(System.in);
    public CLIReader(Class<T> clazz) {
        super(clazz);
    }

    @Override
    public T readFromFile(String fileName) throws ReaderException {
        throw new ReaderException("Not implemented");
    }

    @Override
    public List<T> readListFromFile(String fileName) throws ReaderException {
        throw new ReaderException("Not Implemented");
    }

    @Override
    public int readArray(int[] arr) throws ReaderException {
        try {
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                int[] res = Arrays.stream(line.trim().split("[ ,]")).mapToInt(Integer::parseInt).toArray();
                System.arraycopy(res, 0, arr, 0, res.length);
                return arr.length;
            } else {
                throw new ReaderException("Invalid input");
            }
        } catch (Exception e) {
            throw new ReaderException(e);
        }
    }

    @Override
    public int readInt() throws ReaderException {
        try {
            if (scanner.hasNextLine()) {
                return Integer.parseInt(scanner.nextLine());
            } else  {
                throw new ReaderException("Invalid input");
            }
        } catch (Exception e) {
            throw new ReaderException(e);
        }
    }

    @Override
    public void close() throws ReaderException {
        scanner.close();
    }
}
