package game;

import attributes.PieceColor;
import attributes.Position;
import pieces.Bishop;
import pieces.ChessPiece;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Queen;
import pieces.Rook;

public class ChessBoard {
	private ChessPiece[][] gameBoard;
	
	public ChessBoard() {
		gameBoard = new ChessPiece[8][8];
		setUpPieces();
	}
	
	private void setUpPieces() {
		//set up rooks
		gameBoard[0][0] = new Rook(new Position(0,0), PieceColor.BLACK);
		gameBoard[0][7] = new Rook(new Position(0,7), PieceColor.BLACK);
		gameBoard[7][0] = new Rook(new Position(7,0), PieceColor.WHITE);
		gameBoard[7][7] = new Rook(new Position(7,7), PieceColor.WHITE);
		//set up knights
		gameBoard[0][1] = new Knight(new Position(0,1), PieceColor.BLACK);
		gameBoard[0][6] = new Knight(new Position(0,6), PieceColor.BLACK);
		gameBoard[7][1] = new Knight(new Position(7,1), PieceColor.WHITE);
		gameBoard[7][6] = new Knight(new Position(7,6), PieceColor.WHITE);
		//set up bishops
		gameBoard[0][2] = new Bishop(new Position(0,2), PieceColor.BLACK);
		gameBoard[0][5] = new Bishop(new Position(0,5), PieceColor.BLACK);
		gameBoard[7][2] = new Bishop(new Position(7,2), PieceColor.WHITE);
		gameBoard[7][5] = new Bishop(new Position(7,5), PieceColor.WHITE);
		//set up kings and queens
		gameBoard[0][3] = new Queen(new Position(0,3), PieceColor.BLACK);
		gameBoard[7][3] = new Queen(new Position(7,3), PieceColor.WHITE);
		gameBoard[0][4] = new King(new Position(0,4), PieceColor.BLACK);
		gameBoard[7][4] = new King(new Position(7,4), PieceColor.WHITE);
		//set up pawns
		for (int col = 0; col < 8; col++) {
			gameBoard[1][col] = new Pawn(new Position(1,col), PieceColor.BLACK);
		}
		for (int col = 0; col < 8; col++) {
			gameBoard[6][col] = new Pawn(new Position(6,col), PieceColor.WHITE);
		}
	}
	
	//method to move the pieces on the board
	public void movePiece(Position start, Position end) {
		//check if the space the piece wants to move is empty and that the piece on the starting spot can do a legal move
		if (gameBoard[end.getRow()][end.getCol()] == null && gameBoard[start.getRow()][start.getCol()].isValidMove(end, gameBoard)) {
			//set the end position on the board to the piece occupying the starting spot
			gameBoard[end.getRow()][end.getCol()] = gameBoard[start.getRow()][start.getCol()];
			//update the whole board
			gameBoard[end.getRow()][end.getCol()].setPosition(end); //setting a new position to the recently moved piece
			gameBoard[start.getRow()][start.getCol()] = null; //setting the starting position to an empty space
		}
	}
	
	//returns the piece from specific location on the board
	public ChessPiece getChessPiece(int row, int col) {
		return gameBoard[row][col];
	}
	
	//getter method for the board
	public ChessPiece[][] getBoard() {
		return gameBoard;
	}
	
	//helper method that allows us to set a piece anywhere on the board
	public void setPiece(int row, int column, ChessPiece piece) {
		gameBoard[row][column] = piece;
		if (piece != null) {
			piece.setPosition(new Position(row, column));
		}
	}
}
