package com.groupsix.mapFood.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "motoboy")
public class MotoboyEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private Double lat;
	
	@Column
	private Double lon;
	
	@OneToMany(mappedBy = "motoboy", targetEntity = OrderDeliveryEntity.class)
	private List<OrderDeliveryEntity> orderDeliveries;

	public List<OrderDeliveryEntity> getOrderDeliveries() {
		return orderDeliveries;
	}

	public void setOrderDeliveries(List<OrderDeliveryEntity> orderDeliveries) {
		this.orderDeliveries = orderDeliveries;
	}

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
}
