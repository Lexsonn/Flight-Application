package com.cooksys.component;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Component;

import com.cooksys.entity.Flight;
import com.cooksys.pojo.Cities;

@Component
public class FlightGenerator {

	public ArrayList<Flight> generateNewFlightList() {
		
		ArrayList<Flight> result = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			//*
			int originIndex = ThreadLocalRandom.current().nextInt(0, 4);

			int destinationIndex = ThreadLocalRandom.current().nextInt(0, 4);

			while (destinationIndex == originIndex)
				destinationIndex = ThreadLocalRandom.current().nextInt(0, 4);

			String origin = Cities.values()[originIndex].getName();
			String destination = Cities.values()[destinationIndex].getName();
			int flightTime = ThreadLocalRandom.current().nextInt(1, 4);
			int offset = ThreadLocalRandom.current().nextInt(0, 10);

			Flight f = new Flight(origin, destination, flightTime, offset);

			result.add(f); 
			//*/
		}
		/* Yeah thats right I tested multiple flights (and boy was it hard to make google maps draw in a straight line)
		Flight f = new Flight("Orlando", "Miami", 2, 0);
		result.add(f);
		f = new Flight("Miami", "Jacksonville", 3, 4);
		result.add(f);
		f = new Flight("Jacksonville", "Tallahassee", 2, 8);
		result.add(f);
		//*/
		return result;
	}

}
