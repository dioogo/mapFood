package com.groupsix.mapFood.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupsix.mapFood.entity.CustomerEntity;
import com.groupsix.mapFood.entity.MotoboyEntity;
import com.groupsix.mapFood.entity.OrderDeliveryEntity;
import com.groupsix.mapFood.entity.OrderEntity;
import com.groupsix.mapFood.entity.RestaurantEntity;
import com.groupsix.mapFood.pojo.CacheDestination;
import com.groupsix.mapFood.pojo.CacheMotoboy;
import com.groupsix.mapFood.pojo.CacheMotoboyDistance;
import com.groupsix.mapFood.pojo.CacheMotoboyOrder;
import com.groupsix.mapFood.repository.OrderRepository;
import com.groupsix.mapFood.timestamp.CalculateTimestamp;
import com.groupsix.mapFood.util.TimestampUtil;

@Service
public class OrderDeliveryService {
	
	@Autowired
	private CacheService cacheService;
	
	@Autowired
	private CalculateTimestamp calculateTimestamp;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CacheSearchMotoboyService cacheSearchMotoboyService;
	
	@Autowired
	private TimestampUtil timestampUtil;


	public OrderDeliveryEntity create(CustomerEntity customerEntity) {
		OrderDeliveryEntity orderDeliveryEntity = new OrderDeliveryEntity();

		orderDeliveryEntity.setDestinationLat(customerEntity.getLat());
		orderDeliveryEntity.setDestinationLon(customerEntity.getLon());
		
		return orderDeliveryEntity;
	}
	
	public void searchMotoboy(OrderEntity orderEntity) {

		RestaurantEntity restaurantEntity = orderEntity.getRestaurant();
		List<CacheMotoboy> cache = cacheService.getCache();
		CacheMotoboyDistance cachedMotoboy = cacheSearchMotoboyService.getNearestMotoboy(restaurantEntity, cache);

		MotoboyEntity motoboyEntity = new MotoboyEntity();
		motoboyEntity.setId(cachedMotoboy.getCacheMotoboy().getId());
		motoboyEntity.setLat(cachedMotoboy.getCacheMotoboy().getLat());
		motoboyEntity.setLon(cachedMotoboy.getCacheMotoboy().getLon());
		orderEntity.getOrderDelivery().setMotoboy(motoboyEntity);

		orderEntity.getOrderDelivery().setEstimatedTimeToRestaurant(cachedMotoboy.getTimestampArrivesAtRestaurant());

		Timestamp timeToOrderLeavesTheRestaurant = verifyTimeToOrderLeavesTheRestaurant(
				cachedMotoboy.getTimestampArrivesAtRestaurant());

		Timestamp timeToDelivery = calculateTimestamp.calculateEstimatedTimeToDelivery(orderEntity,
				cachedMotoboy.getCacheMotoboy(), timeToOrderLeavesTheRestaurant);

		orderEntity.setEstimatedTimeToDelivery(timeToDelivery);

		CacheMotoboyOrder cacheMotoboyOrder = createCacheMotoboyOrder(orderEntity, restaurantEntity,
				cachedMotoboy.getTimestampArrivesAtRestaurant(), timeToDelivery);

		cachedMotoboy.getCacheMotoboy().getOrders().add(cacheMotoboyOrder);
		cacheService.updateCache(cache);

		orderRepository.save(orderEntity);
	}

	private CacheMotoboyOrder createCacheMotoboyOrder(OrderEntity orderEntity, RestaurantEntity restaurantEntity,
			Timestamp timeToMotoboyArriveAtRestaurant, Timestamp timeToDelivery) {
		CacheMotoboyOrder cacheMotoboyOrder = new CacheMotoboyOrder();
		cacheMotoboyOrder.setId(orderEntity.getId());
		cacheMotoboyOrder.setRestaurantId(restaurantEntity.getId());
		CacheDestination cacheDestination = new CacheDestination();
		cacheDestination.setLat(orderEntity.getCustomer().getLat());
		cacheDestination.setLon(orderEntity.getCustomer().getLon());
		cacheMotoboyOrder.setCacheDestination(cacheDestination);
		cacheMotoboyOrder.setTimeToMotoboyArrivesAtRestaurant(timeToMotoboyArriveAtRestaurant);
		cacheMotoboyOrder.setTimeToDelivery(timeToDelivery);
		return cacheMotoboyOrder;
	}

	private Timestamp verifyTimeToOrderLeavesTheRestaurant(Timestamp estimatedTimeToRestaurant) {
		Timestamp tenMinutes = timestampUtil.addTenMinutesFromNow();

		if (estimatedTimeToRestaurant.before(tenMinutes)) {
			return tenMinutes;
		} else {
			return estimatedTimeToRestaurant;
		}
	}

}
