package chess;

import java.util.HashMap;

import chess.pieces.Piece;

/**
 * This class is only used for testing.
 * By using this, we can use the protected fields and manipulated them without exposing the interfaces to the public.
 * @author Beatbystick
 *
 */
public class TestableGameState extends GameState {

	public void removePiece(Position position) {
		positionToPieceMap.remove(position);
	}
	
	public void clearBoard() {
		positionToPieceMap = new HashMap<Position, Piece>();
	}
	
	public void placePiece(Piece piece, Position position) {
		positionToPieceMap.put(position, piece);
	}
}
