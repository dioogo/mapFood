package com.groupsix.mapFood.pojo;

import java.sql.Timestamp;

public class RestaurantOrderReport {

	private Integer orderId;
	private Integer motoboyId;
	private Integer customerId;
	private Integer total;
	private Long distance;
	private Long litrosConsumidos;
	private Timestamp estimated_time_to_delivery;
	private Timestamp estimated_time_to_restaurant;
	
	public void setOrderId(Integer orderId) {
		orderId = orderId;
	}

	public void setMotoboyId(Integer motoboyId) {
		motoboyId = motoboyId;
	}

	public void setCustomerId(Integer customerId) {
		customerId = customerId;
	}

	public void setTotal(Integer total) {
		total = total;
	}

	public void setDistance(Long distance) {
		distance = distance;
	}

	public void setLitrosConsumidos(Long litrosConsumidos) {
		litrosConsumidos = litrosConsumidos;
	}

	public void setEstimated_time_to_delivery(Timestamp estimated_time_to_delivery) {
		estimated_time_to_delivery = estimated_time_to_delivery;
	}

	public void setEstimated_time_to_restaurant(Timestamp estimated_time_to_restaurant) {
		estimated_time_to_restaurant = estimated_time_to_restaurant;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public Integer getMotoboyId() {
		return motoboyId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public Integer getTotal() {
		return total;
	}

	public Long getDistance() {
		return distance;
	}

	public Long getLitrosConsumidos() {
		return litrosConsumidos;
	}

	public Timestamp getEstimated_time_to_delivery() {
		return estimated_time_to_delivery;
	}

	public Timestamp getEstimated_time_to_restaurant() {
		return estimated_time_to_restaurant;
	}
}
