/**
 * Title: LOGIC CIRCUIT BUILDER APPLICATION
 * Description:
 * 		This application gives the user the ability to build and simulate a digital logic circuit by dragging logic gates
 *      onto the circuit board grid area, connecting them with wires, changing the input pins, and running a simulation of
 *      the circuit to see what it would output.
 * HOW TO USE:
 *      - Drag and drop logic gates from the ribbon buttons up top to the grid area
 *      - Drag and drop logic gates that are on the actual board to reposition them
 *      - Press on the WIRING MODE button to wire components together
 *          **NOTE: Dragging components and making new I/O Pins is not allowed while wiring mode is toggled; this is intended**
 *      - Press on a click point to start a wire, click anywhere on the grid to create a perpendicular bend in the wire
 *      - Press on any of the click points present to end the wire
 *      - Press on the TEST button to make the program simulate how the circuit would run with the given signals of 
 *      the input pin
 * 
 * Features:
 * 		- Drag-and-drop logic gates for a very interactive logic circuit builder
 *      - User selects where to place wires and clicks on places to make perpendicular bends
 *      - User can choose to add up to 9 input and output pins for added complexity to circuits
 *      - User can, upon finishing their 
 * Major skills:
 * 		- Objected Oriented Programming:
 *          - Various classes are used in a hierarchal fashion to represent various gates and wires, all of which have common
 *          aspects and can inherit from a grandfather EComponent class
 *          - All the EComponent objects have an executable method to represent running a logical operation, this is present
 *          as an interface
 *      - Data Structures used:
 *          - ArrayList: Used to hold dynamically sized array of all the board elements
 *          - HashMap: Used to map out which components 
 * 		- Algorithm used: Directed Graph Traversal
 *          - A circuit can be represented as a directed graph, where wires are edges and the components are nodes. As such, 
 *          a directed graph traversal algorithm can be used to simulate running the circuit 
 * 		- GUI
 * Areas of concern:
 * 		- See individual classes for areas of concern
 */


package application;

import view.*;

public class LogicCircuitBuilderApplication {
    
    public static ApplicationScreen applicationScreen;

    public static void main(String[] args) {
        
        applicationScreen = new ApplicationScreen();

    }

}
