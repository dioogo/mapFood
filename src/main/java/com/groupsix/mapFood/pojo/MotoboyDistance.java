package com.groupsix.mapFood.pojo;

public class MotoboyDistance {

	private Integer motoboyId;
	private Double distance;
	private Double lat;
	private Double lon;
	
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLon() {
		return lon;
	}
	public void setLon(Double lon) {
		this.lon = lon;
	}
	public Integer getMotoboyId() {
		return motoboyId;
	}
	public void setMotoboyId(Integer motoboyId) {
		this.motoboyId = motoboyId;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public MotoboyDistance(Integer motoboyId, Double distance, Double lat, Double lon) {
		super();
		this.motoboyId = motoboyId;
		this.distance = distance;
		this.lat = lat;
		this.lon = lon;
	}
	
	
	
}
