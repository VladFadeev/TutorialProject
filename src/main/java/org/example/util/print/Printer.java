package org.example.util.print;

import java.util.ArrayList;
import java.util.List;

public interface Printer {

    /**
     * Prints text
     *
     * @param text message to be printed
     */
    void print(String text);

    /**
     * Prints lines of text
     *
     * @param lines text separated to lines to print
     */
    void print(List<String> lines);

    /**
     * Prints header.
     *
     * @param header text to print.
     */
    void printHeader(String header);

    /**
     * Prints line to separate messages
     */
    void printLineSeparator();

    /**
     * Prints text aligned to center based on width
     *
     * @param text message to be printed
     */
    void printCenter(String text);


    /**
     * Prints lines aligned to center based on width
     *
     * @param lines text separated to lines to print
     */
    void printCenter(List<String> lines);

    /**
     * Prints introduction section
     */
    void printHelloSection();

    /**
     * Prints end section
     */
    void printEndSection();

    class FormattingService {
        private static volatile FormattingService instance;

        public static final String DEFAULT_LINE_SEPARATOR = "-";
        public static final int DEFAULT_WIDTH = 80;
        private final String LINE_SEPARATOR;
        private final int WIDTH;

        private FormattingService(int width, String lineSeparator) {
            WIDTH = width;
            LINE_SEPARATOR = lineSeparator;
        }

        /**
         * Aligns text to center with respect to width
         *
         * @param text message to align
         * @return aligned text
         */
        public String center(String text) {
            StringBuilder s = new StringBuilder();
            String result;
            if (text.length() <= WIDTH) {
                s.repeat(" ", (WIDTH - text.length()) / 2);
                s.append(text);
                result = s.toString();
            } else {
                String[] lines = formLines(text);
                for (String line : lines) {
                    s.append(center(line)).append("\n");
                }
                result = s.delete(s.length() - 1, s.length()).toString();
            }
            return result;
        }

        /**
         * Forms lines of text with respect to width
         *
         * @param text message to be formatted
         * @return lines of text
         */
        public String[] formLines(String text) {
            List<String> lines = new ArrayList<>();
            String line = text;

            int sepIndex = line.substring(0, Math.min(line.length(), WIDTH))
                    .lastIndexOf(" ");
            while (line.length() > WIDTH) {
                lines.add(line.substring(0, sepIndex).trim());
                line = line.substring(sepIndex).trim();
                sepIndex = line.substring(0, Math.min(line.length(), WIDTH))
                        .lastIndexOf(" ");
            }

            lines.add(line);
            return lines.toArray(new String[0]);
        }

        /**
         * Froms formatted string from lines
         *
         * @param lines lines of text
         * @return formatted string
         */
        public String formString(String[] lines) {
            StringBuilder sb = new StringBuilder();
            for (String line : lines) {
                sb.append(line).append("\n");
            }
            sb.delete(sb.length() - 1, sb.length());
            return sb.toString();
        }

        /**
         * Generates separation line
         *
         * @return string for separation line
         */
        public String separationLine() {
            StringBuilder s = new StringBuilder();
            s.repeat(LINE_SEPARATOR, WIDTH);
            return s.toString();
        }

        public static FormattingService getInstance(int width, String lineSeparator) {
            FormattingService result = instance;
            if (result != null) {
                return result;
            }
            synchronized (Printer.class) {
                if (instance == null) {
                    instance = new FormattingService(width, lineSeparator);
                }
            }
            return instance;
        }
    }
}
