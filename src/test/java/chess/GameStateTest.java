package chess;

import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import chess.pieces.Queen;
import chess.pieces.Rook;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;

/**
 * Basic unit tests for the GameState class
 */
public class GameStateTest {

    private GameState state;

    @Before
    public void setUp() {
        state = new GameState();
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
    public void testInitialGame() {
        // Start the game
        state.reset();

        // White should be the first player
        Player current = state.getCurrentPlayer();
        assertEquals("The initial player should be White", Player.White, current);

        // Spot check a few pieces
        Piece whiteRook = state.getPieceAt("a1");
        assertTrue("A rook should be at a1", whiteRook instanceof Rook);
        assertEquals("The rook at a1 should be owned by White", Player.White, whiteRook.getOwner());


        Piece blackQueen = state.getPieceAt("d8");
        assertTrue("A queen should be at d8", blackQueen instanceof Queen);
        assertEquals("The queen at d8 should be owned by Black", Player.Black, blackQueen.getOwner());
    }
    
    @Test
    public void testFoolsMate() {
    	state.reset();
    	assertTrue(state.movePiece("f2", "f3"));
    	assertFalse(state.isOver());
    	assertTrue(state.movePiece("e7", "e6"));
    	assertFalse(state.isOver());
    	assertTrue(state.movePiece("g2", "g4"));
    	assertFalse(state.isOver());
    	assertTrue(state.movePiece("d8", "h4"));
    	assertTrue(state.isOver());

    	state.reset();
    	assertTrue(state.movePiece("d2", "d4"));
    	assertFalse(state.isOver());
    	assertTrue(state.movePiece("f7", "f5"));
    	assertFalse(state.isOver());
    	assertTrue(state.movePiece("c1", "g5"));
    	assertFalse(state.isOver());
    	assertTrue(state.movePiece("h7", "h6"));
    	assertFalse(state.isOver());
    	assertTrue(state.movePiece("g5", "h4"));
    	assertFalse(state.isOver());
    	assertTrue(state.movePiece("g7", "g5"));
    	assertFalse(state.isOver());
    	assertTrue(state.movePiece("h4", "g3"));
    	assertFalse(state.isOver());
    	assertTrue(state.movePiece("f5", "f4"));
    	assertFalse(state.isOver());
    	assertTrue(state.movePiece("e2", "e3"));
    	assertFalse(state.isOver());
    	assertTrue(state.movePiece("h6", "h5"));
    	assertFalse(state.isOver());
    	assertTrue(state.movePiece("f1", "d3"));
    	assertFalse(state.isOver());
    	assertTrue(state.movePiece("h8", "h6"));
    	assertFalse(state.isOver());
    	assertTrue(state.movePiece("d1", "h5"));
    	assertFalse(state.isOver());
    	assertTrue(state.movePiece("h6", "h5"));
    	assertFalse(state.isOver());
    	assertTrue(state.movePiece("d3", "g6"));
    	assertTrue(state.isOver());

    	state.reset();
    	assertTrue(state.movePiece("e2", "e4"));
    	assertFalse(state.isOver());
    	assertTrue(state.movePiece("g7", "g5"));
    	assertFalse(state.isOver());
    	assertTrue(state.movePiece("d2", "d4"));
    	assertFalse(state.isOver());
    	assertTrue(state.movePiece("f7", "f6"));
    	assertFalse(state.isOver());
    	assertTrue(state.movePiece("d1", "h5"));
    	assertTrue(state.isOver());


    }

    @Test
    public void testManualCheckMate() {
    	TestableGameState testState = new TestableGameState();

    	// Two-way attack
    	testState.whiteKingPos = new Position("e1");
    	testState.blackKingPos = new Position("d8");
    	testState.placePiece(new King(Player.White), new Position("e1"));
    	testState.placePiece(new Pawn(Player.White), new Position("d1"));
    	testState.placePiece(new Pawn(Player.White), new Position("d2"));
    	testState.placePiece(new Pawn(Player.White), new Position("f1"));
    	testState.placePiece(new Pawn(Player.White), new Position("a2"));
    	testState.placePiece(new King(Player.Black), new Position("d8"));
    	testState.placePiece(new Rook(Player.Black), new Position("e8"));
    	testState.placePiece(new Queen(Player.Black), new Position("e7"));
    	assertTrue(testState.movePiece("a2", "a4"));
    	assertFalse(testState.isOver());
    	assertTrue(testState.movePiece("e7", "h4"));
    	assertTrue(testState.isOver());

    	// Two-way attack, with one attacker attacked
    	testState.clearBoard();
    	testState.whiteKingPos = new Position("e1");
    	testState.blackKingPos = new Position("d8");
    	testState.placePiece(new King(Player.White), new Position("e1"));
    	testState.placePiece(new Pawn(Player.White), new Position("d1"));
    	testState.placePiece(new Pawn(Player.White), new Position("d2"));
    	testState.placePiece(new Pawn(Player.White), new Position("f1"));
    	testState.placePiece(new Pawn(Player.White), new Position("a2"));
    	testState.placePiece(new Rook(Player.White), new Position("h1"));
    	testState.placePiece(new King(Player.Black), new Position("d8"));
    	testState.placePiece(new Rook(Player.Black), new Position("e8"));
    	testState.placePiece(new Queen(Player.Black), new Position("e7"));
    	assertTrue(testState.movePiece("a2", "a4"));
    	assertFalse(testState.isOver());
    	assertTrue(testState.movePiece("e7", "h4"));
    	assertTrue(testState.isOver());

    	// There is a chance to recover from check by attacking the attacker
    	testState.clearBoard();
    	testState.whiteKingPos = new Position("e1");
    	testState.blackKingPos = new Position("d8");
    	testState.placePiece(new King(Player.White), new Position("e1"));
    	testState.placePiece(new Pawn(Player.White), new Position("d1"));
    	testState.placePiece(new Pawn(Player.White), new Position("d2"));
    	testState.placePiece(new Pawn(Player.White), new Position("e2"));
    	testState.placePiece(new Knight(Player.White), new Position("f1"));
    	testState.placePiece(new Pawn(Player.White), new Position("a2"));
    	testState.placePiece(new Rook(Player.White), new Position("h1"));
    	testState.placePiece(new King(Player.Black), new Position("d8"));
    	testState.placePiece(new Queen(Player.Black), new Position("e7"));
    	assertTrue(testState.movePiece("a2", "a4"));
    	assertFalse(testState.isOver());
    	assertTrue(testState.movePiece("e7", "h4"));
    	assertFalse(testState.isOver());

    	// There is a chance to recover from check by blocking the attacker
    	testState.clearBoard();
    	testState.whiteKingPos = new Position("e1");
    	testState.blackKingPos = new Position("d8");
    	testState.placePiece(new King(Player.White), new Position("e1"));
    	testState.placePiece(new Pawn(Player.White), new Position("d1"));
    	testState.placePiece(new Pawn(Player.White), new Position("d2"));
    	testState.placePiece(new Pawn(Player.White), new Position("e2"));
    	testState.placePiece(new Rook(Player.White), new Position("f1"));
    	testState.placePiece(new Pawn(Player.White), new Position("a2"));
    	testState.placePiece(new King(Player.Black), new Position("d8"));
    	testState.placePiece(new Queen(Player.Black), new Position("e7"));
    	assertTrue(testState.movePiece("a2", "a4"));
    	assertFalse(testState.isOver());
    	assertTrue(testState.movePiece("e7", "h4"));
    	assertFalse(testState.isOver());

    	// Two-way attack, King will be able to run and attack
    	testState.clearBoard();
    	testState.whiteKingPos = new Position("e1");
    	testState.blackKingPos = new Position("d8");
    	testState.placePiece(new King(Player.White), new Position("e1"));
    	testState.placePiece(new Pawn(Player.White), new Position("a2"));
    	testState.placePiece(new Pawn(Player.White), new Position("d1"));
    	testState.placePiece(new Pawn(Player.White), new Position("d2"));
    	testState.placePiece(new Pawn(Player.White), new Position("f1"));
    	testState.placePiece(new King(Player.Black), new Position("d8"));
    	testState.placePiece(new Pawn(Player.Black), new Position("f3"));
    	testState.placePiece(new Rook(Player.Black), new Position("e8"));
    	assertTrue(testState.movePiece("a2", "a4"));
    	assertFalse(testState.isOver());
    	assertTrue(testState.movePiece("f3", "f2"));
    	assertFalse(testState.isOver());

    	// Two-way attack, King will be able to run and attack
    	testState.clearBoard();
    	testState.whiteKingPos = new Position("d1");
    	testState.blackKingPos = new Position("c8");
    	testState.placePiece(new King(Player.White), new Position("d1"));
    	testState.placePiece(new Pawn(Player.White), new Position("a2"));
    	testState.placePiece(new Pawn(Player.White), new Position("c1"));
    	testState.placePiece(new Pawn(Player.White), new Position("c2"));
    	testState.placePiece(new Pawn(Player.White), new Position("e2"));
    	testState.placePiece(new King(Player.Black), new Position("c8"));
    	testState.placePiece(new Pawn(Player.Black), new Position("f2"));
    	testState.placePiece(new Rook(Player.Black), new Position("e8"));
    	assertTrue(testState.movePiece("a2", "a4"));
    	assertFalse(testState.isOver());
    	assertTrue(testState.movePiece("e8", "d8"));
    	assertTrue(testState.isOver());

    	// Knight attack
    	testState.clearBoard();
    	testState.whiteKingPos = new Position("e1");
    	testState.blackKingPos = new Position("d8");
    	testState.placePiece(new King(Player.White), new Position("e1"));
    	testState.placePiece(new Rook(Player.White), new Position("e2"));
    	testState.placePiece(new Knight(Player.White), new Position("d1"));
    	testState.placePiece(new Knight(Player.White), new Position("d2"));
    	testState.placePiece(new Pawn(Player.White), new Position("f2"));
    	testState.placePiece(new Pawn(Player.White), new Position("f1"));
    	testState.placePiece(new Pawn(Player.White), new Position("a2"));
    	testState.placePiece(new King(Player.Black), new Position("d8"));
    	testState.placePiece(new Knight(Player.Black), new Position("c5"));
    	assertTrue(testState.movePiece("a2", "a4"));
    	assertFalse(testState.isOver());
    	assertTrue(testState.movePiece("c5", "d3"));
    	assertTrue(testState.isOver());

    	// Knight attack
    	testState.clearBoard();
    	testState.whiteKingPos = new Position("e1");
    	testState.blackKingPos = new Position("d8");
    	testState.placePiece(new King(Player.White), new Position("e1"));
    	testState.placePiece(new Rook(Player.White), new Position("e2"));
    	testState.placePiece(new Rook(Player.White), new Position("d1"));
    	testState.placePiece(new Rook(Player.White), new Position("d2"));
    	testState.placePiece(new Pawn(Player.White), new Position("f2"));
    	testState.placePiece(new Pawn(Player.White), new Position("f1"));
    	testState.placePiece(new Pawn(Player.White), new Position("a2"));
    	testState.placePiece(new King(Player.Black), new Position("d8"));
    	testState.placePiece(new Knight(Player.Black), new Position("h4"));
    	assertTrue(testState.movePiece("a2", "a4"));
    	assertFalse(testState.isOver());
    	assertTrue(testState.movePiece("h4", "f3"));
    	assertTrue(testState.isOver());
    }
}
