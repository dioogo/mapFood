package com.groupsix.mapFood.service;

import java.util.List;

import com.groupsix.mapFood.entity.*;
import com.groupsix.mapFood.exception.CustomerTooFarException;
import com.groupsix.mapFood.exception.DiferentRestaurantException;
import com.groupsix.mapFood.exception.ItemsPriceException;
import com.groupsix.mapFood.exception.TotalPriceException;
import com.groupsix.mapFood.factory.OrderFactory;
import com.groupsix.mapFood.validation.OrderValidation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.groupsix.mapFood.pojo.Order;
import com.groupsix.mapFood.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderValidation orderValidation;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private OrderItemService orderItemService;

	@Autowired
	private OrderDeliveryService orderDeliveryService;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private RestaurantService restaurantService;

	@Autowired
	private SearchMotoboyService searchMotoboyService;

	@Autowired
	private OrderFactory orderFactory;

	public Order createOrder(final Order order)
			throws TotalPriceException, ItemsPriceException, DiferentRestaurantException, CustomerTooFarException {
		orderValidation.verifyTotalOrder(order);
		orderValidation.verifyCustomerAndRestaurantDistance(order);

		final List<OrderItemEntity> orderItemsEntities = orderItemService.getOrderItems(order.getOrderItems());

		orderValidation.verifyPricesFromItems(orderItemsEntities);
		orderValidation.verifyItemsFromSameRestaurant(orderItemsEntities, order);

		CustomerEntity customerEntity = customerService.findById(order.getCustomerId()).get();
		RestaurantEntity restaurantEntity = restaurantService.findById(order.getRestaurantId()).get();
		OrderDeliveryEntity orderDeliveryEntity = orderDeliveryService.create(customerEntity);

		OrderEntity orderEntity = orderFactory.fromDTO(order, orderItemsEntities, orderDeliveryEntity, restaurantEntity,
				customerEntity);

		orderEntity = orderRepository.save(orderEntity);

		searchMotoboyService.searchMotoboy(orderEntity);

		return order;
	}
}
