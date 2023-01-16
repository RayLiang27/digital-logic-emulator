/**
 * Class:
 * 		BuildingScreen
 * Description:
 * 		Represents a screen where circuits are built
 * Areas of Concern:
 * 		- The wires do not snap onto their location 
 * 		- Wires cannot be removed once placed down
 * 		- Wires cannot be edited once placed down
 * 		- Gates can be shifted after the wires are layed down, however, the wires will not follow
 * 			- Despite this, the program will still treat the wire as "connecting" the two of them, and the testing algorithm
 * 			will run them as connected
 * 		- After testing, if the user wants to add another component or wire to the board, the wires will become white, but
 * 		various components may remain on; this prevents testing from being done again, but can be solved by toggling all the INPin
 * 		states off and on again
 */

package view;

import javax.swing.*;
import javax.swing.border.*;

import controller.RunCircuit;

import java.awt.event.*;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import model.*;
import model.gates.*;

public class BuildingScreen extends JPanel implements ActionListener,  MouseListener,  MouseMotionListener {

	// other visual parts of the BuildingScreen
	private Ribbon ribbon = new Ribbon();
	private BuildingGrid grid = new BuildingGrid();

	// DEFINES THE ENTIRE BOARD AND SCREEN
	private ArrayList<EComponent> boardComponents = new ArrayList<>();
	private ArrayList<INPin> boardIns = new ArrayList<>();
	private ArrayList<OUTPin> boardOuts = new ArrayList<>();	

	// fields
	private HashMap<Point, ClickPoint> boardClickPoints = new HashMap<>();

	private WireSection currentWire = new WireSection();

	private boolean drag = false;

	private boolean wiringMode = false;
	private boolean testingMode = false;
	private boolean pressedWire = false;
	private EComponent fromComponent;

	private int offsetX;
    private int offsetY;
	private int currentGateButton;

	private Gate[] menuGates = new Gate[7];
	
    // create a new file
    public BuildingScreen() {
        super();

		// setup the JPanel
        setBackground(ColorScheme.backgroundColor);
        setSize(1920, 1080);
        setLayout(null);

		// generate the menu gates (since all are different, no for loop can be used :( )
		int tempI = 0;
		menuGates[tempI] = new ANDGate(ribbon.getGateButtons()[tempI++].getX(), 21);
		menuGates[tempI] = new ORGate(ribbon.getGateButtons()[tempI++].getX(), 21);
		menuGates[tempI] = new XORGate(ribbon.getGateButtons()[tempI++].getX(), 21);
		menuGates[tempI] = new NOTGate(ribbon.getGateButtons()[tempI++].getX(), 21);
		menuGates[tempI] = new NANDGate(ribbon.getGateButtons()[tempI++].getX(), 21);
		menuGates[tempI] = new NORGate(ribbon.getGateButtons()[tempI++].getX(), 21);
		menuGates[tempI] = new XNORGate(ribbon.getGateButtons()[tempI++].getX(), 21);

		// add necessary listeners and add each gate to the panel
		for (Gate gate : menuGates) {
			gate.generateListeners(this);
			add(gate);
			gate.setVisible(false);
		}

		// generate listeners for the ribbon and grid
		ribbon.generateListeners(this);
		grid.addMouseListener(this);
		grid.addMouseMotionListener(this);
		
		// add each component and its necessary listeners to the panel
		for (EComponent component : boardComponents) {
			component.generateListeners(this);
			add(component);
		}

		// add each INPin and OUTPin and its necessary listeners to the panel
		for (INPin pin : boardIns) {
			remove(pin);
			add(pin);
		}
		for (OUTPin pin : boardOuts) {
			remove(pin);
			add(pin);
		}

		// add the currentWire, ribbon and grid objects
		add(currentWire);
		add(ribbon);
		add(grid);

    }

    // open an existing file
    public BuildingScreen(ArrayList<EComponent> boardComponents, ArrayList<INPin> boardIns, ArrayList<OUTPin> boardOuts) {
        super();

		this.boardComponents = boardComponents;
		this.boardIns = boardIns;
		this.boardOuts = boardOuts;
    }
	
	// draw each click point present on the board
	public void drawClickPoints(char type) {

		boardClickPoints = new HashMap<>();

		for (EComponent eComponent : boardComponents)
			insertClickPoints(eComponent.generateClickPoints());

		for (INPin pin : boardIns)
			insertClickPoints(pin.generateClickPoints());
		
		for (OUTPin pin : boardOuts)
			insertClickPoints(pin.generateClickPoints());


		for (ClickPoint point : boardClickPoints.values()) {
			if (point.getType() == type) {
				add(point);
				point.addMouseListener(this);
				point.addMouseMotionListener(this);
			}
		}

	}

