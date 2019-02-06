package com.groupsix.mapFood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupsix.mapFood.entity.CustomerEntity;
import com.groupsix.mapFood.entity.OrderDeliveryEntity;

@Service
public class OrderDeliveryService {

	@Autowired
	private CustomerService customerService;

	public OrderDeliveryEntity getOrderDelivery(final Integer customerId) {
		
		final OrderDeliveryEntity orderDeliveryEntity = new OrderDeliveryEntity();
		
		final CustomerEntity customer = customerService.getCustomer(customerId);
		orderDeliveryEntity.setDestinationLat(customer.getLat());
		orderDeliveryEntity.setDestinationLon(customer.getLon());
		
		return orderDeliveryEntity;
	}
}
