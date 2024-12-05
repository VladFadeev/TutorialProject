package org.example.utils.factory;

import org.example.utils.TaskEntity;

import java.util.Random;

public abstract class TaskFactory {
    private final static int RAND_SEED = 2024;
    protected final static Random RANDOM = new Random(RAND_SEED);
    public abstract TaskEntity createTaskEntity();
}
