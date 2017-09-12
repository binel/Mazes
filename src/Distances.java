import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class Distances {
	private Cell root; 
	private HashMap<Cell, Integer> distances; 
	
	public Distances(Cell root) {
		this.root = root; 
		this.distances = new HashMap<Cell, Integer>(); 
		this.distances.put(root, 0);
	}
	
	public int getDistance(Cell c) {
		if(distances.containsKey(c)) {
			return distances.get(c);
		} else {
			return -1; 
		}
	}
	
	public void setDistance(Cell c, int distance) {
		distances.put(c, distance);
	}
	
	public Set<Cell> getCells() {
		return distances.keySet(); 
	}
	
	public Cell maxDistance() {
		int max = -1; 
		Cell maxCell = null; 
		for(Entry<Cell, Integer> e : distances.entrySet()) {
			if (e.getValue() > max) {
				max = e.getValue(); 
				maxCell = e.getKey(); 
			}
		}
		
		return maxCell;
	}
	
	public ArrayList<Cell> pathTo(Cell goal) {
		Cell current = goal; 
		ArrayList<Cell> breadcrumbs = new ArrayList<Cell>(); 
		breadcrumbs.add(current);
		while(current != this.root) {
			for(Cell link : current.getLinks()) {
				if(this.distances.get(link) < distances.get(current)) {
					breadcrumbs.add(link);
					current = link; 
				}
			}
		}
		
		return breadcrumbs; 
	}
}
