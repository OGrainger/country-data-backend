package com.arca.technicalaptitudetest.enums;

public enum Granularity {
    DAY("day"),
    WEEK("week"),
    MONTH("month"),
    YEAR("year");

    private final String text;

    Granularity(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
