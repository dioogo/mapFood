package com.groupsix.mapFood.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity(name = "`order`")
public class OrderEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private CustomerEntity customer;
	
	@ManyToOne
	@JoinColumn(name = "restaurant_id", nullable = false)
	private RestaurantEntity restaurant;
	
	@Column(name = "estimated_time_to_delivery")
	private Timestamp estimatedTimeToDelivery;

	@Column
	private Integer total;
	
	@OneToMany(mappedBy = "order", targetEntity = OrderItemEntity.class, cascade = CascadeType.ALL)
	private List<OrderItemEntity> orderItems;
	
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "order")
	private OrderDeliveryEntity orderDelivery;
	
	public OrderDeliveryEntity getOrderDelivery() {
		return orderDelivery;
	}

	public void setOrderDelivery(OrderDeliveryEntity orderDelivery) {
		this.orderDelivery = orderDelivery;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CustomerEntity getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}

	public RestaurantEntity getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(RestaurantEntity restaurant) {
		this.restaurant = restaurant;
	}

	public Timestamp getEstimatedTimeToDelivery() {
		return estimatedTimeToDelivery;
	}

	public void setEstimatedTimeToDelivery(Timestamp estimatedTimeToDelivery) {
		this.estimatedTimeToDelivery = estimatedTimeToDelivery;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<OrderItemEntity> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemEntity> orderItems) {
		this.orderItems = orderItems;
	}

}
