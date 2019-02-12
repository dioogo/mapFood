package com.groupsix.mapFood.service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.google.maps.model.LatLng;
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
import com.groupsix.mapFood.util.TimestampUtil;

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
	private GoogleMapsService googleMapsService;

	@Autowired
	private OrderFactory orderFactory;
	
	public Order createOrder(final Order order) throws TotalPriceException, ItemsPriceException, DiferentRestaurantException, CustomerTooFarException {
		orderValidation.verifyTotalOrder(order);
		orderValidation.verifyCustomerAndRestaurantDistance(order);

		OrderEntity orderEntity = convertToEntity(order);

		final List<OrderItemEntity> orderItemsEntities = orderItemService.getOrderItems(order.getOrderItems(), orderEntity);

		orderValidation.verifyPricesFromItems(orderItemsEntities);
		orderValidation.verifyItemsFromSameRestaurant(orderItemsEntities, order);
		
		orderEntity.setOrderDelivery(orderDeliveryService.create(orderEntity));
		
		Timestamp outTime = verifyOutTime(orderEntity.getOrderDelivery().getEstimatedTimeToRestaurant());
		
		Timestamp timeToDelivery = estimateTimeToDelivery(orderEntity, outTime);
		
		System.out.println("Sai do restaurant " + outTime);
		System.out.println("Entrega estimada  " + timeToDelivery);
		System.out.println("\n\n");
		
		orderEntity.setEstimatedTimeToDelivery(timeToDelivery);

		orderRepository.save(orderEntity);
		return order;
	}
	
	private Timestamp verifyOutTime(Timestamp estimatedTimeToRestaurant) {
		long time = System.currentTimeMillis();
		Timestamp tenMinutes = TimestampUtil.addSeconds(600L, new Timestamp(time));
		
		if (estimatedTimeToRestaurant.before(tenMinutes)) {
			return tenMinutes;
		} else {
			return estimatedTimeToRestaurant;
		}
	}

	private Timestamp estimateTimeToDelivery(OrderEntity orderEntity, Timestamp outTime) {
		LatLng start = new LatLng(orderEntity.getRestaurant().getLat(), orderEntity.getRestaurant().getLon());
		LatLng end = new LatLng(orderEntity.getCustomer().getLat(), orderEntity.getCustomer().getLon());
		
		return TimestampUtil.addSeconds(googleMapsService.timeToReach(start, end), outTime);
	}
	
	public OrderEntity convertToEntity(Order order) {
		OrderEntity entity = new OrderEntity();
		entity.setId(order.getId());
		entity.setCustomer(customerService.findById(order.getCustomerId()).get());
		entity.setOrderItems(orderItemService.getOrderItems(order.getOrderItems(), entity));
		entity.setRestaurant(restaurantService.findById(order.getRestaurantId()).get());
		entity.setTotal(order.getTotal());
		return entity;
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
