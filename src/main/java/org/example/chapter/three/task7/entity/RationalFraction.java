package org.example.chapter.three.task7.entity;

import java.util.Objects;

public class RationalFraction implements Cloneable {
    private int numerator;
    private int denominator;

    public RationalFraction(int numerator, int denominator) throws IllegalArgumentException{
        if (denominator == 0) {
            throw  new IllegalArgumentException("denominator is zero");
        }
        if (denominator < 0) {
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
        other.numerator = -other.numerator;
        return this.add(other);
    }

    public RationalFraction multiply(RationalFraction other) {
        numerator = numerator * other.numerator;
        denominator = denominator * other.denominator;
        return this.reduce();
    }

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
                numerator + "/" + denominator +
                '}';
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
