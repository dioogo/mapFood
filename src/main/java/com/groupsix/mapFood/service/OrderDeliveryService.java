package com.groupsix.mapFood.service;

import com.groupsix.mapFood.entity.RestaurantEntity;
import com.groupsix.mapFood.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupsix.mapFood.entity.CustomerEntity;
import com.groupsix.mapFood.entity.OrderDeliveryEntity;
import com.groupsix.mapFood.repository.CustomerRepository;

@Service
public class OrderDeliveryService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private RestaurantRepository restaurantRepository;

	public OrderDeliveryEntity getOrderDelivery(final Integer customerId) {
		
		final OrderDeliveryEntity orderDeliveryEntity = new OrderDeliveryEntity();
		
		final CustomerEntity customer = customerRepository.getOne(customerId);
		orderDeliveryEntity.setDestinationLat(customer.getLat());
		orderDeliveryEntity.setDestinationLon(customer.getLon());
		
		return orderDeliveryEntity;
	}

	public CustomerEntity getCustomerFromOrderDelivery(final Integer customerId) {
		return customerRepository.getOne(customerId);
	}

	public RestaurantEntity getRestaurantFromOrderDelivery(final Integer restaurantId) {
		return restaurantRepository.getOne(restaurantId);
	}
}
