package org.example.chapter.five.task17.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

//TODO Think about building Cinema and repairs in Cinema
public class Cinema {
    private final String name;
    private final String address;
    private final List<Screen> screens = new ArrayList<>();
    private final List<TimeSlot> schedule = new ArrayList<>();
    private final static LocalTime FIRST_SLOT = LocalTime.of(7, 0);

    public record Film(int watchTimeMinutes, int sessions, String name) implements Comparable<Film> {

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
            return "Film{" +
                    "name='" + name + '\'' +
                    ", watchTimeMinutes=" + watchTimeMinutes +
                    '}';
        }
    }

    private record TimeSlot(Film film, Screen screen, LocalDate day,  long sessionDuration) implements Comparable<TimeSlot> {

        @Override
        public int compareTo(TimeSlot o) {
            int result = film.compareTo(o.film);
            if (result != 0) return result;
            result = day.compareTo(o.day);
            if (result != 0) return result;
            result = -Long.compare(sessionDuration, o.sessionDuration);
            return result;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TimeSlot timeSlot)) return false;
            return sessionDuration == timeSlot.sessionDuration && day == timeSlot.day && Objects.equals(film, timeSlot.film) && Objects.equals(screen, timeSlot.screen);
        }

        @Override
        public String toString() {
            return "TimeSlot{" +
                    "film=" + film +
                    ", sessionDuration=" + sessionDuration +
                    ", screen=" + screen +
                    ", day=" + day +
                    '}';
        }
    }

    private static class Screen {
        private final int id;
        private final String type;
        private final String specs;
        private final Map<LocalDate, Long> screenSchedule;

        public Screen(int id, String type, String specs) {
            this.id = id;
            this.type = type;
            this.specs = specs;
            this.screenSchedule = new HashMap<>();
        }

        public int getId() {
            return id;
        }

        public String getType() {
            return type;
        }

        public String getSpecs() {
            return specs;
        }

        public Map<LocalDate, Long> getScreenSchedule() {
            return new HashMap<>(screenSchedule);
        }

        private boolean isAvailable(LocalDate day, long sessionDuration) {
            if (screenSchedule.containsKey(day)) {
                return (screenSchedule.get(day) & sessionDuration) == 0;
            }
            screenSchedule.put(day, 0L);
            return true;
        }

        public void addSession(LocalDate day, long sessionDuration) {
            screenSchedule.put(day, sessionDuration);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Screen screen)) return false;
            return id == screen.id && Objects.equals(type, screen.type) && Objects.equals(specs, screen.specs) && Objects.equals(screenSchedule, screen.screenSchedule);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, type, specs, screenSchedule);
        }

        @Override
        public String toString() {
            return "Screen{" +
                    "id=" + id +
                    ", type='" + type + '\'' +
                    ", specs='" + specs + '\'' +
                    ", screenSchedule=" + screenSchedule +
                    '}';
        }
    }

    public Cinema(String name, String address, List<String> screenTypes, List<String> screenSpecs, List<Film> films) {
        this.name = name;
        this.address = address;
        parseScreens(screenTypes, screenSpecs);
        getSchedule(films);
    }


    private void parseScreens(List<String> screenTypes, List<String> screenSpecs) {
        for (int i = 0; i < screenTypes.size(); i++) {
            screens.add(new Screen(i, screenTypes.get(i), screenSpecs.get(i)));
        }
    }

    private void getSchedule(List<Film> films) {
        //do as factory
        Random random = new Random();
        random.setSeed(3);
        for (Film film : films) {
            int sessions = random.nextInt(10);
            for (int j = 0; j < sessions; j++) {
                LocalDate day = LocalDate.of(2024, 8, random.nextInt(5)+1);
                Screen screen = screens.get(random.nextInt(screens.size()));
                long sessionDuration = getSessionDuration(film, random);
                int count = 0;
                while (!screen.isAvailable(day, sessionDuration) && count < 10) {
                    sessionDuration = getSessionDuration(film, random);
                    count++;
                }
                if (count != 10) {
                    screen.addSession(day, sessionDuration);
                    schedule.add(new TimeSlot(film, screen, day, sessionDuration));
                }
            }
        }
    }

    private long getSessionDuration(Film film, Random rand) {
        long result = 1;
        for (int i = 1; i < film.watchTimeMinutes / 15 + 1; i++) {
            result += (long) Math.pow(2, i);
        }
        int shift = rand.nextInt(64 - film.watchTimeMinutes / 15);
        return result << shift;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public List<String> getScreenSpecs() {
        return screens.stream().map(screen -> screen.specs).toList();
    }

    public HashMap<LocalDate, Long> getSchedule() {
        List<LocalDate> keys = schedule.stream().map(timeSlot -> timeSlot.day).toList();
        List<Long> vals = schedule.stream().map(timeSlot -> timeSlot.sessionDuration).toList();
        HashMap<LocalDate, Long> result = new HashMap<>();
        for (int i = 0; i < keys.size(); i++) {
            result.put(keys.get(i), vals.get(i));
        }
        return result;
    }

    public void sortSchedule() {
        //Comparator<TimeSlot> comparator = Comparator.comparing(TimeSlot::film).thenComparing(TimeSlot::day).thenComparing(TimeSlot::sessionDuration);
        schedule.sort(TimeSlot::compareTo);
    }

    public List<Film> getFilms() {
        List<Film> result = new ArrayList<>(schedule.stream().map(timeSlot -> timeSlot.film).collect(Collectors.toSet()).stream().toList());
        result.sort(Film::compareTo);
        return result;
    }

    public List<String> getFilmSessions() {
        List<Film> films = getFilms();
        List<String> result = new ArrayList<>();
        for (Film film : films) {
            StringBuilder filmSession = new StringBuilder("\n");
            filmSession.append(film.name).append(":\n[");
            for (TimeSlot timeSlot : schedule) {
                if (timeSlot.film.equals(film)) {
                    filmSession.append("{")
                            .append(getSlotTime(timeSlot.sessionDuration)).append(", ")
                            .append(timeSlot.day).append(", ")
                            .append(timeSlot.screen.id).append(", ")
                            .append("watch time: ")
                            .append(film.watchTimeMinutes / 60).append(" h ")
                            .append(film.watchTimeMinutes % 60).append(" min").append(", ")
                            .append(Long.toBinaryString(timeSlot.sessionDuration))
                            .append("},\n");
                }
            }
            filmSession.append("]");
            result.add(filmSession.toString());
        }
        return result;
    }

    public LocalTime getSlotTime(long sessionDuration) {
        return FIRST_SLOT.plusMinutes(Long.numberOfLeadingZeros(sessionDuration) * 15);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cinema cinema)) return false;
        return Objects.equals(name, cinema.name) && Objects.equals(address, cinema.address) && Objects.equals(screens, cinema.screens) && Objects.equals(schedule, cinema.schedule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, screens, schedule);
    }

    @Override
    public String toString() {
        return "Cinema{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", screens=" + screens +
                ", schedule=" + schedule +
                '}';
    }
}
