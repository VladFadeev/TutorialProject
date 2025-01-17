package org.example.chapter.five.task17.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Should be used as Data Transfer Object for class {@code Cinema}.
 * <p>
 * Parameters {@code screenTypes} and {@code screenSpecs} must have
 * the same {@code size} if present.
 * </p>
 * @param name cinema name.
 * @param address cinema address.
 * @param screenTypes types of the screens in the cinema.
 * @param screenSpecs specification of the screens in the cinema.
 * @param films films and their possible sessions to be shown in the cinema.
 */
public record CinemaDTO(
        @JsonProperty("name")
        String name,
        @JsonProperty("address")
        String address,
        @JsonProperty("screenTypes")
        List<String> screenTypes,
        @JsonProperty("screenSpecs")
        List<String> screenSpecs,
        @JsonProperty("films")
        List<FilmDTO> films) {

    @Override
    public String toString() {
        return "CinemaDTO{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", screenTypes=" + screenTypes +
                ", screenSpecs=" + screenSpecs +
                ", films=" + films +
                '}';
    }
}
