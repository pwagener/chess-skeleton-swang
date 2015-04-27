package chess.pieces.move;

import java.util.List;

import chess.GameState;
import chess.Player;
import chess.Position;
import chess.pieces.Piece;

public abstract class MoveFinder {
	public static final int RANGE_INFINITE = -1;

	/**
	 * Used to determine which direction a piece can move
	 * @author Beatbystick
	 *
	 */
	protected Player player;
	
	public MoveFinder(Player player) {
		this.player = player;
	}

	public abstract List<Position> findMoves(GameState state, Position starting);
	
	/**
	 * Recursive function to find possible moves
	 * @param d
	 * @param movement
	 * @param starting
	 */
	protected void findMovesInDirection(GameState state, Direction d, List<Position> movement, Position starting) {
		findMovesInDirection(state, d, movement, starting, -1, true); 
	}

	/**
	 * Recursive function to find possibile moves
	 * @param state
	 * @param d
	 * @param movement
	 * @param starting
	 * @param range Optional, if set, the piece can only move within the given range
	 */
	protected void findMovesInDirection(GameState state, Direction d, List<Position> movement, Position starting, int range) {
		findMovesInDirection(state, d, movement, starting, range, true); 
	}

	/**
	 * Recursive function to find possible moves
	 * 
	 * @param state
	 * @param d
	 * @param movement
	 * @param starting
	 * @param range Optional, if set, the piece can only move within the given range
	 * @param canAttack Optional, set by default, the piece can attack foe
	 */
	protected void findMovesInDirection(GameState state, Direction d, List<Position> movement, Position starting, int range, boolean canAttack) {
		char col = starting.getColumn();
		int row = starting.getRow();
		
		if (range == 0 || !Position.isOnBoard(starting.getColumn(), starting.getRow())) {
			return;
		}

		switch (d) {
        	case UP_RIGHT:
        		col++;
        	case UP:
        		row++;
                break;
        	case DOWN_RIGHT:
        		row--;
        	case RIGHT:
        		col++;
                break;
        	case UP_LEFT:
        		row++;
        	case LEFT:
        		col--;
                break;
        	case DOWN_LEFT:
        		col--;
        	case DOWN:
        		row--;
                break;
			default:
				break;
		}
		
		Position checkPos = new Position(col, row);

		if (checkAndAddToMovement(state, movement, checkPos, canAttack)) {
			findMovesInDirection(state, d, movement, checkPos, range-1, canAttack);
		}
	}

	/**
	 * This function will test if the check position is clear for movement, it will also add to movement accordingly
	 * 
	 * @param state
	 * @param movement
	 * @param starting
	 * @param checkPos
	 * @return false if the check position is a blockage
	 */
	protected boolean checkAndAddToMovement(GameState state, List<Position> movement, Position checkPos) {
		return checkAndAddToMovement(state, movement, checkPos, true);
	}

	/**
	 * This function will test if the check position is clear for movement, it will also add to movement accordingly
	 * @param state
	 * @param movement
	 * @param starting
	 * @param checkPos
	 * @param canAttack
	 * @return false if the check position is a blockage
	 */
	protected boolean checkAndAddToMovement(GameState state, List<Position> movement, Position checkPos, boolean canAttack) {
		if (!Position.isOnBoard(checkPos.getColumn(), checkPos.getRow())) {
			return false;
		}

		Piece checkingPiece = state.getPieceAt(checkPos);
		if (checkingPiece != null) {
			if (canAttack) {  //Think Pawn
				if (checkingPiece.getOwner() != player) {
					movement.add(checkPos);
				}
			} 
			return false;
		}
		movement.add(checkPos);
		
		return true;
	}
	
}
