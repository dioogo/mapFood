package com.groupsix.mapFood.pojo;

import java.sql.Timestamp;

public class CacheMotoboyOrder {

	private Integer id;
	private Integer restaurantId;
	private CacheDestination cacheDestination;
	private Timestamp timeToMotoboyArrivesAtRestaurant;
	private Timestamp timeToDelivery;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getRestaurantId() {
		return restaurantId;
	}
	
	public void setRestaurantId(Integer restaurantId) {
		this.restaurantId = restaurantId;
	}
	
	public CacheDestination getCacheDestination() {
		return cacheDestination;
	}
	
	public void setCacheDestination(CacheDestination cacheDestination) {
		this.cacheDestination = cacheDestination;
	}

	public Timestamp getTimeToMotoboyArrivesAtRestaurant() {
		return timeToMotoboyArrivesAtRestaurant;
	}

	public void setTimeToMotoboyArrivesAtRestaurant(Timestamp timeToMotoboyArrivesAtRestaurant) {
		this.timeToMotoboyArrivesAtRestaurant = timeToMotoboyArrivesAtRestaurant;
	}

	public Timestamp getTimeToDelivery() {
		return timeToDelivery;
	}

	public void setTimeToDelivery(Timestamp timeToDelivery) {
		this.timeToDelivery = timeToDelivery;
	}
}
