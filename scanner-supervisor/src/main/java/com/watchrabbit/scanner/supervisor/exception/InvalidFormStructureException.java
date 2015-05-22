package com.watchrabbit.scanner.supervisor.exception;

/**
 *
 * @author Mariusz
 */
public class InvalidFormStructureException extends Exception {

    public InvalidFormStructureException() {
    }

    public InvalidFormStructureException(String message) {
        super(message);
    }

    public InvalidFormStructureException(String message, Throwable cause) {
        super(message, cause);
    }

}
