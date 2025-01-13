package org.example.chapter.three.task7.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.chapter.three.task7.entity.Point;
import org.example.util.create.Creator;
import org.example.util.exception.CreatorException;
import org.example.util.exception.ReaderException;
import org.example.util.read.Reader;
import org.example.util.read.impl.ReaderJSON;

import java.util.List;

public class PointCreator extends Creator<Point> {
    private final static Logger LOGGER = LogManager.getLogger();

    public PointCreator(String fileName) {
        super(fileName);
    }

    @Override
    public Point create() throws CreatorException {
        Point result;
        try {
            Reader<Point> pointReader = new ReaderJSON<>(Point.class);
            result = pointReader.readFromFile(fileName);
        } catch (ReaderException e) {
            LOGGER.error(e);
            throw new CreatorException(e);
        }
        return result;
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
