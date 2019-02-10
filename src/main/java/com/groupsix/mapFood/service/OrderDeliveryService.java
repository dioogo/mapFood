package com.groupsix.mapFood.service;

import org.springframework.stereotype.Service;

import com.groupsix.mapFood.entity.CustomerEntity;
import com.groupsix.mapFood.entity.OrderDeliveryEntity;

@Service
public class OrderDeliveryService {

	public OrderDeliveryEntity create(CustomerEntity customerEntity) {
		OrderDeliveryEntity orderDeliveryEntity = new OrderDeliveryEntity();

		orderDeliveryEntity.setDestinationLat(customerEntity.getLat());
		orderDeliveryEntity.setDestinationLon(customerEntity.getLon());
		
		return orderDeliveryEntity;
	}

}
