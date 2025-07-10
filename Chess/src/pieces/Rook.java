package pieces;

import attributes.PieceColor;
import attributes.Position;

public class Rook extends ChessPiece {
	public Rook(Position position, PieceColor pieceColor) {
		super(position, pieceColor);
	}

	@Override
	public boolean isValidMove(Position newPos, ChessPiece[][] board) {
		//get start and end points
		int startRow = position.getRow();
		int startCol = position.getCol();
		int endRow = newPos.getRow();
		int endCol = newPos.getCol();
		//Rooks can only move in straight lines, horizontally or vertically. They are not allowed to move diagonally
		if (startRow != endRow || startCol != endCol) {
			return false;
		}
		//first check if the rook can move horizontally
		else if (startRow == endRow) {
			int direction = (startRow > endRow) ? -1 : 1; //can either move left or right in the straight line
			for (int col = startCol + direction; col != endCol; col += direction) { //for-loop runs until it reaches intended destination or if piece is in the way
				if (board[startRow][col] != null) { //if there is a piece blocking the path the move is invalid
					return false;
				}
			}
		}
		//now check if the rook can move vertically
		else if (startCol == endCol) {
			int direction = (startCol > endCol) ? -1 : 1; //can move up or down the board
			for (int row = startRow + direction; row != endRow; row += direction) {
				if (board[row][startCol] != null) { //if there is a piece blocking the way then invalid move
					return false;
				}
			}
		}
		//now check if that space is either open or good to capture another piece
		ChessPiece spot = board[endRow][endCol];
		if (spot == null || spot.pieceColor != this.pieceColor) {
			return true;
		}
		return false;
	}
}
