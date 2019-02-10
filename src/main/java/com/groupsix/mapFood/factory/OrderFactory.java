package com.groupsix.mapFood.factory;

import java.util.List;

import org.springframework.stereotype.Component;

import com.groupsix.mapFood.entity.CustomerEntity;
import com.groupsix.mapFood.entity.OrderDeliveryEntity;
import com.groupsix.mapFood.entity.OrderEntity;
import com.groupsix.mapFood.entity.OrderItemEntity;
import com.groupsix.mapFood.entity.RestaurantEntity;
import com.groupsix.mapFood.pojo.Order;

@Component
public class OrderFactory {

	public OrderEntity fromDTO(final Order order, List<OrderItemEntity> orderItemsEntities,
			final OrderDeliveryEntity orderDeliveryEntity, RestaurantEntity restaurantEntity,
			CustomerEntity customerEntity) {
		final OrderEntity newOrder = new OrderEntity();

		orderItemsEntities.stream().forEach(o -> {
			o.setOrder(newOrder);
		});

		orderDeliveryEntity.setOrder(newOrder);

		newOrder.setRestaurant(restaurantEntity);
		newOrder.setCustomer(customerEntity);
		newOrder.setOrderItems(orderItemsEntities);
		newOrder.setTotal(order.getTotal());
		newOrder.setOrderDelivery(orderDeliveryEntity);

		return newOrder;
	}
}