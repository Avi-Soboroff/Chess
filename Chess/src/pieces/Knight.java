package pieces;

import attributes.PieceColor;
import attributes.Position;

public class Knight extends ChessPiece {
	public Knight(Position position, PieceColor pieceColor) {
		super(position, pieceColor);
	}

	@Override
	public boolean isValidMove(Position newPos, ChessPiece[][] board) {
		//get start and end points
		int startRow = position.getRow();
		int startCol = position.getCol();
		int endRow = newPos.getRow();
		int endCol = newPos.getCol();
		//make sure requested move is in bounds
		if (endRow < 0 || endRow >= 8 || endCol < 0 || endCol >= 8) {
			return false;
		}
		//knights can only move in L-shaped paths,m but can jump over pieces so no need to check if something is in the way
		int rowDiff = Math.abs(endRow - startRow);
		int colDiff = Math.abs(endCol - startCol);
		//checking if the knight is requesting to do a legal move
		if ((rowDiff == 1 && colDiff == 2) || (rowDiff == 2 && colDiff == 1)) {
			ChessPiece spot = board[endRow][endCol];
			if (spot == null || spot.pieceColor != this.pieceColor) { //if spot is empty or piece of opposite color is occupying, then legal move
				return true;
			}
		}
		return false;
	}
}
