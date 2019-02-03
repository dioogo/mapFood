package com.groupsix.mapFood.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupsix.mapFood.entity.OrderItemEntity;
import com.groupsix.mapFood.entity.ProductEntity;
import com.groupsix.mapFood.factory.OrderItemFactory;
import com.groupsix.mapFood.pojo.OrderItem;
import com.groupsix.mapFood.repository.ProductRepository;

@Service
public class OrderItemService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderItemFactory orderItemFactory;
	
	public List<OrderItemEntity> getOrderItems(final List<OrderItem> orderItems) {

		final List<OrderItemEntity> orderItemsEntities = new ArrayList<>();
		
		orderItems.stream().forEach(orderItem -> {
			
			ProductEntity product = productRepository.getOne(orderItem.getProductId());

			final OrderItemEntity orderItemEntity = orderItemFactory.fromDTO(orderItem, product);
			
			orderItemsEntities.add(orderItemEntity);
		});
		
		return orderItemsEntities;
	}

}
