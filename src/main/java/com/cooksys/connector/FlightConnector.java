package com.cooksys.connector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FlightConnector {
	private List<Edge> edges;
	private Set<Vertex> settledNodes;
	private Set<Vertex> unSettledNodes;
	private Map<Vertex, Vertex> predecessors;
	private Map<Vertex, Long> distance;
	
	public FlightConnector(List<Edge> edges) {
		this.edges = edges;
	}
	
	/**
	 * 	Create the list of available paths from the selected vertex to all other vertices.
	 * 
	 * 	@param source: the starting vertex
	 */
	public void execute(Vertex source) {
		settledNodes = new HashSet<Vertex>();
		unSettledNodes = new HashSet<Vertex>();
		distance = new HashMap<Vertex, Long>();
		predecessors = new HashMap<Vertex, Vertex>();
		distance.put(source, 0L);
		unSettledNodes.add(source);
		while (unSettledNodes.size() > 0) {
			Vertex node = getMinimum(unSettledNodes);
			settledNodes.add(node);
			unSettledNodes.remove(node);
			findMinimalDistances(node);
		}
	}
	
	/**
	 * 	Find the smallest distance from the current node to the next node on a graph, if that
	 * 	next node has not been visited yet, or if the current path to that node has less weight.
	 * 
	 * 	@param node: the node to search for edges with
	 */
	private void findMinimalDistances(Vertex node) {
		List<Vertex> adjacentNodes = getNeighbors(node);
		for (Vertex target : adjacentNodes) {
			Long distanceValid = getDistance(node, target);
			if (distanceValid != null) {
				if (getShortestDistance(target) > distanceValid) {
					distance.put(target, distanceValid);
					predecessors.put(target, node);
					unSettledNodes.add(target);
				}
			}
		}
	}
	
	/**
	 * 	get the current distance from the starting vertex to the given vertex
	 * 
	 * 	@param destination: the vertex that is currently being checked
	 * 	@return the current distance of the destination vertex from the starting vertex
	 */
	private long getShortestDistance(Vertex destination) {
		Long d = distance.get(destination);
		if (d == null) {
			return Long.MAX_VALUE;
		} else {
			return d;
		}
	}
	
	/**
	 * 	Get the current distance between the starting node and target.
	 * 
	 * 	@param node: the starting vertex
	 * 	@param target: the target vertex
	 * 	@return the distance from node to the target. returns null if the current target's offset is greater than the current distance
	 */
	private Long getDistance(Vertex node, Vertex target) {
		Edge edge = findEdge(node, target);
		if (edge == null)
			return null;
		return edge.getWeight() + edge.getOffset();
	}
		
	/**
	 * 	Return a list of all possible neighbors to the current vertex
	 * 
	 * 	@param node: the current vertex
	 * 	@return the list of all neighbors to node
	 */
	private List<Vertex> getNeighbors(Vertex node) {
		List<Vertex> neighbors = new ArrayList<Vertex>();
		for (Edge edge : edges) {
			if (edge.getSource().equals(node) && !isSettled(edge.getDestination())) {
				neighbors.add(edge.getDestination());
			}
		}
		return neighbors;
	}
	
	/**
	 * 	return the shortest distance from a given set of vertices
	 * 
	 * 	@param vertexes: list of vertices to check
	 * 	@return the vertex with the shortest distance in the list
	 */
	private Vertex getMinimum(Set<Vertex> vertexes) {
		Vertex minimum = null;
		for (Vertex vertex : vertexes) {
			if (minimum == null) {
				minimum = vertex;
			} else {
				if (getShortestDistance(vertex) < getShortestDistance(minimum)) 
					minimum = vertex;
			}
		}
		return minimum;
	}
	
	private boolean isSettled(Vertex vertex) {
		return settledNodes.contains(vertex);
	}
	
	/**
	 * 	Returns the path from the executed vertex, to the target vertex. Returns null if no path exists.
	 * 
	 * @param target: target node to reach.
	 * @return list of vertices to traverse in order to reach the destination
	 */
	public LinkedList<Vertex> getPath(Vertex target) {
		LinkedList<Vertex> path = new LinkedList<Vertex>();
		Vertex step = target;
		
		if (predecessors.get(step) == null) 
			return null;
		
		path.add(step);
		while (predecessors.get(step) != null) {
			step = predecessors.get(step);
			path.add(step);
		}
		// Reverse path since we are accessing it from the target to the source.
		Collections.reverse(path);
		return path;
	}
	
	public Edge findEdge(Vertex node, Vertex target) {
		Edge smallestEdge = null;
		for (Edge edge : edges) {
			if (edge.getSource().equals(node) && edge.getDestination().equals(target)) {
				if (edge.getOffset() > getShortestDistance(node) || getShortestDistance(node) == 0) {
					if (smallestEdge == null) {
						smallestEdge = edge;
					} else if (edge.getOffset() < smallestEdge.getOffset()) {
						if (edge.getOffset() - edge.getWeight() < smallestEdge.getOffset() + smallestEdge.getWeight()) {
							smallestEdge = edge;
						}
					}
				}
			}
		}
		return smallestEdge;
	}
	
	public List<Edge> getEdges() {
		return edges;
	}
	
	public Map<Vertex, Long> getDistance() {
		return distance;
	}
}
