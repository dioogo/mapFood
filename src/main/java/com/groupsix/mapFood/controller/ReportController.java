package com.groupsix.mapFood.controller;

import com.groupsix.mapFood.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
public class ReportController {

	@Autowired
	private ReportService reportService;
	
	@GetMapping
	@RequestMapping("restaurant/{id}")
	public ResponseEntity<?> restaurantReport(@PathVariable Integer id) {

		try {
			return ResponseEntity.status(HttpStatus.FOUND).body(reportService.getRestaurantOrdersReport(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
		}
	}
}
