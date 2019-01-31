package com.groupsix.mapFood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupsix.mapFood.dao.OrderDAO;
import com.groupsix.mapFood.pojo.Order;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDAO orderDAO;
	
	@Autowired
	private OrderItemService orderItemService;
	
	@Autowired
	private OrderDeliveryService orderDeliveryService;
	
	@Override
	public Order createOrder(Order order) {
		Integer id = orderDAO.createOrder(order);
		order.setId(id);
		
		orderItemService.createOrderItems(id, order.getOrderItems());
		
		orderDeliveryService.createOrderDelivery(id, order.getCustomerId());
		
		return order;
	}
}
