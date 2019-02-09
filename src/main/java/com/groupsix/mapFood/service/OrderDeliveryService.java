package com.groupsix.mapFood.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.maps.model.LatLng;
import com.groupsix.mapFood.entity.OrderDeliveryEntity;
import com.groupsix.mapFood.entity.OrderEntity;
import com.groupsix.mapFood.util.TimestampUtil;

@Service
public class OrderDeliveryService {

	@Autowired
	private MotoboyService motoboyService;
	
	@Autowired
	private GoogleMapsService googleMapsService;
	
	public OrderDeliveryEntity create(OrderEntity orderEntity) {
		OrderDeliveryEntity orderDeliveryEntity = new OrderDeliveryEntity();

		orderDeliveryEntity.setOrder(orderEntity);
		orderDeliveryEntity.setDestinationLat(orderEntity.getCustomer().getLat());
		orderDeliveryEntity.setDestinationLon(orderEntity.getCustomer().getLon());
		
		// busca o motoboy mais proximo do restaurant
		orderDeliveryEntity.setMotoboy(motoboyService.findNearby(
				orderDeliveryEntity.getOrder().getRestaurant().getLat(), 
				orderDeliveryEntity.getOrder().getRestaurant().getLon()).get(0));
		
		Timestamp timeToRestaurant = estimateTimeToRestaurant(orderDeliveryEntity);
		
		
		System.out.println("Atual             " + new Timestamp(System.currentTimeMillis()));
		System.out.println("Depois            " + timeToRestaurant);
		
		orderDeliveryEntity.setEstimatedTimeToRestaurant(timeToRestaurant);
		
		return orderDeliveryEntity;
	}

	private Timestamp estimateTimeToRestaurant(OrderDeliveryEntity orderDeliveryEntity) {
		LatLng start = new LatLng(orderDeliveryEntity.getMotoboy().getLat(), orderDeliveryEntity.getMotoboy().getLon());
		LatLng end = new LatLng(orderDeliveryEntity.getOrder().getRestaurant().getLat(), orderDeliveryEntity.getOrder().getRestaurant().getLon());
		
		return TimestampUtil.addSeconds(googleMapsService.timeToReach(start, end));
	}
}
