package chess.move;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import chess.Player;
import chess.Position;
import chess.TestableGameState;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import chess.pieces.Queen;
import chess.pieces.Rook;
import static junit.framework.Assert.*;

public class MoveFinderTest {
	TestableGameState state;

    @Before
    public void setUp() {
        state = new TestableGameState();
    }

    @Test
    public void testStartsEmpty() {
        // Make sure all the positions are empty
        for (char col = Position.MIN_COLUMN; col <= Position.MAX_COLUMN; col++) {
            for (int row = Position.MIN_ROW; row <= Position.MAX_ROW; row++) {
                assertNull("All pieces should be empty", state.getPieceAt(String.valueOf(col) + row));
            }
        }
    }

    @Test
    public void testBishopRookMovement() {
        // Clear the board
        state.clearBoard();

        // Check and test Bishop
        Position pos = new Position("b1");
        state.placePiece(new Bishop(Player.White), pos);
        Piece piece = state.getPieceAt(pos);
        List<Position> possible = piece.getMoveFinder().findMoves(state, pos);
        Set<Position> checkPos = new HashSet<Position>();
        for (int i=1; i<7; i++) {
        	checkPos.add(new Position((char) (pos.getColumn()+i), pos.getRow()+i));
        }
        checkPos.add(new Position("a2"));
        checkMoves(checkPos, possible);

        // Place a foe at g6
        Position foePos = new Position("g6");
        state.placePiece(new Pawn(Player.Black), foePos);
        Piece foe = state.getPieceAt(foePos);
        assertTrue("A pawn should be at g6", foe instanceof Pawn);
        possible = piece.getMoveFinder().findMoves(state, pos);
        checkPos.remove(new Position("h7"));
        checkMoves(checkPos, possible);

        // Clear the board
        state.clearBoard();

        // Check and test Rook
        pos = new Position("g1");
        state.placePiece(new Rook(Player.White), pos);
        piece = state.getPieceAt(pos);
        possible = piece.getMoveFinder().findMoves(state, pos);
        checkPos = new HashSet<Position>();
        for (char i='f'; i>=Position.MIN_COLUMN; i--) {
        	checkPos.add(new Position(i, 1));
        }
        for (int i=2; i<=Position.MAX_ROW; i++) {
        	checkPos.add(new Position('g', i));
        }
        checkPos.add(new Position("h1"));
        checkMoves(checkPos, possible);

        // Place a friend at g6
        Position friendPos = new Position("g6");
        state.placePiece(new Pawn(Player.White), friendPos);
        Piece friend = state.getPieceAt(friendPos);
        assertTrue("A pawn should be at g6", friend instanceof Pawn);
        possible = piece.getMoveFinder().findMoves(state, pos);
        checkPos.remove(new Position("g6"));
        checkPos.remove(new Position("g7"));
        checkPos.remove(new Position("g8"));
        checkMoves(checkPos, possible);
    }

    @Test
    public void testKnightMovement() {
        // Start the game
        state.reset();

        // Check and test knight
        Position knightPos = new Position("g1");
        Piece knight = state.getPieceAt(knightPos);
        List<Position> possible = knight.getMoveFinder().findMoves(state, knightPos);
        checkMoves(createCheckList(new String[] {"f3", "h3"}), possible);

        // Place a friend at h3
        Position friendPawnPos = new Position("h3");
        state.placePiece(new Pawn(Player.White), friendPawnPos);
        Piece friendPawn= state.getPieceAt(friendPawnPos);
        assertTrue("A pawn should be at h3", friendPawn instanceof Pawn);
        possible = knight.getMoveFinder().findMoves(state, knightPos);
        checkMoves(createCheckList(new String[] {"f3"}), possible);

        //Replace the friend with a foe
        Position foePosition = new Position("h3");
        state.placePiece(new Knight(Player.Black), foePosition);
        Piece foe = state.getPieceAt(foePosition);
        state.placePiece(foe, foePosition);
        assertTrue("A Knight should be at e3", foe instanceof Knight);
        possible = knight.getMoveFinder().findMoves(state, knightPos);
        checkMoves(createCheckList(new String[] {"f3", "h3"}), possible);
        //Remove foe
        state.removePiece(foePosition);
        
        //Now move the knight to d3
        knightPos = movePiece("g1", "d3");
        knight = state.getPieceAt(knightPos);
        assertTrue("A Knight should be at d3", knight instanceof Knight);
        possible = knight.getMoveFinder().findMoves(state, knightPos);
        checkMoves(createCheckList(new String[] {"c5", "e5", "b4", "f4"}), possible);

    }

