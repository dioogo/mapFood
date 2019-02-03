package com.groupsix.mapFood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.groupsix.mapFood.pojo.Order;
import com.groupsix.mapFood.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@PostMapping
	public ResponseEntity<Order> createOrder(final @RequestBody Order order) {
		return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(order)); 
	}
}
