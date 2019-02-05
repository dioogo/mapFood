package com.groupsix.mapFood.exception;

public class CustomerTooFarException extends Exception {

    public CustomerTooFarException() {
        super("The customer is too far from the restaurant.");
    }
}
