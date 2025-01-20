package org.example.chapter.four.task12.entity.impl;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BusinessTariff extends RegularTariff {
    public BusinessTariff(@JsonProperty("price") double price,
                          @JsonProperty("description") String description,
                          @JsonProperty("customers") int customers) {
        super(price, description, -1, -1, customers);
    }

    @Override
    public String toString() {
        return "{" +
                "price=" + getPrice() +
                ", desc='" + getDescription() + '\'' +
                ", mins=" + "Unlimited" +
                ", intLimit=" + "Unlimited" +
                ", customers=" + getCustomers() +
                '}';
    }
}
