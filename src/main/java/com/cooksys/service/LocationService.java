package com.cooksys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooksys.entity.Location;
import com.cooksys.repository.LocationRepository;

@Service
public class LocationService {
	
	@Autowired
	LocationRepository repo;
		
	public List<Location> getAll() {
		return repo.findAll();
	}

	public Location get(long id) {
		return repo.findById(id);
	}
	
	public Location get(String name) {
		return repo.findByCity(name);
	}

	public List<Location> getByList(List<String> cityNames) {
		List<Location> list = new ArrayList<>();
		for (String name : cityNames) {
			list.add(get(name));
		}
		return list;
	}
}
