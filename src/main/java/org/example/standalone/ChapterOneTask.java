package org.example.standalone;

import org.example.Chapter;
import org.example.util.exception.ReaderException;

import java.util.Arrays;

/**
 * This class solves Task 4 from Chapter 1, Variant B
 *
 * <p>Input <i>n</i> integers from console. Output numbers divisible by both 5 and 7.</p>
 */
public class ChapterOneTask extends Chapter {
    public ChapterOneTask(Reader<?> reader) {
        super(reader);
    }

    @Override
    public boolean task() {
        LOGGER.info("Chapter 1, Variant B, Task 7 Started");
        PRINTER.printLineSeparator();
        PRINTER.printHeader("Chapter One, Variant B, Task 7");
        try {
            System.out.println("How many integers would you like to enter?");
            PRINTER.print("How many integers would you like to enter?");
            int n = READER.read();
            LOGGER.debug("{} integers should be entered.", n);
            PRINTER.print(Integer.toString(n));

            int[] arr = new int[n];
            System.out.println("Enter " + n + " integers:");
            PRINTER.print("Enter " + n + " integers:");
            READER.read(arr);
            READER.close();
            LOGGER.debug("{} were entered.", Arrays.toString(arr));
            PRINTER.print(Arrays.toString(arr));

            PRINTER.print("Numbers divisible by both 5 and 7:");
            for (int num: arr) {
                if (num % 5 == 0 && num % 7 == 0) {
                    PRINTER.print(Integer.toString(num));
                    LOGGER.debug("{} is divisible by both 5 and 7.", num);
                }
            }

        } catch (ReaderException e) {
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
}
