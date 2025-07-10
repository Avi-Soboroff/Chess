package pieces;

import attributes.PieceColor;
import attributes.Position;

public class Bishop extends ChessPiece {
	public Bishop(Position position, PieceColor pieceColor) {
		super(position, pieceColor);
	}

	@Override
	public boolean isValidMove(Position newPos, ChessPiece[][] board) {
		//get start and end points
		int startRow = position.getRow();
		int startCol = position.getCol();
		int endRow = newPos.getRow();
		int endCol = newPos.getCol();
		//bishops can only move diagonally. They can do this in any direction, any number of spaces
		//however many rows the bishop moves, it must move that same number of columns
		int rowDiff = Math.abs(endRow - startRow);
		int colDiff = Math.abs(endCol - startCol);
		if (startRow == endRow || startCol == endCol || rowDiff != colDiff) {
			return false;
		}
		//find which directions the bishop needs to move diagonally
		int rowDirection = startRow > endRow ? -1 : 1;
		int colDirection = startCol > endCol ? -1 : 1;
		//for-loop that goes until the spot before final intended destination
		for (int move = 1; move <= rowDiff-1; move++) {
			if (board[startRow + move * rowDirection][startCol + move * colDirection] != null) {
				return false; //there is a piece blocking the way
			}
		}
		//check if final destination is empty or has a piece of another color on it for capture. If yes, then move is valid
		ChessPiece spot = board[endRow][endCol];
		if (spot == null || spot.pieceColor != this.pieceColor) {
			return true;
		}
		return false;
	}
}
