package assignment1;

public class Nodes {
	public Nodes parent;
	public Nodes Action;
	public double Path_cost = 0;
	public String input;
	public Edge[] addCities;
	
	
	public Nodes(String inp){
		input= inp;
		
	}
	
	public String toString(){
		return input;
	}
}
