package org.example.chapter.four.task12.entity.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.chapter.four.task12.service.TariffException;

public class ChildTariff extends RegularTariff {
    private final static Logger LOGGER = LogManager.getLogger();

    public ChildTariff(@JsonProperty("price") double price,
                       @JsonProperty("description") String description,
                       @JsonProperty("minutes") int minutes,
                       @JsonProperty("customers") int customers) {
        super(price, description, minutes, 0, customers);
    }
    @Override
    public void call(String phoneNumber) {
        super.call(phoneNumber);
    }

    @Override
    public void text(String phoneNumber, String message) {
        super.text(phoneNumber, message);
    }

    @Override
    public void internet() throws TariffException {
        LOGGER.error("Mobile internet is not supported");
        throw new TariffException("Mobile internet is not supported");
    }

    @Override
    public String toString() {
        return "{" +
                "price=" + getPrice() +
                ", desc='" + getDescription() + '\'' +
                ", mins=" + getMinutes() +
                ", intLimit=" + "no Internet" +
                ", customers=" + getCustomers() +
                '}';
    }
}
