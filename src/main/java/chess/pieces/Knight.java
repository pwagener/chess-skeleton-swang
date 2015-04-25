package chess.pieces;

import chess.Player;
import chess.pieces.move.KnightMoveFinder;
import chess.pieces.move.MoveFinder;

/**
 * The Knight class
 */
public class Knight extends Piece {
	MoveFinder moveFinder;

    public Knight(Player owner) {
        super(owner);
        moveFinder = new KnightMoveFinder(owner);
    }

    @Override
    protected char getIdentifyingCharacter() {
        return 'n';
    }

	@Override
	public MoveFinder getMoveFinder() {
		return moveFinder;
	}
}
