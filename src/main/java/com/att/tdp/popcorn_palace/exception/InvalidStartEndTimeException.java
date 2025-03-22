package com.att.tdp.popcorn_palace.exception;

public class InvalidStartEndTimeException extends RuntimeException {
    public InvalidStartEndTimeException(String message) {
        super(message);
    }
}