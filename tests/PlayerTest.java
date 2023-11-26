import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    @Before
    public void setUp() throws Exception {
        CardDeck deck1 = new CardDeck();
        CardDeck deck2 = new CardDeck();
        Player emptyPlayer = new Player(deck1, deck2);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void writeHistory() {
    }

    @Test
    public void makeTurn() {
    }

    @Test
    public void receiveCard() {
    }

    @Test
    public void getPlayerNumber() {
    }
}