package com.cooksys.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cooksys.connector.Edge;
import com.cooksys.connector.FlightConnector;
import com.cooksys.connector.Vertex;
import com.cooksys.entity.Flight;
import com.cooksys.entity.Itinerary;
import com.cooksys.entity.User;

public class FlightResponse {
	private static Logger log = LoggerFactory.getLogger(FlightResponse.class);
	
	private String departure;
	private String destination;
	private Long flightTime;
	private Long layover;
	private Long offset;
	
	public FlightResponse() {
		
	}

	public FlightResponse(String destination, String departure, Long flightTime, Long layover, Long offset) {
		super();
		this.destination = destination;
		this.departure = departure;
		this.flightTime = flightTime;
		this.layover = layover;
		this.offset = offset;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Long getFlightTime() {
		return flightTime;
	}

	public void setFlightTime(Long flightTime) {
		this.flightTime = flightTime;
	}

	public Long getLayover() {
		return layover;
	}

	public void setLayover(Long layover) {
		this.layover = layover;
	}

	public Long getOffset() {
		return offset;
	}
	
	public void setOffset(Long offset) {
		this.offset = offset;
	}
	
	@Override
	public String toString() {
		return "FlightResponse [departure=" + departure + ", destination=" + destination + ", flightTime=" + flightTime
				+ ", layover=" + layover + "]";
	}
	
	public static Flight getFlight(FlightResponse fr) {
		return new Flight(fr.getDeparture(), fr.getDestination(), fr.getFlightTime(), fr.getOffset());
	}
	
	public static Itinerary getItinerary(List<FlightResponse> list, User user, Double cost) {
		Itinerary itinerary = new Itinerary();
		List<Flight> flights = new ArrayList<>();
		for (FlightResponse fr : list) 
			flights.add(FlightResponse.getFlight(fr));
		
		itinerary.setFlights(flights);
		itinerary.setUser(user);
		itinerary.setCost(cost);
		return itinerary;
	}
	
	public static FlightResponse get(Flight flight) {
		if (flight == null)
			return null;
		return new FlightResponse(flight.getDestination(), flight.getOrigin(), flight.getFlightTime(), flight.getOffset(), flight.getOffset());
	}
	
	public static List<FlightResponse> getList(FlightConnector fc, Vertex target) {
		Map<Vertex, Long> distance = fc.getDistance();
		List<Vertex> path = fc.getPath(target);
		List<FlightResponse> result = new ArrayList<>();
		
		if (path == null)
			return null;

		Long accumulatedTime = 0L;
		Vertex stop = null;
		for (Vertex next : path) {
			if (stop != null) {
				Long totalTime = distance.get(next);
				Edge edge = fc.findEdge(stop, next);
				long flight = edge.getWeight();
				long layover = totalTime - flight - accumulatedTime;

				result.add(new FlightResponse(next.getName(), stop.getName(), flight, layover, edge.getOffset()));
				accumulatedTime = totalTime;
			}
			stop = next;
		}
		
		log.info("Flight Response:\n{}", result);
		return result;
	}
}
