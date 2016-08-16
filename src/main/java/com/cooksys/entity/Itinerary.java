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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="itinerary")
public class Itinerary {
	@Id
	@GeneratedValue
	private Long id;
	@Column(name="cost")
	private Double cost;
	@ManyToOne
	@JoinColumn(name="user_id")
	@JsonIgnore
    User user;
	@ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(name="itinerary_flights",
			   joinColumns=@JoinColumn(name="itinerary_id", referencedColumnName="id"),
			   inverseJoinColumns=@JoinColumn(name="flights_id", referencedColumnName="id"))
	List<Flight> flights;
	
	public Itinerary() {
		super();
	}

	public Itinerary(Long id, Double cost, User user, List<Flight> flights) {
		super();
		this.id = id;
		this.cost = cost;
		this.user = user;
		this.flights = flights;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Flight> getFlights() {
		return flights;
	}

	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}
}
