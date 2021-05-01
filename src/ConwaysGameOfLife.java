import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ConwaysGameOfLife extends JFrame implements ActionListener {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void controller() {
		createLifeMatrix();
		debug(cellState);
		gameOfLife();
		debug(neighborState);
		updateGrid();
		debug(cellState);
	}
	
	// These two variables store the dimensions of the matrix, and will be supplied by the user
	int gridHeight = 20;
	int gridWidth = 20;
	
	private int[][] cellState;
	private int[][] neighborState;
	
	//This method initializes the grid based on parameters provided by the user
	private void createLifeMatrix() {
		//2 matrices need to be created, one to store information about neighbors, and 1 to store the actual state of the cell
		cellState = new int[gridHeight][gridWidth];
		neighborState = new int[gridHeight][gridWidth];
		
		//Initialize the matrix
		for(int i = 0; i < gridHeight; i++) {
			for(int j = 0; j < gridWidth; j++) {
				cellState[i][j] = 0;
				neighborState[i][j] = 0;
			}
		}
		
		//Generate live cells
		randomGridInitialization();
	}
	
	//Each cell has a chance of being dead or alive
	private void randomGridInitialization() {
		double aliveChance = 0.5; //Determines the odds that a cell is alive
		//double random = Math.random();
		for(int i = 0; i < gridHeight; i++) {
			for(int j = 0; j < gridWidth; j++) {
				double random = Math.random();
				if(random < aliveChance) {
					cellState[i][j] = 1;
				}
			}
		}
	}
	
	private void updateGrid() {
		for(int i = 0; i < gridHeight; i++) {
			for(int j = 0; j < gridWidth; j++) {
				if(neighborState[i][j] == 3) {
					cellState[i][j] = 1;
				}
				else {
					cellState[i][j] = 0;
				}
			}
		}
	}
	
	/*
	 * Here is where the program gets tricky, we need to check for neighboring cells
	 * The algorithm is simple, check the eight surrounding cells for numbers.
	 * However, there are edge cases that must be accounted for. The 4 corners, and the 4 edges, as they do not have 8 neighbors
	 */
	
	//This method checks for surrounding cells
	private void gameOfLife() {
		int surroundCount = 0; //Stores the amount of alive cells that surround
		// We parse through each cell of the matrix
		for(int i = 0; i < gridHeight; i++) {
			for(int j = 0; j < gridWidth; j++) {
				surroundCount = 0; //Reset the surround count for each new cell
				//First we check for edges, as they only have 3 neighbors
				
				//The top left edge
				if(i == 0 && j == 0) {
					//To the right
					if(cellState[i][j+1] == 1) {
						surroundCount++;
					}
					//To the bottom
					if(cellState[i+1][j] == 1) {
						surroundCount++;
					}
					//To the bottom-right
					if(cellState[i+1][j+1] == 1) {
						surroundCount++;
					}
					//Once all checks are complete, time to update the value
					neighborState[i][j] = surroundCount;
				}
				
				//TODO Top-Right
				else if(i == 0 && j == gridWidth-1) {
					//D
					if(cellState[i+1][j] == 1) {
						surroundCount++;
					}
					//DL
					if(cellState[i+1][j-1] == 1) {
						surroundCount++;
					}
					//L
					if(cellState[i][j-1] == 1) {
						surroundCount++;
					}
					neighborState[i][j] = surroundCount;
				}
				//TODO Bottom-Left
				else if(i == gridHeight-1 && j == 0) {
					//U
					if(cellState[i-1][j] == 1) {
						surroundCount++;
					}
					//UR
					if(cellState[i-1][j+1] == 1) {
						surroundCount++;
					}
					//R
					if(cellState[i][j+1] == 1) {
						surroundCount++;
					}
					neighborState[i][j] = surroundCount;
				}
				//TODO Bottom-Right
				else if(i == gridHeight-1 && j == gridWidth-1) {
					//U
					if(cellState[i-1][j] == 1) {
						surroundCount++;
					}
					//UL
					if(cellState[i-1][j-1] == 1) {
						surroundCount++;
					}
					//L
					if(cellState[i][j-1] == 1) {
						surroundCount++;
					}
					neighborState[i][j] = surroundCount;
				}
				//TODO Top Edge
				else if(i == 0) {
					//D
					if(cellState[i+1][j] == 1) {
						surroundCount++;
					}
					//L
					if(cellState[i][j-1] == 1) {
						surroundCount++;
					}
					//R
					if(cellState[i][j+1] == 1) {
						surroundCount++;
					}
					//DL
					if(cellState[i+1][j-1] == 1) {
						surroundCount++;
					}
					//DR
					if(cellState[i+1][j+1] == 1) {
						surroundCount++;
					}
					neighborState[i][j] = surroundCount;
				}
				//TODO Bottom Edge
				else if(i == gridHeight-1) {
					//U
					if(cellState[i-1][j] == 1) {
						surroundCount++;
					}
					//L
					//Left
					if(cellState[i][j-1] == 1) {
						surroundCount++;
					}
					//R
					if(cellState[i][j+1] == 1) {
						surroundCount++;
					}
					//UL
					if(cellState[i-1][j-1] == 1) {
						surroundCount++;
					}
					//UR
					if(cellState[i-1][j+1] == 1) {
						surroundCount++;
					}
					neighborState[i][j] = surroundCount;
				}
				//TODO Right Edge
				else if(j == gridWidth-1) {
					//L
					if(cellState[i][j-1] == 1) {
						surroundCount++;
					}
					//D
					if(cellState[i+1][j] == 1) {
						surroundCount++;
					}
					//U
					if(cellState[i-1][j] == 1) {
						surroundCount++;
					}
					//DL
					if(cellState[i+1][j-1] == 1) {
						surroundCount++;
					}
					//UL
					if(cellState[i-1][j-1] == 1) {
						surroundCount++;
					}
					neighborState[i][j] = surroundCount;
				}
				//TODO Left Edge
				else if(j == 0) {
					//R
					if(cellState[i][j+1] == 1) {
						surroundCount++;
					}
					//D
					if(cellState[i+1][j] == 1) {
						surroundCount++;
					}
					//U
					if(cellState[i-1][j] == 1) {
						surroundCount++;
					}
					//DR
					if(cellState[i+1][j+1] == 1) {
						surroundCount++;
					}
					//UR
					if(cellState[i-1][j+1] == 1) {
						surroundCount++;
					}
					neighborState[i][j] = surroundCount;
				}
				
				//The inner cells
				else {
					//Up
					if(cellState[i-1][j] == 1) {
						surroundCount++;
					}
					//Up-Right
					if(cellState[i-1][j+1] == 1) {
						surroundCount++;
					}
					//Right
					if(cellState[i][j+1] == 1) {
						surroundCount++;
					}
					//Down-Right
					if(cellState[i+1][j+1] == 1) {
						surroundCount++;
					}
					//Down
					if(cellState[i+1][j] == 1) {
						surroundCount++;
					}
					//Down-Left
					if(cellState[i+1][j-1] == 1) {
						surroundCount++;
					}
					//Left
					if(cellState[i][j-1] == 1) {
						surroundCount++;
					}
					//Up-Left
					if(cellState[i-1][j-1] == 1) {
						surroundCount++;
					}
					neighborState[i][j] = surroundCount;
				}
			}
		}
	}
	
	private void printMatrix(int grid[][]) {
		for(int i = 0; i < gridHeight; i++) {
			for(int j = 0; j < gridWidth; j++) {
				System.out.print(grid[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	private void debug(int grid[][]) {
		System.out.println();
		System.out.println("Printing grid");
		System.out.println();
		printMatrix(grid);
	}
	
	public static void main(String[] args) {
		ConwaysGameOfLife overlord = new ConwaysGameOfLife();
		overlord.controller();
	}
	
	private void sampleParse() {
		for(int i = 0; i < gridHeight; i++) {
			for(int j = 0; j < gridWidth; j++) {
				
			}
		}
	}

}
