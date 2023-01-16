/**
 * Class:
 * 		Gate
 * Description:
 * 		Represents the abstract parent class that all logic gates inherit
 * Areas of Concern:
 * 		None
 */

package model.gates;

import java.awt.*;
import java.util.ArrayList;

import model.*;
import view.*;

import javax.swing.BorderFactory;
import javax.swing.border.*;

public abstract class Gate extends EComponent {

    // borders 
    private final Border selectionBorder = BorderFactory.createLineBorder(ColorScheme.selectionColor, 2);
	private final Border invalidBorder = BorderFactory.createLineBorder(ColorScheme.invalidColor, 2);

    private Image graphicIcon;
    private Color outPinColor = Color.WHITE;
    private Color[] inPinColor = {Color.WHITE, Color.WHITE};

    // constructor
    public Gate(int x, int y) {
        super(x, y);
    }
    
    // changes the paintComponent of the label, adds in the output pins and gate image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.drawImage(graphicIcon, 20, 10, 60, 58, null);
        
        g.setColor(outPinColor);
        g.fillRect(79, 39-2, BuildingGrid.gridSize, 4);

    }

    // generates click points at the output pin
    @Override
    public ArrayList<ClickPoint> generateClickPoints() {

        ArrayList<ClickPoint> toBeReturned = new ArrayList<ClickPoint>();
        toBeReturned.add(new ClickPoint(getX() + 99, getY() + 40, 'O', this));
        return toBeReturned;
        
    }

    // getters and setters
    public void setOutline(String type) {
        
        if (type.equals("selection"))
            setBorder(selectionBorder);
        else if (type.equals("invalid"))
            setBorder(invalidBorder);
        else
            setBorder(null);
        
    }
    
    public Image getGraphicIcon() {
        return graphicIcon;
    }
    
    public void setGraphicIcon(Image graphicIcon) {
        this.graphicIcon = graphicIcon;
    }
    
    public Color getOutPinColor() {
        return outPinColor;
    }
    
    public void setOutPinColor(Color outPinColor) {
        this.outPinColor = outPinColor;
    }
    
    public void setOutPinColor(boolean signal) {

        if (signal)
            outPinColor = ColorScheme.aqua;
        else
            outPinColor = Color.WHITE;
        repaint();

    }
    
    public Color[] getInPinColor() {
        return inPinColor;
    }

    public void setInPinColor(Color[] inPinColor) {
        this.inPinColor = inPinColor;
    }

    public void setInPinColor(boolean[] signal) {

        for (int i = 0; i < inPinColor.length; i++)
            inPinColor[i] = signal[i] ? ColorScheme.aqua : Color.WHITE;

    }
    
}
