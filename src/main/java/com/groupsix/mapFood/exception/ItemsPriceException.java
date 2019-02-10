package com.groupsix.mapFood.exception;

public class ItemsPriceException extends Exception {

	private static final long serialVersionUID = 1L;

	public ItemsPriceException() {
        super("Quantity of items and individual price does not match item total.");
    }
}
