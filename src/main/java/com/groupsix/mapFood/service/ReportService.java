package com.groupsix.mapFood.service;

import com.groupsix.mapFood.entity.OrderEntity;
import com.groupsix.mapFood.exception.*;
import com.groupsix.mapFood.pojo.Order;
import com.groupsix.mapFood.pojo.RestaurantOrderReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ReportService {

	@Autowired
	private OrderService orderService;
	
	public List<RestaurantOrderReport> getRestaurantOrdersReport(Integer id) throws Exception {
		List<RestaurantOrderReport> orders = orderService.findAllByRestaurantId(id);

		if(orders.isEmpty()) {
			throw new NoOrdersFoundException();
		}
		return orders;
	}
}
