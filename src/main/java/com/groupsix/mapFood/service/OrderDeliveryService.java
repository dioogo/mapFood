package com.groupsix.mapFood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupsix.mapFood.entity.CustomerEntity;
import com.groupsix.mapFood.entity.MotoboyEntity;
import com.groupsix.mapFood.entity.OrderDeliveryEntity;
import com.groupsix.mapFood.repository.CustomerRepository;
import com.groupsix.mapFood.repository.MotoboyRepository;

@Service
public class OrderDeliveryService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private MotoboyRepository motoboyRepository;
	
	public OrderDeliveryEntity getOrderDelivery(final Integer customerId) {
		
		final OrderDeliveryEntity orderDeliveryEntity = new OrderDeliveryEntity();
		
		final CustomerEntity customer = customerRepository.getOne(customerId);
		orderDeliveryEntity.setDestinationLat(customer.getLat());
		orderDeliveryEntity.setDestinationLon(customer.getLon());
		
		MotoboyEntity motoboy= motoboyRepository.findNearby(
				orderDeliveryEntity.getOrder().getRestaurant().getLat(), 
				orderDeliveryEntity.getOrder().getRestaurant().getLat()).get(0);
		
		orderDeliveryEntity.setMotoboy(motoboy);
		
		return orderDeliveryEntity;
	}
}
