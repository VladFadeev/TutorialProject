package org.example.chapter.three.task7.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class RationalFraction implements Cloneable {
    private final static Logger LOGGER = LogManager.getLogger();
    private int numerator;
    private int denominator;

    public RationalFraction(@JsonProperty("numerator") int numerator,
                            @JsonProperty("denominator") int denominator) throws IllegalArgumentException{
        if (denominator == 0) {
            LOGGER.error("denominator is zero");
            throw  new IllegalArgumentException("denominator is zero");
        }
        if (denominator < 0) {
            LOGGER.warn("denominator is negative");
            numerator = -numerator;
            denominator = -denominator;
        }
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    public RationalFraction add(RationalFraction other) {
        numerator = numerator * other.denominator + other.numerator * denominator;
        denominator = denominator * other.denominator;
        return this.reduce();
    }

    public RationalFraction subtract(RationalFraction other) {
        numerator = numerator * other.denominator - other.numerator * denominator;
        denominator = denominator * other.denominator;
        return this.reduce();
    }

    public RationalFraction multiply(RationalFraction other) {
        numerator = numerator * other.numerator;
        denominator = denominator * other.denominator;
        return this.reduce();
    }

    /***
     * Reduces numerator and denominator by their Greatest Common Divider
     *
     * @return reduced object
     */
    private RationalFraction reduce() {
        int gcd = gcd(numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;
        return this;
    }

    private int gcd(int a, int b) {
        if (a == 0) return b;
        if (b == 0) return a;
        if (b > a) return gcd(a, b % a);
        return gcd(b, a % b);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RationalFraction that)) return false;
        return numerator == that.numerator && denominator == that.denominator;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numerator, denominator);
    }

    @Override
    public String toString() {
        return "{" +
                numerator + (denominator != 1 ? "/" + denominator : "") +
                "}";
    }

    @Override
    public RationalFraction clone() {
        try {
            return (RationalFraction) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
