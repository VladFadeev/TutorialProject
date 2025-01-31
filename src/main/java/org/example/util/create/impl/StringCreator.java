package org.example.util.create.impl;

import org.example.util.commonDTO.StringDTO;
import org.example.util.create.Creator;
import org.example.util.exception.CreatorException;
import org.example.util.exception.ReaderException;
import org.example.util.read.Reader;
import org.example.util.read.impl.ReaderJSON;

import java.util.List;

public class StringCreator extends Creator<String> {

    public StringCreator(String fileName) {
        super(fileName);
    }

    @Override
    public String create() throws CreatorException {
        String result;
        try {
            Reader<StringDTO> reader = new ReaderJSON<>(StringDTO.class);
            result = reader.readFromFile(fileName).str();
        } catch (ReaderException e) {
            LOGGER.error(e.getMessage());
            throw new CreatorException(e);
        }
        return result;
    }

    @Override
    public List<String> createList() throws CreatorException {
        List<String> result;
        try {
            Reader<StringDTO> reader = new ReaderJSON<>(StringDTO.class);
            result = reader.readListFromFile(fileName).stream().map(StringDTO::str).toList();
        } catch (ReaderException e) {
            LOGGER.error(e.getMessage());
            throw new CreatorException(e);
        }
        return result;
    }
}
