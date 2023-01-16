/**
 * Class:
 * 		XORGate
 * Description:
 * 		Represents a logical XOR Gate
 * Areas of Concern:
 * 		None
 */

package model.gates;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import model.*;
import view.*;

public class XORGate extends Gate {

	// constructor
    public XORGate(int x, int y) {
        super(x, y);

		super.setGraphicIcon(new ImageIcon("images/XOR_Icon.png").getImage());
    }

	// changes the paintComponent of the label, adds in the input pins and gate image
    @Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);

		// fill two input pins 
		g.setColor(super.getInPinColor()[0]);
        g.fillRect(0, 19-2, BuildingGrid.gridSize, 4);
		g.setColor(super.getInPinColor()[1]);
		g.fillRect(0, 59-2, BuildingGrid.gridSize, 4);

		g.setColor(Color.WHITE);
		g.setFont(new Font("Courier New", Font.BOLD,18));
		g.drawString("XOR", 39, 44);

	}

	// execution of the XOR Gate
    @Override
    public boolean execute(boolean[] in) {
        
        return in[0] != in[1];
    }

	// generates click points at each input pin
    @Override
	public ArrayList<ClickPoint> generateClickPoints() {
		
		ArrayList<ClickPoint> toBeReturned = super.generateClickPoints();
		toBeReturned.add(new ClickPoint(getX(), getY() + 20, 'I', this));
		toBeReturned.add(new ClickPoint(getX(), getY() + 60, 'I', this));
		return toBeReturned;
	}
    
}
