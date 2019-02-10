package com.groupsix.mapFood.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.groupsix.mapFood.exception.CustomerTooFarException;
import com.groupsix.mapFood.exception.DiferentRestaurantException;
import com.groupsix.mapFood.exception.ItemsPriceException;
import com.groupsix.mapFood.exception.TotalPriceException;
import com.groupsix.mapFood.pojo.Order;
import com.groupsix.mapFood.service.OrderService;
import org.springframework.validation.BindingResult;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {

	@Mock
	private OrderService orderService;
	
	@InjectMocks
	private OrderController controller;
	
	@Mock
	private BindingResult validator;
	
	@Test
	public void testCreateOrder() throws Exception {
		final Order order = new Order();

		when(orderService.createOrder(order)).thenReturn(order);
		when(validator.hasErrors()).thenReturn(false);

		final ResponseEntity<?> response = controller.createOrder(order, validator);
		
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(order, response.getBody());
	}
	
	@Test
	public void testCreateOrderWithInvalidData() throws Exception {
		final Order order = new Order();

		when(orderService.createOrder(order)).thenReturn(order);
		when(validator.hasErrors()).thenReturn(true);

		final ResponseEntity<?> response = controller.createOrder(order, validator);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
	}
	
	@Test
	public void testCreateOrderWithInvalidTotalPrice() throws Exception {
		final Order order = new Order();

		when(orderService.createOrder(order)).thenThrow(new TotalPriceException());

		final ResponseEntity<?> response = controller.createOrder(order, validator);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
		assertEquals("Total sum of items prices does not match order total price.", response.getBody());
	}
	
	@Test
	public void testCreateOrderWithInvalidItemsPriceException() throws Exception {
		final Order order = new Order();

		when(orderService.createOrder(order)).thenThrow(new ItemsPriceException());

		final ResponseEntity<?> response = controller.createOrder(order, validator);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
		assertEquals("Quantity of items and individual price does not match item total.", response.getBody());
	}
	
	@Test
	public void testCreateOrderWithDiferentRestaurantException() throws Exception {
		final Order order = new Order();

		when(orderService.createOrder(order)).thenThrow(new DiferentRestaurantException());

		final ResponseEntity<?> response = controller.createOrder(order, validator);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
		assertEquals("Not all items in the order are from the same restaurant.", response.getBody());
	}
	
	@Test
	public void testCreateOrderWithCustomerTooFarException() throws Exception {
		final Order order = new Order();

		when(orderService.createOrder(order)).thenThrow(new CustomerTooFarException());

		final ResponseEntity<?> response = controller.createOrder(order, validator);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
		assertEquals("The customer is too far from the restaurant.", response.getBody());
	}
}
