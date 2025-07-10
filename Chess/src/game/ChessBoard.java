package game;

import attributes.PieceColor;
import attributes.Position;
import pieces.Bishop;
import pieces.ChessPiece;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Queen;
import pieces.Rook;

public class ChessBoard {
	private ChessPiece[][] gameBoard;
	
	public ChessBoard() {
		gameBoard = new ChessPiece[8][8];
		setUpPieces();
	}
	
	private void setUpPieces() {
		//set up rooks
		gameBoard[0][0] = new Rook(new Position(0,0), PieceColor.BLACK);
		gameBoard[0][7] = new Rook(new Position(0,7), PieceColor.BLACK);
		gameBoard[7][0] = new Rook(new Position(7,0), PieceColor.WHITE);
		gameBoard[7][7] = new Rook(new Position(7,7), PieceColor.WHITE);
		//set up knights
		gameBoard[0][1] = new Knight(new Position(0,1), PieceColor.BLACK);
		gameBoard[0][6] = new Knight(new Position(0,6), PieceColor.BLACK);
		gameBoard[7][1] = new Knight(new Position(7,1), PieceColor.WHITE);
		gameBoard[7][6] = new Knight(new Position(7,6), PieceColor.WHITE);
		//set up bishops
		gameBoard[0][2] = new Bishop(new Position(0,2), PieceColor.BLACK);
		gameBoard[0][5] = new Bishop(new Position(0,5), PieceColor.BLACK);
		gameBoard[7][2] = new Bishop(new Position(7,2), PieceColor.WHITE);
		gameBoard[7][5] = new Bishop(new Position(7,5), PieceColor.WHITE);
		//set up kings and queens
		gameBoard[0][3] = new Queen(new Position(0,3), PieceColor.BLACK);
		gameBoard[7][3] = new Queen(new Position(7,3), PieceColor.WHITE);
		gameBoard[0][4] = new King(new Position(0,4), PieceColor.BLACK);
		gameBoard[7][4] = new King(new Position(7,4), PieceColor.WHITE);
		//set up pawns
		for (int col = 0; col < 8; col++) {
			gameBoard[1][col] = new Pawn(new Position(1,col), PieceColor.BLACK);
		}
		for (int col = 0; col < 8; col++) {
			gameBoard[6][col] = new Pawn(new Position(6,col), PieceColor.WHITE);
		}
	}
}
