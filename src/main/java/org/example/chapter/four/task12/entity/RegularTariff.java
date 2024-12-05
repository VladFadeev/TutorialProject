package org.example.chapter.four.task12.entity;

public class RegularTariff extends Tariff {
    public RegularTariff(double price, String description, int minutes, int internetLimitGB, int customers) {
        super(price, description, minutes, internetLimitGB, customers);
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
    public void onInternet() {
        super.onInternet();
    }

    @Override
    public void offInternet() {
        super.offInternet();
    }

}
