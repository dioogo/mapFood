package com.groupsix.mapFood.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;;

@Entity(name = "product")
public class ProductEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String hash;
	
	@Column
	private String description;
	
	@Column
	private Integer price;
	
	@ManyToOne
	@JoinColumn(name = "restaurant_id", nullable = false)
	private RestaurantEntity restaurant;
	
	@ManyToOne
	@JoinColumn(name = "classification_id", nullable = false)
	private ProductClassificationEntity productClassification;
	
	@OneToMany(mappedBy = "product", targetEntity = OrderItemEntity.class)
	private List<OrderItemEntity> orderItems;

	public List<OrderItemEntity> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemEntity> orderItems) {
		this.orderItems = orderItems;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public RestaurantEntity getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(RestaurantEntity restaurant) {
		this.restaurant = restaurant;
	}

	public ProductClassificationEntity getProductClassification() {
		return productClassification;
	}

	public void setProductClassification(ProductClassificationEntity productClassification) {
		this.productClassification = productClassification;
	}
}
