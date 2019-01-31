package com.groupsix.mapFood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupsix.mapFood.dao.CustomerDAO;
import com.groupsix.mapFood.dao.OrderDeliveryDAO;
import com.groupsix.mapFood.pojo.Customer;
import com.groupsix.mapFood.pojo.OrderDelivery;

@Service
public class OrderDeliveryServiceImpl implements OrderDeliveryService {

	@Autowired
	private CustomerDAO customerDAO;
	
	@Autowired
	private OrderDeliveryDAO orderDeliveryDAO;
	
	@Override
	public OrderDelivery createOrderDelivery(Integer orderId, Integer customerId) {
		
		Customer customer = customerDAO.getById(customerId);
		OrderDelivery orderDelivery = new OrderDelivery();
		orderDelivery.setDestinationLat(customer.getLat());
		orderDelivery.setDestinationLon(customer.getLon());
		orderDelivery.setOrderId(orderId);
		
		orderDeliveryDAO.createOrderDelivery(orderDelivery);
		
		return orderDelivery;
	}
}
