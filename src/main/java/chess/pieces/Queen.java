package chess.pieces;

import chess.Player;
import chess.pieces.move.MoveFinder;
import chess.pieces.move.QueenMoveFinder;

/**
 * The Queen
 */
public class Queen extends Piece{
	MoveFinder moveFinder;

    public Queen(Player owner) {
        super(owner);
        moveFinder = new QueenMoveFinder(owner);
    }

    @Override
    protected char getIdentifyingCharacter() {
        return 'q';
    }

	@Override
	public MoveFinder getMoveFinder() {
		return moveFinder;
	}
}
