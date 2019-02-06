package com.groupsix.mapFood.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupsix.mapFood.entity.OrderEntity;
import com.groupsix.mapFood.entity.OrderItemEntity;
import com.groupsix.mapFood.pojo.Order;
import com.groupsix.mapFood.pojo.OrderItem;
import com.groupsix.mapFood.repository.OrderRepository;

@Service
public class OrderService {

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
	
	/**
	 * Create a order
	 * @param order
	 * @return Order
	 */
	public Order createOrder(final Order order) {
		OrderEntity orderEntity = convertToEntity(order);
		
		orderEntity.setOrderDelivery(orderDeliveryService.create(orderEntity));
		
		// calcular horarios
		
		
		
		orderRepository.save(orderEntity);
		return order;
	}
	
	/**
	 * Convert a Order in OrderEntity
	 * @param order
	 * @return OrderEntity
	 */
	private OrderEntity convertToEntity(Order order) {
		OrderEntity entity = new OrderEntity();
		entity.setId(order.getId());
		entity.setCustomer(customerService.findById(order.getCustomerId()).get());
		entity.setOrderItems(getOrdemItems(order));
		entity.setRestaurant(restaurantService.findById(order.getRestaurantId()).get());
		entity.setTotal(order.getTotal());
		return entity;
	}

	/**
	 * Return a list o orderItems given a order
	 * @param order
	 * @return List<OrderItemEntity>
	 */
	private List<OrderItemEntity> getOrdemItems(Order order) {
		return orderItemService.findByIdIn(
				order.getOrderItems().stream()
					.map(OrderItem::getId)
					.collect(Collectors.toList()));
	}

}
