package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.chapter.five.task17.entity.Cinema;
import org.example.chapter.five.task17.service.CinemaCreator;
import org.example.chapter.four.task12.entity.BusinessTariff;
import org.example.chapter.four.task12.entity.ChildTariff;
import org.example.chapter.four.task12.entity.RegularTariff;
import org.example.chapter.four.task12.entity.Tariff;
import org.example.chapter.four.task12.service.TariffService;
import org.example.chapter.three.task7.entity.Point;
import org.example.chapter.three.task7.entity.RationalFraction;
import org.example.chapter.three.task7.service.PointCreator;
import org.example.chapter.three.task7.service.PointService;
import org.example.util.create.Creator;
import org.example.util.exception.CreatorException;
import org.example.util.print.Printer;
import org.example.util.print.impl.CLIPrinter;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static Printer printer;
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        LOGGER.info("Application started");
        printer = applyApplicationParams(args);
        printer.printHelloSection();
        chapter3Task7();
        //chapter4Task12();
        //chapter5Task17();
        printer.printEndSection();
        LOGGER.info("Application ended");
    }

    private final static String[] CINEMA_NAMES = {"Kiev", "October", "SilverScreen Galileo",
            "SilverScreen Dana Mall", "Mir", "Central"};
    private final static String[] CINEMA_ADDRESSES = {"Kievskiy skver 1", "Independence avenue 55", "Bobruiskaya 3",
            "Independence avenue 104", "Masherova avenue 5", "Independence avenue 2"};

    private static void chapter3Task7() {
        LOGGER.info("Chapter 3 Task 7 Started");
        printer.printLineSeparator();
        printer.printCenter("Chapter 3, Task 7");
        Creator<Point> pointCreator =
                new PointCreator("src/main/resources/chapter/three/task7/points.json");
        List<Point> points;
        try {
            points = pointCreator.createList();
        } catch (CreatorException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
        Point p1 = points.get(0);
        Point p2 = points.get(1);
        Point p3 = points.get(2);
        LOGGER.debug("Point p1 = {}, p2 = {}, p3 = {}", p1, p2, p3);
        printer.print("Are " + p1 + ", " + p2 + ", " + p3 + " " + "on the same line: "
                + PointService.isPointOnLine(p1, p2, p3));
        printer.printLineSeparator();
        LOGGER.info("Chapter 3 Task 7 Ended");
    }

    private static void chapter4Task12() {
        LOGGER.info("Chapter 4 Task 12 Started");
        printer.printLineSeparator();
        printer.printCenter("Chapter 4, Task 12");
        TariffService tariffService = new TariffService();
        tariffService.addTariff(new RegularTariff(13, RegularTariff.class.getSimpleName(), 130, 13, 23));
        tariffService.addTariff(new RegularTariff(10, RegularTariff.class.getSimpleName(), 100, 10, 41));
        tariffService.addTariff(new RegularTariff(15, RegularTariff.class.getSimpleName(), 150, 15, 6));
        tariffService.addTariff(new RegularTariff(20, RegularTariff.class.getSimpleName(), 200, 20, 100));
        tariffService.addTariff(new RegularTariff(24, RegularTariff.class.getSimpleName(), 240, 24, 35));
        tariffService.addTariff(new ChildTariff(7, ChildTariff.class.getSimpleName(), 100, 15));
        tariffService.addTariff(new BusinessTariff(40, BusinessTariff.class.getSimpleName(), 20));

        List<Tariff> sorted = tariffService.sortByPrice();
        printer.print("Tariffs sorted by price: " + sorted);

        List<Tariff> filtered = tariffService.getTariffByParams(20, 60, 15, 25, -1, 1000, -1, 200);
        printer.print("Tariffs filtered by params: " + filtered);
        LOGGER.info("Chapter 4 Task 12 Ended");
    }

    private static void chapter5Task17() {
        LOGGER.info("Chapter 5 Task 17 Started");
        printer.printLineSeparator();
        printer.printCenter("Chapter 5, Task 17");
        Cinema cinema1 = new Cinema("Kiev", "Kievskiy skver 1",
                Arrays.asList("Big", "Small", "VIP"),
                Arrays.asList("SUPER BIG", "very small", "VIP?"),
                Arrays.asList(new Cinema.Film( 120, 2, "CJocker"), new Cinema.Film(103, 3, "ATransformers"), new Cinema.Film(57, 1, "BLook Back")));
        Creator<Cinema> cinemaCreator =
                new CinemaCreator("src/main/resources/chapter/five/task17/cinema.json");
        Cinema cinema;
        try {
            cinema = cinemaCreator.create();
        } catch (CreatorException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
        printer.print("Schedule before sort:\n" + cinema.getFilmSessions());
        cinema.sortSchedule();
        printer.print("Schedule after sort:\n" + cinema.getFilmSessions());
        LOGGER.info("Chapter 5 Task 17 Ended");
    }

    /***
     * Applies application arguments
     *
     * @param arguments arguments to be applied
     * @return Printer with applied arguments
     */
    private static Printer applyApplicationParams(String... arguments) {
        LOGGER.info("Applying application arguments");
        List<String> args = Arrays.stream(arguments).filter(Pattern.compile("-+").asPredicate()).toList();
        String separator = Printer.FormattingService.DEFAULT_LINE_SEPARATOR;
        int width = Printer.FormattingService.DEFAULT_WIDTH;

        //Processing application arguments
        for (String arg : args) {
            switch (arg) {
                case "--separator", "-s": {
                    separator = arguments[args.indexOf(arg) * 2  + 1];
                    LOGGER.debug("Separator provided: {}", separator);
                    if (separator.length() != 1) {
                        separator = Printer.FormattingService.DEFAULT_LINE_SEPARATOR;
                        LOGGER.warn("Warning: inappropriate separator: {}. Leaving default separator.",
                                separator);
                    }
                    break;
                }
                case "--width", "-w": {
                    String width_str = arguments[args.indexOf(arg) * 2 + 1];
                    LOGGER.debug("Width provided: {}", width_str);
                    try {
                        int w = Integer.parseInt(width_str);
                        if (w >= Printer.FormattingService.DEFAULT_WIDTH) {
                            width = w;
                        } else {
                            LOGGER.warn("Small width: {}. Leaving default width.", width_str);
                        }
                    } catch (NumberFormatException e) {
                        LOGGER.warn("Inappropriate width: {}. Leaving default width.", width_str);
                    }
                    break;
                }
            }
        }
        LOGGER.debug("Printer output: {}", args.contains("-f") || args.contains("--file") ? "File" : "Console");
        LOGGER.info("Application arguments applied");
        //TODO Set output to file
        return args.contains("-f") || args.contains("--file") ? CLIPrinter.getInstance(width, separator) :
                CLIPrinter.getInstance(width, separator);
    }
}