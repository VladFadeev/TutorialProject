package org.example.util.create;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.util.exception.CreatorException;

import java.util.List;

public abstract class Creator<T> {
    protected final String fileName;
    protected final static Logger LOGGER = LogManager.getLogger();

    public Creator(String fileName) {
        this.fileName = fileName;
    }

    /***
     * Creates single object of T
     *
     * @return single object of T
     * @throws CreatorException if necessary
     */
    public T create() throws CreatorException {
        LOGGER.error("Create operation is unsupported");
        throw new CreatorException("Create operation is unsupported");
    }

    /***
     * Creates single object of T with random values
     *
     * @return single object of T with random values
     * @throws CreatorException if necessary
     */
    public T createRandom() throws CreatorException {
        LOGGER.error("Create Random operation is unsupported");
        throw new CreatorException("Create Random operation is unsupported");
    }

    /***
     * Creates List of objects T
     *
     * @return List of objects T
     * @throws CreatorException if necessary
     */
    public List<T> createList() throws CreatorException {
        LOGGER.error("Create List operation is unsupported");
        throw new CreatorException("Create List operation is unsupported");
    }

    /***
     * Creates List of objects T with random values
     *
     * @return List of objects T with random values
     * @throws CreatorException if necessary
     */
    public List<T> createListRandom() throws CreatorException {
        LOGGER.error("Create List Random operation is unsupported");
        throw new CreatorException("Create List Random operation is unsupported");
    }
}
