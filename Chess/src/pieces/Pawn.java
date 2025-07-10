package pieces;

import attributes.PieceColor;
import attributes.Position;

public class Pawn extends ChessPiece {
	public Pawn(Position position, PieceColor pieceColor) {
		super(position, pieceColor);
	}

	@Override
	public boolean isValidMove(Position newPos, ChessPiece[][] board) {
		//find out which color the pawn is first to determine the direction that it should go
		int direction = pieceColor == PieceColor.WHITE ? -1 : 1;
		//find starting and new positions
		int startRow = position.getRow();
		int startCol = position.getCol();
		int endRow = newPos.getRow();
		int endCol = newPos.getCol();
		//make sure the requested move is in the board's bounds
		if (endRow < 0 || endRow >= 8 || endCol < 0 || endRow >= 8) {
			return false; //invalid if not in-bounds
		}
		int rowDiff = (endRow - startRow) * direction; //the distance the pawn wants to move
		int colDiff = endCol - startCol;
		//check if the pawn can make a one-square move; if the space ahead is occupied then the move is invalid
		if (rowDiff == 1 && colDiff == 0 && board[endRow][endCol] == null) {
			return true;
		}
		//check if the pawn can make a two-square move; this can only work if the pawn is in its starting position
		if (((pieceColor == PieceColor.WHITE && startRow == 6) || (pieceColor == PieceColor.BLACK && startRow == 1)) 
				&& rowDiff == 2 && colDiff == 0 && board[endRow][endCol] == null) {
			int middleRow = startRow + direction;
			if (board[middleRow][endCol] == null) {
				return true;
			}
		}
		//check if the pawn can make a diagonal capture. We use Math.abs to make sure it can do a diagonal capture in both directions
		if (Math.abs(colDiff) == 1 && rowDiff == 1 && board[endRow][endCol] 
				!= null && board[endRow][endCol].pieceColor != this.pieceColor) {
			return true;
		}
		return false;
	}
}
