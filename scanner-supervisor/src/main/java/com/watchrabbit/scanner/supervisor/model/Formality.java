package com.watchrabbit.scanner.supervisor.model;

/**
 *
 * @author Mariusz
 */
public enum Formality {

    HIGH(100), AVERAGE(50), LOW(10);

    private final Integer factor;

    Formality(Integer factor) {
        this.factor = factor;
    }

    public Integer getFactor() {
        return factor;
    }

}
