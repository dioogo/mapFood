package com.groupsix.mapFood.repository;

import com.groupsix.mapFood.pojo.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.groupsix.mapFood.entity.OrderEntity;

import java.util.List;
import java.util.Set;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {


    List<OrderEntity> findByCustomer_Id(Integer id);

    List<OrderEntity> findByRestaurant_Id(Integer id);
    @Query(value = "SELECT DISTINCT(customer_id) FROM orders where restaurant_id = '%id%'", nativeQuery = true)
    List<OrderEntity> countAllCustomer(Integer id);


}
