package com.groupsix.mapFood.service;

import java.util.List;

import com.groupsix.mapFood.entity.*;
import com.groupsix.mapFood.exception.CustomerTooFarException;
import com.groupsix.mapFood.exception.DiferentRestaurantException;
import com.groupsix.mapFood.exception.TotalPriceException;
import com.groupsix.mapFood.pojo.Customer;
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
	
	public Order createOrder(final Order order) throws TotalPriceException, DiferentRestaurantException, CustomerTooFarException {

		if(order.totalIsInvalid()) {
			throw new TotalPriceException();
		}

		CustomerEntity customer = orderDeliveryService.getCustomerFromOrderDelivery(order.getCustomerId());
		RestaurantEntity restaurant = orderDeliveryService.getRestaurantFromOrderDelivery(order.getRestaurantId());

		if(customer.getDistanceFrom(restaurant.getLat(), restaurant.getLon()) > 1) {
			throw new CustomerTooFarException();
		}

		final List<OrderItemEntity> orderItemsEntities = orderItemService.getOrderItems(order.getOrderItems());

		boolean itemFromAnotherRestaurant = orderItemsEntities.stream()
				.anyMatch(i -> !i.getProduct()
						.getRestaurant()
						.getId()
						.equals(order.getRestaurantId()));
		if(itemFromAnotherRestaurant) {
			throw new DiferentRestaurantException();
		}
		
		final OrderDeliveryEntity orderDeliveryEntity = orderDeliveryService.getOrderDelivery(order.getCustomerId());

		final OrderEntity newOrder = orderFactory.fromDTO(order, orderItemsEntities, orderDeliveryEntity);
		orderRepository.save(newOrder);
		
		return order;
	}
}
