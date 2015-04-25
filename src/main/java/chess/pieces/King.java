package chess.pieces;

import chess.Player;
import chess.pieces.move.KingMoveFinder;
import chess.pieces.move.MoveFinder;

/**
 * The King class
 */
public class King extends Piece {
	MoveFinder moveFinder;

    public King(Player owner) {
        super(owner);
        moveFinder = new KingMoveFinder(owner);
    }

    @Override
    protected char getIdentifyingCharacter() {
        return 'k';
    }

	@Override
	public MoveFinder getMoveFinder() {
		return moveFinder;
	}
}
