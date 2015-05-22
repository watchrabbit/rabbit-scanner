package com.watchrabbit.scanner.supervisor.model;

import com.watchrabbit.commons.exception.SystemException;

/**
 *
 * @author Mariusz
 */
public enum ElementType {

    INPUT, TEXTAREA, SELECT;

    public static ElementType parse(String type) {
        try {
            return ElementType.valueOf(type);
        } catch (IllegalArgumentException ex) {
            throw new SystemException("Cannot parse to ElementType " + type);
        }
    }
}
