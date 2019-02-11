package com.groupsix.mapFood.pojo;

import com.groupsix.mapFood.entity.OrderEntity;

import java.util.List;

public class RestaurantOrdersReport {

	private Integer totalNumOfCustomers;	// ok
	private Long averageTimeToDelivery;		// ok
	private Double averagePricePerDelivery;	//
	private Double averageCustomerDistance;	//
	private Double averageOrderTotal;		// ok
	private Integer highestOrderTotal;		// ok
	private Integer totalNumOfMotoboys;		// ok
	private Integer totalNumOfOrders;		// ok
	private List<OrderEntity> orders;		// ok

	public Integer getTotalNumOfCustomers() {
		return totalNumOfCustomers;
	}

	public void setTotalNumOfCustomers(Integer totalNumOfCustomers) {
		this.totalNumOfCustomers = totalNumOfCustomers;
	}

	public Long getAverageTimeToDelivery() {
		return averageTimeToDelivery;
	}

	public void setAverageTimeToDelivery(Long averageTimeToDelivery) {
		this.averageTimeToDelivery = averageTimeToDelivery;
	}

	public Double getAveragePricePerDelivery() {
		return averagePricePerDelivery;
	}

	public void setAveragePricePerDelivery(Double averagePricePerDelivery) {
		this.averagePricePerDelivery = averagePricePerDelivery;
	}

	public Double getAverageCustomerDistance() {
		return averageCustomerDistance;
	}

	public void setAverageCustomerDistance(Double averageCustomerDistance) {
		this.averageCustomerDistance = averageCustomerDistance;
	}

	public Double getAverageOrderTotal() {
		return averageOrderTotal;
	}

	public void setAverageOrderTotal(Double averageOrderTotal) {
		this.averageOrderTotal = averageOrderTotal;
	}

	public Integer getHighestOrderTotal() {
		return highestOrderTotal;
	}

	public void setHighestOrderTotal(Integer highestOrderTotal) {
		this.highestOrderTotal = highestOrderTotal;
	}

	public Integer getTotalNumOfMotoboys() {
		return totalNumOfMotoboys;
	}

	public void setTotalNumOfMotoboys(Integer totalNumOfMotoboys) {
		this.totalNumOfMotoboys = totalNumOfMotoboys;
	}

	public Integer getTotalNumOfOrders() {
		return totalNumOfOrders;
	}

	public void setTotalNumOfOrders(Integer totalNumOfOrders) {
		this.totalNumOfOrders = totalNumOfOrders;
	}

	public List<OrderEntity> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderEntity> orders) {
		this.orders = orders;
	}
}
