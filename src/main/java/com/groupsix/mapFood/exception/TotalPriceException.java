package com.groupsix.mapFood.exception;

public class TotalPriceException extends Exception {

	private static final long serialVersionUID = 1L;

	public TotalPriceException() {
        super("Total sum of items prices does not match order total price.");
    }
}
