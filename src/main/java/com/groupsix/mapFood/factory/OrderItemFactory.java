package com.groupsix.mapFood.factory;

import org.springframework.stereotype.Component;

import com.groupsix.mapFood.entity.OrderItemEntity;
import com.groupsix.mapFood.entity.ProductEntity;
import com.groupsix.mapFood.pojo.OrderItem;

@Component
public class OrderItemFactory {

	public OrderItemEntity fromDTO(final OrderItem orderItem, final ProductEntity product) {
		
		final OrderItemEntity orderItemEntity = new OrderItemEntity();
		orderItemEntity.setItemPrice(product.getPrice());
		orderItemEntity.setName(product.getDescription());
		orderItemEntity.setQuantity(orderItem.getQuantity());
		orderItemEntity.setTotal(orderItem.getTotal());
		orderItemEntity.setProduct(product);
		
		return orderItemEntity;
	}
}
