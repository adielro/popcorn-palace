package com.att.tdp.popcorn_palace.exception;

public class SeatAlreadyTakenException extends RuntimeException {
    public SeatAlreadyTakenException(String message) {
        super(message);
    }
}