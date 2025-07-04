package game;

import javax.swing.JButton;

//each tile extends JButton which allows the user to interact with each tile by clicking
public class ChessTile extends JButton {
	private int row;
	private int col;
	
	public ChessTile(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
}
