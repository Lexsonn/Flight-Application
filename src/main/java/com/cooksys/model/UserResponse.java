package com.cooksys.model;

import java.util.ArrayList;
import java.util.List;

import com.cooksys.entity.Itinerary;
import com.cooksys.entity.User;

public class UserResponse {
	private Long id;
	private String username;
	private List<Itinerary> itinerary;
	
	public UserResponse() {
		super();
	}

	public UserResponse(Long id, String username) {
		super();
		this.id = id;
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Itinerary> getItinerary() {
		return itinerary;
	}

	public void setItinerary(List<Itinerary> itinerary) {
		this.itinerary = itinerary;
	}
	
	public static UserResponse get(User user) {
		if (user == null)
			return null;
		return new UserResponse(user.getId(), user.getUsername());
	}
	
	public static UserResponse get(User user, Itinerary itinerary) {
		UserResponse response = new UserResponse(user.getId(), user.getUsername());
		List<Itinerary> list = new ArrayList<>();
		list.add(itinerary);
		response.setItinerary(list);
		return response;
	}
	
	public static UserResponse get(User user, List<Itinerary> itinerary) {
		UserResponse response = new UserResponse(user.getId(), user.getUsername());
		response.setItinerary(itinerary);
		return response;
	}
	
	public static List<UserResponse> getList(List<User> users) {
		List<UserResponse> response = new ArrayList<>();
		for (User user : users)
			response.add(UserResponse.get(user));
		return response;
	}
}
