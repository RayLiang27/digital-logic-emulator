/**
 * Class:
 * 		INPin
 * Description:
 * 		Represents a clickable input pin
 * Areas of Concern:
 * 		None
 */

package model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.*;

public class INPin extends EComponent {

    // fields
    private boolean selected = false;
    private JButton buttonSwitch = new JButton();
    private Color pinColor = Color.WHITE;

    // constructor
    public INPin(int y) {
        super(60, y + 260);

        setSize(100, 80);

        // setup the button for changing the input pin's state
        buttonSwitch.setBounds(0, 20, 30, 40);
        buttonSwitch.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
        buttonSwitch.setOpaque(true);
        buttonSwitch.setBackground(ColorScheme.backgroundColor);
        add(buttonSwitch);

    }

    // toggles the on/off state and visuals of the INPin
    public void toggle() {

        selected = !selected;

        if (selected) {
            buttonSwitch.setBackground(ColorScheme.aqua);
            pinColor = ColorScheme.aqua;
        }
        else {
            buttonSwitch.setBackground(ColorScheme.backgroundColor);
            pinColor = Color.WHITE;
        }
        repaint();

    }

    // getters and setters
    public JButton getButtonSwitch() {
        return buttonSwitch;
    }

    public void setButtonSwitch(JButton buttonSwitch) {
        this.buttonSwitch = buttonSwitch;
    }

    public Color getPinColor() {
        return pinColor;
    }

    public void setPinColor(Color pinColor) {
        this.pinColor = pinColor;
    }

    public boolean isSelected() {
        return selected;
    }

    // draws the output wire
    @Override
    protected void paintComponent(Graphics g) {    
        super.paintComponent(g);

        g.setColor(pinColor);
        g.fillRect(0, 38, 100, 4);

    }

    // returns the on/off state of the INPin
    @Override
    public boolean execute(boolean[] in) { 
        return selected;
    }

    // Generates an output click point at the end wire
    @Override
    public ArrayList<ClickPoint> generateClickPoints() {    
        ArrayList<ClickPoint> toBeReturned = new ArrayList<ClickPoint>();
        toBeReturned.add(new ClickPoint(getX() + 99, getY() + 40, 'O', this));
        return toBeReturned;
    }

}
