import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameBoard extends JPanel{
	
	public int cellHeight = 20;
	public int cellWidth = 20 ;
	public int gridHeight = 20;
	public int gridWidth = 20;
	
	public int[][] cellState = new int[gridHeight][gridWidth];
	public int[][] neighborState = new int[gridHeight][gridWidth];
	
	protected void initializeBoardParameters() {
		ConwaysGameOfLife overlord = new ConwaysGameOfLife();
		
		gridHeight = overlord.getGridHeight();
		gridWidth = overlord.getGridWidth();
		
		cellState = new int[gridHeight][gridWidth];
		neighborState = new int[gridHeight][gridWidth];
		
		cellState = overlord.getCellState();
		neighborState = overlord.getNeighborState();
		
		cellHeight = getHeight()/gridHeight;
		cellWidth = getWidth()/gridWidth;
		
	}

	@Override
	public void paintComponent(Graphics g) {
		System.out.println("DEBUG: Attempting to Paint");
		
		super.paintComponent(g);
		
		cellHeight = getHeight()/gridHeight;
		cellWidth = getWidth()/gridWidth;
		
		//Resize attempt:
		
		//cellState = overlord.getCellState();
		//neighborState = overlord.getNeighborState();
		
		
		//initializeBoardParameters();
		//g.fillRect(100, 100, 200, 200);
		
		for(int i = 0; i < gridHeight; i++) {
			for(int j = 0; j < gridWidth; j++) {
				if(cellState[i][j] == 1) {
					g.setColor(Color.GREEN);
					g.fillRect(cellWidth*j, cellHeight*i, cellWidth, cellHeight);
				}
				if(cellState[i][j] == 0) {
					g.setColor(Color.RED);
					g.fillRect(cellWidth*j, cellHeight*i, cellWidth, cellHeight);
				}
//				if(neighborState[i][j] != 3 && cellState[i][j] == 1) {
//					g.setColor(Color.RED);
//					g.fillRect(cellWidth*j, cellHeight*i, cellWidth, cellHeight);
//				}
//				if(neighborState[i][j] == 3 && cellState[i][j] == 0) {
//					g.setColor(Color.YELLOW);
//					g.fillRect(cellWidth*j, cellHeight*i, cellWidth, cellHeight);
//				}
				g.setColor(Color.BLACK);
				g.drawRect(cellWidth*j, cellHeight*i, cellWidth, cellHeight);
			}
		}
	}
	
//	public static void main(String[] args) {
//		
//		GameBoard gameBoard = new GameBoard();
//		gameBoard.initializeBoardParameters();
//		
//		JFrame frame = new JFrame();
//		frame.add(new GameBoard());
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setLocationByPlatform(true);
//        frame.pack();
//        frame.setVisible(true);
//	}
	
	public void setGridHeight(int gridHeight) {
		this.gridHeight = gridHeight;
	}

	public void setGridWidth(int gridWidth) {
		this.gridWidth = gridWidth;
	}

	public void setCellState(int[][] cellState) {
		this.cellState = cellState;
	}

	public void setNeighborState(int[][] neighborState) {
		this.neighborState = neighborState;
	}

	public Dimension getPreferredSize() {
        return new Dimension(400, 400);
    }

}
