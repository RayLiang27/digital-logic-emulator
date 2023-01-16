/**
 * Class:
 * 		EComponent
 * Description:
 * 		Represents a placable Electronic Component on the build screen board
 * Areas of Concern:
 * 		None
 */

package model;

import javax.swing.*;

import java.awt.Point;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.HashMap;

import view.*;

public abstract class EComponent extends JLabel implements LogicAction {

    // fields
    private int realPositionX;
    private int realPositionY;

    private int lastLocX;
    private int lastLocY;

    private boolean isVisited;

    private boolean[] inputs = {false, false};

    private HashMap<EComponent, Integer> outputMap = new HashMap<>();
    
    // constructor
    public EComponent(int x, int y) {
        super();

        // set the size of the JLabel
        setSize(5 * BuildingGrid.gridSize, 4 * BuildingGrid.gridSize);

        realPositionX = x;
        realPositionY = y;
        setLocation(x, y);

    }

    // constructor
    public EComponent(Point position) {
        super();

        // set the size of the JLabel
        setSize(5 * BuildingGrid.gridSize, 4 * BuildingGrid.gridSize);

        realPositionX = (int) position.getX();
        realPositionY = (int) position.getY();
        setLocation(position);

    }

    // snaps the location of the component to the grid
    public void snapLocation() {

        realPositionX = this.getX();
        realPositionY = this.getY();

        setLocation(Math.round((float) realPositionX / BuildingGrid.gridSize) * BuildingGrid.gridSize, Math.round((float) realPositionY / BuildingGrid.gridSize) * BuildingGrid.gridSize + 1);

        System.out.println("ELOC SNAP: (" + getX() + ", " + getY() + ")");
    }

    public static Point snapLocation(Point input) {

        return new Point(Math.round((float) input.getX() / BuildingGrid.gridSize) * BuildingGrid.gridSize, Math.round((float) input.getY() / BuildingGrid.gridSize) * BuildingGrid.gridSize + 1);
        
    }

    public static Point snapLocation(int x, int y) {

        return new Point(Math.round((float) x / BuildingGrid.gridSize) * BuildingGrid.gridSize, Math.round((float) y / BuildingGrid.gridSize) * BuildingGrid.gridSize + 1);
        
    }

    public static Point snapLocation(Point input, int offset) {

        return new Point(
            Math.round((float) input.getX() / BuildingGrid.gridSize) * BuildingGrid.gridSize + offset, 
            Math.round((float) input.getY() / BuildingGrid.gridSize) * BuildingGrid.gridSize + 1 + offset
        );
        
    }

    public static Point snapLocation(int x, int y, int offset) {

        return new Point(
            Math.round((float) x / BuildingGrid.gridSize) * BuildingGrid.gridSize + offset, 
            Math.round((float) y / BuildingGrid.gridSize) * BuildingGrid.gridSize + 1 + offset
        );
        
    }

    public static int snapLocation(int location) {

        return Math.round((float) (location / BuildingGrid.gridSize)) * BuildingGrid.gridSize;

    }

    // notes the last location of the component before the move
    public void popLocation() {

        setLocation(this.getX(), this.getY());
        lastLocX = this.getX();
        lastLocY = this.getY();

        System.out.println("ELOC POP: (" + getX() + ", " + getY() + ")");

    }

    // abstract method for generating click points
    public abstract ArrayList<ClickPoint> generateClickPoints();

    // reverts the location to the last location it was at
    public void revertLocation() {
        setLocation(lastLocX, lastLocY);
    }

    // add various mouse listeners to the component
    public void generateListeners(BuildingScreen screen) {

        this.addMouseListener(screen);
        this.addMouseMotionListener(screen);

    }

    // detect collisions with given JComponent
    public boolean collidesWith(JComponent component) {

        // Source:
        // https://stackoverflow.com/questions/12325553/how-do-i-detect-the-collison-of-components

        Area thisArea = new Area(this.getBounds());
        Area componentArea = new Area(component.getBounds());
    
        return thisArea.intersects(componentArea.getBounds2D());
    }

    // detects if the EComoponent is out of grid bounds
    public boolean isOutOfBounds() {
        return this.getRightBound() > 1780 || this.getTopBound() < 261 || this.getLeftBound() < 140 || this.getBottomBound() > 981;
    }

    // getters and setters 
    public int getTopBound() {
        return getY();
    }

    public int getLeftBound() {
        return getX();
    }

    public int getBottomBound() {
        return getY() + getHeight();
    }
    
    public int getRightBound() {
        return getX() + getWidth();
    }
    
    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }

    public boolean[] getInputs() {
        return inputs;
    }

    public void setInputs(boolean[] inputs) {
        this.inputs = inputs;
    }

    public HashMap<EComponent, Integer> getOutputMap() {
        return outputMap;
    }

}