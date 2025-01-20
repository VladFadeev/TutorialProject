package org.example.chapter.four.task12.service;

import org.example.chapter.four.task12.entity.Tariff;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class TariffService {
    public static List<Tariff> sortByPrice(List<Tariff> tariffs) {
        tariffs.sort(Comparator.comparing(Tariff::getPrice));
        return List.copyOf(tariffs);
    }

    public static int getCustomersSum(List<Tariff> tariffs) {
        return tariffs.stream().mapToInt(Tariff::getCustomers).sum();
    }

    public static Optional<Tariff> getTariffByParams(List<Tariff> tariffs, int[] prices, int[] minutes, int[] internetLimitGB) {
        return tariffs.stream().filter(tariff ->
                        (tariff.getPrice() >= prices[0] && tariff.getPrice() <= prices[1])
                        && (tariff.getMinutes() >= minutes[0] && tariff.getMinutes() <= minutes[1])
                        && (tariff.getInternetLimitGB() >= internetLimitGB[0] && tariff.getInternetLimitGB() <= internetLimitGB[1]))
                .findFirst();
    }
}
