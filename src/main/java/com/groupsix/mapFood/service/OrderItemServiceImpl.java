package com.groupsix.mapFood.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupsix.mapFood.dao.OrderItemDAO;
import com.groupsix.mapFood.dao.ProductDAO;
import com.groupsix.mapFood.pojo.OrderItem;
import com.groupsix.mapFood.pojo.Product;

@Service
public class OrderItemServiceImpl implements OrderItemService {

	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private OrderItemDAO orderItemDAO;
	
	@Override
	public void createOrderItems(Integer orderId, List<OrderItem> orderItems) {

		orderItems.stream().forEach(orderItem -> {
			Product product = productDAO.getById(orderItem.getProductId());
			orderItem.setName(product.getDescription());
			orderItem.setItemPrice(product.getPrice());
			orderItem.setOrderId(orderId);
			
			Integer id = orderItemDAO.createOrderItem(orderItem);
			orderItem.setId(id);
		});
		
	}

}
