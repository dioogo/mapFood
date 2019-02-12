package com.groupsix.mapFood.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.groupsix.mapFood.entity.OrderEntity;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, Integer> {
    List<OrderEntity> findByCustomer_Id(Integer id);

    List<OrderEntity> findByRestaurant_Id(Integer id);

    @Query(value = "SELECT COUNT(DISTINCT(customer_id)) FROM orders where restaurant_id = ?1", nativeQuery = true)
    int countAllCustomer(Integer id);

    @Query(value = "SELECT SUM(total) FROM orders where restaurant_id = ?1", nativeQuery = true)
    int countTotalRevenue(Integer id);

}
