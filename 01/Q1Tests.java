import java.util.ArrayList;

public class Q1Tests {


    private static void runSolverTest(GameModel model, int numberOfSolutions){
        ArrayList<Solution>  results  = LightsOut.solve(model);
        if(results.size()==numberOfSolutions) {
            System.out.println("\n Success!");
        } else {
            System.out.println("\n Failure! Expecting " 
                + numberOfSolutions + " and got " 
                + results.size());            
        }
        System.out.println("***********");
        System.out.println("Starting from :");
        System.out.println(model);
        System.out.println("Solution(s) :");
        for(int i =0; i < results.size(); i++){
            System.out.println(results.get(i));            
        }
    }

    private static void testSolver(){

        System.out.println("\ntestSolver");

        GameModel model = new GameModel(2,2);
        runSolverTest(model,1);

        model.set(0,1,true);
        runSolverTest(model,1);

        model.set(0,0,true);
        model.set(1,0,true);
        model.set(1,1,true);
        runSolverTest(model,1);

        model = new GameModel(2,3);
        runSolverTest(model,4);

        model.set(0,0,true);
        runSolverTest(model,0);
       
        model = new GameModel(3,3);
        runSolverTest(model,1);

        model.set(0,0,true);
        model.set(1,1,true);
        model.set(2,2,true);
        runSolverTest(model,1);
   }


    public static void runShortestSolverTest(GameModel model, int size){

        System.out.println("For model :");
        System.out.println(model);
        Solution s = LightsOut.solveShortest(model);
        if(s == null) {
            if(size == 0) {
                System.out.println("Success. No solution found.");
            } else {
                System.out.println("Failure. No solution found. Expected size " + size);
            }
            return;
        }
        else if (s.getSize() != size) {
            System.out.println("Failure. Size found: " + s.getSize() 
                + ", expected size: " + size);

        } else {
            System.out.println("Success. Size found: " + size);

        }
        System.out.println("shortest solution found:");
        System.out.println(s);

    }

    private static void testShortest(){
        System.out.println("\ntestShortest");
        GameModel model = new GameModel(2,2);
        runShortestSolverTest(model,4);
        
        model = new GameModel(3,2);
        runShortestSolverTest(model,2);

        model.set(2,0,true);
        model.set(0,1,true);
        model.set(1,1,true);
        runShortestSolverTest(model,2);

        model.reset();
        model.set(0,0,true);
        model.set(1,0,true);
        model.set(0,1,true);
        runShortestSolverTest(model,1);

        model = new GameModel(4,4);
        runShortestSolverTest(model,4);
    }

    private static void testModel(){
    
        System.out.println("\ntestModel");
        GameModel g = new GameModel(6,3);
        if(g.getWidth() != 6){
            System.out.println("Failure. getWidth should be 6, it is: " 
                + g.getWidth());
            return;
        }
        if(g.getHeight() != 3){
            System.out.println("Failure. getHeight should be 3, it is: " 
                + g.getHeight());
            return;
        }
  
        // test default values
        for(int i = 0; i < g.getWidth(); i++) {
            for(int j = 0; j < g.getHeight(); j++) {
                if(g.isON(j,i)) {
                    System.out.println("Failure. default value of g.isON("
                        +j+", "+i+") should be false, it is true");
                    return;                    
                }
            }
        }

        // sets all values to true
        for(int i = 0; i < g.getWidth(); i++) {
            for(int j = 0; j < g.getHeight(); j++) {
                g.set(i,j,true);
            }
        }

        // test result
        for(int i = 0; i < g.getWidth(); i++) {
            for(int j = 0; j < g.getHeight(); j++) {
                if(!g.isON(j,i)) {
                    System.out.println("Failure. Value of g.isON("
                        +j+", "+i+") should be true, it is false");
                    return;                    
                }
            }
        }

        //test reset
        g.reset();
        for(int i = 0; i < g.getWidth(); i++) {
            for(int j = 0; j < g.getHeight(); j++) {
                if(g.isON(j,i)) {
                    System.out.println("Failure. after reset value of g.isON("
                        +j+", "+i+") should be false, it is true");
                    return;                    
                }
            }
        }

        // test setting a single value
        g.set(3,2,true);
        if(!g.isON(2,3)) {
            System.out.println("Failure. g.set(3,2,true), g.isON(2,3) is false. Should be true");
            return;                    
        }
        for(int i = 0; i < g.getWidth(); i++) {
            for(int j = 0; j < g.getHeight(); j++) {
                if((i!=3||j!=2)&& g.isON(j,i)) {
                    System.out.println("Failure. g.isON("
                        +j+", "+i+") should be false, it is true");
                    return;                    
                }
            }
        }
        System.out.println("Success");

    }
   
    public static void main(String[] args) {
        StudentInfo.display();
        testSolver();
        testShortest();
        testModel();

    }
}