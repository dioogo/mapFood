package com.groupsix.mapFood.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.groupsix.mapFood.entity.*;
import com.groupsix.mapFood.exception.CustomerTooFarException;
import com.groupsix.mapFood.exception.DiferentRestaurantException;
import com.groupsix.mapFood.exception.ItemsPriceException;
import com.groupsix.mapFood.exception.TotalPriceException;
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
	
	public Order createOrder(final Order order) throws TotalPriceException, ItemsPriceException, DiferentRestaurantException, CustomerTooFarException {
		orderValidation.verifyTotalOrder(order);
		orderValidation.verifyCustomerAndRestaurantDistance(order);

		OrderEntity orderEntity = convertToEntity(order);

		final List<OrderItemEntity> orderItemsEntities = orderItemService.getOrderItems(order.getOrderItems(), orderEntity);

		orderValidation.verifyPricesFromItems(orderItemsEntities);
		orderValidation.verifyItemsFromSameRestaurant(orderItemsEntities, order);
		
		
		orderEntity.setOrderDelivery(orderDeliveryService.create(orderEntity));
		
		// TODO calcular horarios
		orderEntity.setEstimatedTimeToDelivery(new Timestamp(8000));

		orderRepository.save(orderEntity);
		return order;
	}

	public OrderEntity findAllByRestaurant(Integer id) {
		return orderRepository.findById(id).get();
	}
	
	public OrderEntity convertToEntity(Order order) {
		OrderEntity entity = new OrderEntity();
		entity.setId(order.getId());
		entity.setCustomer(customerService.findById(order.getCustomerId()).get());
		entity.setOrderItems(orderItemService.getOrderItems(order.getOrderItems(), entity));
		entity.setRestaurant(restaurantService.findById(order.getRestaurantId()).get());
		entity.setTotal(order.getTotal());
		return entity;
	}

}
