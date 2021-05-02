import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ConwaysGameOfLife extends JFrame implements ActionListener, ChangeListener {
	
	GameBoard gameBoard = new GameBoard();
	
	public int refreshCount;
	
	private void controller() {
		createLifeMatrix();
		debug(cellState);
		//gameOfLife();
		//debug(neighborState);
		//updateGrid();
		//debug(cellState);
	}
	
	// These two variables store the dimensions of the matrix, and will be supplied by the user
	public int gridHeight = 20;
	public int gridWidth = 20;
	
	public int[][] cellState; //Stores information about the cells state
	public int[][] neighborState; //Stores information about the number of numbers per cell
	
	public int refreshRate = 1000; //Default update interval
	
	protected Timer timer;
	
	protected class TimerCallback implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			//System.out.println("timer fired!");
			//repaint();
			
			//Here each iteration updates and repaints the gameBoard
			gameOfLife();
			gameBoard.setNeighborState(neighborState);
			//gameBoard.repaint();
			updateGrid();
			gameBoard.setCellState(cellState);
			gameBoard.repaint();
			cycleCount++;
			cycleCountLabel.setText("Number of Cycles: " + cycleCount);
			
		}
		
	}
	
	//Getters
	public int getGridHeight() {
		return this.gridHeight;
	}

	public int getGridWidth() {
		return this.gridWidth;
	}

	public int[][] getCellState() {
		return this.cellState;
	}

	public int[][] getNeighborState() {
		return this.neighborState;
	}

	//This method initializes the grid based on parameters provided by the user
	private void createLifeMatrix() {
		refreshCount = 0;
		
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
	
	double aliveChance = 0.5; //Determines the odds that a cell is alive
	
	//Each cell has a chance of being dead or alive
	private void randomGridInitialization() {
		
		//double random = Math.random();
		for(int i = 0; i < gridHeight; i++) {
			for(int j = 0; j < gridWidth; j++) {
				double random = Math.random();
				if(random < aliveChance) {
					cellState[i][j] = 1;
				}
				if(random >= aliveChance) {
					cellState[i][j] = 0;
				}
			}
		}
		
		//Calls a setter
		gameBoard.setCellState(this.cellState);
		//gameBoard.setNeighborState(neighborState);
		gameBoard.repaint();
		
	}
	
	//Updates the grid, i.e. 3 neighbors = alive, not 3 = dead
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
		
		refreshCount++;

		gameBoard.setCellState(this.cellState);
		//gameBoard.setNeighborState(neighborState);
		gameBoard.repaint();
		
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
		//Calls setter
		gameBoard.setNeighborState(this.neighborState);
	}
	
	//Used for debug prints the matrix
	private void printMatrix(int grid[][]) {
		for(int i = 0; i < gridHeight; i++) {
			for(int j = 0; j < gridWidth; j++) {
				System.out.print(grid[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	//Debug - currently used to print the grid
	private void debug(int grid[][]) {
		System.out.println();
		System.out.println("Printing grid");
		System.out.println();
		printMatrix(grid);
	}
	
	//default values for the slider
	public int minimumRefresh;
	public int defaultRefresh;
	public int maximumRefresh;
	

	JLabel cycleCountLabel = new JLabel();
	int cycleCount = 0;
	
	public ConwaysGameOfLife() {
		JFrame frame = new JFrame("Conway's Game of Life");
		frame.setLayout(new BorderLayout());
		
		JPanel options = new JPanel();
		
		//options.setLayout(new BoxLayout(options, BoxLayout.X_AXIS));
		//gameBoard.setLayout(new FlowLayout());
		options.setLayout(new GridLayout(3, 2));
		
		//sets values for the slider for frequency
		minimumRefresh = 500;
		defaultRefresh = 1000;
		maximumRefresh = 5000;
		
		JLabel frequencyLabel = new JLabel("Update Rate");
		JLabel densityLabel = new JLabel("Amount of Alive Cells");
		JLabel placeHolder1 = new JLabel();
		cycleCountLabel.setText("Number of Cycles: " + 0);
		
		JLabel gameTitle = new JLabel("Conway's Game of Life");
		JPanel title = new JPanel();
		
		title.setLayout(new GridLayout(1,2));
		title.add(gameTitle);
		title.add(cycleCountLabel);
		
		frequencyLabel.setHorizontalAlignment(JLabel.CENTER);
		densityLabel.setHorizontalAlignment(JLabel.CENTER);
		cycleCountLabel.setHorizontalAlignment(JLabel.CENTER);
		
		JSlider frequency = new JSlider(JSlider.HORIZONTAL, minimumRefresh, maximumRefresh, defaultRefresh);
		frequency.setName("frequency");
		frequency.addChangeListener(this);
		
		JSlider density = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
		density.setName("density");
		options.add(density);
		density.addChangeListener(this);
		
		JButton playPause = new JButton("Play/Pause");
		playPause.setName("Play/Pause");
		playPause.addActionListener(this);
		
		JButton reset = new JButton("Reset");
		reset.setName("Reset");
		reset.addActionListener(this);
		
		options.add(reset);
		options.add(playPause);
		
		//Layout fills each row before moving onto the next
//		options.add(frequencyLabel);
//		options.add(densityLabel);
//		options.add(playPause);
//		options.add(frequency);
//		options.add(density);
//		options.add(reset);
//		options.add(placeHolder1);
//		options.add(cycleCountLabel);
		
		options.add(frequencyLabel);
		options.add(densityLabel);
		options.add(frequency);
		options.add(density);
		options.add(playPause);
		options.add(reset);
		
		
		controller();
	
		frame.add(title, BorderLayout.NORTH);
		frame.add(gameBoard, BorderLayout.CENTER);
		frame.add(options, BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.pack();
        frame.setVisible(true);
        
        timer = new Timer(refreshRate, new TimerCallback());
		timer.start();
	}
	
	public static void main(String[] args) {
		new ConwaysGameOfLife();
	}
	
	//sample code used: parses the matrix
	private void sampleParse() {
		for(int i = 0; i < gridHeight; i++) {
			for(int j = 0; j < gridWidth; j++) {
				
			}
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();
		if(!source.getValueIsAdjusting()) {
			System.out.println("Adjusting");
			String name = source.getName();
			if(name.equals("frequency")) {
				//change frequency and reset timer
				refreshRate = (int)source.getValue();
				timer.stop();
				timer = new Timer(refreshRate, new TimerCallback());
				timer.start();
			}
			if(name.equals("density")) {
				//change alive-chance for next run
				aliveChance = (double)source.getValue()/100;
			}
		}
	}
	
	int resumeCount = 0;

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton)e.getSource();
		System.out.println("Input read");
		String name = source.getName();
		
		if(name.equals("Play/Pause")) {
			System.out.println("Play/Pause");
			//pause or resume the simulation based on a counter
			if (resumeCount % 2 == 0) {
				//pause
				timer.stop();
			}
			if (resumeCount % 2 == 1) {
				//resume
				System.out.println(resumeCount);
				timer.stop();
				timer = new Timer(refreshRate, new TimerCallback());
				timer.start();
			}
			resumeCount++;
		}
		
		if(name.equals("Reset")) {
			//reset the simulation
			randomGridInitialization();
			timer.stop();
			timer = new Timer(refreshRate, new TimerCallback());
			timer.start();
			//change cycle count to 0
			cycleCount = 0;
			resumeCount = 0;
			cycleCountLabel.setText("Number of Cycles: " + 0);
		}
	}
}
