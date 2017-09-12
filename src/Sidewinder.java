import java.util.ArrayList;

public class Sidewinder {

	public static void on(Grid g) {
		for(int row = 0; row < g.rows; row++) {
			ArrayList<Cell> run = new ArrayList<Cell>(); 
			
			for(int col = 0; col < g.columns; col++) {
				Cell c = g.grid[row][col];
				run.add(c);
				Boolean atEastern = false; 
				Boolean atNorthern = false; 
				
				if(c.east == null) {
					atEastern = true; 
				}
				if(c.north == null) {
					atNorthern = true; 
				}
				
				if(atEastern || (!atNorthern && Math.random() < 0.5)) {
					int index = (int) (Math.random() * run.size());
					Cell m = run.get(index);
					if(m.north != null) {
						m.link(m.north, true);
						run.clear(); 
					}
				} else {
					c.link(c.east, true);
				}
			}
		}
	}
	
	public static Grid generateMaze(int x, int y) {
		Grid g = new Grid(100, 100);
		Sidewinder.on(g);
		return g; 
	}
	
	public static void main(String args[]) {
		Grid grid = new Grid(150, 150);
		Sidewinder.on(grid);
		grid.displayGrid(true);
	}
}
