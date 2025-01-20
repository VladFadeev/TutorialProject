package org.example.chapter.four.task12.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.chapter.four.task12.service.TariffException;

public abstract class Tariff {
    private final static Logger LOGGER = LogManager.getLogger();
    private double price;
    private String description;
    private int minutes;
    private int internetLimitGB;
    private int customers;

    protected Tariff(double price, String description,
                     int minutes, int internetLimitGB, int customers) {
        this.price = price;
        this.description = description;
        this.minutes = minutes;
        this.internetLimitGB = internetLimitGB;
        this.customers = customers;
    }

    public void call(String phoneNumber) throws TariffException {
        LOGGER.error("Calling is not supported");
        throw new TariffException("Calling is not supported");
    }

    public void text(String phoneNumber, String message) {
        LOGGER.error("Sending SMS is not supported");
        throw new TariffException("Sending SMS is not supported");
    }

    public void internet() {
        LOGGER.error("Mobile internet is not supported");
        throw new TariffException("Mobile internet is not supported");
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getInternetLimitGB() {
        return internetLimitGB;
    }

    public void setInternetLimitGB(int internetLimitGB) {
        this.internetLimitGB = internetLimitGB;
    }

    public int getCustomers() {
        return customers;
    }

    public void setCustomers(int customers) {
        this.customers = customers;
    }

    @Override
    public String toString() {
        return "{" +
                "price=" + price +
                ", desc='" + description + '\'' +
                ", mins=" + minutes +
                ", intLimit=" + internetLimitGB +
                ", customers=" + customers +
                '}';
    }
}
