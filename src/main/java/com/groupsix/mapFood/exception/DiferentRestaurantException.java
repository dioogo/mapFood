package com.groupsix.mapFood.exception;

public class DiferentRestaurantException extends Exception {

	private static final long serialVersionUID = 1L;

	public DiferentRestaurantException() {
        super("Not all items in the order are from the same restaurant.");
    }
}
