package com.groupsix.mapFood.exception;

public class CustomerTooFarException extends Exception {

	private static final long serialVersionUID = 1L;

	public CustomerTooFarException() {
        super("The customer is too far from the restaurant.");
    }
}
