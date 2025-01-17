package org.example.util.print.impl;

import org.example.util.print.Printer;

import java.util.List;

public class CLIPrinter implements Printer {
    private static volatile CLIPrinter instance;
    private static volatile FormattingService fs;

    private CLIPrinter(int width, String separator) {
        fs = FormattingService.getInstance(width, separator);
    }

    @Override
    public void print(String text) {
        System.out.println(fs.formString(fs.formLines(text)));
    }

    @Override
    public void print(List<String> lines) {
        lines.forEach(this::print);
    }

    @Override
    public void printHeader(String header) {
        print("");
        printCenter(header);
        print("");
    }

    @Override
    public void printLineSeparator() {
        System.out.println(fs.separationLine());
    }

    @Override
    public void printCenter(String text) {
        System.out.println(fs.center(text));
    }

    @Override
    public void printCenter(List<String> lines) {
        lines.forEach(this::printCenter);
    }

    @Override
    public void printHelloSection() {
        printLineSeparator();
        printHeader("Hello!");
        printCenter("This is my study project where I do tasks from a workbook.This is my study project where I do tasks from a workbook.This is my study project where I do tasks from a workbook.");
        printLineSeparator();
    }

    @Override
    public void printEndSection() {
        printLineSeparator();
        printHeader("Application End");
        printLineSeparator();
    }

    public static CLIPrinter getInstance(int width, String separator) {
        CLIPrinter result = instance;
        if (result != null) {
            return result;
        }
        synchronized (Printer.class) {
            if (instance == null) {
                instance = new CLIPrinter(width, separator);
            }
        }
        return instance;
    }
}
