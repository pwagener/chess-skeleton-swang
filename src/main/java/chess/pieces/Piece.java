package chess.pieces;

import chess.Player;
import chess.pieces.move.MoveFinder;

/**
 * A base class for chess pieces
 */
public abstract class Piece {
    private final Player owner;

    protected Piece(Player owner) {
        this.owner = owner;
    }

    public char getIdentifier() {
        char id = getIdentifyingCharacter();
        if (owner.equals(Player.White)) {
            return Character.toLowerCase(id);
        } else {
            return Character.toUpperCase(id);
        }
    }

    public Player getOwner() {
        return owner;
    }

    /**
     * Return all possible movement given its position
     * @param p
     * @return
     */
    public abstract MoveFinder getMoveFinder();

    protected abstract char getIdentifyingCharacter();
    
}
