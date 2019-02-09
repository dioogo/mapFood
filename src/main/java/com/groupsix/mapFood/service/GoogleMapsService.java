package com.groupsix.mapFood.service;

import org.springframework.stereotype.Service;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;

@Service
public class GoogleMapsService {

	private static final String KEY = "AIzaSyCnoKRTY6R02H4gJd13SxKMAhack7SrAfM";
	
	private GeoApiContext getContext() {
		return new GeoApiContext.Builder().apiKey(KEY).build();
	}
	
	private DirectionsResult getDirections(LatLng start, LatLng end) {
		return DirectionsApi.newRequest(getContext())
				.mode(TravelMode.DRIVING)
				.origin(start)
				.destination(end)
				.optimizeWaypoints(true)
				.awaitIgnoreError();
	}
	
	/**
	 * Return the time in seconds driving from point A to B
	 * @param start
	 * @param end
	 * @return Long seconds
	 */
	public Long timeToReach(LatLng start, LatLng end) {
		DirectionsResult result = getDirections(start, end);
		
		Long distance = 0L;
		for (DirectionsLeg leg : result.routes[0].legs) {
			System.out.println(leg.distance.humanReadable);
			System.out.println(leg.duration.humanReadable);
			System.out.println("Resultado      " + leg.duration.inSeconds);
			distance = leg.duration.inSeconds;
		}
		return distance;
	}
	
}
