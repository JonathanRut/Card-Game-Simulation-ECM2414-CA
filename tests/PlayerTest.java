import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.*;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class PlayerTest {


    private CardDeck drawDeck0;
    private CardDeck discardDeck0;
    private Player emptyDrawPlayer;
    private CardDeck drawDeck1;
    private Player oneDrawPlayer;

    @Before
    public void setUp() throws Exception {
        drawDeck0 = new CardDeck();
        discardDeck0 = new CardDeck();
        emptyDrawPlayer = new Player(drawDeck0, discardDeck0);
        drawDeck1 = new CardDeck();
        drawDeck1.addCard(new Card(2));
        oneDrawPlayer = new Player(drawDeck1, discardDeck0);
    }

    @After
    public void tearDown() throws Exception {
        drawDeck0 = null;
        discardDeck0 = null;
        emptyDrawPlayer = null;
        drawDeck1 = null;
        oneDrawPlayer = null;
    }

    @Test
    public void testEmptyDraw() {
        Thread addCardThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {

                }
                drawDeck0.addCard(new Card(7));
            }
        });
        addCardThread.start();
        try {
            Method draw = Player.class.getDeclaredMethod("draw");
            draw.setAccessible(true);
            draw.invoke(emptyDrawPlayer);
            Field field = Player.class.getDeclaredField("HAND");
            field.setAccessible(true);
            LinkedList<Card> hand = (LinkedList<Card>) field.get(emptyDrawPlayer);
            assert hand.peekFirst() != null;
            assertEquals(hand.peekFirst().getNumber(), 7);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void testOneDraw() {
        try {
            Method draw = Player.class.getDeclaredMethod("draw");
            draw.setAccessible(true);
            draw.invoke(oneDrawPlayer);

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            fail();
        }

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