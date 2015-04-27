package chess.pieces;

import chess.Player;
import chess.pieces.move.MoveFinder;
import chess.pieces.move.PawnMoveFinder;

/**
 * The Pawn
 */
public class Pawn extends Piece {
	MoveFinder moveFinder;

    public Pawn(Player owner) {
        super(owner);
        moveFinder = new PawnMoveFinder(owner);
    }

    @Override
    protected char getIdentifyingCharacter() {
        return 'p';
    }

	@Override
	public MoveFinder getMoveFinder() {
		return moveFinder;
	}
}
