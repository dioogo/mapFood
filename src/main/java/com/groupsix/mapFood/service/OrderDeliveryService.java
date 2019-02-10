package com.groupsix.mapFood.service;

import org.springframework.stereotype.Service;

import com.groupsix.mapFood.entity.OrderDeliveryEntity;
import com.groupsix.mapFood.entity.OrderEntity;

@Service
public class OrderDeliveryService {

	public OrderDeliveryEntity create(OrderEntity orderEntity) {
		OrderDeliveryEntity orderDeliveryEntity = new OrderDeliveryEntity();

		orderDeliveryEntity.setOrder(orderEntity);
		orderDeliveryEntity.setDestinationLat(orderEntity.getCustomer().getLat());
		orderDeliveryEntity.setDestinationLon(orderEntity.getCustomer().getLon());
		
		return orderDeliveryEntity;
	}

}
