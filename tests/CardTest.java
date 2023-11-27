import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CardTest {
    Card boundryCard;
    Card typicalCard;
    @Before
    public void setUp(){
        boundryCard = new Card(1);
        typicalCard = new Card(5);
    }

    @Test
    public void testBoundaryGetNumber() {
        assertEquals(1, boundryCard.getNumber());
    }

    @Test
    public void testBoundaryToString() {
        assertEquals("1", boundryCard.toString());
    }

    @Test
    public void testTypicalGetNumber() {
        assertEquals(5, typicalCard.getNumber());
    }

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