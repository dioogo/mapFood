package com.groupsix.mapFood.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.groupsix.mapFood.entity.OrderEntity;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, Integer> {

    @Query(value = "SELECT o.id, " +
            "o.customer_id, " +
            "o.estimated_time_to_delivery, " +
            "o.total, " +
            "od.estimated_time_to_restaurant, " +
            "od.motoboy_id" +
            "FROM order_delivery od INNER JOIN `order` o ON od.order_id = o.id" +
            "WHERE o.restaurant_id = ?1", nativeQuery = true)
    List<OrderSummary>findAllByRestaurantId(Integer id);
}
