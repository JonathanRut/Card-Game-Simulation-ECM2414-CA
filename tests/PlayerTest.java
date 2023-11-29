import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import static org.junit.Assert.*;

public class PlayerTest {


    private CardDeck emptyDrawDeck;
    private CardDeck emptyDiscardDeck;
    private Player emptyPlayer;
    private CardDeck oneCardDrawDeck;
    private Player oneDrawPlayer;
    private CardDeck filledDrawDeck;
    private Player filledDrawPlayer;
    private CardDeck filledDiscardDeck;
    private Player filledPlayer;
    private Player nearlyFullPlayer;
    private Player preferredWinner;
    private Player nonPreferredWinner;
    private Player oneMovePlayer;
    private CardDeck oneMoveDraw;
    private CardDeck oneMoveDiscard;

    @Before
    public void setUp() throws Exception {
        emptyDrawDeck = new CardDeck();
        emptyDiscardDeck = new CardDeck();
        emptyPlayer = new Player(emptyDrawDeck, emptyDiscardDeck);

        oneCardDrawDeck = new CardDeck();
        oneCardDrawDeck.addCard(new Card(1));
        oneDrawPlayer = new Player(oneCardDrawDeck, emptyDiscardDeck);

        filledDrawDeck = new CardDeck();
        filledDrawDeck.addCard(new Card(1));
        filledDrawDeck.addCard(new Card(2));
        filledDrawDeck.addCard(new Card(3));
        filledDrawPlayer = new Player(filledDrawDeck, emptyDiscardDeck);

        filledDiscardDeck = new CardDeck();
        filledDiscardDeck.addCard(new Card(1));
        filledDiscardDeck.addCard(new Card(2));
        filledDiscardDeck.addCard(new Card(3));
        filledPlayer = new Player(filledDrawDeck, filledDiscardDeck);
        filledPlayer.receiveCard(new Card(1));
        filledPlayer.receiveCard(new Card(2));
        filledPlayer.receiveCard(new Card(3));
        filledPlayer.receiveCard(new Card(4));

        oneMoveDraw = new CardDeck();
        oneMoveDraw.addCard(new Card(1));
        oneMoveDraw.addCard(new Card(2));
        oneMoveDraw.addCard(new Card(3));

        oneMoveDiscard = new CardDeck();
        oneMoveDiscard.addCard(new Card(1));
        oneMoveDiscard.addCard(new Card(2));
        oneMoveDiscard.addCard(new Card(3));

        oneMovePlayer = new Player(oneMoveDraw,oneMoveDiscard);
        oneMovePlayer.receiveCard(new Card(1));
        oneMovePlayer.receiveCard(new Card(2));
        oneMovePlayer.receiveCard(new Card(3));
        oneMovePlayer.receiveCard(new Card(4));
        oneMovePlayer.makeTurn();

        nearlyFullPlayer = new Player(emptyDrawDeck,emptyDiscardDeck);
        nearlyFullPlayer.receiveCard(new Card(1));
        nearlyFullPlayer.receiveCard(new Card(2));
        nearlyFullPlayer.receiveCard(new Card(3));

        preferredWinner = new Player(emptyDrawDeck, emptyDiscardDeck);
        preferredWinner.receiveCard(new Card(preferredWinner.getPlayerNumber()));
        preferredWinner.receiveCard(new Card(preferredWinner.getPlayerNumber()));
        preferredWinner.receiveCard(new Card(preferredWinner.getPlayerNumber()));
        preferredWinner.receiveCard(new Card(preferredWinner.getPlayerNumber()));

        nonPreferredWinner = new Player(emptyDrawDeck,emptyDiscardDeck);
        nonPreferredWinner.receiveCard(new Card(preferredWinner.getPlayerNumber() + 1));
        nonPreferredWinner.receiveCard(new Card(preferredWinner.getPlayerNumber() + 1));
        nonPreferredWinner.receiveCard(new Card(preferredWinner.getPlayerNumber() + 1));
        nonPreferredWinner.receiveCard(new Card(preferredWinner.getPlayerNumber() + 1));
    }

