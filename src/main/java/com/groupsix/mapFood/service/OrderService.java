package com.groupsix.mapFood.service;

import java.util.List;

import com.groupsix.mapFood.entity.*;
import com.groupsix.mapFood.exception.CustomerTooFarException;
import com.groupsix.mapFood.exception.DiferentRestaurantException;
import com.groupsix.mapFood.exception.ItemsPriceException;
import com.groupsix.mapFood.exception.TotalPriceException;
import com.groupsix.mapFood.validation.OrderValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	@Autowired
	private OrderValidation orderValidation;

	public Order createOrder(final Order order)
			throws TotalPriceException, DiferentRestaurantException, CustomerTooFarException, ItemsPriceException {

		orderValidation.verifyTotalOrder(order);
		orderValidation.verifyCustomerAndRestaurantDistance(order);

		final List<OrderItemEntity> orderItemsEntities = orderItemService.getOrderItems(order.getOrderItems());

		orderValidation.verifyPricesFromItems(orderItemsEntities);
		orderValidation.verifyItemsFromSameRestaurant(orderItemsEntities, order);

		final OrderDeliveryEntity orderDeliveryEntity = orderDeliveryService.getOrderDelivery(order.getCustomerId());

		final OrderEntity newOrder = orderFactory.fromDTO(order, orderItemsEntities, orderDeliveryEntity);
		orderRepository.save(newOrder);
		
		return order;
	}
}