	// remove each click point present on the board
	public void removeClickPoints() {

		for (ClickPoint point : boardClickPoints.values())
			remove(point);

	}

	// insert more click points into the hashmap that is on the boardA
	public void insertClickPoints(ArrayList<ClickPoint> list) {

		for (ClickPoint point : list) {
			if (boardClickPoints.get(point.getLocation()) == null) {
				boardClickPoints.put(point.getLocation(), point);
				System.out.println("\tloc: " + point.getLocation());
			}
		}

	}

	// when the mouse is pressed down
	@Override
	public void mousePressed(MouseEvent event) {
		// all JComponents on the BuildingScreen
		if (event.getSource() instanceof JComponent && event.getSource() != grid && !(event.getSource() instanceof ClickPoint) && !wiringMode) {
			// enable the dragging flag (the component is going to be dragged)
			drag = true;
			// if it is a button being pressed (ie a new component is being made)
			if (event.getSource() instanceof JButton) {

				// find the index of the button being dragged
				currentGateButton = ribbon.findGateButtonIndex((JButton) event.getSource());

				// make the gate visible
				menuGates[currentGateButton].setVisible(true);
				menuGates[currentGateButton].setOutline("selection");
				
			}
			// if it is already a present component
			else if (event.getSource() instanceof EComponent) {

				EComponent component = (EComponent) event.getSource();

				component.popLocation();

				if (component instanceof Gate) {
					Gate gate = (Gate) component;
					gate.setOutline("selection");
				}
				
			}
			// offset the mouse position within the actual component
			offsetX = event.getX();
			offsetY = event.getY();
		}
		// start of a wire
		else if (event.getSource() instanceof ClickPoint && wiringMode && !pressedWire) {
	
			ClickPoint point = (ClickPoint) event.getSource();

			// snap it to the current clickpoint
			currentWire = new WireSection(EComponent.snapLocation(point.getX() + 15, point.getY() + 15), 'H', point.getOwner());
			currentWire.snapLocation();
			System.out.println(currentWire.getX() + ", added: " + (event.getX() + point.getX()));
			pressedWire = true;

			fromComponent = point.getOwner();

			// redraw the click points
			removeClickPoints();
			drawClickPoints('I');

			update();
			
		}
		// bend of a wire
		else if (!(event.getSource() instanceof ClickPoint) && pressedWire && currentWire.getBackground() == Color.WHITE) {

			JComponent component = (JComponent) event.getSource();

			// fill any gaps between the wires
			currentWire.fillGap();
			
			// add the current wire to the board components and then regenerate it
			boardComponents.add(new WireSection(currentWire.getX(), currentWire.getY(), currentWire.getWidth(), currentWire.getHeight(), currentWire.getOrientation(), currentWire.getOwner()));
			if (currentWire.getOrientation() == 'H') {
				currentWire = new WireSection(
					(int) currentWire.endTerminal(new Point(event.getX(), event.getY()), component.getLocation()).getX(), 
					currentWire.getY(), 
					'V', currentWire.getOwner());
			}
			else if (currentWire.getOrientation() == 'V') {
				currentWire = new WireSection(
					currentWire.getX(), 
					(int) currentWire.endTerminal(new Point(event.getX(), event.getY()), component.getLocation()).getY(), 
					'H', currentWire.getOwner());
			}

			update();

		}
		// end of a wire
		else if (event.getSource() instanceof ClickPoint && pressedWire && currentWire.collidesWith((ClickPoint) event.getSource()) && currentWire.getBackground() == Color.WHITE) {

			ClickPoint point = (ClickPoint) event.getSource();

			// add the current wire to the board components and then make it empty
			boardComponents.add(new WireSection(currentWire.getX(), currentWire.getY(), currentWire.getWidth(), currentWire.getHeight(), currentWire.getOrientation(), currentWire.getOwner()));
			currentWire = new WireSection();
			pressedWire = false;

			// adds the direction of the edge to the graph for traversal
			fromComponent.getOutputMap().put(point.getOwner(), point.getPinNum());

			// redraw the click points
			removeClickPoints();
			drawClickPoints('O');

			update();

			// DEBUG
			for (EComponent eComponent : boardComponents) {
				
				if (eComponent.getOutputMap().size() > 0) {

					System.out.println("ECOMP: ");
					System.out.println("\t" + eComponent.getOutputMap());

				}

			}
			for (INPin pin : boardIns) {

				if (pin.getOutputMap().size() > 0) {

					System.out.println("INPUT PIN: ");
					System.out.println("\t" + pin.getOutputMap());

				}

			}

		}

	}
	
