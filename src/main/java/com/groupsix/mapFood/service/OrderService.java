package com.groupsix.mapFood.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupsix.mapFood.entity.OrderDeliveryEntity;
import com.groupsix.mapFood.entity.OrderEntity;
import com.groupsix.mapFood.entity.OrderItemEntity;
import com.groupsix.mapFood.factory.OrderFactory;
import com.groupsix.mapFood.pojo.Order;
import com.groupsix.mapFood.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemService orderItemService;
	
	@Autowired
	private OrderDeliveryService orderDeliveryService;
	
	@Autowired
	private OrderFactory orderFactory;
	
	public Order createOrder(final Order order) {
		
		final List<OrderItemEntity> orderItemsEntities = orderItemService.getOrderItems(order.getOrderItems());
		
		final OrderDeliveryEntity orderDeliveryEntity = orderDeliveryService.getOrderDelivery(order.getCustomerId());

		final OrderEntity newOrder = orderFactory.fromDTO(order, orderItemsEntities, orderDeliveryEntity);
		orderRepository.save(newOrder);
		
		return order;
	}
}