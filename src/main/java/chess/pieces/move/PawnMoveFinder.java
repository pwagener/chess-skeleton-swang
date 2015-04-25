package chess.pieces.move;

import java.util.ArrayList;
import java.util.List;

import chess.GameState;
import chess.Player;
import chess.Position;
import chess.pieces.Piece;

public class PawnMoveFinder extends MoveFinder {

	public PawnMoveFinder(Player player) {
		super(player);
	}

	@Override
	public List<Position> findMoves(GameState state, Position starting) {
		List<Position> movement = new ArrayList<Position>();
		Piece pawn = state.getPieceAt(starting);
		
		char col = starting.getColumn();
		int row = starting.getRow();
		if (pawn.getOwner() == Player.White) {
			if (row == 2) { //First time moving
				findMovesInDirection(state, Direction.UP, movement, starting, 2, false);
			} else {
				findMovesInDirection(state, Direction.UP, movement, starting, 1, false);
			}
			//Check possible attacks
			Position checkPos = new Position((char)(col+1), row+1);
			if (checkAttack(state, checkPos)) {
				movement.add(checkPos);
			}
			checkPos = new Position((char)(col-1), row+1);
			if (checkAttack(state, checkPos)) {
				movement.add(checkPos);
			}
		} else {
			if (row == 7) { //First time moving
				findMovesInDirection(state, Direction.DOWN, movement, starting, 2, false);
			} else {
				findMovesInDirection(state, Direction.DOWN, movement, starting, 1, false);
			}
			//Check possible attacks
			Position checkPos = new Position((char)(col+1), row-1);
			if (checkAttack(state, checkPos)) {
				movement.add(checkPos);
			}
			checkPos = new Position((char)(col-1), row-1);
			if (checkAttack(state, checkPos)) {
				movement.add(checkPos);
			}
		}

		return movement;
	}
	
	private boolean checkAttack(GameState state, Position checkPos) {
		Piece foe = state.getPieceAt(checkPos);
		return (foe != null && foe.getOwner() != player);
	}

}
