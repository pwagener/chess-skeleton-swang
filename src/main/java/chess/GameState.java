package chess;


import chess.pieces.*;
import chess.pieces.move.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class that represents the current state of the game.  Basically, what pieces are in which positions on the
 * board.
 */
public class GameState {
    /**
     * The current player
     */
    protected Player currentPlayer = Player.White;

    /**
     * A map of board positions to pieces at that position
     */
    protected Map<Position, Piece> positionToPieceMap;
    
    protected Position whiteKingPos;
    protected Position blackKingPos;
    private boolean isOver = false;

    /**
     * Create the game state.
     */
    public GameState() {
        positionToPieceMap = new HashMap<Position, Piece>();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    
    public boolean isOver() {
    	return isOver;
    }

    /**
     * Call to initialize the game state into the starting positions
     */
    public void reset() {
        positionToPieceMap = new HashMap<Position, Piece>();
        // White Pieces
        placePiece(new Rook(Player.White), new Position("a1"));
        placePiece(new Knight(Player.White), new Position("b1"));
        placePiece(new Bishop(Player.White), new Position("c1"));
        placePiece(new Queen(Player.White), new Position("d1"));
        placePiece(new King(Player.White), new Position("e1"));
        whiteKingPos = new Position("e1");
        placePiece(new Bishop(Player.White), new Position("f1"));
        placePiece(new Knight(Player.White), new Position("g1"));
        placePiece(new Rook(Player.White), new Position("h1"));
        placePiece(new Pawn(Player.White), new Position("a2"));
        placePiece(new Pawn(Player.White), new Position("b2"));
        placePiece(new Pawn(Player.White), new Position("c2"));
        placePiece(new Pawn(Player.White), new Position("d2"));
        placePiece(new Pawn(Player.White), new Position("e2"));
        placePiece(new Pawn(Player.White), new Position("f2"));
        placePiece(new Pawn(Player.White), new Position("g2"));
        placePiece(new Pawn(Player.White), new Position("h2"));

        // Black Pieces
        placePiece(new Rook(Player.Black), new Position("a8"));
        placePiece(new Knight(Player.Black), new Position("b8"));
        placePiece(new Bishop(Player.Black), new Position("c8"));
        placePiece(new Queen(Player.Black), new Position("d8"));
        placePiece(new King(Player.Black), new Position("e8"));
        blackKingPos = new Position("e8");
        placePiece(new Bishop(Player.Black), new Position("f8"));
        placePiece(new Knight(Player.Black), new Position("g8"));
        placePiece(new Rook(Player.Black), new Position("h8"));
        placePiece(new Pawn(Player.Black), new Position("a7"));
        placePiece(new Pawn(Player.Black), new Position("b7"));
        placePiece(new Pawn(Player.Black), new Position("c7"));
        placePiece(new Pawn(Player.Black), new Position("d7"));
        placePiece(new Pawn(Player.Black), new Position("e7"));
        placePiece(new Pawn(Player.Black), new Position("f7"));
        placePiece(new Pawn(Player.Black), new Position("g7"));
        placePiece(new Pawn(Player.Black), new Position("h7"));
        
        isOver = false;
        currentPlayer = Player.White;
    }

    /**
     * Get the piece at the position specified by the String
     * @param colrow The string indication of position; i.e. "d5"
     * @return The piece at that position, or null if it does not exist.
     */
    public Piece getPieceAt(String colrow) {
        Position position = new Position(colrow);
        return getPieceAt(position);
    }

    /**
     * Get the piece at a given position on the board
     * @param position The position to inquire about.
     * @return The piece at that position, or null if it does not exist.
     */
    public Piece getPieceAt(Position position) {
        return positionToPieceMap.get(position);
    }

    /**
     * Moves a piece to another location
     * @param fromString origin position
     * @param toString destination position
     * @return false if the move is not permitted
     */
    public boolean movePiece(String fromString, String toString) {
    	Position from = new Position(fromString);
    	Position to = new Position(toString);
    	Piece piece = getPieceAt(from);
    	if (piece != null && piece.getOwner() == currentPlayer) {
    		List<Position> possibleMoves = piece.getMoveFinder().findMoves(this, from);
    		if (possibleMoves.contains(to) && !isInCheckAfterMove(currentPlayer, piece, from, to)) {
    			removeAtPosition(from);
    			placePiece(piece, to);
    			
    			if (currentPlayer == Player.Black) {
    				currentPlayer = Player.White;
    			} else {
    				currentPlayer = Player.Black;
    			}

    			isOver = isInCheckMate(currentPlayer);

    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * Returns all possible movement of the current player.
     * @return
     */
    public Map<Position, List<Position>> getCurrentLegalMoves() {
    	return getLegalMoves(currentPlayer);
    }

    /**
     * Returns all legal movement of the player.
     * @return
     */
    private Map<Position, List<Position>> getLegalMoves(Player player) {
    	Map<Position, List<Position>> totalMoves = new HashMap<Position, List<Position>>();
    	Map<Position, List<Position>> possibleMoves = getPossibleMoves(player);
    	
    	if (possibleMoves != null && possibleMoves.size() > 0) {
    		for (Map.Entry<Position, List<Position>> pair : possibleMoves.entrySet()) {
    			Piece currentPiece = getPieceAt(pair.getKey());
    			List<Position> legalMoves = new ArrayList<Position>();
    			for (Position p : pair.getValue()) {
    				//Filter out moves that will leave the king in check
    				if (!isInCheckAfterMove(player, currentPiece, pair.getKey(), p)) {
    					legalMoves.add(p);
    				}
    			}
    			if (legalMoves.size() > 0) {
    				totalMoves.put(pair.getKey(), legalMoves);
    			}
    		}

    	}
    	return totalMoves;
    }
    
    /**
     * This is used to check if the player in is checkmate
     * 
     * @param checkPlayer
     * @return true if the player has no more legal move
     */
    private boolean isInCheckMate(Player checkPlayer) {
    	Set<Position> attackerMap = findAttackerMovement(checkPlayer);

		//At this point we have all paths to enemy, check if there is paths that will get us out of check
		if (attackerMap.size() > 0) {
			Map<Position, List<Position>> possibleMoves = getPossibleMoves(checkPlayer);
			
			if (possibleMoves != null && possibleMoves.size() > 0) {
				for (Map.Entry<Position, List<Position>> possibleMove : possibleMoves.entrySet()) {
					Position possibleFromPosition = possibleMove.getKey();
					Piece possiblePiece = getPieceAt(possibleFromPosition);
					for (Position possibleToPosition : possibleMove.getValue()) {
						if (!isInCheckAfterMove(checkPlayer, possiblePiece, possibleFromPosition, possibleToPosition)) {
							return false;
						}
					}
				}
			}
    		return true;
		}
		
    	return false;
    }
    
    /**
     * Returns all paths (positions) that leads to attackers
     * 
     * @param currentPlayer
     * @return
     */
    private Set<Position> findAttackerMovement(Player checkPlayer) {
    	Set<Position> attackerMap = new HashSet<Position>();
    	
    	Position targetKingPos = (checkPlayer == Player.Black)? blackKingPos:whiteKingPos;

    	//Search open path starting from king
    	List<Position> path = new ArrayList<Position>();
    	findAttacker(attackerMap, path, Direction.UP, targetKingPos, 1);
    	path.clear();
    	findAttacker(attackerMap, path, Direction.DOWN, targetKingPos, 1);
    	path.clear();
    	findAttacker(attackerMap, path, Direction.LEFT, targetKingPos, 1);
    	path.clear();
    	findAttacker(attackerMap, path, Direction.RIGHT, targetKingPos, 1);
    	path.clear();
    	findAttacker(attackerMap, path, Direction.UP_LEFT, targetKingPos, 1);
    	path.clear();
    	findAttacker(attackerMap, path, Direction.UP_RIGHT, targetKingPos, 1);
    	path.clear();
    	findAttacker(attackerMap, path, Direction.DOWN_LEFT, targetKingPos, 1);
    	path.clear();
    	findAttacker(attackerMap, path, Direction.DOWN_RIGHT, targetKingPos, 1);
    	
    	//Check Knight
    	char col = targetKingPos.getColumn();
    	int row = targetKingPos.getRow();
		//Check RIGHT
		col+=2;
		Position knightPos = new Position(col, row+1);
		if (isEnemyKnight(checkPlayer, knightPos)) {
			attackerMap.add(knightPos);
		}
		knightPos = new Position(col, row-1);
		if (isEnemyKnight(checkPlayer, knightPos)) {
			attackerMap.add(knightPos);
		}
		//Check LEFT
		col-=4;
		knightPos = new Position(col, row+1);
		if (isEnemyKnight(checkPlayer, knightPos)) {
			attackerMap.add(knightPos);
		}
		knightPos = new Position(col, row-1);
		if (isEnemyKnight(checkPlayer, knightPos)) {
			attackerMap.add(knightPos);
		}
		//Check UP
		col = targetKingPos.getColumn();
		row = targetKingPos.getRow() + 2;
		knightPos = new Position((char) (col+1), row);
		if (isEnemyKnight(checkPlayer, knightPos)) {
			attackerMap.add(knightPos);
		}
		knightPos = new Position((char) (col-1), row);
		if (isEnemyKnight(checkPlayer, knightPos)) {
			attackerMap.add(knightPos);
		}
		//Check DOWN
		row -= 4;
		knightPos = new Position((char) (col+1), row);
		if (isEnemyKnight(checkPlayer, knightPos)) {
			attackerMap.add(knightPos);
		}
		knightPos = new Position((char) (col-1), row);
		if (isEnemyKnight(checkPlayer, knightPos)) {
			attackerMap.add(knightPos);
		}
		
		return attackerMap;
    }
    
    /**
     * Checks if there is an enemy Knight at the position
     * @param player
     * @param p
     * @return
     */
    private boolean isEnemyKnight(Player player, Position p) {
    	if (p.isOnBoard()) {
    		Piece knight = getPieceAt(p);
    		return (knight != null && knight instanceof Knight && knight.getOwner() != player);
    	}
    	return false;
    }

    /**
     * This finds a path to attacker recursively and record the path to it
     * @param attackerMap
     * @param path
     * @param d
     * @param checkPos
     * @param degree
     */
	private void findAttacker(Set<Position> attackerMap, List<Position> path, Direction d, Position checkPos, int degree) {
		char col = checkPos.getColumn();
		int row = checkPos.getRow();
		
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

		Position nextPosition = new Position(col, row);
		if (nextPosition.isOnBoard()) {
			Piece checkPiece = getPieceAt(nextPosition);
			if (checkPiece == null) {  //Empty, keep going
				path.add(nextPosition);
				findAttacker(attackerMap, path, d, nextPosition, degree+1);
			} else {
				if (checkPiece.getOwner() != currentPlayer) {  
					boolean enemyFound = false;
					if (d == Direction.DOWN || d == Direction.UP || d == Direction.LEFT || d == Direction.RIGHT) {
						if (checkPiece instanceof Queen || checkPiece instanceof Rook || (degree == 1 && checkPiece instanceof King)) {
							enemyFound = true;
						}
					} else if (d == Direction.DOWN_LEFT || d == Direction.DOWN_RIGHT || d == Direction.UP_LEFT || d == Direction.UP_RIGHT) {
						if (checkPiece instanceof Queen || checkPiece instanceof Bishop || (degree == 1 && checkPiece instanceof King)) {
							enemyFound = true;
						} else if (degree == 1 && checkPiece instanceof Pawn) { 
							if (checkPiece.getOwner() == Player.White && (d == Direction.DOWN_LEFT || d == Direction.DOWN_RIGHT)) {
								enemyFound = true;
							} else if (checkPiece.getOwner() == Player.Black && (d == Direction.UP_LEFT || d == Direction.UP_RIGHT)) {
								enemyFound = true;
							}
						}
					}
					
					if (enemyFound) {
						path.add(nextPosition);
						attackerMap.addAll(path);
					}
				}
			}
		} 
	}

    /**
     * Method to place a piece at a given position
     * @param piece The piece to place
     * @param position The position
     */
    private void placePiece(Piece piece, Position position) {
    	if (piece instanceof King) {
    		if (piece.getOwner() == Player.Black) {
    			blackKingPos = position;
    		} else {
    			whiteKingPos = position;
    		}
    	}
        positionToPieceMap.put(position, piece);
    }
    
    private void removeAtPosition(Position p) {
    	positionToPieceMap.remove(p);
    }
    
    /**
     * Returns all possible movement by player
     * @param player
     * @return
     */
    private Map<Position, List<Position>> getPossibleMoves(Player checkPlayer) {
    	Map<Position, List<Position>> totalMoves = new HashMap<Position, List<Position>>();
    	
    	for (Map.Entry<Position, Piece> pair : positionToPieceMap.entrySet()) {
    		Piece currentPiece = pair.getValue();
    		if (currentPiece.getOwner() == checkPlayer) {
    			List<Position> possibleMoves = currentPiece.getMoveFinder().findMoves(this, pair.getKey()); 
    			if (possibleMoves != null && possibleMoves.size() > 0) {
    				totalMoves.put(pair.getKey(), possibleMoves);
    			}
    		}
    	}
    	
    	return totalMoves;
    }

    /**
     * 
     * Checks to see if the check player is still in check after making a movement
     * @param checkPlayer
     * @param pieceToMove
     * @param fromPosition
     * @param toPosition
     * @return
     */
    private boolean isInCheckAfterMove(Player checkPlayer, Piece pieceToMove, Position fromPosition, Position toPosition) {
    	boolean stillInCheck = false;
    	//Could be an run and attack, save a reference
    	Piece destinationPiece = getPieceAt(toPosition);
    	//Move
    	removeAtPosition(fromPosition);
    	placePiece(pieceToMove, toPosition);
    	
    	stillInCheck = findAttackerMovement(checkPlayer).size() > 0;

    	//Reverse move
    	placePiece(pieceToMove, fromPosition);
    	if (destinationPiece != null) {  
    		placePiece(destinationPiece, toPosition);
    	} else {
    		removeAtPosition(toPosition);
    	}
    	
    	return stillInCheck;
    }

}
