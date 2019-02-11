package com.groupsix.mapFood.timestamp;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.maps.model.LatLng;
import com.groupsix.mapFood.entity.CustomerEntity;
import com.groupsix.mapFood.entity.OrderEntity;
import com.groupsix.mapFood.entity.RestaurantEntity;
import com.groupsix.mapFood.pojo.CacheDestination;
import com.groupsix.mapFood.pojo.CacheMotoboy;
import com.groupsix.mapFood.pojo.CacheMotoboyOrder;
import com.groupsix.mapFood.service.GoogleMapsService;
import com.groupsix.mapFood.util.TimestampUtil;

@Component
public class CalculateTimestamp {

	@Autowired
	private GoogleMapsService googleMapsService;
	
	@Autowired
	private TimestampUtil timestampUtil;
	
	public Timestamp calculateEstimatedTimeToDelivery(OrderEntity orderEntity, CacheMotoboy cachedMotoboy,
			Timestamp timeToOrderLeavesTheRestaurant) {
		Timestamp timeToDelivery = null;
		if (cachedMotoboy.getOrders().size() == 0) {
			timeToDelivery = estimateTimeToDelivery(orderEntity, timeToOrderLeavesTheRestaurant);
		} else {
			CacheMotoboyOrder lastOrderToRestaurant = cachedMotoboy.getOrders()
					.get(cachedMotoboy.getOrders().size() - 1);
			timeToDelivery = estimateTimeToDeliveryFromOtherCustomer(lastOrderToRestaurant.getCacheDestination(),
					orderEntity.getCustomer(), lastOrderToRestaurant.getTimeToDelivery());
		}
		return timeToDelivery;
	}

	public Timestamp calculateEstimatedTimeMotoboyArrivesAtRestaurant(RestaurantEntity restaurantEntity,
			CacheMotoboy cachedMotoboy) {
		Timestamp timeToMotoboyArriveAtRestaurant;
		if (cachedMotoboy.getOrders().size() != 0) {
			timeToMotoboyArriveAtRestaurant = cachedMotoboy.getOrders().get(0).getTimeToMotoboyArrivesAtRestaurant();
		} else {
			timeToMotoboyArriveAtRestaurant = estimateTimeToMotoboyArriveAtRestaurant(restaurantEntity, cachedMotoboy);
		}
		return timeToMotoboyArriveAtRestaurant;
	}
	
	private Timestamp estimateTimeToDelivery(OrderEntity orderEntity, Timestamp outTime) {
		LatLng restaurantPosition = new LatLng(orderEntity.getRestaurant().getLat(),
				orderEntity.getRestaurant().getLon());
		LatLng customerPosition = new LatLng(orderEntity.getCustomer().getLat(), orderEntity.getCustomer().getLon());

		return timestampUtil.addSeconds(googleMapsService.timeToReach(restaurantPosition, customerPosition), outTime);
	}

	private Timestamp estimateTimeToDeliveryFromOtherCustomer(CacheDestination cacheDestination,
			CustomerEntity customerDestination, Timestamp outTime) {
		LatLng customerFromLastOrderPosition = new LatLng(cacheDestination.getLat(), cacheDestination.getLon());
		LatLng customerPosition = new LatLng(customerDestination.getLat(), customerDestination.getLon());

		return timestampUtil.addSeconds(googleMapsService.timeToReach(customerFromLastOrderPosition, customerPosition), outTime);
	}

	private Timestamp estimateTimeToMotoboyArriveAtRestaurant(RestaurantEntity restaurantEntity, CacheMotoboy cachedMotoboy) {
		LatLng start = new LatLng(cachedMotoboy.getLat(), cachedMotoboy.getLon());
		LatLng end = new LatLng(restaurantEntity.getLat(), restaurantEntity.getLon());

		return timestampUtil.addSecondsFromNow(googleMapsService.timeToReach(start, end));
	}
}
