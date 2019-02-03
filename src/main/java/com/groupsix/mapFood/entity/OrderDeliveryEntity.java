package com.groupsix.mapFood.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity(name = "order_delivery")
public class OrderDeliveryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "destination_lat")
	private Double destinationLat;
	
	@Column(name = "destination_lon")
	private Double destinationLon;
	
	@Column(name = "estimated_time_to_restaurant")
	private Timestamp estimatedTimeToRestaurant;
	
	@ManyToOne
	@JoinColumn(name = "motoboy_id", nullable = true)
	private MotoboyEntity motoboy;
	
	@OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
	private OrderEntity order;

	public OrderEntity getOrder() {
		return order;
	}

	public void setOrder(OrderEntity order) {
		this.order = order;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Timestamp getEstimatedTimeToRestaurant() {
		return estimatedTimeToRestaurant;
	}

	public void setEstimatedTimeToRestaurant(Timestamp estimatedTimeToRestaurant) {
		this.estimatedTimeToRestaurant = estimatedTimeToRestaurant;
	}

	public MotoboyEntity getMotoboy() {
		return motoboy;
	}

	public void setMotoboy(MotoboyEntity motoboy) {
		this.motoboy = motoboy;
	}
}
