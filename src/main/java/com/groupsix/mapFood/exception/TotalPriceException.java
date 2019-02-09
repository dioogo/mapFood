package com.groupsix.mapFood.exception;

public class TotalPriceException extends Exception {

    public TotalPriceException() {
        super("Total sum of items prices does not match order total price.");
    }
}
