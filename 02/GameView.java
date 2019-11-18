import javax.swing.*;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;



public class GameView extends JFrame {
    
    private GameController gameController;
    private GameModel model;
    
    private GridButton[][] board;
    private JPanel gridLayout;
    private JPanel buttonPanel;
    private JPanel counter;
    private Checkbox solution;

    private JLabel counterNum;

    /**
     * Constructor used for initializing the Frame
     * 
     * @param gameModel
     *            the model of the game (already initialized)
     * @param gameController
     *            the controller
     */

    public GameView(GameModel gameModel, GameController gameController) {
        this.model = gameModel;
        this.gameController = gameController;
        setTitle("Lights Out");
        setSize(500, 600);
        
        // initialize the three panels
        buttonPanel =new JPanel();
        gridLayout = new JPanel();
        counter = new JPanel();
        
        //checkbox
        solution = new Checkbox("Solution", false);
        solution.addItemListener(gameController);
        
        //buttons
        JButton reset;
        reset = new JButton("Reset");
        reset.addActionListener(gameController);

        JButton random;
        random = new JButton("Random");
        random.addActionListener(gameController);

        JButton quit;
        quit = new JButton("Quit");
        quit.addActionListener(gameController);

        board = new GridButton[model.getHeight()][model.getWidth()]; // board of tallies
        gridLayout.setLayout(new GridLayout(model.getHeight(), model.getWidth()));
        gridLayout.setBackground(Color.WHITE);
        
        for (int i = 0; i < model.getHeight(); i++) {
            for (int j = 0; j < model.getWidth(); j++) {
                board[i][j] = new GridButton(i,j); // creates new GridButton for every tally
                board[i][j].setState(model.isON(i,j),model.solutionSelects(i,j));
                board[i][j].addActionListener(gameController);
                gridLayout.add(board[i][j]);
            }
        }
        
        // adding buttons and checkbox to the buttonPanel
        buttonPanel.add(reset);
        buttonPanel.add(random);
        buttonPanel.add(solution);
        buttonPanel.add(quit);
        
        counterNum = new JLabel("Number of steps : " + model.getNumberOfSteps());
        counter.add(counterNum);


        add(gridLayout, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);
        add(counter, BorderLayout.SOUTH);
        
        setVisible(true);
        
    }

    /**
     * updates the status of the board's GridButton instances based 
     * on the current game model, then redraws the view
     */
    public void update(){
        if(solution.getState()==false){
            for (int i = 0; i < model.getHeight(); i++) {
                for (int j = 0; j < model.getWidth(); j++) {
                    board[i][j].setState(model.isON(i,j),false);
                }
            }
            
        }else{ // solution.getState()==true
            for (int i = 0; i < model.getHeight(); i++) {
                for (int j = 0; j < model.getWidth(); j++) {
                    board[i][j].setState(model.isON(i,j),model.solutionSelects(i,j));
                }
            }
        }
        counterNum.setText("Number of steps : " + model.getNumberOfSteps()); //updates the counter
    }

    /**
     * returns true if the ``solution'' checkbox
     * is checked
     *
     * @return the status of the ``solution'' checkbox
     */

    public boolean solutionShown(){
        return solution.getState();

    }

}
