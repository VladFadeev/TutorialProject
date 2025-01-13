package org.example.chapter.four.task12.util;

import org.example.chapter.four.task12.entity.Tariff;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TariffUtil {
    private final List<Tariff> tariffs;

    public TariffUtil(List<Tariff> tariffs) {
        this.tariffs = tariffs;
    }

    public TariffUtil() {
        tariffs = new ArrayList<>();
    }

    public List<Tariff> getTariffs() {
        return List.copyOf(tariffs);
    }

    public void addTariff(Tariff tariff) {
        tariffs.add(tariff);
    }

    public List<Tariff> sortByPrice() {
        tariffs.sort(Comparator.comparing(Tariff::getPrice));
        return List.copyOf(tariffs);
    }

    public List<Tariff> sortByCustomers() {
        tariffs.sort(Comparator.comparing(Tariff::getCustomers));
        return List.copyOf(tariffs);
    }

    public List<Tariff> getTariffByParams(int minCustomers, int maxCustomers, double minPrice, double maxPrice, int minMinutes, int maxMinutes, int minGB, int maxGB) {
        return tariffs.stream().filter(tariff -> (tariff.getCustomers() >= minCustomers && tariff.getCustomers() <= maxCustomers)
                && (tariff.getPrice() >= minPrice && tariff.getPrice() <= maxPrice)
                && (tariff.getMinutes() >= minMinutes && tariff.getMinutes() <= maxMinutes)
                && (tariff.getInternetLimitGB() >= minGB && tariff.getInternetLimitGB() <=maxGB)).toList();
    }
}
