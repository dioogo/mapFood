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
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {

	@Mock
	private OrderService orderService;
	
	@InjectMocks
	private OrderController controller;
	
	@Test
	public void testCreateOrder() {
		final Order order = new Order();
		final BindingResult validator = new BeanPropertyBindingResult(order, "order");

		try {
			when(orderService.createOrder(order)).thenReturn(order);
		} catch (Exception e) {
			e.printStackTrace();
		}

		final ResponseEntity<?> response = controller.createOrder(order, validator);
		
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(order, response.getBody());
	}
}