    @After
    public void tearDown() throws Exception {
        emptyDrawDeck = null;
        emptyDiscardDeck = null;
        emptyPlayer = null;
        oneCardDrawDeck = null;
        oneDrawPlayer = null;
        filledDrawDeck = null;
        filledDrawPlayer = null;
        filledPlayer = null;
        filledDiscardDeck = null;
        nearlyFullPlayer = null;
        preferredWinner = null;
        nonPreferredWinner = null;
        oneMovePlayer = null;
        oneMoveDraw = null;
        oneMoveDiscard = null;
    }

    /**
     * Tests when a player tries to draw from an empty deck
     */
    @Test
    public void testEmptyDraw() {
        // This thread should add a card to the deck after 1 second
        Thread addCardThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {

                }
                emptyDrawDeck.addCard(new Card(7));
            }
        });
        // The add card thread is started
        addCardThread.start();
        try {
            // The draw method is invoked on empty player using reflection, it should wait 1 second for a card to be
            // added then draw that card
            Method draw = Player.class.getDeclaredMethod("draw");
            draw.setAccessible(true);
            draw.invoke(emptyPlayer);

            // The hand and the decks content are retrieved by reflection
            Field field = Player.class.getDeclaredField("HAND");
            field.setAccessible(true);
            LinkedList<Card> hand = (LinkedList<Card>) field.get(emptyPlayer);

            Field deckField = CardDeck.class.getDeclaredField("DECK");
            deckField.setAccessible(true);
            LinkedList<Card> deck = (LinkedList<Card>) deckField.get(emptyDrawDeck);

            // Assertion ensure the correct card was drawn and the deck is empty
            assert hand.peekFirst() != null;
            assertEquals(7, hand.peekFirst().getNumber());
            assertEquals(1, hand.size());
            assertEquals(0, deck.size());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
            fail();
        }
    }

    /**
     * Tests drawing from a deck with one card
     */
    @Test
    public void testOneDraw() {
        try {
            // The draw method is invoked on the player using reflection
            Method draw = Player.class.getDeclaredMethod("draw");
            draw.setAccessible(true);
            draw.invoke(oneDrawPlayer);

            // The hand and the decks content are retrieved by reflection
            Field field = Player.class.getDeclaredField("HAND");
            field.setAccessible(true);
            LinkedList<Card> hand = (LinkedList<Card>) field.get(oneDrawPlayer);

            Field deckField = CardDeck.class.getDeclaredField("DECK");
            deckField.setAccessible(true);
            LinkedList<Card> deck = (LinkedList<Card>) deckField.get(emptyDrawDeck);

            // Assertion ensure the correct card was drawn and the deck is empty
            assert  hand.peekFirst() != null;
            assertEquals(1, hand.peekFirst().getNumber());
            assertEquals(1, hand.size());
            assertEquals(0, deck.size());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
            fail();
        }
    }

    /**
     * Tests drawing from a filled deck
     */
    @Test
    public void testFilledDraw() {
        try {
            // The draw method is invoked on the player using reflection
            Method draw = Player.class.getDeclaredMethod("draw");
            draw.setAccessible(true);
            draw.invoke(filledDrawPlayer);

            // The hand and the decks content are retrieved by reflection
            Field field = Player.class.getDeclaredField("HAND");
            field.setAccessible(true);
            LinkedList<Card> hand = (LinkedList<Card>) field.get(filledDrawPlayer);

            Field deckField = CardDeck.class.getDeclaredField("DECK");
            deckField.setAccessible(true);
            LinkedList<Card> deck = (LinkedList<Card>) deckField.get(filledDrawDeck);

            // Assertion ensure the correct card was drawn and the deck contains the correct amount of cards
            assert  hand.peekFirst() != null;
            assertEquals(1, hand.peekFirst().getNumber());
            assertEquals(1, hand.size());
            assertEquals(2, deck.size());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
            fail();
        }
    }

    /**
     * Test when discarding from an empty hand
     */
    @Test (expected = AssertionError.class)
    public void testEmptyDiscard() {
        try {
            // Reflection is used to invoke discard on emptyPlayer this should throw an assertion error as the player
            // shouldn't be discarding on an empty hand
            Method discard = Player.class.getDeclaredMethod("discard");
            discard.setAccessible(true);
            discard.invoke(emptyPlayer);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            fail();
        }
    }

    /**
     * Tests when discarding from a hand containing non preferred cards
     */
    @Test
    public void testNonPreferredDiscard() {
        try {
            // The hand field is retrieved using reflection and set to a hand with non preferred cards
            Field handField = Player.class.getDeclaredField("HAND");
            handField.setAccessible(true);
            int playerNum = emptyPlayer.getPlayerNumber();
            LinkedList<Card> nonPreferredHand = new LinkedList<>();
            for(int i = 1; i <= 5; i++){
                nonPreferredHand.add(new Card(playerNum + 1));
            }
            handField.set(emptyPlayer, nonPreferredHand);

            // The discard method is invoked on the player using reflection
            Method discard = Player.class.getDeclaredMethod("discard");
            discard.setAccessible(true);
            int discardedCard = (int) discard.invoke(emptyPlayer);

            // The hand and deck field are retrieved using reflection
            LinkedList<Card> hand = (LinkedList<Card>) handField.get(emptyPlayer);
            Field deckField = CardDeck.class.getDeclaredField("DECK");
            deckField.setAccessible(true);
            LinkedList<Card> deck = (LinkedList<Card>) deckField.get(emptyDiscardDeck);

            // Assertion ensure the corrects card was discarded and the deck and hand are the correct size
            assertEquals(playerNum + 1, discardedCard);
            assertEquals(4, hand.size());
            assertEquals(1, deck.size());
            assertEquals(playerNum + 1, deck.peekFirst().getNumber());
        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            fail();
        }
    }

    @Test
    public void testPreferredDiscard() {
        try {
            // The hand field is retrieved using reflection and set to a hand with one preferred cards
            Field handField = Player.class.getDeclaredField("HAND");
            handField.setAccessible(true);
            int playerNum = emptyPlayer.getPlayerNumber();
            LinkedList<Card> nonPreferredHand = new LinkedList<>();
            nonPreferredHand.add(new Card(playerNum));
            for(int i = 1; i <= 4; i++){
                nonPreferredHand.add(new Card(playerNum + i));
            }
            handField.set(emptyPlayer, nonPreferredHand);

            // The discard method is invoked on the player using reflection
            Method discard = Player.class.getDeclaredMethod("discard");
            discard.setAccessible(true);
            int discardedCard = (int) discard.invoke(emptyPlayer);
            LinkedList<Card> hand = (LinkedList<Card>) handField.get(emptyPlayer);

            Field deckField = CardDeck.class.getDeclaredField("DECK");
            deckField.setAccessible(true);
            LinkedList<Card> deck = (LinkedList<Card>) deckField.get(emptyDiscardDeck);

            // Assertion ensure the corrects card was discarded and the deck and hand are the correct size
            assertEquals(hand.size(), 4);
            assertEquals(deck.size(),1);
            assertNotEquals(playerNum, discardedCard);
            assertNotEquals(deck.peekFirst().getNumber(), playerNum);

        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            fail();
        }
    }

    /**
     * Tests for when discarding a hand of all preferred cards
     */
    @Test(expected = AssertionError.class)
    public void testAllPreferredHandDiscard() {
        try {
            // The hand field is retrieved using reflection and set to a hand with all preferred cards
            Field handField = Player.class.getDeclaredField("HAND");
            handField.setAccessible(true);
            int playerNum = emptyPlayer.getPlayerNumber();
            LinkedList<Card> nonPreferredHand = new LinkedList<>();
            for(int i = 1; i <= 5; i++){
                nonPreferredHand.add(new Card(playerNum));
            }
            handField.set(emptyPlayer, nonPreferredHand);

            // The discard method is invoked on the player using reflection this should throw an assertion error as
            // the player should check has won before discarding
            Method discard = Player.class.getDeclaredMethod("discard");
            discard.setAccessible(true);
            discard.invoke(emptyPlayer);
        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            fail();
        }
    }

    /**
     * Tests write history when the player loses
     */
    @Test
    public void testLoserWriteHistory() {
        // The player history is written and given a number that isn't their own
        oneMovePlayer.writeHistory(oneMovePlayer.getPlayerNumber() + 1);

        // An assertion is made ensuring the output file exists
        File savedFile = new File("player" + oneMovePlayer.getPlayerNumber() + "_output.txt");
        assertTrue(savedFile.exists());
        try {
            // A Scanner is created and reads the content of the file
            Scanner reader = new Scanner(savedFile);
            ArrayList<String> content = new ArrayList<>();
            while (reader.hasNextLine()){
                content.add(reader.nextLine());
            }

            // The reader is closed and the output file is deleted
            reader.close();
            savedFile.delete();

            // Assertion makes sure the file contains the correct number of line and writes the correct loser lines
            assertEquals("player "+ (oneMovePlayer.getPlayerNumber() + 1) + " has informed player " + oneMovePlayer.getPlayerNumber() + " that player " + (oneMovePlayer.getPlayerNumber() + 1) + " has won", content.get(content.size()-3) );
            assertEquals("player " + oneMovePlayer.getPlayerNumber() + " exits", content.get(content.size()-2));
            assertTrue(content.get(content.size()-1).contains("player " + oneMovePlayer.getPlayerNumber() + " hand: "));
            assertEquals(7, content.size());
        } catch (FileNotFoundException e) {
            fail();
        }
    }

    /**
     * Tests write history for if the player has won
     */
    @Test
    public void testWinnerWriteHistory() {
        // The player history is written and given their own number
        oneMovePlayer.writeHistory(oneMovePlayer.getPlayerNumber());

        // An assertion is made ensuring the output file exists
        File savedFile = new File("player" + oneMovePlayer.getPlayerNumber() + "_output.txt");
        assertTrue(savedFile.exists());
        try {
            // A Scanner is created and reads the content of the file
            Scanner reader = new Scanner(savedFile);
            ArrayList<String> content = new ArrayList<>();
            while (reader.hasNextLine()){
                content.add(reader.nextLine());
            }

            // The reader is closed and the output file is deleted
            reader.close();
            savedFile.delete();

            // Assertion makes sure the file contains the correct number of line and writes the winner lines
            assertEquals("player "+ oneMovePlayer.getPlayerNumber() + " wins", content.get(content.size()-3));
            assertEquals("player " + oneMovePlayer.getPlayerNumber() + " exits", content.get(content.size()-2));
            assertTrue(content.get(content.size()-1).contains("player " + oneMovePlayer.getPlayerNumber() + " final hand: "));
            assertEquals(7, content.size());
        } catch (FileNotFoundException e) {
            fail();
        }
    }

    /**
     * Tests when a player makes a turn
     */
    @Test
    public void testMakeTurn() {
        // The player makes a turn
        filledPlayer.makeTurn();
        try {
            // The hand drawDeck and discardDeck fields are retrieved using reflection
            Field handField = Player.class.getDeclaredField("HAND");
            Field deckField = CardDeck.class.getDeclaredField("DECK");
            handField.setAccessible(true);
            deckField.setAccessible(true);
            LinkedList<Card> hand = (LinkedList<Card>) handField.get(filledPlayer);
            LinkedList<Card> discardDeck = (LinkedList<Card>) deckField.get(filledDiscardDeck);
            LinkedList<Card> drawDeck = (LinkedList<Card>) deckField.get(filledDrawDeck);

            // The history field is also retrieved using reflection
            Field historyField = Player.class.getDeclaredField("HISTORY");
            historyField.setAccessible(true);
            ArrayList<String> history = (ArrayList<String>) historyField.get(filledPlayer);
            int playerNumber = filledPlayer.getPlayerNumber();

            // The number of players field is retrieved using reflection
            Field numOfPlayersField = Player.class.getDeclaredField("numberOfPlayers");
            numOfPlayersField.setAccessible(true);
            int numOfPlayers = (int) numOfPlayersField.get(filledPlayer);

            // Assertion ensurers the correct history is written
            assertEquals("player " + playerNumber + " draws a 1 from deck " + playerNumber, history.get(1));
            assertEquals("player " + playerNumber + " discards a " + discardDeck.get(3) + " to deck "+ (playerNumber == numOfPlayers ? 1:playerNumber + 1), history.get(2));
            assertEquals("player " + playerNumber + " current hand is " + hand.toString().replaceAll("[,]|[]]|[\\[]",""), history.get(3));

           // Assertion ensures the draw deck, discard deck and hand are the correct size
            assertEquals(2, drawDeck.size());
            assertEquals(4, discardDeck.size());
            assertEquals(4, hand.size());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
    }

    /**
     * Test when an empty hand receives a card
     */
    @Test
    public void testEmptyReceiveCard() {
        // The player receives card with number 7
        Card receivedCard = new Card(7);
        emptyPlayer.receiveCard(receivedCard);
        try {
            // The hand field is retrieved using reflection
            Field handField = Player.class.getDeclaredField("HAND");
            handField.setAccessible(true);
            LinkedList<Card> hand = (LinkedList<Card>) handField.get(emptyPlayer);

            // Assertion ensures hand contains the card it was given and is the correct size
            assertTrue(hand.contains(receivedCard));
            assertEquals(1, hand.size());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
    }

    /**
     * Tests when the player receives a card while already having 3 cards
     */
    @Test
    public void testNearlyFullReceiveCard() {
        // The player receives card with number 4
        Card receivedCard = new Card(4);
        nearlyFullPlayer.receiveCard(receivedCard);
        try {
            // The hand and history field are retrieved using reflection
            Field handField = Player.class.getDeclaredField("HAND");
            Field historyField = Player.class.getDeclaredField("HISTORY");
            historyField.setAccessible(true);
            handField.setAccessible(true);
            LinkedList<Card> hand = (LinkedList<Card>) handField.get(nearlyFullPlayer);
            ArrayList<String> history = (ArrayList<String>) historyField.get(nearlyFullPlayer);

            // Assertion ensures hand contains the card it was given and is the correct size
            // it also ensures history is written with the initial hand
            assertTrue(hand.contains(receivedCard));
            assertEquals(4, hand.size());
            assertEquals("player " + nearlyFullPlayer.getPlayerNumber() + " initial hand 1 2 3 4", history.get(0));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
    }

    /**
     * Tests when checking if a player has won without a winning hand
     */
    @Test
    public void testNotMatchingHasWon() {
        try {
            // hasWon method is invoked using reflection
            Method hasWon = Player.class.getDeclaredMethod("hasWon");
            hasWon.setAccessible(true);

            // Assertion makes sure has won returns false
            assertFalse((boolean) hasWon.invoke(filledPlayer));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            fail();
        }
    }

    /**
     * Tests when checking if a player has won with a preferred winning hand
     */
    @Test
    public void testPreferredHasWon() {
        try {
            // hasWon method is invoked using reflection
            Method hasWon = Player.class.getDeclaredMethod("hasWon");
            hasWon.setAccessible(true);

            // Assertion makes sure has won returns true
            assertTrue((boolean) hasWon.invoke(preferredWinner));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            fail();
        }
    }

    /**
     * Tests when checking if a player has won with a non preferred winning hand
     */
    @Test
    public void testNonPreferredHasWon() {
        try {
            // hasWon method is invoked using reflection
            Method hasWon = Player.class.getDeclaredMethod("hasWon");
            hasWon.setAccessible(true);

            // Assertion makes sure has won returns true
            assertTrue((boolean) hasWon.invoke(nonPreferredWinner));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            fail();
        }
    }

    /**
     * Tests that the getPlayerNumber returns the correct player number
     */
    @Test
    public void testGetPlayerNumber() {
        try {
            // Reflection is used to retrieve the players number field
            Field playerNumField = Player.class.getDeclaredField("PLAYER_NUMBER");
            playerNumField.setAccessible(true);
            int expectedPlayerNum = (int) playerNumField.get(emptyPlayer);

            // Assertion ensure that getPlayerNumber returns the correct value
            assertEquals(expectedPlayerNum, emptyPlayer.getPlayerNumber());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
    }
}