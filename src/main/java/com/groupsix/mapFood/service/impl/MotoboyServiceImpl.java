package com.groupsix.mapFood.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupsix.mapFood.entities.Motoboy;
import com.groupsix.mapFood.repositories.MotoboyRepository;
import com.groupsix.mapFood.service.MotoboyService;

@Service
public class MotoboyServiceImpl implements MotoboyService {

	private static final Logger log = LoggerFactory.getLogger(MotoboyServiceImpl.class);
	
	@Autowired
	private MotoboyRepository motoboyRepository;
	
	@Override
	public List<Motoboy> findNearby(Double lat, Double lon) {
		log.info("Finding near motoboy in 3km distance");
		return motoboyRepository.findNearby(lat, lon);
	}

	
}