	// when the mouse is pressed down and moved
	@Override
	public void mouseDragged(MouseEvent event) {
		if (drag && event.getSource() != grid && !wiringMode) {

			JComponent component = (JComponent) event.getSource();
			
			if (event.getSource() instanceof JButton && !wiringMode) {
				
				// drag the menu gate object instead
				menuGates[currentGateButton].setLocation(component.getX()+event.getX() - offsetX, component.getY()+event.getY() - offsetY);

				menuGates[currentGateButton].setOutline("selection");

				if (menuGates[currentGateButton].isOutOfBounds())
					menuGates[currentGateButton].setOutline("invalid");

				for (EComponent compared : boardComponents)
					if (menuGates[currentGateButton].collidesWith(compared) && !menuGates[currentGateButton].equals(compared))
						menuGates[currentGateButton].setOutline("invalid");

			}
			else if (component instanceof EComponent) {

				EComponent eComponent = (EComponent) component;

				// drag the component that is currently present on screen
	        	eComponent.setLocation(eComponent.getX()+event.getX() - offsetX, eComponent.getY()+event.getY() - offsetY);
				
				// verify it doesn't collide with any other EComponents
				if (eComponent instanceof Gate) {
					Gate gate = (Gate) eComponent;
					gate.setOutline("selection");

					if (gate.isOutOfBounds())
						gate.setOutline("invalid");

					for (EComponent compared : boardComponents)
						if (eComponent.collidesWith(compared) && !eComponent.equals(compared))
							gate.setOutline("invalid");
				}

			}
	    }
	}

	// when the mouse is released
	@Override
	public void mouseReleased(MouseEvent event) {
		drag = false;

		// snap the piece back down
		if (event.getSource() instanceof JComponent) {

			// if the component being dragged around is from a button
			if (event.getSource() instanceof JButton && !wiringMode) {

				// detect interference
				boolean hasInterference = false;
				for (EComponent eComponent : boardComponents)
					if (menuGates[currentGateButton].collidesWith(eComponent) && !menuGates[currentGateButton].equals(eComponent))
						hasInterference = true;

				// if the gate is in a valid position
				if (!menuGates[currentGateButton].isOutOfBounds() && !hasInterference) {
					
					// add a copy of the gate into the arraylist, and then move the menuGate back to its original position
					if (menuGates[currentGateButton] instanceof ANDGate)
						boardComponents.add(new ANDGate(menuGates[currentGateButton].getX(), menuGates[currentGateButton].getY()));
					else if (menuGates[currentGateButton] instanceof ORGate)
						boardComponents.add(new ORGate(menuGates[currentGateButton].getX(), menuGates[currentGateButton].getY()));
					else if (menuGates[currentGateButton] instanceof XORGate)
						boardComponents.add(new XORGate(menuGates[currentGateButton].getX(), menuGates[currentGateButton].getY()));
					else if (menuGates[currentGateButton] instanceof NOTGate)
						boardComponents.add(new NOTGate(menuGates[currentGateButton].getX(), menuGates[currentGateButton].getY()));
					else if (menuGates[currentGateButton] instanceof NANDGate)
						boardComponents.add(new NANDGate(menuGates[currentGateButton].getX(), menuGates[currentGateButton].getY()));
					else if (menuGates[currentGateButton] instanceof NORGate)
						boardComponents.add(new NORGate(menuGates[currentGateButton].getX(), menuGates[currentGateButton].getY()));
					else if (menuGates[currentGateButton] instanceof XNORGate)
						boardComponents.add(new XNORGate(menuGates[currentGateButton].getX(), menuGates[currentGateButton].getY()));

					boardComponents.get(boardComponents.size() - 1).generateListeners(this);
					boardComponents.get(boardComponents.size() - 1).snapLocation();
					add(boardComponents.get(boardComponents.size() - 1));
					
				}

				// regenerate the invisible gate on the button
				menuGates[currentGateButton].setLocation(ribbon.getGateButtons()[currentGateButton].getLocation());
				menuGates[currentGateButton].setVisible(false);

				update();

			}
			// if the component being dragged around was originally present on the board to begin with
			else if (event.getSource() instanceof EComponent && !wiringMode) {
				EComponent component = (EComponent) event.getSource();
				
				if (component instanceof Gate) {
					Gate gate = (Gate) component;
					gate.setOutline("none");
				}

				component.snapLocation();

				// if the component is being released out of bounds, delete it
				if (component.isOutOfBounds()) {
					remove(component);
					boardComponents.remove(component);
				}

				// if it collides with another component, revert its location
				for (EComponent eComponent : boardComponents)
					if (component.collidesWith(eComponent) && !component.equals(eComponent))
						component.revertLocation();

				System.out.println("size: " + boardComponents.size());
			}

		}

	}

