/**
 * Class:
 * 		ApplicationScreen
 * Description:
 * 		Represents the Frame that will contain all the possible screens the user can navigate to
 * Areas of Concern:
 * 		The planned menu screen is incomplete
 *      As a result, there is only one screen present to the user that cannot be changed
 */

package view;

import javax.swing.*;

import controller.ManageFiles;

public class ApplicationScreen extends JFrame {
    
    // fields for the screens
    private BuildingScreen buildingScreen;
    private MenuScreen menuScreen = new MenuScreen();

    // constructor
    public ApplicationScreen() {
        super("Logic Circuit Builder");

        // setup the JFrame
		setSize(1920, 1080);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // use the menu screen to decide which file the building screen is reading in any information
        //      due to time constraints, the menu screen as well as file-saving and reading had to be removed 
        String decidedFileName = menuScreen.decideBuildAction();
        if (decidedFileName == null)
            buildingScreen = new BuildingScreen();
        else
            buildingScreen = ManageFiles.readFromFile(decidedFileName);

        // set the content pane to be menuScreen
        //      originally going to start at the menu screen, due to time constraints, had to start directly at buildscreen
        setContentPane(buildingScreen);

        setVisible(true);
    }

    // This method is in charge of changing the screens on the frame
    //      due to there only being one screen, this method is useless
    public void changeScreen() {



    }

}