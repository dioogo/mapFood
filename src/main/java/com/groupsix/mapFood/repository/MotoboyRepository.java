package com.groupsix.mapFood.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.groupsix.mapFood.entity.MotoboyEntity;

public interface MotoboyRepository extends JpaRepository<MotoboyEntity, Integer> {

	@Query(value = "select *, (" +
			"6371 * acos(cos(radians(?1)) * " + 
			"cos(radians(lat)) * " +
			"cos(radians(lon) - radians(?2)) + " +
			"sin(radians(?1)) * " +
			"sin(radians(lat )))" +
			") distance " +
			"FROM Motoboy motoboy " +
			"HAVING distance < 5 " +
			"ORDER BY distance " +
			"LIMIT 0, 3", 
			nativeQuery = true)
	List<MotoboyEntity> findNearby(Double lat, Double lon);

}
