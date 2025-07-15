package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import attributes.PieceColor;

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
		//next two lines allow for the tiles to be colored on the board
		setOpaque(true);
		setBorderPainted(false);
		//ensures that the pieces that go on every tile before and during the game are centered
		setHorizontalAlignment(SwingConstants.CENTER);
	    setVerticalAlignment(SwingConstants.CENTER);
	    setFont(new Font("Serif", Font.BOLD, 36));
	}
	
	//method to show the pieces on the board
	public void setPiece(String pieceSymbol, Color pieceColor) {
		this.setText(pieceSymbol);
		this.setForeground(pieceColor);
	}
	
	public void clearPiece() {
		this.setText("");
	}
}
