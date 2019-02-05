package com.groupsix.mapFood.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name = "restaurant")
public class RestaurantEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	private String hash;

	@Column
	private Double lat;

	@Column
	private Double lon;

	@ManyToOne
	@JoinColumn(name = "city_id", nullable = false)
	private CityEntity city;

	@ManyToOne
	@JoinColumn(name = "restaurant_type_id", nullable = false)
	private RestaurantTypeEntity restaurantType;

	@OneToMany(mappedBy = "restaurant", targetEntity = OrderEntity.class)
	private List<OrderEntity> orders;

	@OneToMany(mappedBy = "restaurant", targetEntity = ProductEntity.class)
	private List<ProductEntity> products;

	@OneToMany(mappedBy = "restaurant", targetEntity = ProductClassificationEntity.class)
	private List<ProductClassificationEntity> productClassifications;

	public List<ProductClassificationEntity> getProductClassifications() {
		return productClassifications;
	}

	public void setProductClassifications(List<ProductClassificationEntity> productClassifications) {
		this.productClassifications = productClassifications;
	}

	public List<ProductEntity> getProducts() {
		return products;
	}

	public void setProducts(List<ProductEntity> products) {
		this.products = products;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
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

	public CityEntity getCity() {
		return city;
	}

	public void setCity(CityEntity city) {
		this.city = city;
	}

	public RestaurantTypeEntity getRestaurantType() {
		return restaurantType;
	}

	public void setRestaurantType(RestaurantTypeEntity restaurantType) {
		this.restaurantType = restaurantType;
	}

	public List<OrderEntity> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderEntity> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "RestaurantEntity [id=" + id + ", hash=" + hash + ", lat=" + lat + ", lon=" + lon + ", city=" + city
				+ ", restaurantType=" + restaurantType + ", orders=" + orders + ", products=" + products
				+ ", productClassifications=" + productClassifications + "]";
	}

}
