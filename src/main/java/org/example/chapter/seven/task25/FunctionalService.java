package org.example.chapter.seven.task25;

public class FunctionalService {
    public static boolean isPangram(String text) {
        String regex = "\\W|\\d";
        text = text.replaceAll(regex, "").toLowerCase();
        int count = 0;
        while (!text.isEmpty()) {
            count++;
            text = text.replaceAll(String.valueOf(text.charAt(0)), "");
        }
        return count == 26;
    }
}
