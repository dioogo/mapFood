package com.groupsix.mapFood.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.groupsix.mapFood.entity.MotoboyEntity;

public interface MotoboyRepository extends JpaRepository<MotoboyEntity, Integer> {

	/*
	 * @Query(value = "select *, (" + "6371 * acos(cos(radians(?1)) * " +
	 * "cos(radians(lat)) * " + "cos(radians(lon) - radians(?2)) + " +
	 * "sin(radians(?1)) * " + "sin(radians(lat )))" + ") distance " +
	 * "FROM Motoboy motoboy " + "HAVING distance < ?3 " + "ORDER BY distance " +
	 * "LIMIT 0, 3", nativeQuery = true)
	 */
	@Query(value = "SELECT m.*, SQRT(" + 
			"POW(69.1 * ((m.lat) - (?1)), 2) + " + 
			"POW(69.1 * ((?2) - (m.lon)) * COS((m.lat) / 57.3), 2)) AS distance     " + 
			"FROM Motoboy m HAVING distance < 50 ORDER BY distance", nativeQuery = true
			)
	List<MotoboyEntity> findNeaby(Double lat, Double lon);

}
