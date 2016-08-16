package com.cooksys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.entity.Location;
import com.cooksys.service.LocationService;

@RestController
@RequestMapping("locations")
public class LocationController {
	
	@Autowired
	private LocationService locationService;

	@RequestMapping
	public List<Location> get() {
		return locationService.getAll();
	}

	@RequestMapping("/{id}")
	public Location get(@PathVariable("id") long id) {
		return locationService.get(id);
	}
	
	@RequestMapping("/name")
	public Location get(@RequestParam("name") String cityName) {
		return locationService.get(cityName);
	}
	
	@RequestMapping("/names")
	public List<Location> get(@RequestParam("names") List<String> cityNames) {
		return locationService.getByList(cityNames);
	}
}