	// when the mouse is moved
	@Override
	public void mouseMoved(MouseEvent event) {

		// if we are currently moving a wire around
		if (pressedWire) {

			JComponent component = (JComponent) event.getSource();

			currentWire.setBackground("valid");

			// check if the wire collides with another EComponent
			for (EComponent compared : boardComponents)
				if (currentWire.collidesWith(compared) && !(compared instanceof WireSection))
					currentWire.setBackground("invalid");

			// update the length of the wire
			currentWire.updateLength(event.getPoint(), component.getLocation());

			update();

		}

	}
	
	// when the mouse is pressed and released
	@Override
	public void mouseClicked(MouseEvent event) {

		// meant to delete the previously placed wire section
		//		due to time constraints, it is left unfinished
		if (pressedWire && event.getClickCount() == 2 && boardComponents.get(boardComponents.size() - 1) instanceof WireSection) {

			// delete the last given wire object
			//remove(boardComponents.get(boardComponents.size() - 1));

		}

	}

	// when an action is performed (mainly for buttons)
	@Override
	public void actionPerformed(ActionEvent event) {
		
		// if the button clicked is the wiring button
		if (event.getSource() == ribbon.getWireButton() && !pressedWire) {
			
			// begin wiring mode
			ribbon.toggleWireButton();
			wiringMode = !wiringMode;
			pressedWire = false;
			
			// if wire button border not null (ie it is activated), regenerate the hashmap
			if (ribbon.getWireButton().getBorder() != null) {
				
				drawClickPoints('O');
				System.out.println("add clickpoints");
				
			}
			// otherwise remove them all
			else
				removeClickPoints();
			update();
			
		}
		// if the button clicked is the new INPin button
		else if (event.getSource() == ribbon.getInPinButton() && !wiringMode) {

			// search for suitable spots to place the in pins (no interference)
			for (int i = 0; i < 9; i++) {

				// if there is no interference or we have reached the end of the arraylist
				if (boardIns.size() == i || boardIns.get(i).getY() - 260 != i * 80) {

					// add a new pin at that location
					boardIns.add(new INPin(i * 80));
					add(boardIns.get(boardIns.size() - 1));
					// add actionlistener there
					boardIns.get(boardIns.size() - 1).getButtonSwitch().addActionListener(this);

					break;

				}
			}
			
			update();

		}
		// if the button clicked is the new OUTPin button
		else if (event.getSource() == ribbon.getOutPinButton() && !wiringMode) {

			// search for suitable spots to place the out pins (no interference)
			for (int i = 0; i < 9; i++) {

				// if there is no interference or we have reached the end of the arraylist
				if (boardOuts.size() == i || boardOuts.get(i).getY() - 260 != i * 80) {

					// add a new pin at that location
					boardOuts.add(new OUTPin(i * 80));
					add(boardOuts.get(boardOuts.size() - 1));

					break;

				}
			}

			update();

		}
		// if the button is to test the circuit
		else if (event.getSource() == ribbon.getTestButton() &&  !wiringMode) {
			RunCircuit.traverseCircuit(this);
		}

		// if it is from one of the INPins
		for (INPin pin : boardIns)
			if (event.getSource() == pin.getButtonSwitch())
				// toggle the value of that INPin
				pin.toggle();
				

	}
	
	// empty event listeners
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}

	// redraw all the EComponents that are on the board
	public void update() {

		remove(ribbon);
		remove(grid);
		remove(currentWire);
		for (EComponent component : boardComponents) {
			remove(component);
			add(component);
		}
		add(currentWire);
		add(ribbon);
		add(grid);

		invalidate();
		repaint();
		validate();

	}
	
	// getters and setters for the defining components of the BuildingScreen
	public ArrayList<EComponent> getBoardComponents() {
		return boardComponents;
	}

	public void setBoardComponents(ArrayList<EComponent> boardComponents) {
		this.boardComponents = boardComponents;
	}

	public ArrayList<INPin> getBoardIns() {
		return boardIns;
	}

	public void setBoardIns(ArrayList<INPin> boardIns) {
		this.boardIns = boardIns;
	}

	public ArrayList<OUTPin> getBoardOuts() {
		return boardOuts;
	}

	public void setBoardOuts(ArrayList<OUTPin> boardOuts) {
		this.boardOuts = boardOuts;
	}

}