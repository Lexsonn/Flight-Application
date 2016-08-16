package com.cooksys.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.entity.Flight;
import com.cooksys.model.FlightResponse;
import com.cooksys.service.FlightService;
import com.cooksys.service.LocationService;

@RestController
@RequestMapping("flights")
public class FlightsController {
	
	@Autowired
	LocationService locationService;
	
	@Autowired
	FlightService flightService;
	
	@RequestMapping("update")
	public Long getUpdate() {
		return flightService.getUpdate();
	}
	
	@RequestMapping("all")
	public ArrayList<Flight> getFlightList() {
		return flightService.getDailyFlightList();
	}
	
	@RequestMapping
	public List<FlightResponse> getFlightPath(@RequestParam("to") String destination, @RequestParam("from") String source) {
		return flightService.getFlightPath(destination, source);
	}
}
