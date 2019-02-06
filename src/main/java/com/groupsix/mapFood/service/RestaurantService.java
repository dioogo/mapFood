package com.groupsix.mapFood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupsix.mapFood.entity.RestaurantEntity;
import com.groupsix.mapFood.repository.RestaurantRepository;

@Service
public class RestaurantService {

	@Autowired
	private RestaurantRepository restaurantRepository;
	
	public RestaurantEntity findById(Integer id) {
		return restaurantRepository.findById(id).get();
	}
	
}
