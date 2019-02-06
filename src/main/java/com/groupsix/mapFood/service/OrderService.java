package com.groupsix.mapFood.service;

import java.util.List;

import com.groupsix.mapFood.entity.*;
import com.groupsix.mapFood.exception.CustomerTooFarException;
import com.groupsix.mapFood.exception.DiferentRestaurantException;
import com.groupsix.mapFood.exception.ItemsPriceException;
import com.groupsix.mapFood.exception.TotalPriceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupsix.mapFood.factory.OrderFactory;
import com.groupsix.mapFood.pojo.Order;
import com.groupsix.mapFood.repository.OrderRepository;

@Service
public class OrderService {

	private static Double MAX_DISTANCE = 0.5;

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemService orderItemService;
	
	@Autowired
	private OrderDeliveryService orderDeliveryService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private OrderFactory orderFactory;

	private void verifyTotalOrder(Order order)
			throws TotalPriceException {

		if(order.totalIsInvalid()) {
			throw new TotalPriceException();
		}
	}

	private void verifyCustomerAndRestaurantDistance(Order order)
			throws CustomerTooFarException {

		CustomerEntity customer = customerService.getCustomer(order.getCustomerId());
		RestaurantEntity restaurant = restaurantService.getRestaurant(order.getRestaurantId());

		if(customer.getDistanceFrom(restaurant.getLat(), restaurant.getLon()) > MAX_DISTANCE) {
			throw new CustomerTooFarException();
		}
	}

	private void verifyPricesFromItems(List<OrderItemEntity> orderItemsEntities)
			throws ItemsPriceException {

		boolean itemWithWrongPrice = orderItemsEntities.stream()
				.anyMatch(i -> !i.getTotal().equals(i.getProduct().getPrice() * i.getQuantity()));

		if(itemWithWrongPrice) {
			throw new ItemsPriceException();
		}
	}

	private void verifyProductsFromSameRestaurant(
			List<OrderItemEntity> orderItemsEntities,
			Order order)
			throws DiferentRestaurantException {

		boolean itemFromAnotherRestaurant = orderItemsEntities.stream()
				.anyMatch(i -> !i.getProduct()
						.getRestaurant()
						.getId()
						.equals(order.getRestaurantId()));

		if(itemFromAnotherRestaurant) {
			throw new DiferentRestaurantException();
		}
	}

	public Order createOrder(final Order order)
			throws TotalPriceException, DiferentRestaurantException, CustomerTooFarException, ItemsPriceException {

		verifyTotalOrder(order);
		verifyCustomerAndRestaurantDistance(order);

		final List<OrderItemEntity> orderItemsEntities = orderItemService.getOrderItems(order.getOrderItems());

		verifyPricesFromItems(orderItemsEntities);
		verifyProductsFromSameRestaurant(orderItemsEntities, order);

		final OrderDeliveryEntity orderDeliveryEntity = orderDeliveryService.getOrderDelivery(order.getCustomerId());

		final OrderEntity newOrder = orderFactory.fromDTO(order, orderItemsEntities, orderDeliveryEntity);
		orderRepository.save(newOrder);
		
		return order;
	}
}
