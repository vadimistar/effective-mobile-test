package com.vadimistar.effectivemobiletest.entity.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.vadimistar.effectivemobiletest.exception.InvalidPriorityException;

public enum Priority {

    HIGH,
    MEDIUM,
    LOW;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

    @JsonCreator
    public static Priority fromString(String value) {
        try {
            return Priority.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidPriorityException(String.format("Неверный приоритет: '%s'", value));
        }
    }
}

