//import java.util.Random;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class GridButton extends JButton {

	
    private static int BUTTON_COLOUR = 1;

    /**
     * The coordinate of this cell on the <b>Board</b>.
     */

    private int row, column;


    /**
     * Constructor used for initializing a GridButton at a specific
     * Board location.	
     * 
     * @param column
     *            the column of this Cell
     * @param row
     *            the row of this Cell
     */

    public GridButton(int column, int row) {
    	this.column = column;
    	this.row = row;
 
        setBackground(Color.WHITE);
        setIcon(getImageIcon());
        Border emptyBorder = BorderFactory.createEmptyBorder(0, 0, 0, 0);
        setBorder(emptyBorder);
        setBorderPainted(false);
    }

   /**
    * sets the icon of the button to reflect the
    * on specified by the parameters
    * @param isOn true if that location is ON
    * @param isClicked true if that location is
    * tapped in the model's current solution
    */ 
    public void setState(boolean isOn, boolean isClicked) {
    	if(!isOn && !isClicked){
            BUTTON_COLOUR = 1;
            setIcon(getImageIcon());
        } else if(isOn && !isClicked){
        	BUTTON_COLOUR = 0;
            setIcon(getImageIcon());
        } else if(!isOn && isClicked){
        	BUTTON_COLOUR = 3;
            setIcon(getImageIcon());
        } else if(isOn && isClicked){
        	BUTTON_COLOUR = 2;
            setIcon(getImageIcon());
        }
    }

 

    /**
     * Getter method for the attribute row.
     * 
     * @return the value of the attribute row
     */

    public int getRow() {
       return row;
    }

    /**
     * Getter method for the attribute column.
     * 
     * @return the value of the attribute column
     */

    public int getColumn() {
        return column;
    }

    private ImageIcon getImageIcon() {
    	ImageIcon ic = new ImageIcon(GridButton.class.getResource("Icons/light-"+ BUTTON_COLOUR + ".png"));
    	return ic;
    }
}
