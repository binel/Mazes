import java.util.ArrayList;

public class Cell {
	public int row, column; 
	public Cell north, south, east, west; 
	private ArrayList<Cell> links; 
	
	public Cell(int row, int column) { 
		this.row = row;
		this.column = column; 
		
		this.links = new ArrayList<Cell>(); 
	}
	
	public void link(Cell c, boolean bidirectional) {
		links.add(c);
		if(bidirectional) {
			c.link(this, false);
		}
	}
	
	public void unlink(Cell c, boolean bidirectional) {
		links.remove(c); 
		if(bidirectional) {
			c.unlink(this, false);
		}
	}
	
	public ArrayList<Cell> getLinks() {
		return links; 
	}
	
	public boolean isLinked(Cell c) {
		if(links.contains(c)) {
			return true; 
		} 
		return false; 
	}
	
	public ArrayList<Cell> neighbors() {
		ArrayList<Cell> list = new ArrayList<Cell>();
		
		if(north != null) {
			list.add(north);
		}
		if(south != null) {
			list.add(south);
		}
		if(east != null) {
			list.add(east);
		}
		if(west != null) {
			list.add(west);
		}
		
		return list; 
	}
	
	public Distances getDistances() {
		Distances distances = new Distances(this);
		ArrayList<Cell> frontier = new ArrayList<Cell>(); 
		frontier.add(this);
		
		while(frontier.size() != 0) {
			ArrayList<Cell> newFrontier = new ArrayList<Cell>(); 
			
			for(Cell c : frontier) {
				for(Cell link : c.links) {
					if(distances.getDistance(link) == -1) {
						newFrontier.add(link);
						distances.setDistance(link, distances.getDistance(c) + 1);
					}
				}
			}
			
			frontier = newFrontier; 
		}
		
		return distances; 
	}
	
	
}
