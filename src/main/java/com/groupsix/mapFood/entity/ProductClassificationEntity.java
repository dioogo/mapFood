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

@Entity(name = "product_classification")
public class ProductClassificationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "restaurant_id", nullable = false)
	private RestaurantEntity restaurant;
	
	@OneToMany(mappedBy = "productClassification", targetEntity = ProductEntity.class)
	private List<ProductEntity> products;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RestaurantEntity getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(RestaurantEntity restaurant) {
		this.restaurant = restaurant;
	}
}
