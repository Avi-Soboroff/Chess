package gui;

import attributes.PieceColor;
import attributes.Position;
import game.ChessBoard;
import pieces.ChessPiece;
import pieces.King;

//this class is the game logic. This will take the gameboard and pieces, and deal with all the movements, captures, checks, and checkmates
public class ChessGame {
	private ChessBoard chessBoard;
	private boolean whiteTurn = true; //keeps track of which color turn it is
	
	public ChessGame() {
		chessBoard = new ChessBoard();
	}
	
	public ChessBoard getBoard() {
		return chessBoard;
	}
	
	public boolean makeMove(Position start, Position end) {
		//first, check if we have a piece at our starting position
		ChessPiece movedPiece = chessBoard.getChessPiece(start.getRow(), start.getCol());
		//if there isn't a piece at the starting position or if its the wrong color's turn than move isn't valid
		if (movedPiece == null || movedPiece.getColor() != (whiteTurn ? PieceColor.WHITE : PieceColor.BLACK)) {
			return false;
		}
		//now check if a legal move can be made from that starting position
		if (movedPiece.isValidMove(end, chessBoard.getBoard())) {
			chessBoard.movePiece(start, end); //move the piece
			whiteTurn = !whiteTurn; //switch turns
			return true;
		}
		return false;
	}
	
	//method to check if either king is in check
	public boolean kingInCheck(PieceColor kingColor) {
		Position kingPosition = findKingPos(kingColor);
		if (kingPosition == null) {
			return false;
		}
		//now check if that king position can be attacked
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				ChessPiece attacker = chessBoard.getChessPiece(row, col);
				if (attacker != null && attacker.isValidMove(kingPosition, chessBoard.getBoard()) && attacker.getColor() != kingColor) {
					return true;
				}
			}
		}
		return false;
	}
	
	//helper method to find the king on the board
	private Position findKingPos(PieceColor kingColor) {
		//this method searches the whole board for the king of the intended color
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				//first check if there is a piece on that space
				ChessPiece piece = chessBoard.getChessPiece(row, col);
				//now see if that piece is a king and matches with the color parameter
				if (piece instanceof King && piece.getColor() == kingColor) {
					return new Position(row,col);
				}
			}
		}
		return null; //in the case where somehow that king is not on the board(game would already be over lmao)
	}
	
	//method to check for a checkmate
	public boolean isGameOver(PieceColor kingColor) {
		//if the king isn't in check in the first place then the game goes on
		if (!kingInCheck(kingColor)) {
			return false;
		}
		//find where the king is on the board
		Position kingPosition = findKingPos(kingColor);
		//gives us the king that we want at our intended starting location
		ChessPiece king = chessBoard.getChessPiece(kingPosition.getRow(), kingPosition.getCol());
		//now see if the king can move anywhere safely (reminder that the king only moves one space at a time in any direction)
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 1; col++) {
				Position endPos = new Position(row,col);
				//if the king can make a valid and is not in check after making that move then the game continues 
				if (king.isValidMove(endPos, chessBoard.getBoard()) && !stillInCheck(kingColor, kingPosition, endPos)) {
					return true;
				}
			}
		}
		return true;
	}
	
	//private helper method to see if the king is still in check after temporarily moving
	private boolean stillInCheck(PieceColor kingColor, Position start, Position end) {
		int startRow = start.getRow();
		int startCol = start.getCol();
		int endRow = end.getRow();
		int endCol = end.getCol();
		//do a temporary move of that piece and see if it is still in check
		ChessPiece tempMove = chessBoard.getChessPiece(endRow, endCol); //piece that occupies end location
		//set the end spot with the piece from the starting spot
		chessBoard.setPiece(endRow, endCol, chessBoard.getChessPiece(startRow, startCol));
		//set the initial starting spot to null(temporarily)
		chessBoard.setPiece(startRow, startCol, null);
		//now see if that piece is still in check
		boolean stillInCheck = kingInCheck(kingColor);
		//now move the pieces back
		chessBoard.setPiece(startRow, startCol, chessBoard.getChessPiece(endRow, endCol));
		chessBoard.setPiece(endRow, endCol, tempMove);
		return stillInCheck;
	}
}
