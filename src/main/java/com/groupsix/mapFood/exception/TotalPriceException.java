package com.groupsix.mapFood.exception;

public class TotalPriceException extends Exception {

    public TotalPriceException() {
        super("Sum of items prices does not match order total price.");
    }
}
