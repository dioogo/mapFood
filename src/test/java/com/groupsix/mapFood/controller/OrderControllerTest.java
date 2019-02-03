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

import com.groupsix.mapFood.pojo.Order;
import com.groupsix.mapFood.service.OrderService;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {

	@Mock
	private OrderService orderService;
	
	@InjectMocks
	private OrderController controller;
	
	@Test
	public void testCreateOrder() {
		final Order order = new Order();
		when(orderService.createOrder(order)).thenReturn(order);
		
		final ResponseEntity<Order> response = controller.createOrder(order);
		
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(order, response.getBody());
	}
}
