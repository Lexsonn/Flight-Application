package com.cooksys.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="flight")
public class Flight {
	@Id
	@GeneratedValue
	private Long id;
	@Column(name="origin")
	private String origin;
	@Column(name="destination")
	private String destination;
	@Column(name="flightTime")
	private long flightTime;
	@Column(name="offset")
	private long offset;
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(name="itinerary_flights",
	   joinColumns=@JoinColumn(name="flights_id", referencedColumnName="id"),
	   inverseJoinColumns=@JoinColumn(name="itinerary_id", referencedColumnName="id"))
    @JsonIgnore
    List<Itinerary> itinerary;
	
	public Long getId(){
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public long getFlightTime() {
		return flightTime;
	}
	public void setFlightTime(long flightTime) {
		this.flightTime = flightTime;
	}
	public long getOffset() {
		return offset;
	}
	public void setOffset(long offset) {
		this.offset = offset;
	}
	public List<Itinerary> getItinerary() {
		return itinerary;
	}
	public void setItinerary(List<Itinerary> itinerary) {
		this.itinerary = itinerary;
	}
	public Flight(String origin, String destination, long flightTime, long offset) {
		super();
		this.id = null;
		this.origin = origin;
		this.destination = destination;
		this.flightTime = flightTime;
		this.offset = offset;
		this.itinerary = null;
	}
	
	public Flight() {
		
	}

}
