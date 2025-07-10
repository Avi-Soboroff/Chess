package pieces;

import attributes.PieceColor;
import attributes.Position;

public class Queen extends ChessPiece {
	public Queen(Position position, PieceColor pieceColor) {
		super(position, pieceColor);
	}

	@Override
	public boolean isValidMove(Position newPos, ChessPiece[][] board) {
		//get start and end points
		int startRow = position.getRow();
		int startCol = position.getCol();
		int endRow = newPos.getRow();
		int endCol = newPos.getCol();
		/* The queen is the most powerful piece on the board. It can move like a rook and bishop, but not like knights, any
		 * amount of spaces*/
		if (endRow < 0 || endRow >= 8 || endCol < 0 || endCol >= 8) {
			return false;
		}
		//find distance the queen wants to move
		int rowDiff = Math.abs(endRow - startRow);
		int colDiff = Math.abs(endCol - startCol);
		//then check if the queen can move straight or diagonal
		boolean straight = (startRow == endRow || startCol == endCol);
		boolean diagonal = (rowDiff == colDiff);
		//if the queen can't move straight and diagonal then it is an invalid move
		if (!straight && !diagonal) {
			return false;
		}
		int rowDirection = (startRow > endRow) ? -1 : 1;
		int colDirection = (startCol > endCol) ? -1 : 1;
		int rowStep = startRow + rowDirection;
		int colStep = startCol + colDirection;
		//OR is used instead of AND because when both conditions are false, that when we have reached the spot before the final destination
		while (rowStep != endRow || colStep != endCol) {
			if (board[rowStep][colStep] != null) {
				return false; //there is a piece blocking the way
			}
			//these if-statements make sure that the we don't move past where we intend to go with the queen
			if (rowStep != endRow) {
				rowStep += rowDirection;
			}
			if (colStep != endCol) {
				colStep += colDirection;
			}
		}
		ChessPiece spot = board[endRow][endCol];
		if (spot == null || spot.pieceColor != this.pieceColor) {
			return true;
		}
		return false;
	}
}
