package org.example.chapter.five.task17.service;

import org.example.chapter.five.task17.dto.CinemaDTO;
import org.example.chapter.five.task17.entity.Cinema;
import org.example.chapter.five.task17.entity.Film;
import org.example.util.create.Creator;
import org.example.util.exception.CreatorException;
import org.example.util.exception.ReaderException;
import org.example.util.read.Reader;
import org.example.util.read.impl.ReaderJSON;

import java.util.HashMap;
import java.util.Map;

public class CinemaCreator extends Creator<Cinema> {

    public CinemaCreator(String fileName) {
        super(fileName);
    }

    @Override
    public Cinema create() throws CreatorException, CinemaException {
        CinemaDTO dto;
        try {
            Reader<CinemaDTO> dtoReader = new ReaderJSON<>(CinemaDTO.class);
            dto = dtoReader.readFromFile(fileName);
            LOGGER.debug("Received Cinema DTO: {}", dto);
        } catch (ReaderException e) {
            LOGGER.error(e);
            throw new CreatorException(e);
        }
        Map<Film, Integer> filmSessions = new HashMap<>();
        dto.films().forEach(filmDTO -> filmSessions.put(filmDTO.film(), filmDTO.sessions()));
        return new Cinema(dto.name(), dto.address(), dto.screenTypes(),
                dto.screenSpecs(), filmSessions);
    }
}
