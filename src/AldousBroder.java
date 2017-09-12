import java.util.ArrayList;

public class AldousBroder {
	public static void on(Grid g) {
		Cell c = g.randomCell(); 
		int numUnvisited = g.size() - 1; 
		
		while (numUnvisited > 0) {
			ArrayList<Cell> neighbors = c.neighbors(); 
			
			int index = (int) (Math.random() * neighbors.size()); 
			Cell neighbor = neighbors.get(index);
			
			if(neighbor.getLinks().size() == 0) {
				c.link(neighbor, true);
				numUnvisited--; 
			}
			
			c = neighbor; 
		}
	}
	
	public static Grid generateMaze(int x, int y) {
		Grid g = new Grid(100, 100);
		AldousBroder.on(g);
		return g; 
	}
	
	public static void main(String args[]) {
		Grid g = new Grid(100, 100);
		AldousBroder.on(g);
		System.out.println(g.findDeadEnds().size() + " dead ends");
		g.removeDeadEnds(0.25);
		g.displayGrid(true);
		System.out.println(g.findDeadEnds().size() + " dead ends");
	}
}
