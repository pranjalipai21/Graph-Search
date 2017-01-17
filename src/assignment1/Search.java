package assignment1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import org.json.*;
import java.util.Scanner;
import java.util.Stack;




public class Search {
@SuppressWarnings("resource")
public static void main(String[] args){
		
	
		
		String content;
		JSONObject graph= null;
			
		
		try {
			content = new Scanner(new File("D:/assignment1/src/assignment1/input.json")).useDelimiter("//Z").next();
			graph = new JSONObject(content);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
    HashMap<String,Nodes> graphNodes = new HashMap<String,Nodes>();       
    HashMap<String,JSONObject> graphEdges = new HashMap<String,JSONObject>();
    
    for(Iterator<String> iterator = graph.keys(); iterator.hasNext();) {
	    String key = (String) iterator.next();
	    graphNodes.put(key, new Nodes(key));
	    	
	}
    try {
    for(Iterator<String> iterator = graph.keys(); iterator.hasNext();) {
	    String key = (String) iterator.next();
	    
			graphEdges.put(key, graph.getJSONObject(key));
		
	    Edge[] ed = new Edge[graph.getJSONObject(key).length()];
	    int i =0;
	    for(Iterator<String> iterator1 = graph.getJSONObject(key).keys(); iterator1.hasNext();){
	    	String var = iterator1.next();
	    	ed[i]= new Edge(graphNodes.get(var),graph.getJSONObject(key).getInt(var));
	    	i++;
	    } 
	    graphNodes.get(key).addCities= ed;
        
	    	
	}} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
			bfs(graphNodes.get("Arad"),graphNodes.get("Bucharest"));
			dfs(graphNodes.get("Arad"),graphNodes.get("Bucharest"));
			ucs(graphNodes.get("Arad"),graphNodes.get("Bucharest"));
			
			
}

public static Queue<Nodes> initializeFrontier(Nodes a){
	Queue<Nodes> queue = new LinkedList<Nodes>();
	queue.add(a);
	return queue;
}

public static boolean goalTest(Nodes a, Nodes b){
	double total;
	
	if(a.equals(b)){
	List<Nodes> path = printPath(b);
	total= b.Path_cost;
	System.out.println();
	System.out.println("Path: " + path);
    System.out.println();
    System.out.println("Cost: " + total);
    System.out.println();
    return false; 
    
	}
	return true;
}

public static ArrayList<Nodes> printPath(Nodes print){
	double total =0.0;
	ArrayList<Nodes> p = new ArrayList<Nodes>();
    total = print.Path_cost;
    Nodes node = print;
    while(node!=null){
    	p.add(node);
    	node= node.parent;
    }
    Collections.reverse(p);
    return p;

}

public static void bfs(Nodes s, Nodes e){
	//double t=0;
	
	s.Path_cost=0;
	ArrayList<Nodes> explored = new ArrayList<Nodes>();
	Queue<Nodes> frontier= initializeFrontier(s);
	goalTest(s,e);
	System.out.println("BFS:");
	do{
		Nodes current = frontier.poll();
		explored.add(current);
		if(!goalTest(current,e)){
			break;
		}
		if(current.equals(e)){
			break;
		}
				
		for(Edge edge : current.addCities){
			//childnode
			
			Nodes node = edge.vertex;
			double cost = edge.weight;
			
			node.Path_cost = current.Path_cost+ cost;
			 	//	t=current.Path_cost+ cost;
			
			if(!explored.contains(node) && !frontier.contains(node)){
				
				node.parent = current;
				frontier.add(node);
				//System.out.println(frontier);
				if(!goalTest(node,e)){
					frontier.clear();
					break;
				}
				System.out.println(frontier);
				
				
				
             }
			
		}
		
	}while(!frontier.isEmpty());
//	List<Nodes> path = printPath(e);
	//double total = e.Path_cost;
//	System.out.println();
//    System.out.println("Cost: " + total);
//    System.out.println();
}

public static void ucs(Nodes s, Nodes e){
	s.Path_cost=0;
	double total;
	PriorityQueue<Nodes> frontier = new PriorityQueue<Nodes>(20,
			new Comparator<Nodes>() {
				
				public int compare(Nodes a, Nodes b) {
					// TODO Auto-generated method stub
					if(a.Path_cost >b.Path_cost){
					return 1;
					}
					else if (a.Path_cost< b.Path_cost)
					{
						return -1;
						}
					else{
						return 0;
				}
				}
	}
	);
	frontier.add(s);
	
	ArrayList<Nodes> explored = new ArrayList<Nodes>();
	
	System.out.println();
	System.out.println("UCS:");
	
	do{
		Nodes current = frontier.poll();
		
		if(current == null){
			System.out.print("Failure");
			break;
		}
		explored.add(current);
			if(!goalTest(current,e)){
				break;
			}
			
		for(Edge edge : current.addCities){
			//childnode
			Nodes node = edge.vertex;
			double cost = edge.weight;
			node.Path_cost = current.Path_cost + cost;
			
			if(!explored.contains(node) && !frontier.contains(node)){
				
				frontier.add(node);
				node.parent = current;
				System.out.println(frontier);

			}
			else if (frontier.contains(node)&& (node.Path_cost>current.Path_cost)){
				
				node.parent = current;
				
			}
			
		}
		
	}while(!frontier.isEmpty());
//	double total1 =e.Path_cost;
//	System.out.println("Cost: " + total1);
//    System.out.println();
}

public static void dfs(Nodes s, Nodes e){
	
	ArrayList<Nodes> frontier = new ArrayList<Nodes>();
	ArrayList<Nodes> visited = new ArrayList<Nodes>();
	double total;
	s.Path_cost=0;
//	frontier.push(s);
	
	
	boolean f = false;
	//Nodes snode = null;
	//snode.State= s;
	if (s.equals(e)){
		f= true;
		
	}
	
	System.out.println("DFS:");
	dfsSearch(s,e,frontier,visited);
	System.out.println();
	System.out.print("Path:");
	frontier.add(s);
	Collections.reverse(frontier);
	System.out.println(frontier);
	
	
			
}

public static boolean dfsSearch(Nodes current, Nodes e, ArrayList<Nodes> frontier, ArrayList<Nodes> visited){
			
		visited.add(current);
		//goaltest
		if(current.equals(e)){
			return true;
		}
		
		for(Edge edge : current.addCities){
		Nodes node = edge.vertex;
		double cost = edge.weight;
		node.Path_cost = current.Path_cost + cost;
		if(!visited.contains(node)){
			if(dfsSearch(node,e,frontier,visited))
			{
				frontier.add(node);
				
				//node.Path_cost = current.Path_cost + cost;
				System.out.println(frontier);
				return true;
			}
			//node.Path_cost = current.Path_cost + cost;
			
		}
		
		}
		return false;
			
//		frontier.push(current);
//		
//		while(!frontier.empty()){
//			
//			
//			current = frontier.pop();
//			
//		
//					if(!visited.contains(current)){
//						visited.add(current);
//						for(Edge edge : current.addCities){
//							Nodes node = edge.vertex;
//							frontier.push(node);
//							if(node==e)
//								break;
//						}
//					}
//								
//		}
}
	
}




