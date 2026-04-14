package org.example.util.print;

import org.example.util.print.impl.CLIPrinter;

public class PrinterManager {
    private static volatile Printer PRINTER;
    private static volatile PrinterManager instance;

    private PrinterManager(int width, String separator) {
        PRINTER = CLIPrinter.getInstance(width, separator);
    }
    public static Printer getPrinter(int width, String separator) {
        return getInstance(width, separator).PRINTER;
    }

    public static PrinterManager getInstance(int width, String separator) {
        PrinterManager result = instance;
        if (result != null) {
            return result;
        }
        synchronized (PrinterManager.class) {
            if (instance == null) {
                instance = new PrinterManager(width, separator);
            }
        }
        return instance;
    }
}
