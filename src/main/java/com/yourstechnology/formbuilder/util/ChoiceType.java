package com.yourstechnology.formbuilder.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ChoiceType {
    SHORT_ANSWER("short answer"),
    PARAGRAPH("paragraph"),
    DATE("date"),
    MULTIPLE_CHOICE("multiple choice"),
    DROPDOWN("dropdown"),
    CHECKBOXES("checkboxes");

    private final String label;

    ChoiceType(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static ChoiceType fromValue(String value) {
        for (ChoiceType ct : values()) {
            if (ct.label.equalsIgnoreCase(value.trim())) {
                return ct;
            }
        }
        throw new IllegalArgumentException("Invalid choice type: " + value);
    }
}