    @Test
    public void testPawnStartMovement() {
        // Start the game
        state.reset();

        // Check and test pawn
        Position pawnPos = new Position("e2");
        Piece pawn = state.getPieceAt(pawnPos);
        List<Position> possible = pawn.getMoveFinder().findMoves(state, pawnPos);
        assertEquals("Pawn should have 2 moves", 2, possible.size());

        // Place a friend at e3
        Position friendPawnPos = new Position("e3");
        state.placePiece(new Pawn(Player.White), friendPawnPos);
        Piece friendPawn= state.getPieceAt(friendPawnPos);
        assertTrue("A pawn should be at e3", friendPawn instanceof Pawn);
        possible = pawn.getMoveFinder().findMoves(state, pawnPos);
        Set<Position> checkList = new HashSet<Position>();
        checkMoves(checkList, possible);

        //Replace the friend to a foe
        Position foePosition = new Position("e3");
        state.placePiece(new Knight(Player.Black), foePosition);
        Piece foe = state.getPieceAt(foePosition);
        state.placePiece(foe, foePosition);
        assertTrue("A Knight should be at e3", foe instanceof Knight);
        possible = pawn.getMoveFinder().findMoves(state, pawnPos);
        //Cannot attack the knight
        checkMoves(checkList, possible);
        
        //Now move the foe to f3
        foePosition = movePiece("e3", "f3");
        foe = state.getPieceAt(foePosition);
        assertTrue("A Knight should be at f3", foe instanceof Knight);

        possible = pawn.getMoveFinder().findMoves(state, pawnPos);
        //Can attack the knight
        checkList.addAll(createCheckList(new String[] {"e3", "e4", "f3"}));
        checkMoves(checkList, possible);

        //Remove the knight
        state.removePiece(foePosition);
        possible = pawn.getMoveFinder().findMoves(state, pawnPos);
        checkList.remove(new Position("f3"));
        checkMoves(checkList, possible);
        
    }

    @Test
    public void testPawnRegularMovement() {
        // Clear the board
        state.clearBoard();

        // Add a pawn
        Position pawnPos = new Position("e3");
        state.placePiece(new Pawn(Player.White), pawnPos);
        Piece pawn = state.getPieceAt(pawnPos);
        assertTrue("A pawn should be at e3", pawn instanceof Pawn);

        // Can only move one
        List<Position> possible = pawn.getMoveFinder().findMoves(state, pawnPos);
        Set<Position> checkList = new HashSet<Position>();
        checkMoves(createCheckList(new String[] {"e4"}), possible);

        //Block the pawn with enemy
        Position foePosition = new Position("e4");
        state.placePiece(new Knight(Player.Black), foePosition);
        Piece foe = state.getPieceAt(foePosition);
        state.placePiece(foe, foePosition);
        assertTrue("A Knight should be at e4", foe instanceof Knight);
        possible = pawn.getMoveFinder().findMoves(state, pawnPos);
        checkMoves(null, possible);
        
        //Now move the foe and put it at f4
        foePosition = movePiece("e4", "f4");
        foe = state.getPieceAt(foePosition);
        assertTrue("A Knight should be at f4", foe instanceof Knight);
        possible = pawn.getMoveFinder().findMoves(state, pawnPos);
        checkList.addAll(createCheckList(new String[] {"e4", "f4"}));
        checkMoves(checkList, possible);

        //Remove the knight
        state.removePiece(foePosition);
        possible = pawn.getMoveFinder().findMoves(state, pawnPos);
        checkList.remove(new Position("f4"));
        checkMoves(checkList, possible);
        
    }

