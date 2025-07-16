package gui;

import java.util.ArrayList;
import java.util.List;

import attributes.PieceColor;
import attributes.Position;
import game.ChessBoard;
import pieces.Bishop;
import pieces.ChessPiece;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Queen;
import pieces.Rook;

//this class is the game logic. This will take the gameboard and pieces, and deal with all the movements, captures, checks, and checkmates
public class ChessGame {
	private ChessBoard chessBoard;
	private boolean whiteTurn = true; //keeps track of which color turn it is
	private Position selectedSpot;
	
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
	
	//method to restart a game of chess
	public void resetGame() {
		this.chessBoard = new ChessBoard();
		this.whiteTurn = true;
	}
	
	//method to return the current turn's color
	public PieceColor getTurnsColor() {
		return whiteTurn ? PieceColor.WHITE : PieceColor.BLACK;
	}
	
	//method that returns true if there is a piece on that selected spot
	public boolean isPieceSelected() {
		return selectedSpot != null;
	}
	
	public boolean handleSelection(int row, int col) {
		//first, check if a spot has already been selected
		if (selectedSpot == null) {
			//if a spot hasn't been selected, check if there is a piece there
			ChessPiece selectedPiece = chessBoard.getChessPiece(row, col);
			if (selectedPiece != null && selectedPiece.getColor() == (whiteTurn ? PieceColor.WHITE : PieceColor.BLACK)) {
				//if there is a piece occupying that spot and it is that player's turn then select that spot but don't move the piece
				selectedSpot = new Position(row,col);
				return false;
			}
		}
		else { //if a spot was selected already
			//see if a legal move can be made from that spot
			boolean legalMove = makeMove(selectedSpot, new Position(row,col));
			selectedSpot = null; //undo selection whether or not move is legal
			return legalMove;
		}
		return false;
	}
	
	//method that will highlight the legal moves for every piece at any time in the game
	public List<Position> getAllLegalMovesForChessPiece(Position piecePosition) {
		//first, find the piece you want to move
		int pieceRow = piecePosition.getRow();
		int pieceCol = piecePosition.getCol();
		ChessPiece myPiece = chessBoard.getChessPiece(pieceRow, pieceCol);
		if (myPiece == null) {return new ArrayList<>();} //return empty array list if no piece exists at the position
		List<Position> legalMoves = new ArrayList<Position>(); //create list to store positions
		if (myPiece instanceof Pawn) {
			pawnMoves(piecePosition, myPiece.getColor(), legalMoves);
		}
		else if (myPiece instanceof King) {
			kingAndKnightMoves(piecePosition, new int[][] {{1,0}, {1,1}, {0,1}, {-1,1}, {-1,0}, {-1,-1}, {-1,0}, {1,-1}},
					myPiece.getColor(), legalMoves);
		}
		else if (myPiece instanceof Knight) {
			kingAndKnightMoves(piecePosition, new int[][] {{2,1}, {2,-1}, {-2,1}, {-2,-1}, {1,2}, {1,-2}, {-1,2}, {-1,-2}},
					myPiece.getColor(), legalMoves);
		}
		else if (myPiece instanceof Rook) {
			rookBishopQueenMoves(piecePosition, new int[][] {{1,0}, {0,1}, {-1,0}, {0,-1}}, myPiece.getColor(), legalMoves);
		}
		else if (myPiece instanceof Bishop) {
			rookBishopQueenMoves(piecePosition, new int[][] {{1,1}, {-1,1}, {-1,-1}, {1,-1}}, myPiece.getColor(), legalMoves);
		}
		else if (myPiece instanceof Queen) {
			rookBishopQueenMoves(piecePosition, new int[][] {{1,0}, {1,1}, {0,1}, {-1,1}, {-1,0}, {-1,-1}, {0,-1}, {1,-1}},
					myPiece.getColor(), legalMoves);
		}
		return legalMoves;
	}
	
	//method to make sure we don't go out-of-bounds
	private boolean isPosValid(Position endPos) {
		if (endPos.getRow() < 0 || endPos.getRow() >= 8 || endPos.getCol() < 0 || endPos.getCol() >= 8) {
			return false;
		}
		return true;
	}
	
	//method that gets all legal pawn moves
	private void pawnMoves(Position selectedPosition, PieceColor pawnColor, List<Position> legalMoves) {
		//find out which direction the pawn has to go(using its color)
		int direction = pawnColor == PieceColor.WHITE ? -1 : 1;
		int destRow = selectedPosition.getRow();
		int destCol = selectedPosition.getCol();
		//start with showing all the legal single moves that pawns can do
		Position endPos = new Position(destRow + direction, destCol);
		if (isPosValid(endPos) && chessBoard.getChessPiece(endPos.getRow(), endPos.getCol()) == null) {
			legalMoves.add(endPos);
		}
		//now factor in a pawn starting move where it can move two spaces ahead
		if ((pawnColor == PieceColor.WHITE && selectedPosition.getRow() == 6) || 
				(pawnColor == PieceColor.BLACK && selectedPosition.getCol() == 1)) {
			endPos = new Position(destRow + 2 * direction, destCol);
			Position midPos = new Position(destRow + direction, destCol);
			if (isPosValid(midPos) && chessBoard.getChessPiece(midPos.getRow(), midPos.getCol()) == null) {
				if (isPosValid(endPos) && chessBoard.getChessPiece(endPos.getRow(), endPos.getCol()) == null) {
					legalMoves.add(endPos);
				}
			}
		}
		//now handle possible capture moves
		int[] possCols = {destRow-1,destRow+1};
		for (int col: possCols) {
			endPos = new Position(destRow + direction, col);
			if (isPosValid(endPos) && chessBoard.getChessPiece(endPos.getRow(), endPos.getCol()) != null 
					&& chessBoard.getChessPiece(endPos.getRow(), endPos.getCol()).getColor() != pawnColor) {
				legalMoves.add(endPos);
			}
		}
	}
	
	//method for all legal knight and king moves
	private void kingAndKnightMoves(Position selectedPosition, int[][] coordinates, PieceColor pieceColor, List<Position> legalMoves) {
		int destRow = selectedPosition.getRow();
		int destCol = selectedPosition.getCol();
		//loop through every coordinate row and see if that spot is empty or can capture a piece
		for (int[] coord: coordinates) {
			Position endPos = new Position(destRow + coord[0], destCol + coord[1]);
			if (isPosValid(endPos) && (chessBoard.getChessPiece(endPos.getRow(), endPos.getCol()) == null 
					|| chessBoard.getChessPiece(endPos.getRow(), endPos.getCol()).getColor() != pieceColor)) {
				legalMoves.add(endPos);
			}
		}
	}
	
	//method for all legal rook, bishop, and queen moves
	private void rookBishopQueenMoves(Position selectedPosition, int[][] directions, PieceColor pieceColor, List<Position> legalMoves) {
		int destRow = selectedPosition.getRow();
		int destCol = selectedPosition.getCol();
		//loop through every possible direction
		for (int[] path: directions) {
			Position endPos = new Position(destRow + path[0], destCol + path[1]);
			while (isPosValid(endPos)) { //while the end position exists on the board
				if (chessBoard.getChessPiece(endPos.getRow(), endPos.getCol()) == null) {
					legalMoves.add(endPos);
					//update the end position
					endPos = new Position(endPos.getRow() + path[0], endPos.getCol() + path[1]);
				}
				else {
					if (chessBoard.getChessPiece(endPos.getRow(), endPos.getCol()).getColor() != pieceColor) {
						legalMoves.add(endPos);
					}
					break;
				}
			}
		}
	}
}
