package org.example;

import org.example.chapter.five.task17.entity.Cinema;
import org.example.chapter.four.task12.entity.BusinessTariff;
import org.example.chapter.four.task12.entity.ChildTariff;
import org.example.chapter.four.task12.entity.RegularTariff;
import org.example.chapter.four.task12.entity.Tariff;
import org.example.chapter.four.task12.util.TariffUtil;
import org.example.chapter.three.task7.entity.Point;
import org.example.chapter.three.task7.entity.RationalFraction;
import org.example.chapter.three.task7.util.PointUtil;

import java.util.Arrays;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        chapter3Task7();
        System.out.println("-".repeat(40));
        chapter4Task12();
        System.out.println("-".repeat(40));
        chapter5Task17();
    }

    private static void chapter3Task7() {
        Point p1 = new Point(new RationalFraction(1, 1), new RationalFraction(1, 1), new RationalFraction(1, 1));
        Point p2 = new Point(new RationalFraction(2, 1), new RationalFraction(3, 1), new RationalFraction(4, 1));
        Point p3 = new Point(new RationalFraction(10_001, 1), new RationalFraction(20_001, 1), new RationalFraction(30_001, 1));
        System.out.println("Are " + p1 + ", " + p2 + ", " + p3 + " " + "on the same line: " + PointUtil.isPointOnLine(p1, p2, p3));
    }

    private static void chapter4Task12() {
        TariffUtil tariffUtil = new TariffUtil();
        tariffUtil.addTariff(new RegularTariff(13, RegularTariff.class.getSimpleName(), 130, 13, 23));
        tariffUtil.addTariff(new RegularTariff(10, RegularTariff.class.getSimpleName(), 100, 10, 41));
        tariffUtil.addTariff(new RegularTariff(15, RegularTariff.class.getSimpleName(), 150, 15, 6));
        tariffUtil.addTariff(new RegularTariff(20, RegularTariff.class.getSimpleName(), 200, 20, 100));
        tariffUtil.addTariff(new RegularTariff(24, RegularTariff.class.getSimpleName(), 240, 24, 35));
        tariffUtil.addTariff(new ChildTariff(7, ChildTariff.class.getSimpleName(), 100, 15));
        tariffUtil.addTariff(new BusinessTariff(40, BusinessTariff.class.getSimpleName(), 20));

        List<Tariff> sorted = tariffUtil.sortByPrice();
        System.out.println("Tariffs sorted by price: " + sorted);

        List<Tariff> filtered = tariffUtil.getTariffByParams(20, 60, 15, 25, -1, 1000, -1, 200);
        System.out.println("Tariffs filtered by params: " + filtered);
    }

    private static void chapter5Task17() {
        Cinema cinema = new Cinema("Kiev", "Kievskiy skver 1",
                Arrays.asList("Big", "Small", "VIP"),
                Arrays.asList("SUPER BIG", "very small", "VIP?"),
                Arrays.asList(new Cinema.Film("CJocker", 120), new Cinema.Film("ATransformers", 103), new Cinema.Film("BLook Back", 57)));
        System.out.println("Schedule before sort:\n" + cinema.getFilmSessions());
        cinema.sortSchedule();
        System.out.println("Schedule after sort:\n" + cinema.getFilmSessions());
    }
}