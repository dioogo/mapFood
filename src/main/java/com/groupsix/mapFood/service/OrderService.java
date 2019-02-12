package com.groupsix.mapFood.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.groupsix.mapFood.entity.*;
import com.groupsix.mapFood.exception.CustomerTooFarException;
import com.groupsix.mapFood.exception.DiferentRestaurantException;
import com.groupsix.mapFood.exception.ItemsPriceException;
import com.groupsix.mapFood.exception.TotalPriceException;
import com.groupsix.mapFood.factory.OrderFactory;
import com.groupsix.mapFood.validation.OrderValidation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.groupsix.mapFood.pojo.Order;
import com.groupsix.mapFood.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderValidation orderValidation;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private OrderItemService orderItemService;

	@Autowired
	private OrderDeliveryService orderDeliveryService;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private RestaurantService restaurantService;

	@Autowired
	private OrderFactory orderFactory;

	public Order createOrder(final Order order)
			throws TotalPriceException, ItemsPriceException, DiferentRestaurantException, CustomerTooFarException {
		orderValidation.verifyTotalOrder(order);
		orderValidation.verifyCustomerAndRestaurantDistance(order);

		final List<OrderItemEntity> orderItemsEntities = orderItemService.getOrderItems(order.getOrderItems());

		orderValidation.verifyPricesFromItems(orderItemsEntities);
		orderValidation.verifyItemsFromSameRestaurant(orderItemsEntities, order);

		CustomerEntity customerEntity = customerService.findById(order.getCustomerId()).get();
		RestaurantEntity restaurantEntity = restaurantService.findById(order.getRestaurantId()).get();
		OrderDeliveryEntity orderDeliveryEntity = orderDeliveryService.create(customerEntity);

		OrderEntity orderEntity = orderFactory.fromDTO(order, orderItemsEntities, orderDeliveryEntity, restaurantEntity,
				customerEntity);

		orderEntity = orderRepository.save(orderEntity);

		orderDeliveryService.searchMotoboy(orderEntity);

		return order;
	}

	public List<Order> listOrder(Integer id) {
		return StreamSupport.stream(orderRepository.findByCustomer_Id(id).spliterator(), false).map(orderFactory::getInstance).collect(Collectors.toList());

	}

	public List<Order> listRestaurantOrder(Integer id){
		return StreamSupport.stream(orderRepository.findByRestaurant_Id(id).spliterator(), false).map(orderFactory::getInstance).collect(Collectors.toList());
	}

	public Map<String, String> informationsRestaurant(Integer id){
		int totalOrders = (int) StreamSupport.stream(orderRepository.findByRestaurant_Id(id).spliterator(), false).count();
		int totalUsers = orderRepository.countAllCustomer(id);
		int totalRevenue = orderRepository.countTotalRevenue(id);
		HashMap<String, String> map = new HashMap<>();

		map.put("numero_pedidos", String.valueOf(totalOrders));
		map.put("numero_clientes", String.valueOf(totalUsers));
		map.put("faturamento", String.valueOf(totalRevenue));



		return map;

	}

}
