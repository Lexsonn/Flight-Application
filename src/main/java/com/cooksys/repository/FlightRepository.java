package com.cooksys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.entity.Flight;

public interface FlightRepository extends JpaRepository<Flight, Long> {
	
	List<Flight> findByOriginAndDestination(String origin, String destination);
	Flight findByOriginAndDestinationAndOffsetAndFlightTime(String origin, String destination, Long offset, Long flightTime);
	
}
