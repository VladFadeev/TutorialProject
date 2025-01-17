package org.example.chapter.five.task17.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.chapter.five.task17.service.CinemaException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * Represents cinema.
 * <p>
 * Stores information about {@code name} of the cinema, it's {@code address},
 * session schedule, screens, films that are shown, work hours and work days.
 * </p>
 * <p>
 * {@code schedule} can be empty, which means that cinema is closed.
 * If {@code screens} is empty - cinema is under construction.
 * {@code films} stores number of active session for each film.
 * </p>
 */
public class Cinema {
    private final String name;
    private final String address;
    private final List<Screen> screens = new ArrayList<>();
    private final List<TimeSlot> schedule = new ArrayList<>();
    private final Map<Film, Integer> films = new HashMap<>();

    private final static LocalTime FIRST_SLOT = LocalTime.of(8, 0);
    private final static LocalDate FIRST_DAY = LocalDate.of(2025, 1, 15);
    private final static Logger LOGGER = LogManager.getLogger();
    private static int WORK_HOURS = 14;
    private static int WORK_DAYS = 7;

    /**
     * Represents session in the cinema schedule.
     * <p>
     * When creating {@code TimeSlot} object, sessions is also added to the screen's schedule.
     *
     * @param film   film of the session.
     * @param screen screen auditorium for the session.
     * @param day    day of the session.
     * @param start  time of the session beginning.
     */
    private record TimeSlot(Film film, Screen screen, LocalDate day, LocalTime start) implements Comparable<TimeSlot> {

        /**
         * Represents session in the cinema schedule.
         * <p>
         * When creating {@code TimeSlot} object, sessions is also added to the screen's schedule.
         *
         * @param film   film of the session.
         * @param screen screen auditorium for the session.
         * @param day    day of the session.
         * @param start  time of the session beginning.
         */
        public TimeSlot(Film film, Screen screen, LocalDate day, LocalTime start) {
            this.film = film;
            this.screen = screen;
            this.day = day;
            this.start = start;
            boolean isAdded = this.screen.addSession(film.watchTimeMinutes(), day, start);
            LOGGER.debug("Session was added to screen: {}", isAdded);
        }

