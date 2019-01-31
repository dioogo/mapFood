package com.groupsix.mapFood.service;

import com.groupsix.mapFood.pojo.OrderDelivery;

public interface OrderDeliveryService {

	OrderDelivery createOrderDelivery(Integer orderId, Integer customerId);
}
