package pieces;

import Attributes.PieceColor;
import Attributes.Position;

public class Pawn extends ChessPiece {
	public Pawn(Position position, PieceColor pieceColor) {
		super(position, pieceColor);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isValidMove(Position newPos, ChessPiece[][] board) {
		// TODO Auto-generated method stub
		return false;
	}
}
