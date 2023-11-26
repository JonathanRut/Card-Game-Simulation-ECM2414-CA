import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.*;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class CardDeckTest {
    CardDeck emptyDeck;
    CardDeck filledDeck;

    @Before
    public void setUp() throws Exception {
        emptyDeck = new CardDeck();
        filledDeck = new CardDeck();
        filledDeck.addCard(new Card(1));
        filledDeck.addCard(new Card(2));
    }

    @After
    public void tearDown() throws Exception {
        emptyDeck = null;
        filledDeck = null;
    }

    @Test
    public void removeCard() {
    }

    @Test
    public void testEmptyAddCard() {
        emptyDeck.addCard(new Card(7));
        Field field = null;
        try {
            field = ((Object)emptyDeck).getClass().getDeclaredField("DECK");
        } catch (NoSuchFieldException e) {
            fail();
        }
        field.setAccessible(true);
        LinkedList<Card> deck = null;
        try {
            deck = (LinkedList<Card>) field.get(emptyDeck);
        } catch (IllegalAccessException e) {
            fail();
        }
        assertEquals(deck.size(), 1);

    }

    @Test
    public void testFilledAddCard(){
        filledDeck.addCard(new Card(7));
        Field field = null;
        try {
            field = ((Object)filledDeck).getClass().getDeclaredField("DECK");
        } catch (NoSuchFieldException e) {
            fail();
        }
        field.setAccessible(true);
        LinkedList<Card> deck = null;
        try {
            deck = (LinkedList<Card>) field.get(filledDeck);
        } catch (IllegalAccessException e) {
            fail();
        }
        assertEquals(deck.size(), 3);
    }

    @Test
    public void writeHistory() {
    }
}