    @Test
    public void testKingMovement() {
        // Start the game
        state.reset();

        // Check and test queen
        Position kingPos = new Position("e1");
        Piece whiteKing = state.getPieceAt(kingPos);
        assertTrue("A king should be at e1", whiteKing instanceof King);
        assertEquals("The king at e1 should be owned by White", Player.White, whiteKing.getOwner());
        List<Position> possible = whiteKing.getMoveFinder().findMoves(state, kingPos);
        assertEquals("King should not be able to move", 0, possible.size());

        // Clear board and place king at e2, and a friend at e3, d2, f2
        state.clearBoard();
        kingPos = new Position("e2");
        state.placePiece(new King(Player.White), kingPos);
        whiteKing = state.getPieceAt(kingPos);
        assertTrue("A king should be at e2", whiteKing instanceof King);
        Position pawn1Position = new Position("e3");
        Position pawn2Position = new Position("d2");
        Position pawn3Position = new Position("f2");
        state.placePiece(new Pawn(Player.White), pawn1Position);
        state.placePiece(new Pawn(Player.White), pawn2Position);
        state.placePiece(new Pawn(Player.White), pawn3Position);
        Piece pawn = state.getPieceAt(pawn1Position);
        assertTrue("A pawn should be at e3", pawn instanceof Pawn);
        possible = whiteKing.getMoveFinder().findMoves(state, kingPos);
        Set<Position> checkList = createCheckList(new String[] {"d3", "f3", "d1", "e1", "f1"}); 
        checkMoves(checkList, possible);

        //Replace the friend to a foe
        Position foePosition = new Position("f2");
        state.placePiece(new Knight(Player.Black), foePosition);
        Piece foe = state.getPieceAt("f2");
        state.placePiece(foe, foePosition);
        assertTrue("A Knight should be at f2", foe instanceof Knight);
        possible = whiteKing.getMoveFinder().findMoves(state, kingPos);
        //Can attack the knight, add the knight to check list
        checkList.add(new Position("f2"));
        checkMoves(checkList, possible);
        
        //Now remove the foe
        state.removePiece(new Position("e3"));
        state.removePiece(new Position("d2"));
        possible = whiteKing.getMoveFinder().findMoves(state, kingPos);
        checkList.addAll(createCheckList(new String[] {"e3", "d2", "f2"}));
        checkMoves(checkList, possible);
    }

    @Test
    public void testQueenMovement() {
        // Start the game
        state.reset();

        // Check and test queen
        Piece blackQueen = state.getPieceAt("d8");
        assertTrue("A queen should be at d8", blackQueen instanceof Queen);
        assertEquals("The queen at d8 should be owned by Black", Player.Black, blackQueen.getOwner());

        List<Position> possible = blackQueen.getMoveFinder().findMoves(state, new Position('d', 8));
        assertEquals("Queen should not be able to move", 0, possible.size());

        // Clear board and place queen at a8, and a friend at b7
        state.clearBoard();
        Position queenPos = new Position("a8");
        state.placePiece(new Queen(Player.Black), queenPos);
        blackQueen = state.getPieceAt("a8");
        assertTrue("A queen should be at a8", blackQueen instanceof Queen);
        Position pawnPosition = new Position("b7");
        state.placePiece(new Pawn(Player.Black), pawnPosition);
        Piece pawn = state.getPieceAt("b7");
        assertTrue("A pawn should be at b7", pawn instanceof Pawn);
        possible = blackQueen.getMoveFinder().findMoves(state, queenPos);
        Set<Position> checkPos = new HashSet<Position>();
        for (char i='b'; i<=Position.MAX_COLUMN; i++) {
        	checkPos.add(new Position(i, 8));
        }
        for (int i=7; i>=Position.MIN_ROW; i--) {
        	checkPos.add(new Position('a', i));
        }
        checkMoves(checkPos, possible);

        //Replace the friend to a foe
        Position foePosition = new Position("b7");
        state.placePiece(new Knight(Player.White), foePosition);
        Piece foe = state.getPieceAt("b7");
        state.placePiece(foe, foePosition);
        assertTrue("A Knight should be at b7", foe instanceof Knight);

        possible = blackQueen.getMoveFinder().findMoves(state, queenPos);
        //Can attack the knight, add the knight to check list
        checkPos.add(new Position('b', 7));
        checkMoves(checkPos, possible);
        
        //Now remove the foe
        state.removePiece(foePosition);
        possible = blackQueen.getMoveFinder().findMoves(state, queenPos);
        for (int i=1; i<Position.MAX_ROW; i++) {
        	checkPos.add(new Position((char) (queenPos.getColumn()+i), queenPos.getRow()-i));
        }
        checkMoves(checkPos, possible);
    }
    
    private void checkMoves(Set<Position> checkList, List<Position> movement) {
    	if (checkList == null) {
    		assertEquals("Should have no movement", 0, movement.size());
    	} else {
    		assertEquals("Number of moves is wrong.", checkList.size(), movement.size());
    		for (Position p : checkList) {
    			if (!movement.contains(p)) {
    				fail("Missing possible move: "+p.toString());
    			}
    		}
    	}
    }
    
    private Set<Position> createCheckList(String[] positions) {
    	Set<Position> set = new HashSet<Position>();
    	for (String p : positions) {
    		set.add(new Position(p));
    	}
    	
    	return set;
    }
    
    private Position movePiece(String from, String to) {
    	Position fromPos = new Position(from);
    	Position toPos = new Position(to);
    	Piece piece = state.getPieceAt(fromPos);
    	state.removePiece(fromPos);
    	state.placePiece(piece, toPos);
    	
    	return toPos;
    }
}
