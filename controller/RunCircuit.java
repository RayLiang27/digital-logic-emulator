/**
 * Class:
 * 		RunCircuit
 * Description:
 * 		Used to hold the algorithm that traverses the entire BuildingScreen (representing it as a directed graph)
 * Areas of Concern:
 * 		- To prevent infinite loops, this algorithm prevents logic gates from being visited more than once, restricting
 *      many of the possible logic circuits from being built
 */

package controller;

import model.*;
import model.gates.Gate;
import view.*;

import javax.swing.*;

import application.LogicCircuitBuilderApplication;

import java.awt.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class RunCircuit {
    
    // this method reads in a BuildingScreen and iteratively traverses it, executing and visually changing each gate along the way
    public static void traverseCircuit(BuildingScreen screen) {

        // Create a queue
        Queue<EComponent> toBeVisited = new LinkedList<>();
        
        // set all visited to false
        for(EComponent component : screen.getBoardComponents())
            component.setVisited(false);

        // if there are neither input pins nor output pins, let the user know, then leave without traversing
        if (screen.getBoardIns().size() == 0) {
            System.out.println("There are no inputs to start from");
            JOptionPane.showMessageDialog(LogicCircuitBuilderApplication.applicationScreen,"There are no inputs to start from.","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (screen.getBoardOuts().size() == 0) {
            System.out.println("There are no outputs to end at");
            JOptionPane.showMessageDialog(LogicCircuitBuilderApplication.applicationScreen,"There are no outputs to end at.","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        // for each INPin on the screens
        for (INPin pin : screen.getBoardIns()) {

            // get all the components that are mapped as output from the INPin
            for  (EComponent component : pin.getOutputMap().keySet()) {

                System.out.println("pin: " + pin.execute(null));

                // if the component is found inside the hashmaps of the queue
                if (!toBeVisited.contains(component))
                    toBeVisited.add(component);
                component.getInputs()[pin.getOutputMap().get(component).intValue()] = pin.execute(null);
                System.out.println("\t\t" + component.getInputs()[pin.getOutputMap().get(component).intValue()]);
                System.out.println("\t\t" + pin.getOutputMap().get(component).intValue());
                
            }
            
        }

        // continue while there is still an element in the queue
        while(!toBeVisited.isEmpty()) {

            EComponent currentlyVisiting = toBeVisited.poll();

            // to prevent infinite cycles, continue upon seeing a previously visited gate
            if (currentlyVisiting.isVisited())
                continue;

            // execute the logic of the logic gate
            boolean result = currentlyVisiting.execute(currentlyVisiting.getInputs());

            // If it's an outpin, don't add it back to the queue
            if (currentlyVisiting instanceof OUTPin)
                continue;
            
            // set the visited to false
            currentlyVisiting.setVisited(true);

            for  (EComponent receivingComponent : currentlyVisiting.getOutputMap().keySet()) {

                // if the component is found inside the queue
                if (!toBeVisited.contains(receivingComponent))
                    toBeVisited.add(receivingComponent);
                receivingComponent.getInputs()[currentlyVisiting.getOutputMap().get(receivingComponent).intValue()] = result;
                System.out.println("\t\t" + receivingComponent.getInputs()[currentlyVisiting.getOutputMap().get(receivingComponent).intValue()]);
                System.out.println("\t\t" + currentlyVisiting.getOutputMap().get(receivingComponent).intValue());
                
            }

            if (currentlyVisiting instanceof Gate) {

                Gate asGate = (Gate) currentlyVisiting;
                asGate.setOutPinColor(result);
                asGate.setInPinColor(asGate.getInputs());

            }

        }

        // color all the wires separately
        for (EComponent component : screen.getBoardComponents())
            if (component instanceof WireSection) {
                WireSection section = (WireSection) component;
                section.setBackground();
            }

    }

}
