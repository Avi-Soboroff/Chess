package pieces;

import attributes.PieceColor;
import attributes.Position;

public class King extends ChessPiece {
	public King(Position position, PieceColor pieceColor) {
		super(position, pieceColor);
	}

	@Override
	public boolean isValidMove(Position newPos, ChessPiece[][] board) {
		//get start and end points
		int startRow = position.getRow();
		int startCol = position.getCol();
		int endRow = newPos.getRow();
		int endCol = newPos.getCol();
		//kings can move in any direction, one tile at a time
		if (endRow < 0 || endRow >= 8 || endCol < 0 || endCol >= 8) {
			return false;
		}
		//find distance and make sure that it isn't greater than one space
		int rowDiff = Math.abs(endRow - startRow);
		int colDiff = Math.abs(endCol - startCol);
		if (!((rowDiff <= 1 && colDiff <= 1) && !(rowDiff == 0 && colDiff == 0))) {
			return false;
		}
		ChessPiece spot = board[endRow][endCol];
		if (spot == null || spot.pieceColor != this.pieceColor) { //empty space or another color on the tile then valid move
			return true;
		}
		return false;
	}
}
