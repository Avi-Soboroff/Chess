package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import attributes.PieceColor;
import game.ChessBoard;
import game.ChessTile;
import pieces.Bishop;
import pieces.ChessPiece;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Queen;
import pieces.Rook;

public class GameGUI extends JFrame {
	private final ChessTile[][] chessSquares = new ChessTile[8][8]; //gives us a full chess board that focuses on each individual square
	private final ChessGame game = new ChessGame(); //instance of the game that will be shown on the screen
	private final Map<Class<? extends ChessPiece>, String> gamePieces = new HashMap<>(); //Map to contain the unicode/visual representation of the pieces
	
	public GameGUI() {
		setTitle("My Chess Game"); //gives the game a title
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //immediately exits the game once closed out
		setLayout(new GridLayout(8,8));
		putPiecesOnBoard();
		setUpBoard(); 
		pack(); //makes sure chess board fits in the window
		setVisible(true);
	}
	
	//helper method to put the piece on the board
	private void putPiecesOnBoard() {
		gamePieces.put(Pawn.class, "♟");
		gamePieces.put(Rook.class, "♜");
		gamePieces.put(Knight.class, "♞");
		gamePieces.put(Bishop.class, "♝");
		gamePieces.put(Queen.class, "♛");
		gamePieces.put(King.class, "♚");
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
						tileSelection(boardRow,boardCol);
					}
				});
				add(newTile);
				chessSquares[row][col] = newTile; //adds tile to tile board
			}
		}
		updateScreen();
	}
	
	private void updateScreen() {
		ChessBoard board = game.getBoard(); //returns our chess board
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				ChessPiece piece = board.getChessPiece(row, col);
				//now check if there is a piece on that spot
				if (piece != null) {
					//if there is a piece there, put it on the board
					String pieceSymbol = gamePieces.get(piece.getClass());
					Color pieceColor = (piece.getColor() == PieceColor.WHITE ? Color.WHITE : Color.BLACK);
					//visually place the piece on board 
					chessSquares[row][col].setPiece(pieceSymbol, pieceColor);
				}
				else {
					chessSquares[row][col].clearPiece();
				}
			}
		}
	}
	
	private void tileSelection(int row, int col) {
		checkGameState();
	}
	
	private void checkGameState() {
		PieceColor playerColor = game.getTurnsColor();
		boolean inCheck = game.kingInCheck(playerColor);
		if (inCheck) {
			JOptionPane.showMessageDialog(this, playerColor + " is in check!");
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(GameGUI::new);
	}
}