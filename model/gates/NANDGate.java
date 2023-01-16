/**
 * Class:
 * 		NANDGate
 * Description:
 * 		Represents a logical NAND Gate
 * Areas of Concern:
 * 		None
 */

package model.gates;

import java.awt.*;
import java.util.ArrayList;

import model.*;
import view.*;

import javax.swing.*;

public class NANDGate extends Gate {
	
	// constructor
    public NANDGate(int x, int y) {
        super(x, y);

        super.setGraphicIcon(new ImageIcon("images/NAND_Icon.png").getImage());
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
		g.setFont(new Font("Courier New", Font.BOLD,15));
		g.drawString("NAND", 28, 43);

	}

	// execution of the NAND Gate
    @Override
    public boolean execute(boolean[] in) {
        return !(in[0] && in[1]);
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
