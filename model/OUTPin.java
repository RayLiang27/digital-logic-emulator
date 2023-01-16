/**
 * Class:
 * 		OUTPin
 * Description:
 * 		Represents a clickable output pin
 * Areas of Concern:
 * 		None
 */

package model;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class OUTPin extends EComponent{
    
    // fields
    private final Image onIcon = (new ImageIcon("images/on.png")).getImage();
    private final Image offIcon = (new ImageIcon("images/off.png")).getImage();

    private boolean state = false;

    // constructor
    public OUTPin(int y) {
        super(1760, y + 260);

        setSize(100, 80);

    }

    // changes the state of the light based on a boolean input
    @Override
    public boolean execute(boolean[] in) {
        setState(in[0]);
        return in[0];
    }

    // draws the wire and the LED image of the pin
    @Override
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g);

        if (state)
            g.setColor(ColorScheme.aqua);
        else
            g.setColor(Color.WHITE);
        g.fillRect(0, 38, 80, 4);

        if (state)
            g.drawImage(onIcon, 40, 10, 60, 60, null);
        else
            g.drawImage(offIcon, 40, 10, 60, 60, null);

    }

    // generates a single input clickpoint at the start of the wire
    @Override
    public ArrayList<ClickPoint> generateClickPoints() {
        
        ArrayList<ClickPoint> toBeReturned = new ArrayList<ClickPoint>();
        toBeReturned.add(new ClickPoint(getX(), getY() + 40, 'I', this));
        return toBeReturned;
    }

    // getters and setters
    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
        repaint();
    }

}
