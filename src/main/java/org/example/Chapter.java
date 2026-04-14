package org.example;

import org.example.util.print.Printer;
import org.example.util.print.PrinterManager;
import org.example.util.read.Reader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <p>This abstract class provides {@linkplain Printer},
 * {@linkplain Logger} and {@linkplain Reader} utilities for each task.</p>
 *
 * <p>{@code PRINTER} and {@code LOGGER} are set during app start-up,
 * while {@code READER} is specified for each task individually.</p>
 */
public abstract class Chapter {
    protected static final Printer PRINTER = PrinterManager.getPrinter(80, "-");
    protected static final Logger LOGGER = LogManager.getLogger("org.example.Main");
    protected final Reader<?> READER;

    public Chapter(Reader<?> reader) {
        this.READER = reader;
    }

    /**
     * Main method to be executed. Core logic should be placed here.
     *
     * @return {@code true} if the task was completed successfully,
     * otherwise {@code false}
     */
    public abstract boolean task();
}
