package com.groupsix.mapFood.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.groupsix.mapFood.pojo.RestaurantOrderReport;

import com.groupsix.mapFood.entity.OrderEntity;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, Integer> {

    @Query(value = "SELECT o.id, " +
            "o.customer_id, " +
            "o.estimated_time_to_delivery, " +
            "o.total, " +
            "od.estimated_time_to_restaurant, " +
            "od.motoboy_id, " +
            "SQRT((POWER(r.lon - od.destination_lon, 2) + POWER(r.lat - od.destination_lat, 2))) * 111 as distance, " +
            "SQRT((POWER(r.lon - od.destination_lon, 2) + POWER(r.lat - od.destination_lat, 2))) * 111 / 56 as litrosConsumidos " +
            "FROM order_delivery od INNER JOIN `order` o ON od.order_id = o.id INNER JOIN restaurant r ON o.restaurant_id = r.id " +
            "WHERE o.restaurant_id = ?1", nativeQuery = true)
    List<RestaurantOrderReport>findAllByRestaurantId(Integer id);
}
