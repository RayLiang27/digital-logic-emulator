/**
 * Class:
 * 		Ribbon
 * Description:
 * 		Represents a section of the build screen where users can perform various options on their circuits
 * Areas of Concern:
 * 		- Due to the lack of time, all the buttons had to be squeezed into the same tab
 */

package view;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.Font;

import model.*;

public class Ribbon extends JPanel {
    
    // fields

    // represents the possible tabs the user can switch to on the ribbon (top buttons of the screen)
    //      due to time constraints, these panels were not able to be implemented, all buttons are present in the same area
    private JPanel componentsTab = new JPanel();
    private JPanel runTab = new JPanel();
    private JPanel windowTab = new JPanel();

    private final Border selectionBorder = BorderFactory.createLineBorder(ColorScheme.selectionColor, 5);
    
    private JButton[] gateButtons = {
        new JButton("AND"),
        new JButton("OR"),
        new JButton("XOR"),
        new JButton("NOT"),
        new JButton("NAND"),
        new JButton("NOR"),
        new JButton("XNOR")
    };
    
    // various menu buttons
    private JButton wireButton = new JButton("<html>WIRING<br>MODE");
    private JButton inPinButton = new JButton("<html>NEW<br>IN-PIN");
    private JButton outPinButton = new JButton("<html>NEW<br>OUT-PIN");
    private JButton testButton = new JButton("TEST"); 
    
    // constructor
    public Ribbon() {
        super();

        // setup the JPanel
        setBackground(ColorScheme.ribbonColor);
        setBounds(0, 0, 1920, 180);
        setLayout(null);
        
        // setup the various buttons
        wireButton.setBounds(20, 20, 110, 110);
        wireButton.setHorizontalAlignment(SwingConstants.CENTER);
        wireButton.setFont(new Font("Courier New", Font.BOLD,22));
        add(wireButton);

        inPinButton.setBounds(1240, 20, 110, 110);
        inPinButton.setFont(new Font("Courier New", Font.BOLD,22));
        inPinButton.setHorizontalAlignment(SwingConstants.CENTER);
        add(inPinButton);

        outPinButton.setBounds(1370, 20, 110, 110);
        outPinButton.setFont(new Font("Courier New", Font.BOLD,22));
        outPinButton.setHorizontalAlignment(SwingConstants.CENTER);
        add(outPinButton);

        testButton.setBounds(1500, 20, 110, 110);
        testButton.setFont(new Font("Courier New", Font.BOLD,22));
        testButton.setHorizontalAlignment(SwingConstants.CENTER);
        add(testButton);

        for (int i = 0; i < gateButtons.length; i++) {
            
            gateButtons[i].setBounds(200 + i * 130, 20, 110, 110);
            gateButtons[i].setFont(new Font("Courier New", Font.BOLD,22));
            add(gateButtons[i]);

        }

    }

    // generates listeners for each button
    public void generateListeners(BuildingScreen screen) {

        // add action listeners for each of the buttons
        wireButton.addActionListener(screen);
        inPinButton.addActionListener(screen);
        outPinButton.addActionListener(screen);
        testButton.addActionListener(screen);

        for (JButton button : gateButtons) {
            button.addMouseListener(screen);
            button.addMouseMotionListener(screen);
        }

    }

    // find a gate button that matches one from the gateButtons array
    public int findGateButtonIndex(JButton target) {

        for (int i = 0; i < gateButtons.length; i++)
            if (gateButtons[i] == target)
                return i;

        return -1;

    }

    // toggles whether or not to have a border around the wire button (whether or not its mode is active)
    public void toggleWireButton() {

        if (wireButton.getBorder() == selectionBorder)
            wireButton.setBorder(null);
        else
            wireButton.setBorder(selectionBorder);

    }

    // getters and setters
    public JButton[] getGateButtons() {
        return gateButtons;
    }

    public JButton getWireButton() {
        return wireButton;
    }

    public JButton getInPinButton() {
        return inPinButton;
    }

    public JButton getOutPinButton() {
        return outPinButton;
    }

    public JButton getTestButton() {
        return testButton;
    }

}