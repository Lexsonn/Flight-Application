package com.cooksys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooksys.entity.Itinerary;
import com.cooksys.repository.FlightRepository;
import com.cooksys.repository.ItineraryRepository;

@Service
public class ItineraryService {
	//private Logger log = LoggerFactory.getLogger(ItineraryService.class);
	@Autowired
	ItineraryRepository repo;
	@Autowired
	FlightRepository flightRepo;
	
	public Itinerary getById(Long id) {
		return repo.findOne(id);
	}
	
	public List<Itinerary> getAll() {
		return repo.findAll();
	}
	
	public Itinerary saveItinerary(Itinerary itinerary) {
		flightRepo.save(itinerary.getFlights());
		Itinerary saved = repo.save(itinerary);
		
		saved.setUser(null);
		return saved;
	}

	public List<Itinerary> getUserItinerary(Long id) {
		return repo.findByUserId(id);
	}
}
