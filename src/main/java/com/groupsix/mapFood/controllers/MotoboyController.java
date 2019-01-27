package com.groupsix.mapFood.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groupsix.mapFood.entities.Motoboy;
import com.groupsix.mapFood.service.MotoboyService;

@RestController
@RequestMapping("/motoboy")
public class MotoboyController {

	@Autowired
	private MotoboyService motoboyService;
	
	@GetMapping(value = "/nearby")
	public ResponseEntity<List<Motoboy>> listarPorPerto() {
		List<Motoboy> motoboys = motoboyService.findNearby(-30.0283716, -51.13405395);
		
		return ResponseEntity.ok(motoboys);
	}
}
