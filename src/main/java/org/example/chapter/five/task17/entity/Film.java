package org.example.chapter.five.task17.entity;

import java.util.Objects;

public record Film(int watchTimeMinutes, String name) implements Comparable<Film> {

    @Override
    public int compareTo(Film o) {
        return name.compareTo(o.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Film film)) return false;
        return watchTimeMinutes == film.watchTimeMinutes && Objects.equals(name, film.name);
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", watch time = " + watchTimeMinutes / 60 + "h " +
                watchTimeMinutes % 60 + "min " +
                '}';
    }
}

