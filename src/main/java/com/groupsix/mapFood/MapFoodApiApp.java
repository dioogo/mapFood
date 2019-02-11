package com.groupsix.mapFood;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MapFoodApiApp {

	public static void main (String[] args) {
		SpringApplication.run(MapFoodApiApp.class, args);
	}
	
	 @PostConstruct
     void started() {
       TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
     }

	
}
