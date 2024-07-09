package tileMatchingGame;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TileMatchingGame extends JFrame implements ActionListener {
	// Project 3 By Muhammad Yassa - Tile Matching Game
	
	// initializes variables.
	int rows;
	int cols;
	int numOfTypes;
	Color nextColor;
	ArrayList <Color> colorTypes;
	Color[][] colorGrid;
	
	JFrame nextTile = new JFrame();
	JFrame gameGrid = new JFrame();
	JButton[][] buttons;
	
	
	// default constructor for TileMatchingGame.
	public TileMatchingGame() {
		// use of JOptionPane to take input and display a message. also uses Integer.parseInt to parse input as an integer.
		rows = Integer.parseInt(JOptionPane.showInputDialog(null, "Please enter the number of Rows:"));
		cols = Integer.parseInt(JOptionPane.showInputDialog(null, "Please enter the number of Columns:"));
		numOfTypes = Integer.parseInt(JOptionPane.showInputDialog(null, "Please enter the number of Types:"));
		colorGrid = new Color[rows][cols];
		// calls Colors method to let the user decide which colors to use.
		Colors();
		// calls GameGrid and NextTile methods for game windows.
		GameGrid();
		NextTile();
	}
	
	// Colors method displays random colors equal to the number of types and confirms with user if they are okay.
	public void Colors() {
		colorTypes = new ArrayList <Color> (Collections.nCopies(numOfTypes, null));
		int confirm = 0;
		JFrame display = new JFrame();
		JPanel panel = new JPanel (new GridLayout (1, numOfTypes));
		display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		display.setTitle("Colors");
		display.setVisible(true);
		display.setSize(numOfTypes * 100,100);
		display.add(panel);
		// continues generating random Colors and keeps storing in colorTypes until user is okay with the set of colors.
		do {
			panel.removeAll();
			for (int i=0; i<numOfTypes; i++) {
				JPanel gridBox = new JPanel();
				Color color = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
				gridBox.setBackground (color);
				colorTypes.set(i, color);
				panel.add(gridBox);
			}
			panel.revalidate();
			panel.repaint();
			confirm = JOptionPane.showConfirmDialog(null, "Are these colors okay?", "Confirmation", JOptionPane.YES_NO_OPTION);
		} while (confirm != JOptionPane.YES_OPTION);
		display.dispose();
	}
	
	// GameGrid displays the game and sets it up using the 2d array of buttons.
	public void GameGrid() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(rows,cols));
		buttons = new JButton [rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				buttons[i][j] = new JButton();
				buttons[i][j].addActionListener(this);
				panel.add(buttons[i][j]);
			}
		}
		gameGrid.setTitle("Tile Matching Game");
		gameGrid.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameGrid.setVisible(true);
		gameGrid.setSize(rows*100, cols*100);
		gameGrid.add(panel);
	}
	
	//NextTile method generates the color of the next tile.
	public void NextTile() {
		// first clears the JFrame.
		// sets JFrame "nextTile" visible, its size, title and the default close operation.
		nextTile.getContentPane().removeAll();
		nextTile.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		nextTile.setVisible(true);
		nextTile.setSize(250,250);
		nextTile.setTitle("Next Color");
		// creates a new JPanel and a new Random object and determines the next Color using the colorTypes array.
		JPanel color = new JPanel();
		Random rand = new Random();
		nextColor = colorTypes.get(rand.nextInt(numOfTypes));
		// sets the background of the panel to this color and then adds panel to the nextTile JFrame.
		color.setBackground(nextColor);
		nextTile.add(color);
		nextTile.revalidate();
		nextTile.repaint();
	}

	//check function for a set of three or more consecutive colors horizontally or vertically
	//replaces consecutive colors with 'null' and returns true if any set of three or more is found, else returns false.
	public boolean setOfThree() {
		for (int i=0; i<colorGrid.length; i++) {
			for (int j=0; j<colorGrid[i].length; j++) {
				// Initializes count to track consecutive occurrences of currentColor in the same row.
				// Iterates through consecutive cells to count occurrences of currentColor.
				Color currentColor = colorGrid[i][j];
				int count = 1;
				if (currentColor != null) {
					while (j+ count < colorGrid[i].length && colorGrid[i][j+count] != null && colorGrid[i][j+count].equals(currentColor)){
						count++;
					}
					if (count >= 3) {
						JOptionPane.showMessageDialog(null, "A Set was made!!!", "Set", JOptionPane.PLAIN_MESSAGE);
						for (int x = j; x < j + count; x++) {
							buttons [i][x].setBackground(null);
							colorGrid [i][x] = null;
						}
						return true;		
					}
				}
				// Adjusts the column index to skip already processed consecutive tiles 
				// and continue the iteration from the next non-consecutive tile.
				j = j + count -1;
			}
		}
		for (int i=0; i < colorGrid[0].length; i++) {
			for (int j=0; j< colorGrid.length; j++) {
				// Initializes count to track consecutive occurrences of currentColor in the same column.
				// Iterates through consecutive cells to count occurrences of currentColor.
				Color currentColor = colorGrid[j][i];
				int count = 1;
				if (currentColor != null) {
					while (j + count < colorGrid.length && colorGrid[j+count][i] != null && colorGrid[j + count][i].equals(currentColor)){
						count++;
					}
					if (count >= 3) {
						JOptionPane.showMessageDialog(null, "A Set was made!!!", "Set", JOptionPane.PLAIN_MESSAGE);
						for (int x=j; x< j + count; x++) {
							buttons [x][i].setBackground(null);
							colorGrid [x][i] = null;
						}
						return true;
					}
				}
				// Adjusts the column index to skip already processed consecutive tiles 
				// and continue the iteration from the next non-consecutive tile.
				j = j + count -1;
			}
		}
		return false;
	}
	
	//Fills gaps by shifting all tiles downwards.
	public void dropSetOfTiles() {
		for(int i = 0; i < colorGrid.length; i++) {
			for(int j = 0; j < colorGrid[i].length; j++) {
				if(colorGrid[i][j] == null) {
					for(int x = i; x > 0; x--) {
						colorGrid[x][j] = colorGrid[x - 1][j];
						buttons[x][j].setBackground(colorGrid[x][j]);
					}
					colorGrid[0][j] = null;
					buttons[0][j].setBackground(null);
				}
			}
		}
	}
	
	// Drops a tiles into the specified column.
	public void addTile(int column) {
		// finds the first empty cell from the bottom of the specified column and places Color there.
		for (int i=colorGrid.length - 1; i>=0; i--) {
			if (colorGrid[i][column] == null) {
				colorGrid[i][column] = nextColor;
				buttons[i][column].setBackground(nextColor);
				NextTile();
				return;
			}
		}
		// If the column the user chose is filled and there are no null gaps. calls endGame method.
		endGame();
	}
	
	// endGame method displays an end of game message and disposes of all JFrames and exits the program.
	public void endGame() {
		JOptionPane.showMessageDialog(null, "COLUMN FILLED! The Game Has Ended", "Game End", JOptionPane.PLAIN_MESSAGE);
		gameGrid.dispose();
		nextTile.dispose();
		System.exit(0);
	}
	
	// actionPerformed method is implemented from ActionListener interface.
	@Override
	public void actionPerformed(ActionEvent e) {
		// Stores the button clicked and compares it to the buttons array to locate which column was clicked.
		JButton buttonClicked = (JButton) e.getSource();
		int columnToAdd=0;
		for (int i=0; i<rows; i++) {
			for (int j=0; j<cols; j++) {
				if (buttonClicked == buttons[i][j]) {
					columnToAdd = j;
				}
			}
		}
		//call addTile method.
		addTile(columnToAdd);
		
		//while loop runs while a setOfThree is present and handles chain reactions.
		while (setOfThree()) {
			dropSetOfTiles();
		}
	}
	
	//main method.
	public static void main (String [] args) {
		// creates new TileMatchingGame object.
		new TileMatchingGame();
	}
}
