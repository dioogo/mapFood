package com.groupsix.mapFood.factory;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.groupsix.mapFood.entity.OrderDeliveryEntity;
import com.groupsix.mapFood.entity.OrderEntity;
import com.groupsix.mapFood.entity.OrderItemEntity;
import com.groupsix.mapFood.pojo.Order;

public class OrderFactoryTest {

	@Test
	public void testFromDTO() {
		final Order order = new Order();
		order.setRestaurantId(1);
		order.setCustomerId(2);
		order.setTotal(100);
		
		final List<OrderItemEntity> orderItemsEntities = new ArrayList<>();
		final OrderItemEntity orderItemEntity1 = new OrderItemEntity();
		final OrderItemEntity orderItemEntity2 = new OrderItemEntity();
		orderItemsEntities.add(orderItemEntity1);
		orderItemsEntities.add(orderItemEntity2);
		
		final OrderDeliveryEntity orderDeliveryEntity = new OrderDeliveryEntity();
		
		final OrderEntity orderEntity = new OrderFactory().fromDTO(order, orderItemsEntities, orderDeliveryEntity);
		
		assertEquals(100, orderEntity.getTotal().intValue());
		assertEquals(1, orderEntity.getRestaurant().getId().intValue());
		assertEquals(2, orderEntity.getCustomer().getId().intValue());
		assertEquals(orderItemsEntities, orderEntity.getOrderItems());
		assertEquals(orderDeliveryEntity, orderEntity.getOrderDelivery());
		
		assertEquals(orderEntity, orderDeliveryEntity.getOrder());
		assertEquals(orderEntity, orderItemEntity1.getOrder());
		assertEquals(orderEntity, orderItemEntity2.getOrder());
	}
}
