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
        assertEquals(boundryCard.getNumber(), 1);
    }

    @Test
    public void testBoundaryToString() {
        assertEquals(boundryCard.toString(), "1");
    }

    @Test
    public void testTypicalGetNumber() {
        assertEquals(typicalCard.getNumber(), 5);
    }

    @Test
    public void testTypicalToString() {
        assertEquals(typicalCard.toString(), "5");
    }

    @After
    public void tearDown(){
        boundryCard = null;
        typicalCard = null;
    }
}