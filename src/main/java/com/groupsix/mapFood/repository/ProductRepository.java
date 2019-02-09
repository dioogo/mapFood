package com.groupsix.mapFood.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groupsix.mapFood.entity.OrderItemEntity;
import com.groupsix.mapFood.entity.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

	List<OrderItemEntity> findByIdIn(List<Integer> ids);

}
