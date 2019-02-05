package com.groupsix.mapFood.pojo;

import com.fasterxml.jackson.annotation.JsonAlias;

public class OrderItem {
	
	private Integer id;
	private Integer productId;
	private Integer quantity;
	private String name;
	private Integer itemPrice;
	private Integer total;
	private Integer orderId;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@JsonAlias("product_id")
	public Integer getProductId() {
		return productId;
	}
	
	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@JsonAlias("item_price")
	public Integer getItemPrice() {
		return itemPrice;
	}
	
	public void setItemPrice(Integer itemPrice) {
		this.itemPrice = itemPrice;
	}
	
	public Integer getTotal() {
		return total;
	}
	
	public void setTotal(Integer total) {
		this.total = total;
	}

	@JsonAlias("order_id")
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
}
