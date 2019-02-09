package com.groupsix.mapFood.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.groupsix.mapFood.entity.MotoboyEntity;

public interface MotoboyRepository extends JpaRepository<MotoboyEntity, Integer> {

	@Query(value = "SELECT m.*, SQRT(" + 
			"POW(69.1 * ((m.lat) - (?1)), 2) + " + 
			"POW(69.1 * ((?2) - (m.lon)) * COS((m.lat) / 57.3), 2)) AS distance     " + 
			"FROM Motoboy m HAVING distance < 50 ORDER BY distance", nativeQuery = true
			)
	List<MotoboyEntity> findNearby(Double lat, Double lon);

}
