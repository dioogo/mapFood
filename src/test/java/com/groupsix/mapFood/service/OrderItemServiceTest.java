package com.groupsix.mapFood.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.groupsix.mapFood.entity.OrderItemEntity;
import com.groupsix.mapFood.entity.ProductEntity;
import com.groupsix.mapFood.factory.OrderItemFactory;
import com.groupsix.mapFood.pojo.OrderItem;
import com.groupsix.mapFood.repository.ProductRepository;
import com.groupsix.mapFood.service.OrderItemService;

@RunWith(MockitoJUnitRunner.class)
public class OrderItemServiceTest {
	
	@Mock
	private ProductRepository productRepository;
	
	@Mock
	private OrderItemFactory orderItemFactory;
	
	@InjectMocks
	private OrderItemService service;
	
	@Test
	public void testGetOrderItems() {
		final List<OrderItem> orderItems = new ArrayList<>();
		
		final OrderItem item1 = new OrderItem();
		item1.setProductId(5);
		orderItems.add(item1);
		
		final OrderItem item2 = new OrderItem();
		item2.setProductId(6);
		orderItems.add(item2);
		
		final ProductEntity product1 = new ProductEntity();
		when(productRepository.getOne(5)).thenReturn(product1);
		
		final ProductEntity product2 = new ProductEntity();
		when(productRepository.getOne(6)).thenReturn(product2);
		
		final OrderItemEntity orderItemEntity1 = new OrderItemEntity();
		when(orderItemFactory.fromDTO(item1, product1)).thenReturn(orderItemEntity1);
		
		final OrderItemEntity orderItemEntity2 = new OrderItemEntity();
		when(orderItemFactory.fromDTO(item2, product2)).thenReturn(orderItemEntity2);
		
		final List<OrderItemEntity> orderItemsEntities = service.getOrderItems(orderItems, null);
		
		assertEquals(2, orderItemsEntities.size());
		assertEquals(orderItemEntity1, orderItemsEntities.get(0));
		assertEquals(orderItemEntity2, orderItemsEntities.get(1));
	}
}
