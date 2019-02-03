package com.grousix.mapFood.service;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.groupsix.mapFood.entity.OrderDeliveryEntity;
import com.groupsix.mapFood.entity.OrderEntity;
import com.groupsix.mapFood.entity.OrderItemEntity;
import com.groupsix.mapFood.factory.OrderFactory;
import com.groupsix.mapFood.pojo.Order;
import com.groupsix.mapFood.pojo.OrderItem;
import com.groupsix.mapFood.repository.OrderRepository;
import com.groupsix.mapFood.service.OrderDeliveryService;
import com.groupsix.mapFood.service.OrderItemService;
import com.groupsix.mapFood.service.OrderService;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

	@Mock
	private OrderRepository orderRepository;
	
	@Mock
	private OrderItemService orderItemService;
	
	@Mock
	private OrderDeliveryService orderDeliveryService;
	
	@Mock
	private OrderFactory orderFactory;
	
	@InjectMocks
	private OrderService service;
	
	@Test
	public void testCreateOrder() {
		
		final Order order = new Order();
		order.setCustomerId(5);
		final List<OrderItem> orderItems = new ArrayList<>();
		
		order.setOrderItems(orderItems);
		
		final List<OrderItemEntity> orderItemsEntities = new ArrayList<>();
		
		final OrderDeliveryEntity orderDeliveryEntity = new OrderDeliveryEntity();
		
		when(orderItemService.getOrderItems(orderItems)).thenReturn(orderItemsEntities);
		when(orderDeliveryService.getOrderDelivery(5)).thenReturn(orderDeliveryEntity);
		
		final OrderEntity newOrder = new OrderEntity();
		when(orderFactory.fromDTO(order, orderItemsEntities, orderDeliveryEntity)).thenReturn(newOrder);
		
		service.createOrder(order);
		
		verify(orderRepository, times(1)).save(newOrder);
	}
}
