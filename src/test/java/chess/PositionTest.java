package chess;

import org.junit.Test;

import static junit.framework.Assert.*;

/**
 * Basic Unit Tests for the Position class
 */
public class PositionTest {

    @Test
    public void testStringParsingConstructor() {
        Position pos = new Position("d5");

        assertEquals("The column should be 'd'", 'd', pos.getColumn());
        assertEquals("The row should be 5", 5, pos.getRow());
    }

    @Test
    public void testPositionEquality() {
        Position one = new Position('a', 1);
        Position other = new Position('a', 1);

        assertEquals("The positions should equal each other", one, other);
    }
}
