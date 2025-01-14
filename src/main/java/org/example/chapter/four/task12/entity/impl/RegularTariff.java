package org.example.chapter.four.task12.entity.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.chapter.four.task12.entity.Tariff;

public class RegularTariff extends Tariff {
    private final static Logger LOGGER = LogManager.getLogger();

    public RegularTariff(@JsonProperty("price") double price,
                         @JsonProperty("description") String description,
                         @JsonProperty("minutes") int minutes,
                         @JsonProperty("internetLimitGB") int internetLimitGB,
                         @JsonProperty("customers") int customers) {
        super(price, description, minutes, internetLimitGB, customers);
    }

    @Override
    public void call(String phoneNumber) {
        LOGGER.info("Calling {} using {}", phoneNumber, getClass().getSimpleName());
    }

    @Override
    public void text(String phoneNumber, String message) {
        LOGGER.info("Sending SMS to {} with message {} using {}",
                phoneNumber, message, getClass().getSimpleName());
    }

    @Override
    public void internet() {
        LOGGER.info("Internet is tuned on using {}", getClass().getSimpleName());
    }
}
