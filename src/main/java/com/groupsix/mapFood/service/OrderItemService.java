package com.groupsix.mapFood.service;

import java.util.List;

import com.groupsix.mapFood.pojo.OrderItem;

public interface OrderItemService {

	void createOrderItems(Integer orderId, List<OrderItem> orderItem);
}
