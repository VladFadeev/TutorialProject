package org.example.chapter.five.task17.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.chapter.five.task17.entity.Film;

/**
 * Should be used as Data Transfer Object for class {@code Film}.
 *
 * @param film information about film.
 * @param sessions number of sessions to add.
 */
public record FilmDTO(
        @JsonProperty("film")
        Film film,
        @JsonProperty("sessions")
        int sessions) {

    @Override
    public String toString() {
        return "FilmDTO{" +
                "film=" + film +
                ", sessions=" + sessions +
                '}';
    }
}