        @Override
        public int compareTo(TimeSlot o) {
            int result = day.compareTo(o.day);
            if (result != 0) return result;
            result = film.compareTo(o.film);
            if (result != 0) return result;
            result = start.compareTo(o.start);
            return result;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TimeSlot timeSlot)) return false;
            return Objects.equals(film, timeSlot.film)
                    && Objects.equals(screen, timeSlot.screen)
                    && Objects.equals(day, timeSlot.day)
                    && Objects.equals(start, timeSlot.start);
        }

        @Override
        public int hashCode() {
            return Objects.hash(film, screen, day, start);
        }

        @Override
        public String toString() {
            return "{" +
                    "day=" + day +
                    ", film=" + film +
                    ", start=" + start +
                    ", screen=" + screen.id +
                    '}';
        }
    }

    /**
     * Describes screens in {@code Cinema}.
     *
     * @param id             screen id.
     * @param type           screen type.
     * @param specs          screen specification.
     * @param screenSchedule schedule for the specific screen.
     */
    private record Screen(int id, String type, String specs, Map<LocalDate, OccupationTime> screenSchedule) {

        /**
         * Describes screens in {@code Cinema}. Default constructor.
         *
         * @param id    screen id.
         * @param type  screen type.
         * @param specs screen specification.
         */
        private Screen(int id, String type, String specs) {
            this(id, type, specs, new HashMap<>());
        }

        /**
         * Stores occupation time in bit-representation of short.
         * <p>
         * Each short represents one hour. One bit stores information about 5 minutes.
         * </p>
         *
         * @param time occupation time representation stored.
         */
        private record OccupationTime(short[] time) {
            private final static int SIZE = Cinema.WORK_HOURS;
            private final static int MINUTES_IN_HOUR = 60;
            private final static int MINUTES_PER_BIT = 5;
            private final static int USED_BITS = MINUTES_IN_HOUR / MINUTES_PER_BIT;

            /**
             * Stores occupation time in bit-representation of short.
             * <p>
             * Each short represents one hour. One bit stores information about 5 minutes.
             * </p>
             * <p>
             * Default constructor.
             * Creates empty {@code OccupationTime} with {@code SIZE}
             * of {@code Cinema.WORK_HOURS}.
             * </p>
             */
            public OccupationTime() {
                this(new short[SIZE]);
            }

            /**
             * Stores occupation time in bit-representation of short.
             * <p>
             * Each short represents one hour. One bit stores information about 5 minutes.
             * </p>
             *
             * @param time occupation time representation stored.
             */
            public OccupationTime(short[] time) {
                this.time = time.clone();
            }

            /**
             * Stores occupation time in bit-representation of short.
             * <p>
             * Each short represents one hour. One bit stores information about 5 minutes.
             * </p>
             *
             * @param watchTime time to be occupied.
             * @param start     start of the session
             */
            public OccupationTime(int watchTime, LocalTime start) {
                this(convertWatchTime(watchTime,
                        start.getMinute() / MINUTES_PER_BIT,
                        start.getHour() - FIRST_SLOT.getHour()));
            }

            /**
             * Checks if {@code occupationTime} is available at {@code this} object.
             *
             * @param occupationTime time to check.
             * @return {@code true} if time is available. Otherwise {@code false}.
             */
            public boolean isAvailable(OccupationTime occupationTime) {
                boolean isAvailable = true;
                for (int i = 0; i < SIZE; i++) {
                    isAvailable = (time[i] & occupationTime.time[i]) == 0;
                    if (!(isAvailable)) {
                        break;
                    }
                }
                return isAvailable;
            }

            /**
             * Adds session of {@code occupationTime} to {@code this} object.
             * <p>
             * Before adding session, check that its time is available with
             * {@code this.isAvailable(occupationTime)}.
             * </p>
             *
             * @param occupationTime session to add.
             * @return {@code true}.
             */
            public boolean addOccupationTime(OccupationTime occupationTime) {
                for (int i = 0; i < SIZE; i++) {
                    time[i] += occupationTime.time[i];
                }
                return true;
            }

            /**
             * Finds offset in minutes for the first sequence of
             * available bits to fill {@code watchTime}.
             *
             * @param watchTime time for which sequence is searched.
             * @return Offset in minutes before found sequence.
             * {@code -1} if no sequence is found.
             */
            public int findFirstAvailableTime(int watchTime) {
                int bits = getBits(watchTime), hour = 0,
                        maxPos = 0, currentPos = 0,
                        maxAvailable = 0, availableConsecutiveBits = 0;
                // for every hour or until there is enough space
                while (hour < SIZE && bits > maxAvailable) {
                    int[] availableBits = getAvailableBits(hour);
                    int bitCount = availableBits[0];
                    int position = availableBits[1];
                    if (bitCount == 0 || (position == 0 && bitCount < USED_BITS)) {
                        // if there is no available bits, or it's right end
                        if (maxAvailable < availableConsecutiveBits + bitCount) {
                            maxAvailable = availableConsecutiveBits + bitCount;
                            maxPos = currentPos;
                        }
                        availableConsecutiveBits = 0;
                        currentPos = 0;
                    } else if (position > 0) {
                        // left end of consecutive bits
                        if (maxAvailable < availableConsecutiveBits) {
                            maxAvailable = availableConsecutiveBits;
                            maxPos = currentPos;
                        }
                        availableConsecutiveBits = bitCount;
                        currentPos = position;
                    } else if (position < 0) {
                        // if consecutive bits in the middle
                        if (maxAvailable < availableConsecutiveBits) {
                            maxAvailable = availableConsecutiveBits;
                            maxPos = currentPos;
                        } else if (maxAvailable < bitCount) {
                            maxAvailable = bitCount;
                            maxPos = position;
                        }
                        availableConsecutiveBits = 0;
                        currentPos = 0;
                    } else {
                        availableConsecutiveBits += bitCount;
                        if (maxAvailable < availableConsecutiveBits) {
                            maxAvailable = availableConsecutiveBits;
                            maxPos = currentPos;
                        }
                    }
                    hour++;
                }
                // not enough bits available
                if (bits > maxAvailable) {
                    return -1;
                }
                int offset = 0;
                if (maxPos != 0) {
                    offset = USED_BITS - (maxPos < 0 ? -maxPos : maxPos);
                }
                if (bits % USED_BITS > (maxPos < 0 ? -maxPos : maxPos) && maxPos != 0) {
                    offset += (hour - 1 - bits / USED_BITS - 1) * USED_BITS;
                } else {
                    offset += (hour - 1 - bits / USED_BITS) * USED_BITS;
                }
                return offset * MINUTES_PER_BIT;
            }

            /**
             * Gets number of available bits in {@code hour}'s short.
             * <p>
             * Result is {@code int[2]} where {@code int[0]} stores number of available
             * bits and {@code int[1]} stores their start position from the right end.
             * Position is {@code < 0} if available bits are found in the middle,
             * {@code > 0} if bits are at the right end, {@code 0} if at the left end.
             * </p>
             *
             * @param hour index to select short in {@code time}.
             * @return array with number of available bits and their start position from the right.
             */
            private int[] getAvailableBits(int hour) {
                // if no bits are available
                if (Long.bitCount(this.time[hour]) == USED_BITS) {
                    return new int[]{0, 0};
                }
                short time = this.time[hour], startFromRight = 0;
                // reverse zeros and delete unused bits
                short temp = (short) (((short) ~time << Integer.SIZE - USED_BITS)
                        >>> Integer.SIZE - USED_BITS);
                // count consecutive one bits
                int bitCount = countConsecutiveOnes(temp);
                // create line of consecutive ones
                int ones = ones(bitCount);
                // find right end of consecutive ones
                while (Long.numberOfTrailingZeros(temp ^ ones) < bitCount) {
                    startFromRight++;
                    temp >>>= 1;
                }
                startFromRight += (short) bitCount;
                int[] result = new int[2];
                result[0] = bitCount;
                if (Long.numberOfTrailingZeros(time) == bitCount) {
                    // if bits are at the right end
                    result[1] = startFromRight;
                } else if (startFromRight != USED_BITS) {
                    // if bits are at the middle
                    result[1] = -startFromRight;
                }
                return result;
            }

            /**
             * Converts {@code time} to array of binary strings without
             * {@code UNUSED_BITS = Short.SIZE - USED_BITS}.
             *
             * @return Binary representation of {@code time}.
             */
            public String[] getOccupationTimeBinary() {
                String[] result = new String[SIZE];
                for (int i = 0; i < SIZE; i++) {
                    result[i] = Long.toBinaryString(this.time[i]);
                }
                return result;
            }

            /**
             * Converts {@code watchTime} to occupation time representation
             * with respect to {@code minutesOffset} and {@code hourOffset}.
             *
             * @param watchTime time to be converted.
             * @param minutesOffset offset in minutes.
             * @param hourOffset offset in hours.
             * @return array of {@code short} as occupation time representation.
             */
            private static short[] convertWatchTime(int watchTime, int minutesOffset, int hourOffset) {
                int bits = getBits(watchTime);
                short[] time = new short[(bits + minutesOffset) / USED_BITS
                        + ((bits + minutesOffset) % USED_BITS == 0 ? 0 : 1)];
                //Setting first hour with respect to offset
                time[0] = 1;
                for (int i = 1; i < USED_BITS - minutesOffset && i < bits; i++) {
                    time[0] = (short) ((time[0] << 1) + 1);
                }
                //Setting bits until the last hour
                for (int i = 1; i < time.length - 1; i++) {
                    time[i] = ones(USED_BITS);
                }
                //Setting bits for the last hour
                if (time[time.length - 1] == 0) {
                    time[time.length - 1] = ones((bits + minutesOffset) % USED_BITS);
                    time[time.length - 1] <<= USED_BITS - ((bits + minutesOffset) % USED_BITS);
                }
                short[] result = new short[SIZE];
                System.arraycopy(time, 0, result, hourOffset, time.length);
                return result;
            }

            /**
             * Counts how many bits {@code watchTime} will occupy.
             * <p>
             * {@code 2} additional bits are reserved for cleaning screen auditorium.
             * Adds additional bit if {@code watchTime} is not divisible by {@code MINUTES_PER_BIT}.
             * </p>
             *
             * @param watchTime time that will be occupied.
             * @return number of occupied bits.
             */
            private static int getBits(int watchTime) {
                return 2 + watchTime / MINUTES_PER_BIT + (watchTime % MINUTES_PER_BIT == 0 ? 0 : 1);
            }

            /**
             * Counts number of consecutive one-bits in {@code number}.
             *
             * @param number number for which consecutive bits are counted.
             * @return number of consecutive one-bits
             */
            private static int countConsecutiveOnes(short number) {
                int count = 0;
                while (number > 0) {
                    number = (short) (number & number << 1);
                    count++;
                }
                return count;
            }

            /**
             * Creates number with {@code size} number of consecutive one-bits.
             *
             * @param size number of consecutive one-bits.
             * @return number with consecutive one-bits.
             */
            private static short ones(int size) {
                int ones = 1;
                for (int j = 1; j < size; j++) {
                    ones = (ones << 1) + 1;
                }
                return (short) ones;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof OccupationTime that)) return false;
                return Objects.deepEquals(time, that.time);
            }

            @Override
            public int hashCode() {
                return Arrays.hashCode(time);
            }

            @Override
            public String toString() {
                return "OccupationTime{"
                        + Arrays.toString(getOccupationTimeBinary()) +
                        '}';
            }
        }

        @Override
        public Map<LocalDate, OccupationTime> screenSchedule() {
            return new HashMap<>(screenSchedule);
        }

        /**
         * Gets formatted string of screen schedule.
         *
         * @return screen schedule.
         */
        public String getScreenScheduleString() {
            StringBuilder result = new StringBuilder();
            result.append("{");
            for (Map.Entry<LocalDate, OccupationTime> entry : screenSchedule.entrySet()) {
                result.append(entry.getKey());
                result.append(": ");
                result.append(entry.getValue());
                result.append(", ");
            }
            result.delete(result.lastIndexOf(", "), result.length());
            result.append("}");
            return result.toString();
        }

        /**
         * Checks if {@code occupationTime} is available at {@code day}.
         *
         * @param day  day at which to check availability.
         * @param time time which is checked for availability.
         * @return {@code true} if time is available. Otherwise {@code false}.
         */
        private boolean isAvailable(LocalDate day, OccupationTime time) {
            if (screenSchedule.containsKey(day)) {
                return (screenSchedule.get(day).isAvailable(time));
            }
            screenSchedule.put(day, new OccupationTime());
            return true;
        }

        /**
         * Adds session to screen schedule.
         *
         * @param day day of the session.
         * @param time time of the session.
         * @return {@code true} if session was added. Otherwise {@code false}.
         */
        private boolean addSession(LocalDate day, OccupationTime time) {
            if (isAvailable(day, time)) {
                return screenSchedule.get(day).addOccupationTime(time);
            }
            return false;
        }

        /**
         * Adds session to screen schedule.
         *
         * @param watchTime time of the session.
         * @param day       day of the session.
         * @param start     time of the beginning of the session.
         * @return {@code true} if session was added. Otherwise {@code false}.
         */
        public boolean addSession(int watchTime, LocalDate day, LocalTime start) {
            OccupationTime time = new OccupationTime(watchTime, start);
            return addSession(day, time);
        }

        /**
         * Checks if screen has available time at {@code day}.
         *
         * @param day day to check.
         * @param watchTime occupied time to check.
         * @return {@code true} if time is available. Otherwise {@code false}.
         */
        private boolean hasAvailableTime(LocalDate day, int watchTime) {
            if (screenSchedule.containsKey(day)) {
                return screenSchedule.get(day).findFirstAvailableTime(watchTime) != -1;
            }
            screenSchedule.put(day, new OccupationTime());
            return true;
        }

        /**
         * Finds first available time for the session of {@code watchTime}
         * at {@code day}.
         * <p>
         *     Returns {@code LocalTime} shifted accordingly
         *     to the minutes offset from {@code OccupationTime.findFirstAvailableTime(int))}.
         * </p>
         * <p>If no available time is found return {@code LocalTime} one minute before
         * {@code Cinema.FIRST_SLOT}.
         * </p>
         * @param day day to find first available time.
         * @param watchTime occupied time to find allocation for.
         * @return {@code LocalTime} of the first available time.
         */
        private LocalTime findFirstAvailableTime(LocalDate day, int watchTime) {
            int minutes = 0;
            if (screenSchedule.containsKey(day)) {
                minutes = screenSchedule.get(day).findFirstAvailableTime(watchTime);
            } else {
                screenSchedule.put(day, new OccupationTime());
            }
            return FIRST_SLOT.plusMinutes(minutes);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Screen screen)) return false;
            return Objects.equals(type, screen.type)
                    && Objects.equals(specs, screen.specs);
        }

        @Override
        public int hashCode() {
            return Objects.hash(type, specs);
        }

        @Override
        public String toString() {
            return "Screen{" +
                    "id=" + id +
                    ", type='" + type + '\'' +
                    ", specs='" + specs + '\'' +
                    ", screenSchedule=" + getScreenScheduleString() +
                    '}';
        }
    }

    /**
     * Empty Cinema without screens and schedule.
     *
     * @param name    cinema's name.
     * @param address cinema's address.
     */
    public Cinema(String name, String address) {
        this.name = name;
        this.address = address;
    }

    /**
     * Empty Cinema without screens and schedule.
     *
     * @param name      cinema's name.
     * @param address   cinema's address.
     * @param WORK_DAYS number of working days for cinema.
     *                  Default value {@code 7}.
     * @throws CinemaException if object cannot be created with provided parameters.
     */
    public Cinema(String name, String address, int WORK_DAYS) throws CinemaException {
        this(name, address);
        if (WORK_DAYS <= 0) {
            LOGGER.error("Illegal work days: {}", WORK_DAYS);
            throw new CinemaException("Illegal work days: " + WORK_DAYS);
        }
        Cinema.WORK_DAYS = WORK_DAYS;
    }

    /**
     * Empty Cinema without screens and schedule.
     *
     * @param name       cinema's name.
     * @param address    cinema's address.
     * @param WORK_DAYS  number of working days for cinema.
     *                   Default value {@code 7}.
     * @param WORK_HOURS number of working hours per day for cinema.
     *                   Default value {@code 14}.
     * @throws CinemaException if object cannot be created with provided parameters.
     */
    public Cinema(String name, String address, int WORK_DAYS, int WORK_HOURS) throws CinemaException {
        this(name, address, WORK_DAYS);
        if (WORK_HOURS <= 0 || WORK_HOURS > 24) {
            LOGGER.error("Illegal work hours: {}", WORK_HOURS);
            throw new CinemaException("Illegal work hours: " + WORK_HOURS);
        }
        Cinema.WORK_HOURS = WORK_HOURS;
    }

    /**
     * Cinema with screens. Schedule is created based on {@code filmSession} parameter.
     *
     * @param name         cinema's name.
     * @param address      cinema's address.
     * @param screenTypes  list of types of screens in the cinema.
     *                     Must be the same size as {@code screenSpecs}.
     * @param screenSpecs  list of specifications of screens in the cinema.
     *                     Must be the same size as {@code screenTypes}.
     * @param filmSessions map between film and number of sessions to be shown in the cinema.
     *                     {@link  Cinema#addToSchedule(Map filmSessions)} for more information
     *                     on how schedule is created.
     * @throws CinemaException if object cannot be created with provided parameters.
     */
    public Cinema(String name, String address,
                  List<String> screenTypes,
                  List<String> screenSpecs,
                  Map<Film, Integer> filmSessions) throws CinemaException {
        this(name, address, screenTypes, screenSpecs, filmSessions, WORK_DAYS, WORK_HOURS);
    }

    /**
     * Cinema with screens. Schedule is created based on {@code filmSession} parameter.
     *
     * @param name         cinema's name.
     * @param address      cinema's address.
     * @param screenTypes  list of types of screens in the cinema.
     *                     Must be the same size as {@code screenSpecs}.
     * @param screenSpecs  list of specifications of screens in the cinema.
     *                     Must be the same size as {@code screenTypes}.
     * @param filmSessions map between film and number of sessions to be shown in the cinema.
     *                     {@link  Cinema#addToSchedule(Map filmSessions)} for more information
     *                     on how schedule is created.
     * @param WORK_DAYS    number of working days for cinema.
     *                     Default value {@code 7}.
     * @throws CinemaException if object cannot be created with provided parameters.
     */
    public Cinema(String name, String address,
                  List<String> screenTypes,
                  List<String> screenSpecs,
                  Map<Film, Integer> filmSessions, int WORK_DAYS) throws CinemaException {
        this(name, address, screenTypes, screenSpecs, filmSessions, WORK_DAYS, WORK_HOURS);
    }

    /**
     * Cinema with screens. Schedule is created based on {@code filmSession} parameter.
     *
     * @param name         cinema's name.
     * @param address      cinema's address.
     * @param screenTypes  list of types of screens in the cinema.
     *                     Must be the same size as {@code screenSpecs}.
     * @param screenSpecs  list of specifications of screens in the cinema.
     *                     Must be the same size as {@code screenTypes}.
     * @param filmSessions map between film and number of sessions to be shown in the cinema.
     *                     {@link  Cinema#addToSchedule(Map filmSessions)} for more information
     *                     on how schedule is created.
     * @param WORK_DAYS    number of working days for cinema.
     *                     Default value {@code 7}.
     * @param WORK_HOURS   number of working hours per day for cinema.
     *                     Default value {@code 14}.
     * @throws CinemaException if object cannot be created with provided parameters.
     */
    public Cinema(String name, String address,
                  List<String> screenTypes,
                  List<String> screenSpecs,
                  Map<Film, Integer> filmSessions,
                  int WORK_DAYS, int WORK_HOURS) throws CinemaException {
        this(name, address, WORK_DAYS, WORK_HOURS);
        int screens = addScreens(screenTypes, screenSpecs);
        LOGGER.debug("Created {} screens from passed parameters", screens);
        int sessions = addToSchedule(filmSessions); // stopped here
        LOGGER.debug("Created {} sessions from passed parameters", sessions);
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public List<String> getScreens() {
        return screens.stream().map(Screen::toString).toList();
    }

    public List<String> getSchedule() {
        return schedule.stream().map(TimeSlot::toString).toList();
    }

    public List<Film> getFilms() {
        return films.keySet().stream().toList();
    }

    /**
     * Gets all sessions for specified {@code film}.
     *
     * @param film film which sessions to get.
     * @return String of sessions
     */
    public String getFilmSessions(Film film) {
        StringBuilder filmSession = new StringBuilder();
        filmSession.append("Film sessions for film ").append(film).append(": [");
        for (TimeSlot timeSlot : schedule) {
            if (timeSlot.film.equals(film)) {
                filmSession.append("{")
                        .append(timeSlot.day).append(", screen = ")
                        .append(timeSlot.screen.id).append(", ")
                        .append(timeSlot.start)
                        .append("}, ");
            }
        }
        filmSession.delete(filmSession.lastIndexOf(", "), filmSession.length());
        filmSession.append("]");
        return filmSession.toString();
    }

    /**
     * Adds screens to the cinema based on their specifications and types.
     *
     * @param screenTypes list of screen types.
     * @param screenSpecs list of screen specifications.
     * @return number of screens added.
     * @throws CinemaException if {@code screenTypes} and {@code screenSpecs} doesn't match {@code size()}.
     */
    public int addScreens(List<String> screenTypes, List<String> screenSpecs) throws CinemaException {
        if (screenTypes.size() != screenSpecs.size()) {
            LOGGER.error("Screen types size: {} is not equal to screen specs size: {} ", screenTypes, screenSpecs);
            throw new CinemaException("Screen types and screen specs do not match size");
        }
        int before = screens.size();
        for (int i = 0; i < screenTypes.size(); i++) {
            boolean isAdded = addScreen(screenTypes.get(i), screenSpecs.get(i));
            LOGGER.debug("Screen {} was added: {}", i, isAdded);
        }
        return screens.size() - before;
    }

    /**
     * Adds screen to the cinema.
     *
     * @param screenType type of the screen.
     * @param screenSpec specification of the screen.
     * @return {@code true} if screen was added.
     */
    public boolean addScreen(String screenType, String screenSpec) {
        boolean isAdded = false;
        Screen screen = new Screen(screens.size() + 1, screenType, screenSpec);
        if (!screens.contains(screen)) {
            isAdded = screens.add(screen);
        }
        return isAdded;
    }

    /**
     * Adds films sessions to schedule.
     *
     * @param filmSessions map between film and number of sessions to be shown.
     * @return number of added sessions.
     * @throws CinemaException if there was a problem in adding session.
     * @see #addToSchedule(Film, int) for more information on adding strategy.
     */
    public int addToSchedule(Map<Film, Integer> filmSessions) throws CinemaException {
        int before = schedule.size();
        filmSessions.forEach((film, sessions) -> {
            int added = addToSchedule(film, sessions);
            films.put(film, added);
            LOGGER.debug("Added {} sessions from passed parameters", added);
        });
        return schedule.size() - before;
    }

    /**
     * Adds film sessions to the schedule.
     * <p>
     * Number of actually added sessions depends on whether there is space
     * in screen's schedule for this film.
     * </p>
     * Screen and day are selected from the first and the earliest
     * to the last and the latest respectively.
     *
     * @param film     film which session is to be added.
     * @param sessions number of sessions to be added to schedule.
     * @return number of actually added sessions.
     * Returns {@code 0} if no sessions where added for the film.
     * @throws CinemaException if number of sessions is less than zero.
     */
    public int addToSchedule(Film film, int sessions) throws CinemaException {
        if (sessions <= 0) {
            LOGGER.error("Illegal sessions: {}", sessions);
            throw new CinemaException("Illegal sessions: " + sessions);
        }
        int before = schedule.size();
        for (int i = 0, dayCounter; i < sessions; ) {
            boolean hasAvailableScreens = false;
            // trying to add session to each screen
            for (Screen screen : screens) {
                // looking for an available time for selected screen
                if (i == sessions) {
                    break;
                }
                boolean isAvailable = false;
                for (dayCounter = 0; dayCounter < WORK_DAYS; dayCounter++) {
                    isAvailable = screen.hasAvailableTime(
                            FIRST_DAY.plusDays(dayCounter), film.watchTimeMinutes());
                    //found available time
                    if (isAvailable) {
                        break;
                    }
                }
                hasAvailableScreens = hasAvailableScreens || isAvailable;
                // no available time for selected screen
                if (!isAvailable) {
                    continue;
                }
                LocalTime start = screen.findFirstAvailableTime(
                        FIRST_DAY.plusDays(dayCounter), film.watchTimeMinutes());
                schedule.add(new TimeSlot(film, screen, FIRST_DAY.plusDays(dayCounter), start));
                i++;
            }
            // if all screens didn't have available time - stop
            if (!hasAvailableScreens) {
                break;
            }
        }
        return schedule.size() - before;
    }

    /**
     * Sorts schedule by day, film and session start.
     */
    public void sortSchedule() {
        schedule.sort(TimeSlot::compareTo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cinema cinema)) return false;
        return Objects.equals(name, cinema.name)
                && Objects.equals(address, cinema.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address);
    }

    @Override
    public String toString() {
        return "Cinema{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", screens=" + getScreens().toString() +
                ", schedule=" + getSchedule().toString() +
                '}';
    }
}
