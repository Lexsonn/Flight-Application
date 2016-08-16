package com.cooksys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.entity.Itinerary;
import com.cooksys.entity.User;
import com.cooksys.model.BookItinerary;
import com.cooksys.model.FlightResponse;
import com.cooksys.model.UserResponse;
import com.cooksys.service.ItineraryService;
import com.cooksys.service.UserService;

@RestController
@RequestMapping("itineraries")
public class ItineraryController {
	
	@Autowired
	UserService userService;
	@Autowired
	ItineraryService itineraryService;
	
	@RequestMapping(method=RequestMethod.POST)
	public UserResponse createUserItinerary(@RequestBody BookItinerary bi) {
		User user = userService.login(bi.getUser());
		if (user == null)
			return null;
		return UserResponse.get(user, itineraryService.saveItinerary(FlightResponse.getItinerary(bi.getFlights(), user, null)));
	}
	
	@RequestMapping("/{id}")
	public Itinerary getById(@PathVariable("id") Long id) {
		return itineraryService.getById(id);
	}
	
	@RequestMapping("name/{name}")
	public UserResponse getUserItinerary(@PathVariable String name) {
		User user = userService.getUserByUsername(name);
		if (user == null)
			return null;
		return UserResponse.get(user, itineraryService.getUserItinerary(user.getId()));
	}
}
