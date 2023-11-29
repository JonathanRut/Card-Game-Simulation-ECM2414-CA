import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for testing the Card class
 *
 * @author Jonathan Rutland & Daniel Giles
 * @version 1.0
 */
public class CardTest {
    Card boundryCard;
    Card typicalCard;
    @Before
    public void setUp(){
        // The set-up creates 2 cards one boundry card and one you will typically see in the game
        boundryCard = new Card(1);
        typicalCard = new Card(5);
    }

    /**
     * Tests boundary case for get number
     */
    @Test
    public void testBoundaryGetNumber() {
        assertEquals(1, boundryCard.getNumber());
    }

    /**
     * Tests boundary case for to string
     */
    @Test
    public void testBoundaryToString() {
        assertEquals("1", boundryCard.toString());
    }

    /**
     * Tests typical case for get number
     */
    @Test
    public void testTypicalGetNumber() {
        assertEquals(5, typicalCard.getNumber());
    }

    /**
     * Tests typical case for to string
     */
    @Test
    public void testTypicalToString() {
        assertEquals("5", typicalCard.toString());
    }

    @After
    public void tearDown(){
        boundryCard = null;
        typicalCard = null;
    }
}