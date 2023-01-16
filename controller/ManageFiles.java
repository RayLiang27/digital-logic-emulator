/**
 * Class:
 * 		ManageFiles
 * Description:
 * 		Used to read and save BuildingScreens from and to text files
 * Areas of Concern:
 * 		Due to time constraints, this entire class could not be completed
 */

package controller;

import view.*;
import model.*;

import java.util.ArrayList;

public class ManageFiles {
    
    // takes in a BuildingScreen and parses all the ArrayLists from it, then saves it into a file
    public static void saveToFile(BuildingScreen screen) {

        // create a JOptionPane that allows the user to enter the file name they want to save their circuit to

            // prompt the user if there is already a saved file under the same name, if they want to overwrite it

        // write each element in the INPins, OUTPins, and boardComponents ArrayList to a String

        // create a new File with the given name

        // write the Strings of elements to the new File

    }

    // reads a file and parses it for the various ArrayLists
    public static BuildingScreen readFromFile(String filename) {

        // create ArrayLists for the file
        ArrayList<EComponent> boardComponents = new ArrayList<>();
        ArrayList<INPin> boardIns = new ArrayList<>();
        ArrayList<OUTPin> boardOuts = new ArrayList<>();

        // return a version that is created from the list of components
        return new BuildingScreen(boardComponents, boardIns, boardOuts);

    }

}
