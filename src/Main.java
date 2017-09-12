	
public class Main {
	public static void main(String args[]) {
		
		double reduction = 0.1; 
		
		for(reduction = 0.1; reduction <= 1; reduction += 0.1) {
			double BTStart = 0; 
			double BTEnd = 0; 
			
			double SStart = 0; 
			double SEnd = 0; 
			
			double ABStart = 0; 
			double ABEnd = 0; 
			
			for(int i = 0; i < 100; i++) {
				Grid BT = BinaryTree.generateMaze(100, 100);
				BTStart += BT.findDeadEnds().size();
				BT.removeDeadEnds(reduction);
				BTEnd += BT.findDeadEnds().size();
				
				Grid S = Sidewinder.generateMaze(100, 100);
				SStart += S.findDeadEnds().size();
				S.removeDeadEnds(reduction);
				SEnd += S.findDeadEnds().size(); 
				
				Grid AB = AldousBroder.generateMaze(100, 100);
				ABStart += AB.findDeadEnds().size();
				AB.removeDeadEnds(reduction);
				ABEnd += AB.findDeadEnds().size(); 
			}
			
			System.out.println("Binary Tree: \n\tGoal Reduction: " + reduction + "\n\tActual Reduction: " + (BTStart / BTEnd));
			System.out.println("Sidewinder: \n\tGoal Reduction: " + reduction + "\n\tActual Reduction: " + (SStart / SEnd));
			System.out.println("AldousBroder: \n\tGoal Reduction: " + reduction + "\n\tActual Reduction: " + (ABStart / ABEnd));	
		}
	}
}
