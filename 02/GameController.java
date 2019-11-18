import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.awt.Checkbox;
/**
 * The class <b>GameController</b> is the controller of the game. It is a listener
 * of the view, and has a method <b>play</b> which computes the next
 * step of the game, and  updates model and view.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */

public class GameController implements ActionListener, ItemListener {
	private GameModel model;
	private GameView view;
	//private int numberOfViews;

    /**
     * Constructor used for initializing the controller. It creates the game's view 
     * and the game's model instances
     * 
     * @param width
     *            the width of the board on which the game will be played
     * @param height
     *            the height of the board on which the game will be played
     */
    public GameController(int width, int height) {

        model = new GameModel(width, height);
        view = new GameView(model,this);
        update();
    }


    /**
     * Callback used when the user clicks a button (reset, 
     * random or quit)
     *
     * @param e
     *            the ActionEvent
     */

    public void actionPerformed(ActionEvent e) {
    	
    	if(e.getSource() instanceof GridButton) {
    		GridButton src = (GridButton) e.getSource();
    		model.click((src.getColumn()), (src.getRow()));
    	}
    	if(e.getActionCommand().equals("Reset")) {
    		model.reset();
    	} 
    	if(e.getActionCommand().equals("Random")){
    		model.randomize();
    	}
    	if(e.getActionCommand().equals("Quit")) {
    		System.exit(0);	
    	}
    	
    	update();
    }
   
    /**
     * Callback used when the user select/unselects
     * a checkbox
     *
     * @param e
     *            the ItemEvent
     */	

    public void  itemStateChanged(ItemEvent e){
    	Checkbox check =(Checkbox)e.getItemSelectable();
    	if(check.getState()==true){ // view.solutionShown()
    		model.setSolution();
    	}
    	update();
    	
    }

    public GameModel getModel() {
		return model;
	}

	public GameView getViews() {
		return view;
	}


	private void update() {
		view.update();
	}

}
