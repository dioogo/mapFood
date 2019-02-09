package com.groupsix.mapFood.exception;

public class DiferentRestaurantException extends Exception {

    public DiferentRestaurantException() {
        super("Not all items in the order are from the same restaurant.");
    }
}
