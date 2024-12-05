package org.example.chapter.four.task12.entity;

public abstract class Tariff {
    private double price;
    private String description;
    private int minutes;
    private int internetLimitGB;
    private int customers;

    protected Tariff(double price, String description, int minutes, int internetLimitGB , int customers) {
        this.price = price;
        this.description = description;
        this.minutes = minutes;
        this.internetLimitGB = internetLimitGB;
        this.customers = customers;
    }

    public void call(String phoneNumber) {
        System.out.println("Call " + phoneNumber + " using " + getClass().getSimpleName());
    }

    public void text(String phoneNumber, String message) {
        System.out.println("SMS " + phoneNumber + " with message " + message + " using " + getClass().getSimpleName());
    }

    public void onInternet() {
        System.out.println("Internet is tuned on using " + getClass().getSimpleName());
    }

    public void offInternet() {
        System.out.println("Internet is tuned off using " + getClass().getSimpleName());
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
        return this.getClass().getSimpleName() + "{" +
                "price=" + price +
                ", description='" + description + '\'' +
                ", minutes=" + minutes +
                ", internetLimitGB=" + internetLimitGB +
                ", customers=" + customers +
                '}';
    }
}
