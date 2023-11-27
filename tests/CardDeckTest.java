import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.*;
import java.util.LinkedList;
import java.util.Scanner;

import static org.junit.Assert.*;

public class CardDeckTest {
    CardDeck emptyDeck;
    CardDeck filledDeck;
    CardDeck oneCardDeck;

    @Before
    public void setUp() throws Exception {
        emptyDeck = new CardDeck();
        filledDeck = new CardDeck();
        oneCardDeck = new CardDeck();
        oneCardDeck.addCard(new Card(1));
        filledDeck.addCard(new Card(1));
        filledDeck.addCard(new Card(2));
    }

    @After
    public void tearDown() throws Exception {
        emptyDeck = null;
        filledDeck = null;
        oneCardDeck = null;
    }
    @Test
    public void testEmptyRemoveCard() {
        Thread addThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored){}
                emptyDeck.addCard(new Card(7));
            }
        });
        addThread.start();
        Card removedCard = emptyDeck.removeCard();
        assertEquals(7, removedCard.getNumber());
        try {
            Field field = ((Object)emptyDeck).getClass().getDeclaredField("DECK");
            field.setAccessible(true);
            LinkedList<Card> deck = (LinkedList<Card>) field.get(emptyDeck);
            assertEquals(0, deck.size());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
    }

    @Test
    public void testOneCardRemoveCard(){
        Card removedCard = oneCardDeck.removeCard();
        assertEquals(1, removedCard.getNumber());
        try {
            Field field = ((Object)oneCardDeck).getClass().getDeclaredField("DECK");
            field.setAccessible(true);
            LinkedList<Card> deck = (LinkedList<Card>) field.get(oneCardDeck);
            assertEquals(0, deck.size());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
    }

    @Test
    public void testFilledRemoveCard(){
        Card removedCard = filledDeck.removeCard();
        assertEquals(1, removedCard.getNumber());
        try {
            Field field = ((Object)filledDeck).getClass().getDeclaredField("DECK");
            field.setAccessible(true);
            LinkedList<Card> deck = (LinkedList<Card>) field.get(filledDeck);
            assertEquals(1, deck.size());
            assertEquals(2, deck.peekFirst().getNumber());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
    }

    @Test
    public void testEmptyAddCard() {
        emptyDeck.addCard(new Card(7));
        try {
            Field field = CardDeck.class.getDeclaredField("DECK");
            field.setAccessible(true);
            LinkedList<Card> deck = (LinkedList<Card>) field.get(emptyDeck);
            assert deck.peekFirst() != null;
            assertEquals(7, deck.peekFirst().getNumber());
            assertEquals(1, deck.size());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
    }

    @Test
    public void testFilledAddCard(){
        filledDeck.addCard(new Card(7));
        try {
            Field field = CardDeck.class.getDeclaredField("DECK");
            field.setAccessible(true);
            LinkedList<Card> deck = (LinkedList<Card>) field.get(filledDeck);
            assertEquals(7, deck.get(2).getNumber());
            assertEquals(3, deck.size());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
    }
    @Test
    public void testOneCardAddCard(){
        oneCardDeck.addCard(new Card(7));
        try {
            Field field = ((Object)oneCardDeck).getClass().getDeclaredField("DECK");
            field.setAccessible(true);
            LinkedList<Card> deck = (LinkedList<Card>) field.get(oneCardDeck);
            assertEquals(7, deck.get(1).getNumber());
            assertEquals(2, deck.size());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
    }
    @Test
    public void testEmptyWriteHistory() {
        emptyDeck.writeHistory();
        try {
            Field field = CardDeck.class.getDeclaredField("DECK_NUM");
            field.setAccessible(true);
            int deckNum = (int) field.get(emptyDeck);
            File emptyDeckFile = new File("deck" + deckNum + "_output.txt");
            Scanner fileScanner = new Scanner(emptyDeckFile);
            assertEquals("deck"+ deckNum + " contents: ", fileScanner.nextLine());
            emptyDeckFile.deleteOnExit();
            fileScanner.close();
        } catch (NoSuchFieldException | IllegalAccessException | FileNotFoundException e) {
            fail();
        }
    }
    @Test
    public void testFilledWriteHistory(){
        filledDeck.writeHistory();
        try {
            Field field = CardDeck.class.getDeclaredField("DECK_NUM");
            field.setAccessible(true);
            int deckNum = (int) field.get(filledDeck);
            File filledDeckFile = new File("deck" +  deckNum + "_output.txt");
            Scanner fileScanner = new Scanner(filledDeckFile);
            assertEquals("deck" + deckNum + " contents: 1 2", fileScanner.nextLine());
            filledDeckFile.deleteOnExit();
            fileScanner.close();
        } catch (NoSuchFieldException | IllegalAccessException | FileNotFoundException e) {
            fail();
        }
    }
}