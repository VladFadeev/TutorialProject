package org.example.util.read.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.util.exception.ReaderException;
import org.example.util.read.Reader;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ReaderJSON<T> extends Reader<T> {
    public ReaderJSON(Class<T> clazz) {
        super(clazz);
    }

    @Override
    public T readFromFile(String fileName) throws ReaderException {
        T result;
        try {
            ObjectMapper mapper = new ObjectMapper();
            result = mapper.readValue(new File(fileName), clazz);
        } catch (IOException e) {
            throw new ReaderException("Couldn't read object from JSON file: " + fileName, e);
        }
        return result;
    }

    @Override
    public List<T> readListFromFile(String fileName) throws ReaderException {
        List<T> result;
        try {
            ObjectMapper mapper = new ObjectMapper();
            result = mapper.readValue(new File(fileName),
                    mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            throw new ReaderException("Couldn't read list from JSON file: " + fileName, e);
        }
        return result;
    }
}
