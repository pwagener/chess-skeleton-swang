package chess.pieces.move;

import java.util.List;

import chess.GameState;
import chess.Player;
import chess.Position;

public class QueenMoveFinder extends MoveFinder {
	MoveFinder crossFinder;
	MoveFinder diagonalFinder;
	
	public QueenMoveFinder(Player player) {
		super(player);
		crossFinder = new CrossMoveFinder(player);
		diagonalFinder = new DiagonalMoveFinder(player);
	}

	@Override
	public List<Position> findMoves(GameState state, Position starting) {
		List<Position> movement = crossFinder.findMoves(state, starting);
		movement.addAll(diagonalFinder.findMoves(state, starting));

		return movement;
	}
}
