package com.groupsix.mapFood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupsix.mapFood.entity.CustomerEntity;
import com.groupsix.mapFood.entity.OrderDeliveryEntity;
import com.groupsix.mapFood.pojo.Order;
import com.groupsix.mapFood.repository.CustomerRepository;
import com.groupsix.mapFood.repository.RestaurantRepository;

@Service
public class OrderDeliveryService {

	@Autowired
	private CustomerRepository customerRepository;
	
	public OrderDeliveryEntity getOrderDelivery(final Order order) {
		
		final OrderDeliveryEntity orderDeliveryEntity = new OrderDeliveryEntity();
		
		final CustomerEntity customer = customerRepository.getOne(order.getCustomerId());
		orderDeliveryEntity.setDestinationLat(customer.getLat());
		orderDeliveryEntity.setDestinationLon(customer.getLon());
		
		return orderDeliveryEntity;
	}
}
