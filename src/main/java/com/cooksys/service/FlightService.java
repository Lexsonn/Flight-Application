package com.cooksys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cooksys.component.FlightGenerator;
import com.cooksys.connector.Edge;
import com.cooksys.connector.FlightConnector;
import com.cooksys.connector.Vertex;
import com.cooksys.entity.Flight;
import com.cooksys.model.FlightResponse;
import com.cooksys.pojo.Cities;
import com.cooksys.repository.FlightRepository;

@Service
public class FlightService {
	private Logger log = LoggerFactory.getLogger(FlightService.class);
	@Autowired
	FlightGenerator generator;
	@Autowired
	FlightRepository repo;

	private ArrayList<Flight> flightList = new ArrayList<>();
	private List<Vertex> nodes = getNodes();		// List of cities
	private List<Edge> edges = new ArrayList<>();	// List of flights between cities
	private Long update = 1L;						// Current version of flights (for auto updating clients)
	
	public Flight getFlightById(Long id) {
		return repo.findOne(id);
	}
	
	public List<Flight> getFlightByOriginAndDestination(String origin, String destination) {
		return repo.findByOriginAndDestination(origin, destination);
	}
	
	public List<Flight> getAll() {
		return repo.findAll();
	}
	
	public Flight saveFlight(Flight flight) {
		return repo.save(flight);
	}
	
	public ArrayList<Flight> getDailyFlightList() {
		return flightList;
	}
	
	/**
	 * 	Find flight that gets the user to his destination, favoring single flights over layovers
	 * 
	 * 	@return list of flights needed to get the user from his source to his destination
	 * 	@param destination: the city to arrive at
	 * 	@param source: the city to depart from
	 */
	public List<FlightResponse> getFlightPath(String destination, String source) {
		Flight fastest = checkSingleFlights(destination, source);
		if (fastest != null) {
			List<FlightResponse> response = new ArrayList<>();
			response.add(FlightResponse.get(fastest));
			return response;
		}
		
		FlightConnector fc = new FlightConnector(edges);
		fc.execute(getNode(source));
		return FlightResponse.getList(fc, getNode(destination));
	}
	
	/**
	 * 	Find flight that gets the user to his destination as soon as possible
	 * 
	 * 	@return list of flights needed to get the user from his source to his destination
	 * 	@param destination: the city to arrive at
	 * 	@param source: the city to depart from
	 */
	public List<FlightResponse> getQuickestFlightPath(String destination, String source) {
		FlightConnector fc = new FlightConnector(edges);
		fc.execute(getNode(source));
		return FlightResponse.getList(fc, getNode(destination));
	}
	
	public Long getUpdate() {
		return update;
	}
	
	@Scheduled(fixedDelay=15000)
	private void refreshFlights() {
		flightList = generator.generateNewFlightList();
		generateFlightPaths();
		update++;
	}
	
	/*
	 * 	Create edges for the newly generated flight list
	 */
	private void generateFlightPaths() {
		edges.clear();
		int i = 0;
		for (Flight flight : flightList) 
			edges.add(new Edge("edge_" + i++, getNode(flight.getOrigin()), getNode(flight.getDestination()),
							   flight.getFlightTime(), flight.getOffset()));
		
		log.info("Flights: {}", edges);
	}
	
	private Vertex getNode(String name) {
		return nodes.get(Cities.valueOf(name.toUpperCase()).ordinal());
	}
	
	/*
	 * 	Initialize the array of available cities
	 */
	private List<Vertex> getNodes() {
		List<Vertex> result = new ArrayList<>();
		int i = 0;
		for (Cities city : Cities.values()) {
			result.add(new Vertex("city_" + i + "_" + city.getName(), city.getName()));
			log.info("City added... id={}", result.get(i++).getId());
		}
		log.info("Cities: {}", result);
		return result;
	}
	
	private Flight checkSingleFlights(String destination, String source) {
		List<Flight> flightsToFrom = new ArrayList<>();
		for (Flight flight : flightList) {
			if (flight.getDestination().equals(destination) && flight.getOrigin().equals(source)) {
				flightsToFrom.add(flight);
			}
		}
		
		long small = Long.MAX_VALUE;
		Flight fastest = null;
		for (Flight flight : flightsToFrom) {
			if (flight.getFlightTime() < small) {
				fastest = flight;
				small = fastest.getFlightTime();
			}
		}
		
		return fastest;
	}
}
