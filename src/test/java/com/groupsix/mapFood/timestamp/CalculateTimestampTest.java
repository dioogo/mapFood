package com.groupsix.mapFood.timestamp;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.maps.model.LatLng;
import com.groupsix.mapFood.entity.CustomerEntity;
import com.groupsix.mapFood.entity.OrderEntity;
import com.groupsix.mapFood.entity.RestaurantEntity;
import com.groupsix.mapFood.pojo.CacheDestination;
import com.groupsix.mapFood.pojo.CacheMotoboy;
import com.groupsix.mapFood.pojo.CacheMotoboyOrder;
import com.groupsix.mapFood.service.GoogleMapsService;
import com.groupsix.mapFood.util.TimestampUtil;

@RunWith(MockitoJUnitRunner.class)
public class CalculateTimestampTest {

	@Mock
	private GoogleMapsService googleMapsService;
	
	@Mock
	private TimestampUtil timestampUtil;
	
	@InjectMocks
	private CalculateTimestamp calculateTimestamp;
	
	@Captor
	private ArgumentCaptor<LatLng> latLonFrom;
	
	@Captor
	private ArgumentCaptor<LatLng> latLonTo;
	
	@Test
	public void testCalculateEstimatedTimeToDeliveryWithoutOrders() {
		
		CacheMotoboy motoboy = new CacheMotoboy();
		motoboy.setOrders(new ArrayList<>());
		
		Timestamp timeToOrderLeavesTheRestaurant = new Timestamp(100);
		
		CustomerEntity customer = new CustomerEntity();
		customer.setLat(1.0);
		customer.setLon(2.0);
		
		RestaurantEntity restaurant = new RestaurantEntity();
		restaurant.setLat(3.0);
		restaurant.setLon(4.0);
		
		OrderEntity order = new OrderEntity();
		order.setRestaurant(restaurant);
		order.setCustomer(customer);
		
		when(googleMapsService.timeToReach(latLonFrom.capture(), latLonTo.capture())).thenReturn(100L);
		Timestamp timestampToDelivery = new Timestamp(200);
		when(timestampUtil.addSeconds(100L, timeToOrderLeavesTheRestaurant)).thenReturn(timestampToDelivery);
		
		Timestamp result = calculateTimestamp.calculateEstimatedTimeToDelivery(order, motoboy, timeToOrderLeavesTheRestaurant);
		assertEquals(timestampToDelivery, result);
		assertEquals(new Double(3.0), new Double(latLonFrom.getValue().lat));
		assertEquals(new Double(4.0), new Double(latLonFrom.getValue().lng));
		
		assertEquals(new Double(1.0), new Double(latLonTo.getValue().lat));
		assertEquals(new Double(2.0), new Double(latLonTo.getValue().lng));
	}
	
	@Test
	public void testCalculateEstimatedTimeToDeliveryWithOneOrder() {
		
		CacheMotoboy motoboy = new CacheMotoboy();
		motoboy.setOrders(new ArrayList<>());
		CacheMotoboyOrder cachedOrder = new CacheMotoboyOrder();
		CacheDestination cachedDestination = new CacheDestination();
		cachedDestination.setLat(5.0);
		cachedDestination.setLon(6.0);
		cachedOrder.setCacheDestination(cachedDestination);
		Timestamp timeLastOrderLeavesTheRestaurant = new Timestamp(100);
		cachedOrder.setTimeToDelivery(timeLastOrderLeavesTheRestaurant);
		motoboy.getOrders().add(cachedOrder);
		
		Timestamp timeToOrderLeavesTheRestaurant = new Timestamp(100);
		
		CustomerEntity customer = new CustomerEntity();
		customer.setLat(1.0);
		customer.setLon(2.0);
		
		OrderEntity order = new OrderEntity();
		order.setCustomer(customer);
		
		when(googleMapsService.timeToReach(latLonFrom.capture(), latLonTo.capture())).thenReturn(100L);
		Timestamp timestampToDelivery = new Timestamp(200);
		when(timestampUtil.addSeconds(100L, timeLastOrderLeavesTheRestaurant)).thenReturn(timestampToDelivery);
		
		Timestamp result = calculateTimestamp.calculateEstimatedTimeToDelivery(order, motoboy, timeToOrderLeavesTheRestaurant);
		assertEquals(timestampToDelivery, result);
		
		assertEquals(new Double(5.0), new Double(latLonFrom.getValue().lat));
		assertEquals(new Double(6.0), new Double(latLonFrom.getValue().lng));
		
		assertEquals(new Double(1.0), new Double(latLonTo.getValue().lat));
		assertEquals(new Double(2.0), new Double(latLonTo.getValue().lng));
	}
	
	@Test
	public void testCalculateEstimatedTimeMotoboyArrivesAtRestaurantWithoutOrders() {
		CacheMotoboy motoboy = new CacheMotoboy();
		motoboy.setLat(1.0);
		motoboy.setLon(2.0);
		motoboy.setOrders(new ArrayList<>());

		RestaurantEntity restaurant = new RestaurantEntity();
		restaurant.setLat(3.0);
		restaurant.setLon(4.0);
		
		when(googleMapsService.timeToReach(latLonFrom.capture(), latLonTo.capture())).thenReturn(100L);
		Timestamp timestampToDelivery = new Timestamp(200);
		when(timestampUtil.addSecondsFromNow(100L)).thenReturn(timestampToDelivery);
		
		Timestamp result = calculateTimestamp.calculateEstimatedTimeMotoboyArrivesAtRestaurant(restaurant, motoboy);
		assertEquals(timestampToDelivery, result);
		
		assertEquals(new Double(1.0), new Double(latLonFrom.getValue().lat));
		assertEquals(new Double(2.0), new Double(latLonFrom.getValue().lng));
		
		assertEquals(new Double(3.0), new Double(latLonTo.getValue().lat));
		assertEquals(new Double(4.0), new Double(latLonTo.getValue().lng));
	}
	
	@Test
	public void testCalculateEstimatedTimeMotoboyArrivesAtRestaurantWithOrders() {
		CacheMotoboy motoboy = new CacheMotoboy();
		motoboy.setOrders(new ArrayList<>());
		CacheMotoboyOrder cachedOrder = new CacheMotoboyOrder();

		Timestamp timeMotoboyArrivesArRestaurant = new Timestamp(100);
		cachedOrder.setTimeToMotoboyArrivesAtRestaurant(timeMotoboyArrivesArRestaurant);
		motoboy.getOrders().add(cachedOrder);

		Timestamp result = calculateTimestamp.calculateEstimatedTimeMotoboyArrivesAtRestaurant(null, motoboy);
		assertEquals(timeMotoboyArrivesArRestaurant, result);
	}
	
}
