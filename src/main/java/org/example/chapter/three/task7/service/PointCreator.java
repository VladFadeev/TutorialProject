package org.example.chapter.three.task7.service;

import org.example.chapter.three.task7.entity.Point;
import org.example.util.create.Creator;
import org.example.util.exception.CreatorException;
import org.example.util.exception.ReaderException;
import org.example.util.read.Reader;
import org.example.util.read.impl.ReaderJSON;

import java.util.List;

public class PointCreator extends Creator<Point> {

    public PointCreator(String fileName) {
        super(fileName);
    }

    @Override
    public List<Point> createList() throws CreatorException {
        List<Point> result;
        try {
            Reader<Point> pointReader = new ReaderJSON<>(Point.class);
            result = pointReader.readListFromFile(fileName);
        } catch (ReaderException e) {
            LOGGER.error(e);
            throw new CreatorException(e);
        }
        return result;
    }
}
