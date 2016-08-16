package com.cooksys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.entity.User;
import com.cooksys.model.BookItinerary;
import com.cooksys.model.FlightResponse;
import com.cooksys.model.UserResponse;
import com.cooksys.service.ItineraryService;
import com.cooksys.service.UserService;

@RestController
@RequestMapping("users")
public class UserController {
	
	@Autowired
	UserService userService;
	@Autowired
	ItineraryService itineraryService;
	
	@RequestMapping(method=RequestMethod.POST)
	public UserResponse createUser(@RequestBody User user) {
		return UserResponse.get(userService.createUser(user));
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public List<UserResponse> getAll() {
		return UserResponse.getList(userService.getAllUsers());
	}
	
	@RequestMapping(value="login", method=RequestMethod.POST)
	public UserResponse loginUser(@RequestBody User user) {
		return UserResponse.get(userService.login(user));
	}
	/*
	@RequestMapping("itinerary/{name}")
	public UserResponse getUserItinerary(@PathVariable String name) {
		User user = userService.getUserByUsername(name);
		if (user == null)
			return null;
		return UserResponse.get(user, itineraryService.getUserItinerary(user.getId()));
	}
	
	@RequestMapping(value="itinerary", method=RequestMethod.POST)
	public UserResponse createUserItinerary(@RequestBody BookItinerary bi) {
		User user = userService.login(bi.getUser());
		if (user == null)
			return null;
		return UserResponse.get(user, itineraryService.saveItinerary(FlightResponse.getItinerary(bi.getFlights(), user, null)));
	}
	*/
}
