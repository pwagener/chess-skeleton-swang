package chess.pieces;

import chess.Player;
import chess.pieces.move.DiagonalMoveFinder;
import chess.pieces.move.MoveFinder;

/**
 * The 'Bishop' class
 */
public class Bishop extends Piece {
	MoveFinder moveFinder;

    public Bishop(Player owner) {
        super(owner);
        moveFinder = new DiagonalMoveFinder(owner);
    }

    @Override
    protected char getIdentifyingCharacter() {
        return 'b';
    }

	@Override
	public MoveFinder getMoveFinder() {
		return moveFinder;
	}

}
