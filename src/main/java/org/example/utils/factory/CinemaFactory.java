package org.example.utils.factory;

import org.example.chapter.five.task17.entity.Cinema;
import org.example.utils.TaskEntity;

public class CinemaFactory extends TaskFactory {
    private final static String[] CINEMA_NAMES = {"Kiev", "October", "SilverScreen Galileo",
            "SilverScreen Dana Mall", "Mir", "Central"};
    private final static String[] CINEMA_ADDRESSES = {"Kievskiy skver 1", "Independence avenue 55", "Bobruiskaya 3",
    "Independence avenue 104", "Masherova avenue 5", "Independence avenue 2"};
    private final static
    @Override
    public TaskEntity createTaskEntity() {
        int cinemaId = RANDOM.nextInt(CINEMA_NAMES.length);
        Cinema cinema = new Cinema(CINEMA_NAMES[cinemaId], CINEMA_ADDRESSES[cinemaId], );
        return null;
    }
}
