package com.groupsix.mapFood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.groupsix.mapFood.pojo.Order;
import com.groupsix.mapFood.service.OrderService;

import javax.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@PostMapping
	public ResponseEntity<?> createOrder(final @RequestBody @Valid Order order, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(bindingResult.getFieldErrors());
		}
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(order));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
		}
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<?> listOrder(@PathVariable Integer id){
		if(orderService.listOrder(id).isEmpty()){
			return ResponseEntity.ok("Nenhum pedido");
		}else{
			return ResponseEntity.ok(orderService.listOrder(id));
		}

	}
}
