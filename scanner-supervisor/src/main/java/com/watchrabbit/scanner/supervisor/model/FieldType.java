package com.watchrabbit.scanner.supervisor.model;

/**
 *
 * @author Mariusz
 */
public enum FieldType {

    CHECKBOX, EMAIL, HIDDEN, PASSWORD, TEXT, URL, OTHER;

    public static FieldType parse(String type) {
        try {
            return FieldType.valueOf(type);
        } catch (IllegalArgumentException ex) {
            return FieldType.OTHER;
        }
    }
}
