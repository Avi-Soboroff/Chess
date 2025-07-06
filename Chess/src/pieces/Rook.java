package pieces;

import Attributes.PieceColor;
import Attributes.Position;

public class Rook extends ChessPiece {
	public Rook(Position position, PieceColor pieceColor) {
		super(position, pieceColor);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isValidMove(Position newPos, ChessPiece[][] board) {
		// TODO Auto-generated method stub
		return false;
	}
}
