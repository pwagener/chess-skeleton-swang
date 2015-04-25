package chess.pieces.move;

import java.util.List;

import chess.GameState;
import chess.Player;
import chess.Position;

public class KingMoveFinder extends MoveFinder {
	MoveFinder crossFinder;
	MoveFinder diagonalFinder;
	
	public KingMoveFinder(Player player) {
		super(player);
		crossFinder = new CrossMoveFinder(player, 1);
		diagonalFinder = new DiagonalMoveFinder(player, 1);
	}

	@Override
	public List<Position> findMoves(GameState state, Position starting) {
		List<Position> movement = crossFinder.findMoves(state, starting);
		movement.addAll(diagonalFinder.findMoves(state, starting));

		return movement;
	}

}
