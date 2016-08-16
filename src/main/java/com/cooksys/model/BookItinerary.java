package com.cooksys.model;

import java.util.List;

import com.cooksys.entity.User;

public class BookItinerary {
	private User user;
	private List<FlightResponse> flights;
	
	public BookItinerary() {
		super();
	}

	public BookItinerary(User user, List<FlightResponse> flights) {
		super();
		this.user = user;
		this.flights = flights;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<FlightResponse> getFlights() {
		return flights;
	}

	public void setFlights(List<FlightResponse> flights) {
		this.flights = flights;
	}
}
