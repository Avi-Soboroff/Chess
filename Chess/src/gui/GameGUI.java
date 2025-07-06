package gui;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import game.ChessBoard;
import game.ChessTile;
import pieces.ChessPiece;import pieces.Pawn;

public class GameGUI extends JFrame {
	private final ChessTile[][] chessSquares = new ChessTile[8][8]; //gives us a full chess board that focuses on each individual square
	private final ChessGame game = new ChessGame(); //instance of the game that will be shown on the screen
	private final Map<Class<? extends ChessPiece>, String> gamePieces = new HashMap<>(); //Map to contain the unicode/visual representation of the pieces
	
	public GameGUI() {
		setTitle("My Chess Game"); //gives the game a title
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //immediately exits the game once closed out
		setLayout(new GridLayout(8,8));
		setUpBoard(); 
		pack(); //makes sure chess board fits in the window
		setVisible(true);
	}
	
	//sets up the full 8x8 chess board on the screen
	private void setUpBoard() {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				final int boardRow = row;
				final int boardCol = col;
				ChessTile newTile = new ChessTile(row, col);
				newTile.addMouseListener(new MouseAdapter() { //allows user to click on each chess square
					@Override
					public void mouseClicked(MouseEvent e) {
						
					}
				});
				add(newTile);
				chessSquares[row][col] = newTile; //adds tile to tile board
			}
		}
		updateScreen();
	}
	
	private void updateScreen() {
		ChessBoard gameBoard = game.getBoard();
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				//check every tile and see if there is a piece there
				ChessTile tile = chessSquares[row][col];
	            
			}
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(GameGUI::new);
	}
}