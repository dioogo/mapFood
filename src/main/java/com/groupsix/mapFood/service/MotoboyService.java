package com.groupsix.mapFood.service;

import java.util.List;

import com.groupsix.mapFood.entities.Motoboy;

public interface MotoboyService {

	/**
	 * Provide a list of motoboys nearby given the latitude and longitude
	 * @param lat
	 * @param lon
	 * @return List<Motoboy>
	 */
	public List<Motoboy> findNearby(Double lat, Double lon);
	
}
