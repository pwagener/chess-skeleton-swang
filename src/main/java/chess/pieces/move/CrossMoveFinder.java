package chess.pieces.move;

import java.util.ArrayList;
import java.util.List;

import chess.GameState;
import chess.Player;
import chess.Position;

public class CrossMoveFinder extends MoveFinder {
	private int range = MoveFinder.RANGE_INFINITE;
	
	public CrossMoveFinder(Player player) {
		super(player);
	}
	public CrossMoveFinder(Player player, int range) {
		this(player);
		this.range = range;
	}

	@Override
	public List<Position> findMoves(GameState state, Position starting) {
		List<Position> movement = new ArrayList<Position>();
		findMovesInDirection(state, Direction.UP, movement, starting, range);
		findMovesInDirection(state, Direction.LEFT, movement, starting, range);
		findMovesInDirection(state, Direction.DOWN, movement, starting, range);
		findMovesInDirection(state, Direction.RIGHT, movement, starting, range);

		return movement;
	}

}
