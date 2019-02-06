package com.groupsix.mapFood.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.groupsix.mapFood.entity.OrderItemEntity;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Integer> {

	List<OrderItemEntity> findByIdIn(List<Integer> ids);

}
