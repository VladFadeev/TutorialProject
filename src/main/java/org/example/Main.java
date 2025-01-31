package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.chapter.five.task17.entity.Cinema;
import org.example.chapter.five.task17.entity.Film;
import org.example.chapter.five.task17.service.CinemaCreator;
import org.example.chapter.five.task17.service.CinemaException;
import org.example.chapter.four.task12.entity.Tariff;
import org.example.chapter.four.task12.service.TariffCreator;
import org.example.chapter.four.task12.service.TariffService;
import org.example.chapter.seven.task25.FunctionalService;
import org.example.chapter.six.task10.entity.Building;
import org.example.chapter.six.task10.entity.Customer;
import org.example.chapter.six.task10.service.BuildingException;
import org.example.chapter.six.task10.service.BuildingService;
import org.example.chapter.six.task10.service.impl.HouseService;
import org.example.chapter.six.task10.service.impl.OfficeService;
import org.example.chapter.six.task10.service.impl.ShoppingCenterService;
import org.example.chapter.three.task7.entity.Point;
import org.example.chapter.three.task7.service.PointCreator;
import org.example.chapter.three.task7.service.PointService;
import org.example.util.create.Creator;
import org.example.util.create.impl.StringCreator;
import org.example.util.exception.CreatorException;
import org.example.util.print.Printer;
import org.example.util.print.impl.CLIPrinter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
        chapter4Task12();
        chapter5Task17();
        chapter6Task10();
        chapter7Task25();
        printer.printEndSection();
        LOGGER.info("Application ended");
    }

    private static void chapter3Task7() {
        LOGGER.info("Chapter 3 Task 7 Started");
        printer.printLineSeparator();
        printer.printHeader("Chapter 3, Task 7");
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
        printer.printHeader("Chapter 4, Task 12");
        Creator<Tariff> tariffCreator =
                new TariffCreator("src/main/resources/chapter/four/task12/tariffs.json");
        List<Tariff> tariffs;
        try {
            tariffs = tariffCreator.createList();
        } catch (CreatorException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
        printer.printHeader("Tariffs before sort");
        printer.print(tariffs.stream().map(Tariff::toString).toList());
        List<Tariff> sorted = TariffService.sortByPrice(tariffs);
        printer.printHeader("Tariffs sorted by price");
        printer.print(sorted.stream().map(Tariff::toString).toList());
        Optional<Tariff> tariff = TariffService.getTariffByParams(tariffs, new int[]{15, 25}, new int[]{-1, 200}, new int[]{-1, 15});
        printer.print("");
        printer.print("Customers across all tariffs: " + TariffService.getCustomersSum(tariffs));
        printer.print("");
        printer.print("Tariffs filtered by params: " + (tariff.isPresent() ? tariff.get() : "none"));
        LOGGER.info("Chapter 4 Task 12 Ended");
    }

    private static void chapter5Task17() {
        LOGGER.info("Chapter 5 Task 17 Started");
        printer.printLineSeparator();
        printer.printHeader("Chapter 5, Task 17");
        Creator<Cinema> cinemaCreator =
                new CinemaCreator("src/main/resources/chapter/five/task17/cinema.json");
        Cinema cinema;
        try {
            cinema = cinemaCreator.create();
        } catch (CreatorException | CinemaException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
        printer.printHeader("Schedule before sort");
        printer.print(cinema.getSchedule());
        cinema.sortSchedule();
        printer.printHeader("Schedule after sort");
        printer.print(cinema.getSchedule());
        printer.printHeader("Screens");
        printer.print(cinema.getScreens());
        printer.printHeader("Films");
        List<Film> films = cinema.getFilms();
        printer.print(films.stream().map(Film::toString).toList());
        printer.printHeader("Sessions for the first film");
        printer.print(cinema.getFilmSessions(films.getFirst()));
        LOGGER.info("Chapter 5 Task 17 Ended");
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void chapter6Task10() {
        LOGGER.info("Chapter 6 Task 10 Started");
        printer.printLineSeparator();
        printer.printHeader("Chapter 6, Task 10");
        BuildingService[] services = new BuildingService[]{new HouseService(), new OfficeService(), new ShoppingCenterService()};
        Customer owner = new Customer("Owner", 30);
        Customer tenant = new Customer("Tenant", 30);
        for (BuildingService service : services) {
            try {
                Building building = service.build(1, new double[]{2});
                printer.print("Created " + building.getClass().getSimpleName() + ": " + building);
                service.sell(building, owner);
                printer.print("New owner: " + owner);
                service.increaseArea(building, 5);
                printer.print("Increased area: " + (building.getArea() - 5) + "->" + building.getArea());
                int roomCount = service.getRoomCount(building);
                service.doRenovations(building, roomCount);
                printer.print("Did renovations in room: " + roomCount);
                double priceM2 = service.getPricePerM2(building);
                printer.print("Price per square meter: " + priceM2);
                service.let(building, tenant);
                service.let(building, tenant, roomCount);
            } catch (BuildingException e) {
                LOGGER.error(e);
                printer.print(e.getMessage());
            }
        }
        LOGGER.info("Chapter 6 Task 10 Ended");
    }

    private static void chapter7Task25() {
        LOGGER.info("Chapter 7 Task 25 Started");
        printer.printLineSeparator();
        printer.printHeader("Chapter 7, Task 25");
        Creator<String> stringCreator =
                new StringCreator("src/main/resources/chapter/seven/task25/strings.json");
        List<String> strings;
        try {
            strings = stringCreator.createList();
        } catch (CreatorException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
        printer.print("Strings to check: " + strings);
        strings = strings.stream().filter(FunctionalService::isPangram).toList();
        printer.print("Strings that are pangrams: " + strings);
        LOGGER.info("Chapter 7 Task 25 Ended");
    }

    /**
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
                    separator = arguments[args.indexOf(arg) * 2 + 1];
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