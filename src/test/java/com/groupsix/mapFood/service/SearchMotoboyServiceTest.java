package com.groupsix.mapFood.service;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.groupsix.mapFood.entity.CustomerEntity;
import com.groupsix.mapFood.entity.OrderDeliveryEntity;
import com.groupsix.mapFood.entity.OrderEntity;
import com.groupsix.mapFood.entity.RestaurantEntity;
import com.groupsix.mapFood.pojo.CacheMotoboy;
import com.groupsix.mapFood.pojo.CacheMotoboyDistance;
import com.groupsix.mapFood.repository.OrderRepository;
import com.groupsix.mapFood.timestamp.CalculateTimestamp;
import com.groupsix.mapFood.util.TimestampUtil;

@RunWith(MockitoJUnitRunner.class)
public class SearchMotoboyServiceTest {

	@Mock
	private CacheService cacheService;

	@Mock
	private CalculateTimestamp calculateTimestamp;

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private CacheSearchMotoboyService cacheSearchMotoboyService;
	
	@Mock
	private TimestampUtil timestampUtil;
	
	@InjectMocks
	private SearchMotoboyService service;
	
	@Test
	public void testSearchMotoboyWithMotoboyArrivingBeforeOrderReady() {
		OrderEntity order = new OrderEntity();
		OrderDeliveryEntity orderDelivery = new OrderDeliveryEntity();
		order.setOrderDelivery(orderDelivery);
		order.setId(10);
		
		RestaurantEntity restaurant = new RestaurantEntity();
		restaurant.setId(50);
		order.setRestaurant(restaurant);
		
		CustomerEntity customer = new CustomerEntity();
		customer.setLat(3.0);
		customer.setLon(4.0);
		order.setCustomer(customer);
		
		List<CacheMotoboy> cache = new ArrayList<>();
		when(cacheService.getCache()).thenReturn(cache);
		
		CacheMotoboy motoboy = new CacheMotoboy();
		motoboy.setId(1);
		motoboy.setLat(1.0);
		motoboy.setLon(2.0);
		motoboy.setOrders(new ArrayList<>());
		
		Timestamp timestampArrivesAtRestaurant = new Timestamp(100L);
		CacheMotoboyDistance cacheMotoboyDistance = new CacheMotoboyDistance(motoboy, timestampArrivesAtRestaurant);
		when(cacheSearchMotoboyService.getNearestMotoboy(restaurant, cache)).thenReturn(cacheMotoboyDistance);
		
		Timestamp tenMinutes = new Timestamp(600L);
		when(timestampUtil.addSecondsFromNow(600L)).thenReturn(tenMinutes);
		
		Timestamp timeToDelivery = new Timestamp(1000L);
		when(calculateTimestamp.calculateEstimatedTimeToDelivery(order, motoboy, tenMinutes)).thenReturn(timeToDelivery);
		
		service.searchMotoboy(order);
		
		verify(cacheService, times(1)).updateCache(cache);
		verify(orderRepository, times(1)).save(order);
		
		assertEquals(timeToDelivery, order.getEstimatedTimeToDelivery());
		assertEquals(1, motoboy.getOrders().size());
		assertEquals(10, motoboy.getOrders().get(0).getId().intValue());
		assertEquals(50, motoboy.getOrders().get(0).getRestaurantId().intValue());
		assertEquals(new Double(3.0), motoboy.getOrders().get(0).getCacheDestination().getLat());
		assertEquals(new Double(4.0), motoboy.getOrders().get(0).getCacheDestination().getLon());
		assertEquals(timeToDelivery, motoboy.getOrders().get(0).getTimeToDelivery());
		assertEquals(timestampArrivesAtRestaurant, motoboy.getOrders().get(0).getTimeToMotoboyArrivesAtRestaurant());
	}
	
	@Test
	public void testSearchMotoboyWithMotoboyArrivingAfterOrderReady() {
		OrderEntity order = new OrderEntity();
		OrderDeliveryEntity orderDelivery = new OrderDeliveryEntity();
		order.setOrderDelivery(orderDelivery);
		order.setId(10);
		
		RestaurantEntity restaurant = new RestaurantEntity();
		restaurant.setId(50);
		order.setRestaurant(restaurant);
		
		CustomerEntity customer = new CustomerEntity();
		customer.setLat(3.0);
		customer.setLon(4.0);
		order.setCustomer(customer);
		
		List<CacheMotoboy> cache = new ArrayList<>();
		when(cacheService.getCache()).thenReturn(cache);
		
		CacheMotoboy motoboy = new CacheMotoboy();
		motoboy.setId(1);
		motoboy.setLat(1.0);
		motoboy.setLon(2.0);
		motoboy.setOrders(new ArrayList<>());
		
		Timestamp timestampArrivesAtRestaurant = new Timestamp(1000L);
		CacheMotoboyDistance cacheMotoboyDistance = new CacheMotoboyDistance(motoboy, timestampArrivesAtRestaurant);
		when(cacheSearchMotoboyService.getNearestMotoboy(restaurant, cache)).thenReturn(cacheMotoboyDistance);
		
		Timestamp tenMinutes = new Timestamp(600L);
		when(timestampUtil.addSecondsFromNow(600L)).thenReturn(tenMinutes);
		
		Timestamp timeToDelivery = new Timestamp(1000L);
		when(calculateTimestamp.calculateEstimatedTimeToDelivery(order, motoboy, timestampArrivesAtRestaurant)).thenReturn(timeToDelivery);
		
		service.searchMotoboy(order);
		
		verify(cacheService, times(1)).updateCache(cache);
		verify(orderRepository, times(1)).save(order);
		
		assertEquals(timeToDelivery, order.getEstimatedTimeToDelivery());
		assertEquals(1, motoboy.getOrders().size());
		assertEquals(10, motoboy.getOrders().get(0).getId().intValue());
		assertEquals(50, motoboy.getOrders().get(0).getRestaurantId().intValue());
		assertEquals(new Double(3.0), motoboy.getOrders().get(0).getCacheDestination().getLat());
		assertEquals(new Double(4.0), motoboy.getOrders().get(0).getCacheDestination().getLon());
		assertEquals(timeToDelivery, motoboy.getOrders().get(0).getTimeToDelivery());
		assertEquals(timestampArrivesAtRestaurant, motoboy.getOrders().get(0).getTimeToMotoboyArrivesAtRestaurant());
	}
}
