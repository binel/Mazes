import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Set;

public class Grid {
	public int rows, columns; 
	public Cell[][] grid; 
	
	public Grid(int rows, int columns) {
		this.rows = rows; 
		this.columns = columns; 
		this.grid = new Cell[rows][columns];
		prepareGrid(grid);
		configureCells(grid); 
	}
	
	public void prepareGrid(Cell[][] grid) {
		for(int i = 0; i < this.rows; i++) {
			for(int j = 0; j < this.columns; j++) {
				grid[i][j] = new Cell(i, j); 
			}
		}
	}
	
	public void configureCells(Cell[][] grid) {
		for(int i = 0; i < this.rows; i++) {
			for(int j = 0; j < this.columns; j++) {
				if(!(i - 1 < 0)) {
					grid[i][j].north = grid[i - 1][j]; 
				}
				if(!(i + 1 >= this.rows)) {
					grid[i][j].south = grid[i + 1][j]; 
				}
				if(!(j - 1 < 0)) {
					grid[i][j].west = grid[i][j - 1]; 
				}
				if(!(j + 1 >= this.columns)) {
					grid[i][j].east = grid[i][j + 1]; 
				}
			}
		}
	}
	
	public Cell randomCell() { 
		int row = (int) Math.random() * this.rows; 
		int column = (int) Math.random() * this.columns; 
		
		return grid[row][column]; 
	}
	
	public int size() {
		return this.rows * this.columns; 
	}
	
	@Override
	public String toString() {
		String ret = ""; 

		
		ret += "+"; 
		for(int i = 0; i < this.columns; i++) {
			ret += "---+"; 
		}
		ret += "\n"; 
		for(int i = 0; i < this.rows; i++) {
			for(int j = 0; j < this.columns; j++) {
				Cell c = this.grid[i][j]; 
				
				if(c.isLinked(c.west)) {
					ret += " ";
				} else {
					ret += "|"; 
				}
				
				ret += "   "; 
			}
			
			ret += "|\n+"; 
			
			for(int j = 0; j < this.columns; j++) {
				Cell c = this.grid[i][j];
				if(c.isLinked(c.south)) {
					ret += "   ";
				} else {
					ret += "---";
				}
				ret += "+";
			}
			
			ret += "\n"; 
		}
		
		
		return ret; 
	}
	
	public ArrayList<Cell> getLongestPath() {
		ArrayList<Cell> path = new ArrayList<Cell>(); 
		
		Distances first = grid[0][0].getDistances();
		Distances last = first.maxDistance().getDistances(); 
		
		path = last.pathTo(last.maxDistance()); 
		
		return path; 
	}
	
	public ArrayList<Cell> findDeadEnds() {
		ArrayList<Cell> deadends = new ArrayList<Cell>(); 
		
		for(int i = 0; i < this.rows; i++) {
			for(int j = 0; j < this.columns; j++) {
				Cell c = this.grid[i][j]; 
				
				if(c.getLinks().size() == 1) {
					deadends.add(c);
				}
			}
		}
		
		return deadends; 
	}
	
	public void removeDeadEnds(double fraction) {
		ArrayList<Cell> deadends = this.findDeadEnds(); 
		
		int numToRemove = (int) (deadends.size() * fraction);
		
		while(numToRemove > 0) {
			int index = (int) (Math.random() * deadends.size());
			
			Cell c = deadends.get(index);
			ArrayList<Cell> neighbors = c.neighbors();
			neighbors.remove(c.getLinks().get(0));
			if(neighbors.size() > 0) {
				int nIndex = (int) (Math.random() * neighbors.size());
				c.link(neighbors.get(nIndex), true);
				deadends.remove(c);
				numToRemove--; 
			}
		}
	}
	
	public void displayGrid(boolean colorize) {
		new Display(this, colorize);
	}
	
	public class Display extends Frame {
		private Grid grid; 
		private int cellSize = 10;
		private int bS = 50; 
		private boolean colorize = false; 
		private boolean showLongestPath = true; 
		
		public Display(Grid grid, boolean colorize) {
			super("Grid Display"); 
			
			this.grid = grid; 
			this.colorize = colorize; 
			setSize((bS * 2) + (cellSize * grid.columns), (bS * 2) + (cellSize * grid.rows));
			
			setVisible(true);
			
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					dispose(); 
					System.exit(0);
				}
			});
		}
		
		public void paint(Graphics g) {
			Graphics2D g2 = (Graphics2D) g; 
			
			if(colorize) {
				Distances d = grid.grid[0][0].getDistances();
				for(int i = 0; i < grid.rows; i++) {
					for(int j = 0; j < grid.columns; j++) {
						Cell c = grid.grid[i][j];
						g.setColor(new Color(0, 0, Math.max(255 -  (d.getDistance(c) * 2 % 255), 0)));
						g.fillRect(bS + (cellSize * j), bS + (cellSize * i), cellSize, cellSize);
					}
				}
			}
			
			if(showLongestPath) {
				ArrayList<Cell> path = grid.getLongestPath(); 
				g.setColor(Color.RED);
				for(Cell c : path) {
					g.fillRect(bS + (cellSize * c.column), bS + (cellSize * c.row), cellSize, cellSize);
				}
			}
			
			g2.setStroke(new BasicStroke(3));
			g2.setColor(Color.BLACK);
			
			for(int i = 0; i < grid.rows; i++) {
				for(int j = 0; j < grid.columns; j++) {
					Cell c = grid.grid[i][j];
					
					if(!c.isLinked(c.north)) {
						g2.drawLine(bS + (cellSize * j), bS + (cellSize * i), bS + (cellSize * (j + 1)), bS + (cellSize * i));
					}
					if(!c.isLinked(c.south)) {
						g2.drawLine(bS + (cellSize * j), bS + (cellSize * (i + 1)), bS + (cellSize * (j + 1)), bS + (cellSize * (i + 1)));
					}
					if(!c.isLinked(c.west)) {
						g2.drawLine(bS + (cellSize * j), bS + (cellSize * i), bS + (cellSize * j), bS + (cellSize * (i + 1)));
					}
					if(!c.isLinked(c.east)) {
						g2.drawLine(bS + (cellSize * (j + 1)),bS + (cellSize * i), bS + (cellSize * (j + 1)), bS + (cellSize * (i + 1)));
					}
					
				}
			}
		}
	}
	
	//maybe look up how to write iterators 
}
