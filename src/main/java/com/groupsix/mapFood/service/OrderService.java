package com.groupsix.mapFood.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.maps.model.LatLng;
import com.groupsix.mapFood.entity.MotoboyEntity;
import com.groupsix.mapFood.entity.OrderDeliveryEntity;
import com.groupsix.mapFood.entity.OrderEntity;
import com.groupsix.mapFood.entity.OrderItemEntity;
import com.groupsix.mapFood.factory.OrderFactory;
import com.groupsix.mapFood.pojo.Order;
import com.groupsix.mapFood.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderItemService orderItemService;
	
	@Autowired
	private OrderDeliveryService orderDeliveryService;
	
	@Autowired
	private MotoboyService motoboyService;
	
	@Autowired
	private OrderFactory orderFactory;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private GoogleMapsService googleMapsService;
	
	public Order createOrder(final Order order) {
		// BUSCA OS ITENS DO PEDIDO PELO ID
		final List<OrderItemEntity> orderItemsEntities = orderItemService.getOrderItems(order.getOrderItems());
		// CRIA A ENTREGA
		final OrderDeliveryEntity orderDeliveryEntity = orderDeliveryService.getOrderDelivery(order);

		final OrderEntity newOrder = orderFactory.fromDTO(order, orderItemsEntities, orderDeliveryEntity);
		
		orderDeliveryEntity.getOrder().setRestaurant(restaurantService.findById(order.getRestaurantId()));
		
		MotoboyEntity motoboy= motoboyService.findNearby(
				orderDeliveryEntity.getOrder().getRestaurant().getLat(), 
				orderDeliveryEntity.getOrder().getRestaurant().getLon()).get(0);
		
		orderDeliveryEntity.setMotoboy(motoboy);
		
		String time = googleMapsService.timeToReach(
				getLatLon(motoboy.getLat(), motoboy.getLon()), 
				getLatLon(orderDeliveryEntity.getOrder().getRestaurant().getLat(), 
						orderDeliveryEntity.getOrder().getRestaurant().getLon()));
		
		orderRepository.save(newOrder);
		return order;
	}
	
	private LatLng getLatLon(Double lat, Double lon) {
		return new LatLng(lat, lon);
	}
}
