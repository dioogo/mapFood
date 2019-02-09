package com.groupsix.mapFood.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupsix.mapFood.entity.MotoboyEntity;
import com.groupsix.mapFood.repository.MotoboyRepository;

@Service
public class MotoboyService {

	@Autowired
	private MotoboyRepository motoboyRepository;
	
	public List<MotoboyEntity> findNearby(Double lat, Double lon) {
		return motoboyRepository.findNearby(lat, lon);
	}
}
