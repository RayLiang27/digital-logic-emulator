/**
 * Class:
 * 		ClickPoint
 * Description:
 * 		Represents a point that can be clicked on to link wires
 * Areas of Concern:
 * 		None
 */

package model;

import javax.swing.*;

public class ClickPoint extends JButton {
    
    // fields
    private final ImageIcon btnImg = new ImageIcon("images/clickpoint.png");
    private char type;
    private EComponent owner;
    private int pinNum = 0;
    // 'I' = input
    // 'O' = output

    // constructor
    public ClickPoint(int x, int y, char type, EComponent owner) {
        super();

        this.type = type;
        this.owner = owner;

        // determines which pin number the click point belongs
        if (y - owner.getY() == 60)
            pinNum = 1;
        
        setSize(30, 30);
        setLocation(x - 15, y - 15);

        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);

        setIcon(btnImg);

    }

    // setters and getters
    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public EComponent getOwner() {
        return owner;
    }

    public void setOwner(EComponent owner) {
        this.owner = owner;
    }

    public int getPinNum() {
        return pinNum;
    }

}
