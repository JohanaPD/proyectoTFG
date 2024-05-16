package org.example.proyectotfg.exceptions;

public class DataAccessException extends Exception {
    public DataAccessException() {
    }
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataAccessException(String message) {
        super(message);
    }
}