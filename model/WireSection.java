/**
 * Class:
 * 		WireSection
 * Description:
 * 		Represents a single line of wire that is used to connect multiple pins
 * Areas of Concern:
 * 		None
 */

package model;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import model.gates.*;

public class WireSection extends EComponent {

    // fields
    private char orientation;
    /*
     * H = horizontal
     * V = vertical
     */

    // used to dictate the actual position 
    private int realPositionX;
    private int realPositionY;
    private EComponent owner;
    
    // constructors
    public WireSection() {

        super(0, 0);

        setSize(0, 0);

    }
    
    public WireSection(int x, int y, char orientation, EComponent owner) {
        super(x, y);

        setOpaque(true);
        setSize(4, 4);

        // setBorder(BorderFactory.createLineBorder(Color.RED));
        setBackground(Color.WHITE);

        realPositionX = x;
        realPositionY = y;

        this.orientation = orientation;
        this.owner = owner;

    }

    public WireSection(int x, int y, int width, int height, char orientation, EComponent owner) {
        this(x, y, orientation, owner);

        setSize(width, height);

    }

    public WireSection(Point location, char orientation, EComponent owner) {
        this((int) location.getX(), (int) location.getY(), orientation, owner);

    }

    public WireSection(Point location, int width, int height, char orientation, EComponent owner) {
        this((int) location.getX(), (int) location.getY(), width, height, orientation, owner);
    }

    // returns the inputted boolean value
    @Override
    public boolean execute(boolean[] in) {
        return in[0];
    }

    // update the length of the WireSection
    public void updateLength(Point position, Point offsetPoint) {

        int distX = (int) (position.getX() - realPositionX + offsetPoint.getX());
        int distY = (int) (position.getY() - realPositionY + offsetPoint.getY());

        if (orientation == 'H') {
            if (distX < 0) {
                setLocation((int) (position.getX() + offsetPoint.getX()), realPositionY);
            }
            else {
                setLocation(realPositionX, realPositionY);
            }
            
            setSize(Math.abs(distX), 4);

        }
        else if (orientation == 'V') {
            if (distY < 0) {
                setLocation(realPositionX, (int) (position.getY() + offsetPoint.getY()));
            }
            else {
                setLocation(realPositionX, realPositionY);
            }
            setSize(4, Math.abs(distY));

        }
        
    }

    // determines the position of the currently moving end terminal
    public Point endTerminal(Point position, Point offsetPoint) {
        
        if (orientation == 'H') {
            if (position.getX() + offsetPoint.getX() > realPositionX)
            return new Point(getX() + getWidth(), getY());
            else
            return new Point(getX(), getY());
            
        }
        else if (orientation == 'V') {
            if (position.getY() + offsetPoint.getY() > realPositionY)
            return new Point(getX(), getY() + getHeight());
            else
            return new Point(getX(), getY());
        }
        else
        return new Point();
        
    }

    // fills the 4x4 pixel gaps that can appear when wires are being connected
    public void fillGap() {

        if (orientation == 'H')
            setSize(getWidth() + 4, getHeight());
        else if (orientation == 'V')
            setSize(getWidth(), getHeight() + 4);

    }

    // sets the color of the wire based on whether or not it is invalid (intersects another EComponent)
    public void setBackground(String type) {

        if (type.equals("invalid"))
            setBackground(ColorScheme.invalidColor);
        else
            setBackground(Color.WHITE);
        
    }

    // sets the color of the wire based on the owner component's output value
    public void setBackground() {

        if (owner instanceof Gate) {
            Gate asGate = (Gate) owner;
            setBackground(asGate.getOutPinColor());
        }
        else if (owner instanceof INPin) {
            INPin asPin = (INPin) owner;
            setBackground(asPin.getPinColor());

        }

    }

    // getters and setters
    public EComponent getOwner() {
        return owner;
    }

    public char getOrientation() {
        return orientation;
    }

    // filler implementation of abstract method
    @Override
    public ArrayList<ClickPoint> generateClickPoints() { 
        return new ArrayList<>();
    }

    // snaps the endTerminal (along with the entire wire) to the grid
    //      due to time constraints, this method does not work
    public void snapLine() {

        if (orientation == 'H') {
        
            

        }
        else if (orientation == 'V') {



        }

    }

    // snaps the location of the the wire onto the grid off by an offset
    @Override
    public void snapLocation() {
        if (orientation == 'H') {
        
            super.snapLocation();
            setLocation(getX(), getY() - 3);
            realPositionX = getX();
            realPositionY = getY();

        }
        else if (orientation == 'V') {



        }

    }

}
