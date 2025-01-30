package org.example.chapter.six.task10.entity;

import java.util.Objects;

/**
 * Represents Rentable Room in {@code ShoppingCenter}.
 * Extends {@code Room} and adds {@code tenantNumber}
 * with default value {@code -1}.
 */
public class RentableRoom extends Room {
    private int tenantNumber = -1;

    /**
     * Default constructor for {@code RentableRoom}.
     *
     * @param area room's area in square meters.
     */
    public RentableRoom(double area) {
        super(area);
    }

    public int getTenantNumber() {
        return tenantNumber;
    }

    public void setTenantNumber(int tenantNumber) {
        this.tenantNumber = tenantNumber;
    }

    public boolean isAvailable() {
        return tenantNumber == -1;
    }

    /**
     * Makes {@code isAvailable()} return true.
     */
    public void terminateTenantContract() {
        tenantNumber = -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RentableRoom that)) return false;
        if (!super.equals(o)) return false;
        return tenantNumber == that.tenantNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tenantNumber);
    }
}
