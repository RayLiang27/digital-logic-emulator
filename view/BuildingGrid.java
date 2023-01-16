/**
 * Class:
 * 		BuildingGrid
 * Description:
 * 		Represents the visual grid that is present on the BuildingScreen
 * Areas of Concern:
 * 		None
 */

package view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

import model.*;

public class BuildingGrid extends JPanel {

    // fields
    private final Border gridBorder = BorderFactory.createLineBorder(ColorScheme.lightGrey, 1);

    public static final int gridSize = 20;

    private JLabel[][] grid;

    // constructor
	public BuildingGrid() {
		super();

        // setup the JPanel
		setBounds((1920-1640)/2, 260, 1640, 720);
        setLayout(new GridLayout(this.getHeight()/gridSize, this.getWidth()/gridSize));
		
        grid = new JLabel[this.getWidth()/gridSize][this.getHeight()/gridSize];

		setBackground(ColorScheme.backgroundColor);
		
        // add each cell to the grid and give it a border
        for (JLabel[] row : grid)
            for (JLabel cell : row) {
                cell = new JLabel();
                cell.setBorder(gridBorder);
                add(cell);
            }

	}
	
}