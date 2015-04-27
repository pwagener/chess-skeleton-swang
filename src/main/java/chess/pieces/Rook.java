package chess.pieces;

import chess.Player;
import chess.pieces.move.CrossMoveFinder;
import chess.pieces.move.MoveFinder;

/**
 * The 'Rook' class
 */
public class Rook extends Piece {
	MoveFinder moveFinder;

    public Rook(Player owner) {
        super(owner);
        moveFinder = new CrossMoveFinder(owner);
    }

    @Override
    protected char getIdentifyingCharacter() {
        return 'r';
    }

	@Override
	public MoveFinder getMoveFinder() {
		return moveFinder;
	}
}
