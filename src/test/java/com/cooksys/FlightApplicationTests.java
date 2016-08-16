package com.cooksys;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.cooksys.connector.Edge;
import com.cooksys.connector.FlightConnector;
import com.cooksys.connector.Vertex;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FlightApplication.class)
@WebAppConfiguration
public class FlightApplicationTests {
	
	private static List<Vertex> nodes;
	private static List<Edge> edges;
	
	@Test
	public void flightConnectorTest() {
		nodes = new ArrayList<Vertex>();
	    edges = new ArrayList<Edge>();
	    
	    for (int i = 0; i < 5; i++) {
	    	Vertex location = new Vertex("Node_" + i, "Node_" + i);
	    	nodes.add(location);
	    }
	    
	    FlightConnector fc;
	    LinkedList<Vertex> path;

	    addEdge("Edge_0", 0, 1, 2, 4);
	    addEdge("Edge_1", 0, 2, 3, 1);
	    addEdge("Edge_2", 0, 4, 1, 2);
	    addEdge("Edge_3", 1, 0, 1, 3);
	    addEdge("Edge_4", 1, 2, 3, 2);
	    addEdge("Edge_5", 1, 4, 1, 0);
	    addEdge("Edge_6", 2, 1, 2, 8);
	    addEdge("Edge_8", 2, 3, 3, 5);
	    addEdge("Edge_9", 3, 1, 1, 3);
	    addEdge("Edge_10", 3, 4, 3, 6);
	    addEdge("Edge_11", 4, 2, 5, 8);
	    
	    fc = new FlightConnector(edges);
	    fc.execute(nodes.get(0));
	    path = fc.getPath(nodes.get(3));
	    
	    assertTrue(path.size() > 0);
	    
	    fc = new FlightConnector(edges);
	    fc.execute(nodes.get(1));
	    path = fc.getPath(nodes.get(3));
	    
	    assertNull(path);
	    
	    edges.clear();
	    
	    addEdge("Edge_0", 0, 1, 2, 0);
	    addEdge("Edge_1", 1, 2, 3, 4);
	    addEdge("Edge_2", 2, 3, 2, 8);
	     
	    fc = new FlightConnector(edges);
	    fc.execute(nodes.get(0));
	    path = fc.getPath(nodes.get(3));
	    
	    assertTrue(path.size() > 0);
	    
	    edges.clear();
	    
	    addEdge("Edge_0", 0, 1, 3, 7);
	    addEdge("Edge_1", 2, 0, 1, 7);
	    addEdge("Edge_2", 1, 0, 3, 9);
	    addEdge("Edge_3", 0, 1, 3, 0);
	    addEdge("Edge_4", 1, 2, 2, 8);
	    
	    fc = new FlightConnector(edges);
	    fc.execute(nodes.get(0));
	    path = fc.getPath(nodes.get(2));

	    System.out.println(edges);
	    System.out.println(path);

	    assertTrue(path.size() > 0);
	}
	
	private static void addEdge(String laneId, int sourceLocNo, int destLocNo, int duration, int offset) {
		Edge lane = new Edge(laneId,nodes.get(sourceLocNo), nodes.get(destLocNo), duration, offset);
		edges.add(lane);
	}
}
