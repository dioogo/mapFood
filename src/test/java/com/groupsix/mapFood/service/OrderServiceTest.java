package com.groupsix.mapFood.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.groupsix.mapFood.entity.CustomerEntity;
import com.groupsix.mapFood.entity.OrderDeliveryEntity;
import com.groupsix.mapFood.entity.OrderEntity;
import com.groupsix.mapFood.entity.OrderItemEntity;
import com.groupsix.mapFood.entity.RestaurantEntity;
import com.groupsix.mapFood.factory.OrderFactory;
import com.groupsix.mapFood.pojo.Order;
import com.groupsix.mapFood.pojo.OrderItem;
import com.groupsix.mapFood.repository.OrderRepository;
import com.groupsix.mapFood.validation.OrderValidation;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

	@Mock
	private OrderRepository orderRepository;
	
	@Mock
	private OrderItemService orderItemService;
	
	@Mock
	private OrderDeliveryService orderDeliveryService;
	
	@Mock
	private CustomerService customerService;
	
	@Mock
	private OrderFactory orderFactory;
	
	@Mock
	private RestaurantService restaurantService;
	
	@Mock
	private OrderValidation orderValidation;

	@InjectMocks
	private OrderService service;
	
	@Test
	public void testCreateOrder() throws Exception {
		
		final Order order = new Order();
		order.setCustomerId(5);
		order.setRestaurantId(10);
		final List<OrderItem> orderItems = new ArrayList<>();
		
		order.setOrderItems(orderItems);
		
		final List<OrderItemEntity> orderItemsEntities = new ArrayList<>();
		when(orderItemService.getOrderItems(orderItems)).thenReturn(orderItemsEntities);
		
		CustomerEntity customerEntity = new CustomerEntity();
		when(customerService.findById(5)).thenReturn(Optional.of(customerEntity));
		
		RestaurantEntity restaurantEntity = new RestaurantEntity();
		when(restaurantService.findById(10)).thenReturn(Optional.of(restaurantEntity));
		
		OrderDeliveryEntity orderDeliveryEntity = new OrderDeliveryEntity();
		when(orderDeliveryService.create(customerEntity)).thenReturn(orderDeliveryEntity);
		
		final OrderEntity newOrder = new OrderEntity();
		when(orderFactory.fromDTO(order, orderItemsEntities, orderDeliveryEntity, restaurantEntity, customerEntity)).thenReturn(newOrder);
		when(orderRepository.save(newOrder)).thenReturn(newOrder);
		
		service.createOrder(order);
		
		verify(orderRepository, times(1)).save(newOrder);
		verify(orderDeliveryService, times(1)).searchMotoboy(newOrder);
	}
}
