package org.example;

import org.example.util.exception.ChapterException;
import org.example.util.io.Printer;
import org.example.util.io.PrinterManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <p>This abstract class provides {@linkplain Printer},
 * {@linkplain Logger} utilities for each task.</p>
 *
 * <p>{@code PRINTER} and {@code LOGGER} are set during app start-up,
 * while {@code READER} is specified for each task individually.</p>
 */
public abstract class Chapter {
    protected static final Printer PRINTER = PrinterManager.getPrinter(80, "-");
    protected static final Logger LOGGER = LogManager.getLogger("org.example.Main");

    /**
     * Main method to be executed. Core logic should be placed here.
     *
     * @return {@code true} if the task was completed successfully,
     * otherwise {@code false}
     */
    public abstract boolean task() throws ChapterException;
}
