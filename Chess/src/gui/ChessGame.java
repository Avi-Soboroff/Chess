package gui;

import game.ChessBoard;

public class ChessGame {
	private ChessBoard chessBoard;
	
	public ChessGame() {
		chessBoard = new ChessBoard();
	}
	
	public ChessBoard getBoard() {
		return chessBoard;
	}
}
