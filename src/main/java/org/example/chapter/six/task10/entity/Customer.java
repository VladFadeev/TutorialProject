package org.example.chapter.six.task10.entity;

import java.util.Objects;

/**
 * Placeholder class that represents {@code Customer} for
 * task 10 from chapter six. It can be owner of the
 * building or tenant.
 *
 * @param name customer's name.
 * @param age customer's age.
 */
public record Customer(String name, int age) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        return age == customer.age && Objects.equals(name, customer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
