package com.groupsix.mapFood.util;

public class DistanceUtil {

	public static Double distance(double lat1, double lon1, double lat2, double lon2) {
		Double dist = 0.0;
		if ((lat1 == lat2) && (lon1 == lon2)) {
			return dist;
		}
		else {
			double theta = lon1 - lon2;
			dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
			dist = Math.acos(dist);
			dist = Math.toDegrees(dist);
			dist = dist * 60 * 1.1515 * 1.609344;
			return (dist);
		}
	}
}
