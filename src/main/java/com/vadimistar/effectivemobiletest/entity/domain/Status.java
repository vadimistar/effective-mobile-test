package com.vadimistar.effectivemobiletest.entity.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.vadimistar.effectivemobiletest.exception.InvalidStatusException;

public enum Status {

    PENDING,
    IN_PROCESS,
    DONE;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

    @JsonCreator
    public static Status fromString(String value) {
        try {
            return Status.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidStatusException(String.format("Неверный статус: '%s'", value));
        }
    }
}
