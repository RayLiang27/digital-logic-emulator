/**
 * Class:
 * 		NOTGate
 * Description:
 * 		Represents a logical NOT Gate
 * Areas of Concern:
 * 		None
 */

package model.gates;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import model.*;
import view.*;

public class NOTGate extends Gate {

    // constructor
    public NOTGate(int x, int y) {
        super(x, y);

        super.setGraphicIcon(new ImageIcon("images/NOT_Icon.png").getImage());
    }

    // changes the paintComponent of the label, adds in the input pins and gate image
    @Override
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g);

        // fill one input pins 
        g.setColor(super.getInPinColor()[0]);
        g.fillRect(0, 39-2, BuildingGrid.gridSize, 4);

        g.setColor(Color.WHITE);
		g.setFont(new Font("Courier New", Font.BOLD,15));
		g.drawString("NOT", 27, 43);
    }
    
    // execution of the NOT Gate
    @Override
    public boolean execute(boolean[] in) {
        return !in[0];
    }

    // generates click points at each input pin
    @Override
	public ArrayList<ClickPoint> generateClickPoints() {
		ArrayList<ClickPoint> toBeReturned = super.generateClickPoints();
		toBeReturned.add(new ClickPoint(getX(), getY() + 40, 'I', this));
		return toBeReturned;
	}

}
