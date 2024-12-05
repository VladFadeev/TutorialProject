package org.example.test;

public class Parent {
    protected final int parentId;

    public Parent() {
        this.parentId = 0;
    }

    public Parent(int id) {
        parentId = id;
    }

    public int getInt() {
        return parentId;
    }
}
