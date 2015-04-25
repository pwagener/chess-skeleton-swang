package chess.pieces.move;

import java.util.ArrayList;
import java.util.List;

import chess.GameState;
import chess.Player;
import chess.Position;

public class KnightMoveFinder extends MoveFinder {

	public KnightMoveFinder(Player player) {
		super(player);
	}

	@Override
	public List<Position> findMoves(GameState state, Position starting) {
		List<Position> movement = new ArrayList<Position>();
		
		char col = starting.getColumn();
		int row = starting.getRow();

		//Check RIGHT
		col+=2;
		checkAndAddToMovement(state, movement, new Position(col, row+1));
		checkAndAddToMovement(state, movement, new Position(col, row-1));
		
		//Check LEFT
		col-=4;
		checkAndAddToMovement(state, movement, new Position(col, row+1));
		checkAndAddToMovement(state, movement, new Position(col, row-1));
		
		//Check UP
		col = starting.getColumn();
		row = starting.getRow() + 2;
		checkAndAddToMovement(state, movement, new Position((char)(col+1), row));
		checkAndAddToMovement(state, movement, new Position((char)(col-1), row));
		
		//Check DOWN
		row -= 4;
		checkAndAddToMovement(state, movement, new Position((char)(col+1), row));
		checkAndAddToMovement(state, movement, new Position((char)(col-1), row));

		return movement;
	}

}
