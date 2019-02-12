package com.groupsix.mapFood.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.groupsix.mapFood.entity.RestaurantEntity;
import com.groupsix.mapFood.pojo.CacheMotoboy;
import com.groupsix.mapFood.pojo.CacheMotoboyDistance;
import com.groupsix.mapFood.pojo.CacheMotoboyOrder;
import com.groupsix.mapFood.timestamp.CalculateTimestamp;
import com.groupsix.mapFood.util.TimestampUtil;

@RunWith(MockitoJUnitRunner.class)
public class CacheSearchMotoboyServiceTest {

	@Mock
	private CalculateTimestamp calculateTimestamp;
	
	@Mock
	private TimestampUtil timestampUtil;
	
	@InjectMocks
	private CacheSearchMotoboyService service;
	
	@Test
	public void testGetNearestMotoboyWithMotoboyDeliveringToSameRestaurantAndLessThan5Orders() {
		
		List<CacheMotoboy> cache = new ArrayList<>();
		CacheMotoboy m1 = new CacheMotoboy();
		m1.setOrders(new ArrayList<>());
		cache.add(m1);
		
		CacheMotoboy m2 = new CacheMotoboy();
		m2.setOrders(new ArrayList<>());
		CacheMotoboyOrder motoboyOrder = new CacheMotoboyOrder();
		Timestamp afterTenMinutes = new Timestamp(1000L);
		motoboyOrder.setTimeToMotoboyArrivesAtRestaurant(afterTenMinutes);
		motoboyOrder.setRestaurantId(1);
		m2.getOrders().add(motoboyOrder);
		cache.add(m2);
		
		Timestamp tenMinutes = new Timestamp(600L);
		when(timestampUtil.addTenMinutesFromNow()).thenReturn(tenMinutes);
		
		RestaurantEntity restaurantEntity = new RestaurantEntity();
		restaurantEntity.setId(1);
		CacheMotoboyDistance result = service.getNearestMotoboy(restaurantEntity, cache);
		
		assertEquals(m2, result.getCacheMotoboy());
		assertEquals(afterTenMinutes, result.getTimestampArrivesAtRestaurant());
		
	}
	
	@Test
	public void testGetNearestMotoboyWithoutAnyOrderAndDistanceAndTimeAreOrdered() {
		
		List<CacheMotoboy> cache = new ArrayList<>();
		// Terceiro mais próximo
		CacheMotoboy m1 = new CacheMotoboy();
		m1.setLat(-30.0255805);
		m1.setLon(-51.2013164);
		m1.setOrders(new ArrayList<>());
		cache.add(m1);
		
		// Segundo mais próximo
		CacheMotoboy m2 = new CacheMotoboy();
		m2.setLat(-30.0282025);
		m2.setLon(-51.2033363);
		m2.setOrders(new ArrayList<>());
		cache.add(m2);
		
		//Motoboy mais próximo
		CacheMotoboy m3 = new CacheMotoboy();
		m3.setLat(-30.035264);
		m3.setLon(-51.2117427);
		m3.setOrders(new ArrayList<>());
		cache.add(m3);
		
		// Mais longe
		CacheMotoboy m4 = new CacheMotoboy();
		m4.setLat(-30.0196725);
		m4.setLon(-51.195394);
		m4.setOrders(new ArrayList<>());
		cache.add(m4);
		
		RestaurantEntity restaurantEntity = new RestaurantEntity();
		restaurantEntity.setId(1);
		restaurantEntity.setLat(-30.035264);
		restaurantEntity.setLon(-51.2117427);
		
		when(calculateTimestamp.calculateEstimatedTimeMotoboyArrivesAtRestaurant(restaurantEntity, m3)).thenReturn(new Timestamp(100L));
		when(calculateTimestamp.calculateEstimatedTimeMotoboyArrivesAtRestaurant(restaurantEntity, m2)).thenReturn(new Timestamp(200L));
		when(calculateTimestamp.calculateEstimatedTimeMotoboyArrivesAtRestaurant(restaurantEntity, m1)).thenReturn(new Timestamp(300L));
		
		CacheMotoboyDistance result = service.getNearestMotoboy(restaurantEntity, cache);
		
		assertEquals(m3, result.getCacheMotoboy());
	}
	
