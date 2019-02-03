package com.groupsix.mapFood.factory;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.groupsix.mapFood.entity.OrderItemEntity;
import com.groupsix.mapFood.entity.ProductEntity;
import com.groupsix.mapFood.pojo.OrderItem;

public class OrderItemFactoryTest {

	@Test
	public void testFromDTO() {
		final OrderItem orderItem = new OrderItem();
		orderItem.setQuantity(5);
		orderItem.setTotal(1000);
		
		final ProductEntity product = new ProductEntity();
		product.setPrice(200);
		product.setDescription("Xis Bacon");
		
		final OrderItemEntity ordemItemEntity = new OrderItemFactory().fromDTO(orderItem, product);
		
		assertEquals(5, ordemItemEntity.getQuantity().intValue());
		assertEquals(1000, ordemItemEntity.getTotal().intValue());
		assertEquals(200, ordemItemEntity.getItemPrice().intValue());
		assertEquals("Xis Bacon", ordemItemEntity.getName());
		assertEquals(product, ordemItemEntity.getProduct());
	}
}
