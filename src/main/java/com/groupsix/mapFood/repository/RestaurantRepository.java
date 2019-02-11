package com.groupsix.mapFood.repository;

import com.groupsix.mapFood.entity.RestaurantEntity;
import com.groupsix.mapFood.pojo.RestaurantOrdersReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Integer> {

}
