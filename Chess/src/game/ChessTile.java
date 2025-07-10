package game;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.SwingConstants;

//each tile extends JButton which allows the user to interact with each tile by clicking
public class ChessTile extends JButton {
	private int row;
	private int col;
	
	public ChessTile(int row, int col) {
		this.row = row;
		this.col = col;
		setUpTile();
	}
	
	private void setUpTile() {
		setPreferredSize(new Dimension(100,100)); //sets the size of each chess tile
		if ((row+col) % 2 == 0) {
			setBackground(Color.LIGHT_GRAY);
		}
		else {
			setBackground(Color.DARK_GRAY);
		}
		//ensures that the pieces that go on every tile before and during the game are centered
		setHorizontalAlignment(SwingConstants.CENTER);
	    setVerticalAlignment(SwingConstants.CENTER);
	    
	}
}
