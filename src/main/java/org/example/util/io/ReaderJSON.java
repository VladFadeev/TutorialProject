package org.example.util.io;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.util.exception.ReaderException;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Utility class to read user input from JSON files.
 */
public class ReaderJSON {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * This class should be used directly, without object initialization.
     */
    private ReaderJSON() {
    }

    /**
     * Reads object of class {@code T} from file.
     *
     * @param fileName path to the file.
     * @param clazz    class of {@code T}.
     * @param <T>      class to read.
     * @return object of class {@code T}.
     * @throws ReaderException if necessary.
     */
    public static <T> T read(String fileName, Class<T> clazz) throws ReaderException {
        T result;
        try {
            result = MAPPER.readValue(new File(fileName), clazz);
            LOGGER.debug("Read value: {}", result);
        } catch (DatabindException e) {
            LOGGER.error("JSON structure mismatch when reading single value at {}", fileName);
            throw new ReaderException("JSON structure mismatch when reading single value at " + fileName, e);
        } catch (StreamReadException e) {
            LOGGER.error("JSON content parsing error when reading single value at {}", fileName);
            throw new ReaderException("JSON content parsing error when reading single value at " + fileName, e);
        } catch (IOException e) {
            LOGGER.error("Couldn't read single value from: {}", fileName);
            throw new ReaderException("Couldn't read single value from: " + fileName, e);
        }
        return result;
    }

    /**
     * Reads {@link List} of objects of class {@code T} from file.
     *
     * @param fileName path to the file.
     * @param clazz    class of {@code T}.
     * @param <T>      class to read.
     * @return {@link List} of objects of class {@code T}.
     * @throws ReaderException if necessary.
     */
    public static <T> List<T> readList(String fileName, Class<T> clazz) throws ReaderException {
        List<T> result;
        try {
            result = MAPPER.readValue(new File(fileName),
                    MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            LOGGER.debug("Read list of values: {}", result);
        } catch (DatabindException e) {
            LOGGER.error("JSON structure mismatch when reading list of values at {}", fileName);
            throw new ReaderException("JSON structure mismatch when reading list of values at " + fileName, e);
        } catch (StreamReadException e) {
            LOGGER.error("JSON content parsing error when reading list of values at {}", fileName);
            throw new ReaderException("JSON content parsing error when reading list of values at " + fileName, e);
        } catch (IOException e) {
            LOGGER.error("Couldn't read list from: {}", fileName);
            throw new ReaderException("Couldn't read list from: " + fileName, e);
        }
        return result;
    }

    /**
     * Adds deserializer for class {@code T}.
     *
     * @param clazz        class of {@code T}.
     * @param deserializer deserializer to add.
     * @param <T>          which class' deserializer to add.
     */
    public static <T> void addDeserializer(Class<T> clazz, StdDeserializer<T> deserializer) {
        MAPPER.registerModules((new SimpleModule()).addDeserializer(clazz, deserializer));
    }
}
