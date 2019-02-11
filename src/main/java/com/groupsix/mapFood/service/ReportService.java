package com.groupsix.mapFood.service;

import com.groupsix.mapFood.entity.OrderEntity;
import com.groupsix.mapFood.exception.*;
import com.groupsix.mapFood.pojo.Order;
import com.groupsix.mapFood.pojo.RestaurantOrdersReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ReportService {

	@Autowired
	private OrderService orderService;
	
	public OrderEntity getRestaurantOrdersReport(Integer id) throws Exception {
		OrderEntity orders = orderService.findAllByRestaurant(id);

		/*if(orders.isEmpty()) {
			throw new NoOrdersFoundException();
		}
		RestaurantOrdersReport report = new RestaurantOrdersReport();

		report.setTotalNumOfCustomers((int) orders.stream()
				.map(o -> o.getCustomer())
				.distinct()
				.count());
		report.setAverageTimeToDelivery(orders.stream()
				.mapToLong(o -> o.getEstimatedTimeToDelivery().getTime())
				.sum() / orders.size());
		report.setAverageOrderTotal(orders.stream()
				.mapToDouble(o -> o.getTotal())
				.sum() / orders.size());
		report.setHighestOrderTotal(orders.stream()
				.map(o -> o.getTotal())
				.max(Comparator.naturalOrder())
				.get());
		report.setTotalNumOfMotoboys((int) orders.stream()
				.map(o -> o.getOrderDelivery().getMotoboy())
				.distinct()
				.count());
		report.setTotalNumOfOrders(orders.size());
		report.setOrders(orders);

		return report;*/
		return orders;
	}
}
