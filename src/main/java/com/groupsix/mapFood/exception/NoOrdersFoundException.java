package com.groupsix.mapFood.exception;

public class NoOrdersFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public NoOrdersFoundException() {
        super("No order could be found in this interval of time.");
    }
}
