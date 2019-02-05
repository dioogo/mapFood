package com.groupsix.mapFood.factory;

import java.sql.Timestamp;
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

	public OrderEntity fromDTO(final Order order, List<OrderItemEntity> orderItemsEntities, final OrderDeliveryEntity orderDeliveryEntity) {
		final OrderEntity newOrder = new OrderEntity();
		
		orderItemsEntities.stream().forEach(o -> { o.setOrder(newOrder); });
		
		orderDeliveryEntity.setOrder(newOrder);
		
		final RestaurantEntity restaurant = new RestaurantEntity();
		restaurant.setId(order.getRestaurantId());
		
		final CustomerEntity customer = new CustomerEntity();
		customer.setId(order.getCustomerId());
		
		newOrder.setRestaurant(restaurant);
		newOrder.setCustomer(customer);
		newOrder.setOrderItems(orderItemsEntities);
		newOrder.setEstimatedTimeToDelivery(new Timestamp(System.currentTimeMillis() + 600000));
		newOrder.setTotal(order.getTotal());
		newOrder.setOrderDelivery(orderDeliveryEntity);
		
		return newOrder;
	}
}
