package org.example.standalone;

import org.example.Chapter;
import org.example.util.exception.ChapterException;

import java.util.Arrays;
import java.util.Scanner;

/**
 * This class solves Task 4 from Chapter 1, Variant B
 *
 * <p>Input <i>n</i> integers from console. Output numbers divisible by both 5 and 7.</p>
 */
public class ChapterOneTask extends Chapter {
    private static volatile ChapterOneTask instance;

    private final boolean isAuto;

    private ChapterOneTask(boolean isAuto) {
        this.isAuto = isAuto;
    }

    @Override
    public boolean task() throws ChapterException {
        LOGGER.info("Chapter 1, Variant B, Task 7 Started");
        PRINTER.printLineSeparator();
        PRINTER.printHeader("Chapter One, Variant B, Task 7");
        if (!this.isAuto) {
            System.out.println("How many integers would you like to enter?");
        }
        PRINTER.print("How many integers would you like to enter?");
        try (Scanner scanner = new Scanner(System.in)) {
            int n = 0;
            if (scanner.hasNextLine()) {
                n = Integer.parseInt(scanner.nextLine());
                LOGGER.debug("{} integers should be entered.", n);
            }
            if (!this.isAuto) {
                PRINTER.print(Integer.toString(n));
            }

            int[] arr = new int[n];
            if (!this.isAuto) {
                System.out.println("Enter " + n + " integers:");
            }
            PRINTER.print("Enter " + n + " integers:");
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                arr = Arrays.stream(line.trim().split("[ ,]")).mapToInt(Integer::parseInt).toArray();
                if (arr.length != n) {
                    LOGGER.error("{} integers should be entered. {} were entered.", n, arr.length);
                    throw new ChapterException("Incorrect number of integers: " + arr.length + ", should be: " + n);
                }
                LOGGER.debug("{} were entered.", Arrays.toString(arr));
                if (!this.isAuto) {
                    PRINTER.print(Arrays.toString(arr));
                }
            }


            PRINTER.print("Numbers divisible by both 5 and 7:");
            for (int num : arr) {
                if (num % 5 == 0 && num % 7 == 0) {
                    PRINTER.print(Integer.toString(num));
                    LOGGER.debug("{} is divisible by both 5 and 7.", num);
                }
            }

        } catch (Exception e) {
            LOGGER.error(e);
            PRINTER.print(e.getMessage());
            PRINTER.printLineSeparator();
            LOGGER.info("Chapter 1, Variant B, Task 7 Ended");
            return false;
        }
        PRINTER.printLineSeparator();
        LOGGER.info("Chapter 1, Variant B, Task 7 Ended");
        return true;
    }

    public static ChapterOneTask getInstance() {
        ChapterOneTask result = instance;
        if (result != null) {
            return result;
        }
        synchronized (ChapterOneTask.class) {
            if (instance == null) {
                instance = new ChapterOneTask(true);
            }
        }
        return instance;
    }

    public static ChapterOneTask getInstance(boolean isAuto) {
        ChapterOneTask result = instance;
        if (result != null) {
            return result;
        }
        synchronized (ChapterOneTask.class) {
            if (instance == null) {
                instance = new ChapterOneTask(isAuto);
            }
        }
        return instance;
    }
}
