package com.groupsix.mapFood.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupsix.mapFood.entity.MotoboyEntity;
import com.groupsix.mapFood.entity.OrderDeliveryEntity;
import com.groupsix.mapFood.entity.OrderEntity;
import com.groupsix.mapFood.entity.OrderItemEntity;
import com.groupsix.mapFood.factory.OrderFactory;
import com.groupsix.mapFood.pojo.Order;

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
	
	public Order createOrder(final Order order) {
		// BUSCA OS ITENS DO PEDIDO PELO ID
		final List<OrderItemEntity> orderItemsEntities = orderItemService.getOrderItems(order.getOrderItems());
		// CRIA A ENTREGA
		final OrderDeliveryEntity orderDeliveryEntity = orderDeliveryService.getOrderDelivery(order);

		final OrderEntity newOrder = orderFactory.fromDTO(order, orderItemsEntities, orderDeliveryEntity);
		
		MotoboyEntity motoboy= motoboyService.findNearby(
				orderDeliveryEntity.getOrder().getRestaurant().getLat(), 
				orderDeliveryEntity.getOrder().getRestaurant().getLat(),
				1000).get(0);
		
		orderDeliveryEntity.setMotoboy(motoboy);
		
		//orderRepository.save(newOrder);
		System.out.println(newOrder.getRestaurant().toString());
		System.out.println(newOrder.getOrderDelivery().getMotoboy().toString());
		return order;
	}
}
