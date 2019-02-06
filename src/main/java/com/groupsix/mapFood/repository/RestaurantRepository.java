package com.groupsix.mapFood.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.groupsix.mapFood.entity.RestaurantEntity;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Integer> {

}
