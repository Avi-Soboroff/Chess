package gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import game.ChessTile;

//class the holds the game visuals(What pops up on the screen)
public class GameGUI extends JFrame {
	private final ChessTile[][] chessSquares = new ChessTile[8][8]; //gives us a full chess board that focuses on each individual square
	private final ChessGame game = new ChessGame(); //instance of the game that will be shown on the screen
	
	public GameGUI() {
		setTitle("My Chess Game"); //gives the game a title
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //immediately exits the game once closed out
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(GameGUI::new);
	}
}
