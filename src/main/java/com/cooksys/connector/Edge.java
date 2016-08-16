package com.cooksys.connector;

public class Edge  {
	private final String id; 
	private final Vertex source;
	private final Vertex destination;
	private final long weight; 
	private final long offset;
	  
	public Edge(String id, Vertex source, Vertex destination, long weight, long offset) {
		this.id = id;
		this.source = source;
		this.destination = destination;
		this.weight = weight;
		this.offset = offset;
	}
	  
	public String getId() {
		return id;
	}
	public Vertex getDestination() {
		return destination;
	}
	
	public Vertex getSource() {
		return source;
	}
	public long getWeight() {
		return weight;
	}
	
	public long getOffset() {
		return offset;
	}
	  
	@Override
	public String toString() {
		return source + " -> " + destination;
	}
} 
