package com.groupsix.mapFood.exception;

public class ItemsPriceException extends Exception {

    public ItemsPriceException() {
        super("Quantity of items and individual price does not match item total.");
    }
}
