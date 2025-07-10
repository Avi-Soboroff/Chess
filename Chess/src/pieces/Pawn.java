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
		//find the distance from starting position to new position
		int numRows = (newPos.getRow()-position.getRow()) * direction; //pawn will either go forwards or backwards depending on the color
		int numCols = newPos.getCol()-position.getCol();
		//now check if it can do a one-square move
		
		return false;
	}
}
