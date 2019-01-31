package com.groupsix.mapFood.pojo;

public class OrderDelivery {

	private Integer id;
	private Integer orderId;
	private Integer motoboyId;
	private Double destinationLat;
	private Double destinationLon;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getOrderId() {
		return orderId;
	}
	
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	public Integer getMotoboyId() {
		return motoboyId;
	}
	
	public void setMotoboyId(Integer motoboyId) {
		this.motoboyId = motoboyId;
	}
	
	public Double getDestinationLat() {
		return destinationLat;
	}
	
	public void setDestinationLat(Double destinationLat) {
		this.destinationLat = destinationLat;
	}
	
	public Double getDestinationLon() {
		return destinationLon;
	}
	
	public void setDestinationLon(Double destinationLon) {
		this.destinationLon = destinationLon;
	}
}
