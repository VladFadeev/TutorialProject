package org.example.chapter.four.task12.entity;

public class BusinessTariff extends RegularTariff {
    public BusinessTariff(double price, String description, int customers) {
        super(price, description, -1, -1, customers);
    }

    @Override
    public void offInternet() {
        super.offInternet();
    }

    @Override
    public void onInternet() {
        super.onInternet();
    }

    @Override
    public void text(String phoneNumber, String message) {
        super.text(phoneNumber, message);
    }

    @Override
    public void call(String phoneNumber) {
        super.call(phoneNumber);
    }
}
