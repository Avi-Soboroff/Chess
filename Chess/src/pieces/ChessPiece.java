package pieces;

import Attributes.PieceColor;
import Attributes.Position;

public abstract class ChessPiece {
	protected Position position;
	protected PieceColor pieceColor; 
	
	public ChessPiece(Position position, PieceColor pieceColor) {
		this.position = position;
		this.pieceColor = pieceColor;
	}
	
	//getter method to get the position of the piece
	public Position getPosition() {
		return position;
	}
	
	//returns the color of the chess piece
	public PieceColor getColor() {
		return pieceColor;
	}
	
	//sets a new position of the piece
	public Position setPosition(int row, int col) {
		return new Position(row, col);
	}
	
	public abstract boolean isValidMove(Position newPos, ChessPiece[][] board);
}
