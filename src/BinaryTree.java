public class BinaryTree {
	public static void on(Grid g) {
		
		for(int i = 0; i < g.rows; i++) {
			for(int j = 0; j < g.columns; j++) {
				Cell c = g.grid[i][j];
				
				if(c.north != null && c.east != null) {
					if(Math.random()  < 0.5) {
						c.link(c.north, true);
					} else {
						c.link(c.east, true);
					}
				}
				
				else if (c.north == null && c.east == null) {
					continue; 
				}
				
				else if(c.north == null) {
					c.link(c.east, true);
				} 
				
				else {
					c.link(c.north, true);
				}
			}
		}
	}
	
	public static Grid generateMaze(int x, int y) {
		Grid g = new Grid(100, 100);
		BinaryTree.on(g);
		return g; 
	}
	
	public static void main(String args[]) {
		Grid grid = new Grid(100, 100);
		BinaryTree.on(grid);
		System.out.println(grid.findDeadEnds().size() + " dead ends ");
		grid.removeDeadEnds(1);
		System.out.println(grid.findDeadEnds().size() + " dead ends ");
		grid.displayGrid(false);
		grid.displayGrid(false);
	}
}
