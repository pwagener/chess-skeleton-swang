package chess.pieces.move;

import java.util.ArrayList;
import java.util.List;

import chess.GameState;
import chess.Player;
import chess.Position;

public class DiagonalMoveFinder extends MoveFinder {
	private int range = MoveFinder.RANGE_INFINITE;
	
	public DiagonalMoveFinder(Player player) {
		super(player);
	}
	public DiagonalMoveFinder(Player player, int range) {
		this(player);
		this.range = range;
	}

	@Override
	public List<Position> findMoves(GameState state, Position starting) {
		List<Position> movement = new ArrayList<Position>();

		findMovesInDirection(state, Direction.UP_RIGHT, movement, starting, range);
		findMovesInDirection(state, Direction.UP_LEFT, movement, starting, range);
		findMovesInDirection(state, Direction.DOWN_RIGHT, movement, starting, range);
		findMovesInDirection(state, Direction.DOWN_LEFT, movement, starting, range);

		return movement;
	}

}