	@Test
	public void testGetNearestMotoboyWithoutAnyOrderAndDistanceAndTimeAreNotOrdered() {
		
		List<CacheMotoboy> cache = new ArrayList<>();
		// Terceiro mais próximo
		CacheMotoboy m1 = new CacheMotoboy();
		m1.setLat(-30.0255805);
		m1.setLon(-51.2013164);
		m1.setOrders(new ArrayList<>());
		cache.add(m1);
		
		// Segundo mais próximo
		CacheMotoboy m2 = new CacheMotoboy();
		m2.setLat(-30.0282025);
		m2.setLon(-51.2033363);
		m2.setOrders(new ArrayList<>());
		cache.add(m2);
		
		//Motoboy mais próximo, mas demora mais para chegar que o segundo
		CacheMotoboy m3 = new CacheMotoboy();
		m3.setLat(-30.035264);
		m3.setLon(-51.2117427);
		m3.setOrders(new ArrayList<>());
		cache.add(m3);
		
		// Mais longe
		CacheMotoboy m4 = new CacheMotoboy();
		m4.setLat(-30.0196725);
		m4.setLon(-51.195394);
		m4.setOrders(new ArrayList<>());
		cache.add(m4);
		
		RestaurantEntity restaurantEntity = new RestaurantEntity();
		restaurantEntity.setId(1);
		restaurantEntity.setLat(-30.035264);
		restaurantEntity.setLon(-51.2117427);
		
		when(calculateTimestamp.calculateEstimatedTimeMotoboyArrivesAtRestaurant(restaurantEntity, m3)).thenReturn(new Timestamp(200L));
		when(calculateTimestamp.calculateEstimatedTimeMotoboyArrivesAtRestaurant(restaurantEntity, m2)).thenReturn(new Timestamp(100L));
		when(calculateTimestamp.calculateEstimatedTimeMotoboyArrivesAtRestaurant(restaurantEntity, m1)).thenReturn(new Timestamp(300L));
		
		CacheMotoboyDistance result = service.getNearestMotoboy(restaurantEntity, cache);
		
		assertEquals(m2, result.getCacheMotoboy());
	}
	
	@Test
	public void testGetNearestMotoboyWithNearestMotoboyFullOfOrders() {
		
		List<CacheMotoboy> cache = new ArrayList<>();
		// Terceiro mais próximo
		CacheMotoboy m1 = new CacheMotoboy();
		m1.setId(1);
		m1.setLat(-30.0255805);
		m1.setLon(-51.2013164);
		m1.setOrders(new ArrayList<>());
		cache.add(m1);
		
		// Segundo mais próximo
		CacheMotoboy m2 = new CacheMotoboy();
		m2.setId(2);
		m2.setLat(-30.0282025);
		m2.setLon(-51.2033363);
		m2.setOrders(new ArrayList<>());
		cache.add(m2);
		
		//Motoboy mais próximo
		CacheMotoboy m3 = new CacheMotoboy();
		m3.setId(3);
		m3.setLat(-30.035264);
		m3.setLon(-51.2117427);
		m3.setOrders(new ArrayList<>());
		CacheMotoboyOrder motoboyOrder = new CacheMotoboyOrder();
		Timestamp afterTenMinutes = new Timestamp(1000L);
		motoboyOrder.setTimeToMotoboyArrivesAtRestaurant(afterTenMinutes);
		motoboyOrder.setRestaurantId(1);
		m3.getOrders().add(motoboyOrder);
		m3.getOrders().add(motoboyOrder);
		m3.getOrders().add(motoboyOrder);
		m3.getOrders().add(motoboyOrder);
		m3.getOrders().add(motoboyOrder);
		cache.add(m3);
		
		// Mais longe
		CacheMotoboy m4 = new CacheMotoboy();
		m4.setId(4);
		m4.setLat(-30.0196725);
		m4.setLon(-51.195394);
		m4.setOrders(new ArrayList<>());
		cache.add(m4);
		
		RestaurantEntity restaurantEntity = new RestaurantEntity();
		restaurantEntity.setId(1);
		restaurantEntity.setLat(-30.035264);
		restaurantEntity.setLon(-51.2117427);
		
		Timestamp tenMinutes = new Timestamp(600L);
		when(timestampUtil.addSecondsFromNow(600L)).thenReturn(tenMinutes);
		
		when(calculateTimestamp.calculateEstimatedTimeMotoboyArrivesAtRestaurant(restaurantEntity, m2)).thenReturn(new Timestamp(200L));
		when(calculateTimestamp.calculateEstimatedTimeMotoboyArrivesAtRestaurant(restaurantEntity, m1)).thenReturn(new Timestamp(300L));
		when(calculateTimestamp.calculateEstimatedTimeMotoboyArrivesAtRestaurant(restaurantEntity, m4)).thenReturn(new Timestamp(400L));
		
		CacheMotoboyDistance result = service.getNearestMotoboy(restaurantEntity, cache);
		
		assertEquals(m2, result.getCacheMotoboy());
		verify(calculateTimestamp, never()).calculateEstimatedTimeMotoboyArrivesAtRestaurant(restaurantEntity, m3);
	}
	
