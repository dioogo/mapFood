package com.groupsix.mapFood.service;

import com.groupsix.mapFood.entity.CustomerEntity;
import com.groupsix.mapFood.entity.OrderDeliveryEntity;
import com.groupsix.mapFood.entity.RestaurantEntity;
import com.groupsix.mapFood.repository.CustomerRepository;
import com.groupsix.mapFood.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

	@Autowired
	private RestaurantRepository restaurantRepository;

	public RestaurantEntity getRestaurant(final Integer restaurantId) {
		return restaurantRepository.getOne(restaurantId);
	}
}
