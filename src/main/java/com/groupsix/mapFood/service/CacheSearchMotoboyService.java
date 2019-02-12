package com.groupsix.mapFood.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupsix.mapFood.entity.RestaurantEntity;
import com.groupsix.mapFood.pojo.CacheMotoboy;
import com.groupsix.mapFood.pojo.CacheMotoboyDistance;
import com.groupsix.mapFood.timestamp.CalculateTimestamp;
import com.groupsix.mapFood.util.DistanceUtil;
import com.groupsix.mapFood.util.TimestampUtil;

@Service
public class CacheSearchMotoboyService {

	private static final int MAX_ORDERS_PER_MOTOBOY = 5;
	private static final int LIMIT_MOTOBOYS_TO_QUERY_IN_GOOGLE = 3;

	@Autowired
	private CalculateTimestamp calculateTimestamp;

	@Autowired
	private TimestampUtil timestampUtil;

	public CacheMotoboyDistance getNearestMotoboy(RestaurantEntity restaurantEntity, List<CacheMotoboy> cache) {

		List<CacheMotoboy> cacheMotoboysWithSameRestaurant = searchForMotoboysDeliveringForSameRestaurant(
				restaurantEntity, cache);

		if (cacheMotoboysWithSameRestaurant.size() != 0) {

			Timestamp tenMinutes = timestampUtil.addTenMinutesFromNow();
			// Filtro por motoboys que estão entregando no mesmo restaurante com menos de 5
			// pedidos e que
			// vai chegar depois do novo pedido ficar pronto
			Optional<CacheMotoboy> optionalCacheMotoboy = cacheMotoboysWithSameRestaurant.stream()
					.filter(c -> c.getOrders().size() < MAX_ORDERS_PER_MOTOBOY
							&& c.getOrders().get(0).getTimeToMotoboyArrivesAtRestaurant().after(tenMinutes))
					.findFirst();
			if (optionalCacheMotoboy.isPresent()) {
				return new CacheMotoboyDistance(optionalCacheMotoboy.get(),
						optionalCacheMotoboy.get().getOrders().get(0).getTimeToMotoboyArrivesAtRestaurant());
			}
		}

		return getNearestMotoboyWithoutOrders(restaurantEntity, cache, cacheMotoboysWithSameRestaurant);

	}

	private CacheMotoboyDistance getNearestMotoboyWithoutOrders(RestaurantEntity restaurantEntity,
			List<CacheMotoboy> cache, List<CacheMotoboy> cacheMotoboysWithSameRestaurant) {

		// Removo os motoboys com pedido no mesmo restaurante mas que não se aplicaram a
		// regra de reusar um motoboy
		Stream<CacheMotoboy> stream = cacheMotoboysWithSameRestaurant.size() == 0 ? cache.stream()
				: cache.stream().filter(c -> !cacheMotoboysWithSameRestaurant.stream().map(c1 -> c1.getId())
						.collect(Collectors.toList()).contains(c.getId()));

		// Removo os motoboys com pedidos de outros restaurantes e pego os três mais
		// próximos
		Stream<CacheMotoboy> nearestMotoboys = stream.filter(c -> c.getOrders().size() == 0)
				.sorted((c1, c2) -> Double.compare(
						DistanceUtil.distance(c1.getLat(), c1.getLon(), restaurantEntity.getLat(),
								restaurantEntity.getLon()),
						DistanceUtil.distance(c2.getLat(), c2.getLon(), restaurantEntity.getLat(),
								restaurantEntity.getLon())))
				.limit(LIMIT_MOTOBOYS_TO_QUERY_IN_GOOGLE);

		// Dos três mais próximos, eu calculo qual chega mais rápido baseado no Google
		// Maps
		return nearestMotoboys
				.map(m -> new CacheMotoboyDistance(m,
						calculateTimestamp.calculateEstimatedTimeMotoboyArrivesAtRestaurant(restaurantEntity, m)))
				.sorted((m1, m2) -> Long.compare(m1.getTimestampArrivesAtRestaurant().getTime(),
						m2.getTimestampArrivesAtRestaurant().getTime()))
				.collect(Collectors.toList()).get(0);
	}

	private List<CacheMotoboy> searchForMotoboysDeliveringForSameRestaurant(RestaurantEntity restaurantEntity,
			List<CacheMotoboy> cache) {
		return cache.stream()
				.filter(m -> m.getOrders().size() != 0
						&& m.getOrders().get(0).getRestaurantId().equals(restaurantEntity.getId()))
				.collect(Collectors.toList());
	}
}
