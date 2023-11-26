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
        File filledDeckFile = new File("deck2_output.txt");
        filledDeckFile.delete();
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
        emptyDeck.removeCard();
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
        assertEquals(deck.size(), 0);
    }

    @Test
    public void testOneCardRemoveCard(){
        oneCardDeck.removeCard();
        Field field = null;
        try {
            field = ((Object)oneCardDeck).getClass().getDeclaredField("DECK");
        } catch (NoSuchFieldException e) {
            fail();
        }
        field.setAccessible(true);
        LinkedList<Card> deck = null;
        try {
            deck = (LinkedList<Card>) field.get(oneCardDeck);
        } catch (IllegalAccessException e) {
            fail();
        }
        assertEquals(deck.size(), 0);
    }

    @Test
    public void testFilledRemoveCard(){
        filledDeck.removeCard();
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
        assertEquals(deck.size(), 1);
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
    public void testOneCardAddCard(){
        oneCardDeck.addCard(new Card(7));
        Field field = null;
        try {
            field = ((Object)oneCardDeck).getClass().getDeclaredField("DECK");
        } catch (NoSuchFieldException e) {
            fail();
        }
        field.setAccessible(true);
        LinkedList<Card> deck = null;
        try {
            deck = (LinkedList<Card>) field.get(oneCardDeck);
        } catch (IllegalAccessException e) {
            fail();
        }
        assertEquals(deck.size(), 2);
    }
    @Test
    public void testEmptyWriteHistory() {
        emptyDeck.writeHistory();
        Field field = null;
        try {
            field = ((Object)emptyDeck).getClass().getDeclaredField("DECK_NUM");
        } catch (NoSuchFieldException e) {
            fail();
        }
        field.setAccessible(true);
        int deckNum = 0;
        try {
            deckNum = (int) field.get(emptyDeck);
        } catch (IllegalAccessException e) {
            fail();
        }
        File emptyDeckFile = new File("deck" + deckNum + "_output.txt");
        try {
            Scanner fileScanner = new Scanner(emptyDeckFile);
            assertEquals(fileScanner.nextLine(), "deck"+ deckNum + " contents: ");
        } catch (FileNotFoundException e) {
            fail();
        }
        emptyDeckFile.delete();
    }
    @Test
    public void testFilledWriteHistory(){
        filledDeck.writeHistory();
        Field field = null;
        try {
            field = ((Object)filledDeck).getClass().getDeclaredField("DECK_NUM");
        } catch (NoSuchFieldException e) {
            fail();
        }
        field.setAccessible(true);
        int deckNum = 0;
        try {
            deckNum = (int) field.get(filledDeck);
        } catch (IllegalAccessException e) {
            fail();
        }
        File filledDeckFile = new File("deck" +  deckNum + "_output.txt");
        try {
            Scanner fileScanner = new Scanner(filledDeckFile);
            assertEquals(fileScanner.nextLine(), "deck" + deckNum + " contents: 1 2");
        } catch (FileNotFoundException e) {
            fail();
        }
        filledDeckFile.delete();
    }
}