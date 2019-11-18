import java.util.ArrayList;


/**
 * The class <b>LightsOut</b> is the
 * class that implements the method to
 * computs solutions of the Lights Out game.
 * It contains the main of our application.
 *
 */

public class LightsOut {

     /**
     * default width of the game.
     */
    public static final int DEFAULT_WIDTH = 3;
     /**
     * default height of the game.
     */
    public static final int DEFAULT_HEIGHT = 3;

    /**
     * The method <b>solve</b> finds all the 
     * solutions to the <b>Lights Out</b> game 
     * for an initially completely ``off'' board 
     * of size <b>widthxheight</b>, using a  
     * Breadth-First Search algorithm. 
     *
     * It returns an <b>ArrayList&lt;Solution&gt;</b> 
     * containing all the valid solutions to the 
     * problem.
     *
     * This version does not continue exploring a 
     * partial solution that is known to be
     * impossible. It will also attempt to complete
     * a solution as soon as possible
     *
     * During the computation of the solution, the 
     * method prints out a message each time a new 
     * solution  is found, along with the total time 
     * it took (in milliseconds) to find that solution.
     *
     * @param width
     *  the width of the board
     * @param height
     *  the height of the board
     * @return
     *  an instance of <b>ArrayList&lt;Solution&gt;</b>
     * containing all the solutions
     */
    public static ArrayList<Solution> solve(int width, int height){

        Queue<Solution> q  = new QueueImplementation<Solution>();
        ArrayList<Solution> solutions  = new ArrayList<Solution>();

        q.enqueue(new Solution(width,height));
        long start = System.currentTimeMillis();
        while(!q.isEmpty()){
            Solution s  = q.dequeue();
            if(s.isReady()){
                // by construction, it is successfull
                System.out.println("Solution found in " + (System.currentTimeMillis()-start) + " ms" );
                solutions.add(s);
            } else {
                boolean withTrue = s.stillPossible(true);
                boolean withFalse = s.stillPossible(false);
                if(withTrue && withFalse) {
                    Solution s2 = new Solution(s);
                    s.setNext(true);
                    q.enqueue(s);
                    s2.setNext(false);
                    q.enqueue(s2);
                } else if (withTrue) {
                    s.setNext(true);
                    if(s.finish()){
                        q.enqueue(s);
                    }                
                } else if (withFalse) {
                    s.setNext(false);
                    if( s.finish()){
                        q.enqueue(s); 
                    }               
                }
            }
        }
        return solutions;
    }
    
    
    // finds all the solutions to the LightsOut game for a board
    // in the state specified by the GameModel instance model
    // returns an instance of <b>ArrayList&lt;Solution&gt;</b> containing all the solutions
    public static ArrayList<Solution> solve(GameModel model){
    	Queue<Solution> q  = new QueueImplementation<Solution>();
        ArrayList<Solution> solutions  = new ArrayList<Solution>();

        q.enqueue(new Solution(model.getWidth(),model.getHeight()));
        long start = System.currentTimeMillis();
        while(!q.isEmpty()){
            Solution s  = q.dequeue();
            if(s.isReady()){
            	if(s.isSuccesful(model)){
                    System.out.println("Solution found in " + (System.currentTimeMillis()-start) + " ms" );
                    solutions.add(s);
                }
            } else {
                boolean withTrue = s.stillPossible(true,model);
                boolean withFalse = s.stillPossible(false,model);
                if(withTrue && withFalse) {
                    Solution s2 = new Solution(s);
                    s.setNext(true);
                    q.enqueue(s);
                    s2.setNext(false);
                    q.enqueue(s2);
                } else if (withTrue) {
                    s.setNext(true);
                    if(s.finish(model)){
                        q.enqueue(s);
                    }                
                } else if (withFalse) {
                    s.setNext(false);
                    if( s.finish(model)){
                        q.enqueue(s); 
                    }               
                }
            }
        }
        return solutions;
    }
    	
    // Returns the shortest Solution found in the array list 
    public static Solution solveShortest(GameModel model) {
		ArrayList<Solution> a = solve(model);
		
		// case where there is no solution to the board
		if(a.size()==0) {
			throw new IndexOutOfBoundsException();
		}
		
		int index = 0;
		for (int i = 0; i < a.size() - 1; i++) {
			if (a.get(i + 1).getSize() < a.get(index).getSize()) {
				index = i + 1;
			}
		}return a.get(index);
    }

    /**
     * <b>main</b> method  calls the method <b>solve</b> 
     * and then prints out the number of solutions found,
     * as well as the details of each solution.
     *
     * The <b>width</b> and <b>height</b> used by the 
     * main are passed as runtime parameters to
     * the program. If no runtime parameters are passed 
     * to the program, or if the parameters are incorrect,
     * then the default values are used.
     *
     * @param args
     *  Strings array of runtime parameters
     */
    public static void main(String[] args) {


        int width   = DEFAULT_WIDTH;
        int height  = DEFAULT_HEIGHT;

        StudentInfo.display();

        if (args.length == 2) {

            try{
                width = Integer.parseInt(args[0]);
                if(width < 1) {
                    System.out.println("Invalid width, using default...");
                    width   = DEFAULT_WIDTH;
                }
                height = Integer.parseInt(args[1]);
                if(height < 1) {
                    System.out.println("Invalid height, using default...");
                    height  = DEFAULT_HEIGHT;
                }
                
            } catch(NumberFormatException e){
                System.out.println("Invalid argument, using default...");
                width   = DEFAULT_WIDTH;
                height  = DEFAULT_HEIGHT;
            }
        }
        ArrayList<Solution> results   = solve(width,height);
        for(int i =0; i < results.size(); i++){

            System.out.println("****");
            System.out.println(results.get(i));

        }
        System.out.println("In a board of "+ width + "x" + height +": " + results.size() + " solution" + (results.size() > 1 ? "s." : "."));

    }
}
