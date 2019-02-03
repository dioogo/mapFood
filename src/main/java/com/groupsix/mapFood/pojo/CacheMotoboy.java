package com.groupsix.mapFood.pojo;

import java.util.List;

public class CacheMotoboy {

	private Integer id;
	private Double lat;
	private Double lon;
	private List<CacheMotoboyOrder> orders;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}
	
	public Double getLon() {
		return lon;
	}
	
	public void setLon(Double lon) {
		this.lon = lon;
	}
	
	public List<CacheMotoboyOrder> getOrders() {
		return orders;
	}
	
	public void setOrders(List<CacheMotoboyOrder> orders) {
		this.orders = orders;
	}
}
