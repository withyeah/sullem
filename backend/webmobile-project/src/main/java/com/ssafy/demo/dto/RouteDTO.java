package com.ssafy.demo.dto;

public class RouteDTO {
	private String origin;
	private String destination;
	private String travelMode;
	private String start_time;
	private String end_time;
	private String comment;
	public RouteDTO(String origin, String destination, String travelMode, String start_time, String end_time,
			String comment) {
		this.origin = origin;
		this.destination = destination;
		this.travelMode = travelMode;
		this.start_time = start_time;
		this.end_time = end_time;
		this.comment = comment;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getTravelMode() {
		return travelMode;
	}
	public void setTravelMode(String travelMode) {
		this.travelMode = travelMode;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@Override
	public String toString() {
		return "DayDTO [origin=" + origin + ", destination=" + destination + ", travelMode=" + travelMode
				+ ", start_time=" + start_time + ", end_time=" + end_time + ", comment=" + comment + "]";
	}
	
	
}
