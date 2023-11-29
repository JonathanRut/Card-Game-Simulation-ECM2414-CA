import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.*;
import java.util.LinkedList;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * This Test clas tests the CardDeck class
 */
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

    /**
     * Test for when trying to remove card from an empty deck
     */
    @Test
    public void testEmptyRemoveCard() {
        // The add thread should add a card to the deck after 1 second
        Thread addThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored){}
                emptyDeck.addCard(new Card(7));
            }
        });
        // The thread is started then card is tried to be removed, the main thread should wait until a card has been added
        addThread.start();
        Card removedCard = emptyDeck.removeCard();
        // Assertion makes sure the right card was successfully removed
        assertEquals(7, removedCard.getNumber());
        try {
            // Reflection is used to see if the deck is empty
            Field field = ((Object)emptyDeck).getClass().getDeclaredField("DECK");
            field.setAccessible(true);
            LinkedList<Card> deck = (LinkedList<Card>) field.get(emptyDeck);
            assertEquals(0, deck.size());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
    }

    /**
     * Test for when trying to remove from deck with one card
     */
    @Test
    public void testOneCardRemoveCard(){
        // Card is removed and assertion ensure right card was removed
        Card removedCard = oneCardDeck.removeCard();
        assertEquals(1, removedCard.getNumber());
        try {
            // Reflection is taken to see if the deck is empty
            Field field = ((Object)oneCardDeck).getClass().getDeclaredField("DECK");
            field.setAccessible(true);
            LinkedList<Card> deck = (LinkedList<Card>) field.get(oneCardDeck);
            assertEquals(0, deck.size());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
    }

    /**
     * Test for when trying to remove card from a filled deck
     */
    @Test
    public void testFilledRemoveCard(){
        // Card is removed and assertion ensure right card was removed
        Card removedCard = filledDeck.removeCard();
        assertEquals(1, removedCard.getNumber());
        try {
            // Reflection is taken to see if the deck is correct size and contains the correct card
            Field field = ((Object)filledDeck).getClass().getDeclaredField("DECK");
            field.setAccessible(true);
            LinkedList<Card> deck = (LinkedList<Card>) field.get(filledDeck);
            assertEquals(1, deck.size());
            assertEquals(2, deck.peekFirst().getNumber());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
    }

    /**
     * Test fro when trying to add a card to a empty deck
     */
    @Test
    public void testEmptyAddCard() {
        // A card with value of 7 is added to the deck
        emptyDeck.addCard(new Card(7));
        try {
            // Reflection is taken to see if the deck is correct size and contains the correct card
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

    /**
     * Test for when trying to add card to filled deck
     */
    @Test
    public void testFilledAddCard(){
        // A card with value of 7 is added to the deck
        filledDeck.addCard(new Card(7));
        try {
            // Reflection is taken to see if the deck is correct size and contains the correct card
            Field field = CardDeck.class.getDeclaredField("DECK");
            field.setAccessible(true);
            LinkedList<Card> deck = (LinkedList<Card>) field.get(filledDeck);
            assertEquals(7, deck.get(2).getNumber());
            assertEquals(3, deck.size());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
    }

    /**
     * Test for when adding a card to a deck with one card
     */
    @Test
    public void testOneCardAddCard(){
        // A card with value 7 is added to the deck
        oneCardDeck.addCard(new Card(7));
        try {
            // Reflection is taken to see if the deck is correct size and contains the correct card
            Field field = ((Object)oneCardDeck).getClass().getDeclaredField("DECK");
            field.setAccessible(true);
            LinkedList<Card> deck = (LinkedList<Card>) field.get(oneCardDeck);
            assertEquals(7, deck.get(1).getNumber());
            assertEquals(2, deck.size());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
    }

    /**
     * Test for when trying to write history of empty deck
     */
    @Test
    public void testEmptyWriteContent() {
        // The empty decks history is written
        emptyDeck.writeContent();
        try {
            // Reflection is taken to get the decks number
            Field field = CardDeck.class.getDeclaredField("DECK_NUM");
            field.setAccessible(true);
            int deckNum = (int) field.get(emptyDeck);

            // The file is read and assertion ensure its content is correct
            File emptyDeckFile = new File("deck" + deckNum + "_output.txt");
            Scanner fileScanner = new Scanner(emptyDeckFile);
            assertEquals("deck"+ deckNum + " contents: ", fileScanner.nextLine());

            // The scanner is then closed and file is deleted
            fileScanner.close();
            emptyDeckFile.delete();
        } catch (NoSuchFieldException | IllegalAccessException | FileNotFoundException e) {
            fail();
        }
    }

    /**
     * Test for when writing the history of a filled deck
     */
    @Test
    public void testFilledWriteContent(){
        // The filed decks history is written
        filledDeck.writeContent();
        try {
            // Reflection is taken for the decks number
            Field field = CardDeck.class.getDeclaredField("DECK_NUM");
            field.setAccessible(true);
            int deckNum = (int) field.get(filledDeck);

            // The file is read and assertion ensures the content is correct
            File filledDeckFile = new File("deck" +  deckNum + "_output.txt");
            Scanner fileScanner = new Scanner(filledDeckFile);
            assertEquals("deck" + deckNum + " contents: 1 2", fileScanner.nextLine());

            // The scanner is closed and file is deleted
            fileScanner.close();
            filledDeckFile.delete();
        } catch (NoSuchFieldException | IllegalAccessException | FileNotFoundException e) {
            fail();
        }
    }
}