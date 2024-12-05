package org.example.chapter.four.task12.entity;

public class ChildTariff extends RegularTariff {
    public ChildTariff(double price, String description, int minutes, int customers) {
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
    public void onInternet() {
        System.out.println("Internet can't be turned on because this is " +  getClass().getSimpleName());
    }

    @Override
    public void offInternet() {
        super.offInternet();
    }
}
