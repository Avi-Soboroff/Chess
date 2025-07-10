package pieces;

import attributes.PieceColor;
import attributes.Position;

public class King extends ChessPiece {
	public King(Position position, PieceColor pieceColor) {
		super(position, pieceColor);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isValidMove(Position newPos, ChessPiece[][] board) {
		// TODO Auto-generated method stub
		return false;
	}
}