	@Test
	public void testGetNearestMotoboyWithNearestMotoboyArrivingBeforeOrderIsReady() {
		
		List<CacheMotoboy> cache = new ArrayList<>();
		// Terceiro mais próximo
		CacheMotoboy m1 = new CacheMotoboy();
		m1.setId(1);
		m1.setLat(-30.0255805);
		m1.setLon(-51.2013164);
		m1.setOrders(new ArrayList<>());
		cache.add(m1);
		
		// Segundo mais próximo
		CacheMotoboy m2 = new CacheMotoboy();
		m2.setId(2);
		m2.setLat(-30.0282025);
		m2.setLon(-51.2033363);
		m2.setOrders(new ArrayList<>());
		cache.add(m2);
		
		//Motoboy mais próximo
		CacheMotoboy m3 = new CacheMotoboy();
		m3.setId(3);
		m3.setLat(-30.035264);
		m3.setLon(-51.2117427);
		m3.setOrders(new ArrayList<>());
		CacheMotoboyOrder motoboyOrder = new CacheMotoboyOrder();
		Timestamp beforeTenMinutes = new Timestamp(200L);
		motoboyOrder.setTimeToMotoboyArrivesAtRestaurant(beforeTenMinutes);
		motoboyOrder.setRestaurantId(2);
		m3.getOrders().add(motoboyOrder);
		cache.add(m3);
		
		// Mais longe
		CacheMotoboy m4 = new CacheMotoboy();
		m4.setId(4);
		m4.setLat(-30.0196725);
		m4.setLon(-51.195394);
		m4.setOrders(new ArrayList<>());
		cache.add(m4);
		
		RestaurantEntity restaurantEntity = new RestaurantEntity();
		restaurantEntity.setId(1);
		restaurantEntity.setLat(-30.035264);
		restaurantEntity.setLon(-51.2117427);
		
		Timestamp tenMinutes = new Timestamp(600L);
		when(timestampUtil.addSecondsFromNow(600L)).thenReturn(tenMinutes);
		
		when(calculateTimestamp.calculateEstimatedTimeMotoboyArrivesAtRestaurant(restaurantEntity, m2)).thenReturn(new Timestamp(200L));
		when(calculateTimestamp.calculateEstimatedTimeMotoboyArrivesAtRestaurant(restaurantEntity, m1)).thenReturn(new Timestamp(300L));
		when(calculateTimestamp.calculateEstimatedTimeMotoboyArrivesAtRestaurant(restaurantEntity, m4)).thenReturn(new Timestamp(400L));
		
		CacheMotoboyDistance result = service.getNearestMotoboy(restaurantEntity, cache);
		
		assertEquals(m2, result.getCacheMotoboy());
		verify(calculateTimestamp, never()).calculateEstimatedTimeMotoboyArrivesAtRestaurant(restaurantEntity, m3);
	}
	
	@Test
	public void testGetNearestMotoboyWithNearestMotoboyDeliveringForAnotherRestaurant() {
		
		List<CacheMotoboy> cache = new ArrayList<>();
		// Terceiro mais próximo
		CacheMotoboy m1 = new CacheMotoboy();
		m1.setId(1);
		m1.setLat(-30.0255805);
		m1.setLon(-51.2013164);
		m1.setOrders(new ArrayList<>());
		cache.add(m1);
		
		// Segundo mais próximo
		CacheMotoboy m2 = new CacheMotoboy();
		m2.setId(2);
		m2.setLat(-30.0282025);
		m2.setLon(-51.2033363);
		m2.setOrders(new ArrayList<>());
		cache.add(m2);
		
		//Motoboy mais próximo
		CacheMotoboy m3 = new CacheMotoboy();
		m3.setId(3);
		m3.setLat(-30.035264);
		m3.setLon(-51.2117427);
		m3.setOrders(new ArrayList<>());
		CacheMotoboyOrder motoboyOrder = new CacheMotoboyOrder();
		Timestamp beforeTenMinutes = new Timestamp(200L);
		motoboyOrder.setTimeToMotoboyArrivesAtRestaurant(beforeTenMinutes);
		motoboyOrder.setRestaurantId(1);
		m3.getOrders().add(motoboyOrder);
		m3.getOrders().add(motoboyOrder);
		m3.getOrders().add(motoboyOrder);
		m3.getOrders().add(motoboyOrder);
		m3.getOrders().add(motoboyOrder);
		cache.add(m3);
		
		// Mais longe
		CacheMotoboy m4 = new CacheMotoboy();
		m4.setId(4);
		m4.setLat(-30.0196725);
		m4.setLon(-51.195394);
		m4.setOrders(new ArrayList<>());
		cache.add(m4);
		
		RestaurantEntity restaurantEntity = new RestaurantEntity();
		restaurantEntity.setId(1);
		restaurantEntity.setLat(-30.035264);
		restaurantEntity.setLon(-51.2117427);
		
		Timestamp tenMinutes = new Timestamp(600L);
		when(timestampUtil.addSecondsFromNow(600L)).thenReturn(tenMinutes);
		
		
		when(calculateTimestamp.calculateEstimatedTimeMotoboyArrivesAtRestaurant(restaurantEntity, m2)).thenReturn(new Timestamp(200L));
		when(calculateTimestamp.calculateEstimatedTimeMotoboyArrivesAtRestaurant(restaurantEntity, m1)).thenReturn(new Timestamp(300L));
		when(calculateTimestamp.calculateEstimatedTimeMotoboyArrivesAtRestaurant(restaurantEntity, m4)).thenReturn(new Timestamp(400L));
		
		CacheMotoboyDistance result = service.getNearestMotoboy(restaurantEntity, cache);
		
		assertEquals(m2, result.getCacheMotoboy());
		verify(calculateTimestamp, never()).calculateEstimatedTimeMotoboyArrivesAtRestaurant(restaurantEntity, m3);
	}
	
}
