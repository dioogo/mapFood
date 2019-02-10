package com.groupsix.mapFood.pojo;

import java.sql.Timestamp;

public class CacheMotoboyDistance {

	private CacheMotoboy cacheMotoboy;
	private Timestamp timestampArrivesAtRestaurant;
	
	public CacheMotoboy getCacheMotoboy() {
		return cacheMotoboy;
	}
	
	public void setCacheMotoboy(CacheMotoboy cacheMotoboy) {
		this.cacheMotoboy = cacheMotoboy;
	}

	public Timestamp getTimestampArrivesAtRestaurant() {
		return timestampArrivesAtRestaurant;
	}

	public void setTimestampArrivesAtRestaurant(Timestamp timestampArrivesAtRestaurant) {
		this.timestampArrivesAtRestaurant = timestampArrivesAtRestaurant;
	}

	public CacheMotoboyDistance(CacheMotoboy cacheMotoboy, Timestamp timestampArrivesAtRestaurant) {
		super();
		this.cacheMotoboy = cacheMotoboy;
		this.timestampArrivesAtRestaurant = timestampArrivesAtRestaurant;
	}
}
