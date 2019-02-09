package com.groupsix.mapFood.service;

import java.sql.Timestamp;
import java.util.List;

import com.google.maps.model.LatLng;
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
import com.groupsix.mapFood.util.TimestampUtil;

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
	private GoogleMapsService googleMapsService;
	
	public Order createOrder(final Order order) throws TotalPriceException, ItemsPriceException, DiferentRestaurantException, CustomerTooFarException {
		orderValidation.verifyTotalOrder(order);
		orderValidation.verifyCustomerAndRestaurantDistance(order);

		OrderEntity orderEntity = convertToEntity(order);

		final List<OrderItemEntity> orderItemsEntities = orderItemService.getOrderItems(order.getOrderItems(), orderEntity);

		orderValidation.verifyPricesFromItems(orderItemsEntities);
		orderValidation.verifyItemsFromSameRestaurant(orderItemsEntities, order);
		
		
		orderEntity.setOrderDelivery(orderDeliveryService.create(orderEntity));
		
		
		
		// TODO calcular horarios
		Timestamp outTime = verifyOutTime(orderEntity.getOrderDelivery().getEstimatedTimeToRestaurant());
		
		Timestamp timeToDelivery = estimateTimeToDelivery(orderEntity, outTime);
		
		System.out.println("Sai do restaurant " + outTime);
		System.out.println("Entrega estimada  " + timeToDelivery);
		
		orderEntity.setEstimatedTimeToDelivery(timeToDelivery);

		orderRepository.save(orderEntity);
		return order;
	}
	
	private Timestamp verifyOutTime(Timestamp estimatedTimeToRestaurant) {
		long time = System.currentTimeMillis();
		Timestamp tenMinutes = TimestampUtil.addSeconds(600L, new Timestamp(time));
		
		if (estimatedTimeToRestaurant.before(tenMinutes)) {
			return tenMinutes;
		} else {
			return estimatedTimeToRestaurant;
		}
	}

	private Timestamp estimateTimeToDelivery(OrderEntity orderEntity, Timestamp outTime) {
		LatLng start = new LatLng(orderEntity.getRestaurant().getLat(), orderEntity.getRestaurant().getLon());
		LatLng end = new LatLng(orderEntity.getCustomer().getLat(), orderEntity.getCustomer().getLon());
		
		return TimestampUtil.addSeconds(googleMapsService.timeToReach(start, end), outTime);
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
