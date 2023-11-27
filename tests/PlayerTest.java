import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.LinkedList;

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
                emptyDrawDeck.addCard(new Card(7));
            }
        });
        addCardThread.start();
        try {
            Method draw = Player.class.getDeclaredMethod("draw");
            draw.setAccessible(true);
            draw.invoke(emptyPlayer);

            Field field = Player.class.getDeclaredField("HAND");
            field.setAccessible(true);
            LinkedList<Card> hand = (LinkedList<Card>) field.get(emptyPlayer);

            Field deckField = CardDeck.class.getDeclaredField("DECK");
            deckField.setAccessible(true);
            LinkedList<Card> deck = (LinkedList<Card>) deckField.get(emptyDrawDeck);

            assert hand.peekFirst() != null;
            assertEquals(7, hand.peekFirst().getNumber());
            assertEquals(1, hand.size());
            assertEquals(0, deck.size());
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

            Field field = Player.class.getDeclaredField("HAND");
            field.setAccessible(true);
            LinkedList<Card> hand = (LinkedList<Card>) field.get(oneDrawPlayer);

            Field deckField = CardDeck.class.getDeclaredField("DECK");
            deckField.setAccessible(true);
            LinkedList<Card> deck = (LinkedList<Card>) deckField.get(emptyDrawDeck);

            assert  hand.peekFirst() != null;
            assertEquals(1, hand.peekFirst().getNumber());
            assertEquals(1, hand.size());
            assertEquals(0, deck.size());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
            fail();
        }
    }
    @Test
    public void testFilledDraw() {
        try {
            Method draw = Player.class.getDeclaredMethod("draw");
            draw.setAccessible(true);
            draw.invoke(filledDrawPlayer);

            Field field = Player.class.getDeclaredField("HAND");
            field.setAccessible(true);
            LinkedList<Card> hand = (LinkedList<Card>) field.get(filledDrawPlayer);

            Field deckField = CardDeck.class.getDeclaredField("DECK");
            deckField.setAccessible(true);
            LinkedList<Card> deck = (LinkedList<Card>) deckField.get(filledDrawDeck);

            assert  hand.peekFirst() != null;
            assertEquals(1, hand.peekFirst().getNumber());
            assertEquals(1, hand.size());
            assertEquals(2, deck.size());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
            fail();
        }
    }

    @Test (expected = AssertionError.class)
    public void testEmptyDiscard() {
        try {
            Method discard = Player.class.getDeclaredMethod("discard");
            discard.setAccessible(true);
            discard.invoke(emptyPlayer);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            fail();
        }
    }

    @Test
    public void testNonPreferredDiscard() {
        try {
            Field handField = Player.class.getDeclaredField("HAND");
            handField.setAccessible(true);
            int playerNum = emptyPlayer.getPlayerNumber();
            LinkedList<Card> nonPreferredHand = new LinkedList<>();
            for(int i = 1; i <= 5; i++){
                nonPreferredHand.add(new Card(playerNum + 1));
            }
            handField.set(emptyPlayer, nonPreferredHand);

            Method discard = Player.class.getDeclaredMethod("discard");
            discard.setAccessible(true);
            int discardedCard = (int) discard.invoke(emptyPlayer);

            LinkedList<Card> hand = (LinkedList<Card>) handField.get(emptyPlayer);
            Field deckField = CardDeck.class.getDeclaredField("DECK");
            deckField.setAccessible(true);
            LinkedList<Card> deck = (LinkedList<Card>) deckField.get(emptyDiscardDeck);
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
            Field handField = Player.class.getDeclaredField("HAND");
            handField.setAccessible(true);
            int playerNum = emptyPlayer.getPlayerNumber();
            LinkedList<Card> nonPreferredHand = new LinkedList<>();
            nonPreferredHand.add(new Card(playerNum));
            for(int i = 1; i <= 4; i++){
                nonPreferredHand.add(new Card(playerNum + i));
            }
            handField.set(emptyPlayer, nonPreferredHand);

            Method discard = Player.class.getDeclaredMethod("discard");
            discard.setAccessible(true);
            int discardedCard = (int) discard.invoke(emptyPlayer);
            LinkedList<Card> hand = (LinkedList<Card>) handField.get(emptyPlayer);

            Field deckField = CardDeck.class.getDeclaredField("DECK");
            deckField.setAccessible(true);
            LinkedList<Card> deck = (LinkedList<Card>) deckField.get(emptyDiscardDeck);

            assertEquals(hand.size(), 4);
            assertEquals(deck.size(),1);
            assertNotEquals(playerNum, discardedCard);
            assertNotEquals(deck.peekFirst().getNumber(), playerNum);

        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            fail();
        }
    }

    @Test(expected = AssertionError.class)
    public void testAllPreferredHand() {
        try {
            Field handField = Player.class.getDeclaredField("HAND");
            handField.setAccessible(true);
            int playerNum = emptyPlayer.getPlayerNumber();
            LinkedList<Card> nonPreferredHand = new LinkedList<>();
            for(int i = 1; i <= 5; i++){
                nonPreferredHand.add(new Card(playerNum));
            }
            handField.set(emptyPlayer, nonPreferredHand);

            Method discard = Player.class.getDeclaredMethod("discard");
            discard.setAccessible(true);
            discard.invoke(emptyPlayer);
        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            fail();
        }
    }

    @Test
    public void writeHistory() {
    }

    @Test
    public void testMakeTurn() {
        filledPlayer.makeTurn();
        try {
            Field handField = Player.class.getDeclaredField("HAND");
            Field deckField = CardDeck.class.getDeclaredField("DECK");
            handField.setAccessible(true);
            deckField.setAccessible(true);
            LinkedList<Card> hand = (LinkedList<Card>) handField.get(filledPlayer);
            LinkedList<Card> discardDeck = (LinkedList<Card>) deckField.get(filledDiscardDeck);
            LinkedList<Card> drawDeck = (LinkedList<Card>) deckField.get(filledDrawDeck);

            Field historyField = Player.class.getDeclaredField("HISTORY");
            historyField.setAccessible(true);
            ArrayList<String> history = (ArrayList<String>) historyField.get(filledPlayer);
            int playerNumber = filledPlayer.getPlayerNumber();

            Field numOfPlayersField = Player.class.getDeclaredField("numberOfPlayers");
            numOfPlayersField.setAccessible(true);
            int numOfPlayers = (int) numOfPlayersField.get(filledPlayer);

            assertEquals("player " + playerNumber + " draws a 1 from deck " + playerNumber, history.get(1));
            assertEquals("player " + playerNumber + " discards a " + discardDeck.get(3) + " to deck "+ (playerNumber + 1 == numOfPlayers ? 1:playerNumber + 1), history.get(2));
            assertEquals("player " + playerNumber + " current hand is " + hand.toString().replaceAll("[,]|[]]|[\\[]",""), history.get(3));
            assertEquals(2, drawDeck.size());
            assertEquals(4, discardDeck.size());
            assertEquals(4, hand.size());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
    }

    @Test
    public void testEmptyReceiveCard() {
        Card receivedCard = new Card(7);
        emptyPlayer.receiveCard(receivedCard);
        try {
            Field handField = Player.class.getDeclaredField("HAND");
            handField.setAccessible(true);
            LinkedList<Card> hand = (LinkedList<Card>) handField.get(emptyPlayer);
            assertTrue(hand.contains(receivedCard));
            assertEquals(1, hand.size());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
    }

    @Test
    public void testNearlyFullReceiveCard() {
        Card receivedCard = new Card(4);
        nearlyFullPlayer.receiveCard(receivedCard);
        try {
            Field handField = Player.class.getDeclaredField("HAND");
            Field historyField = Player.class.getDeclaredField("HISTORY");
            historyField.setAccessible(true);
            handField.setAccessible(true);
            LinkedList<Card> hand = (LinkedList<Card>) handField.get(nearlyFullPlayer);
            ArrayList<String> history = (ArrayList<String>) historyField.get(nearlyFullPlayer);

            assertTrue(hand.contains(receivedCard));
            assertEquals(4, hand.size());
            assertEquals("player " + nearlyFullPlayer.getPlayerNumber() + " initial hand 1 2 3 4", history.get(0));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
    }

    @Test
    public void testNotMatchingHasWon() {
        try {
            Method hasWon = Player.class.getDeclaredMethod("hasWon");
            hasWon.setAccessible(true);
            assertFalse((Boolean) hasWon.invoke(filledPlayer));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            fail();
        }
    }

    @Test
    public void testPreferredHasWon() {
        try {
            Method hasWon = Player.class.getDeclaredMethod("hasWon");
            hasWon.setAccessible(true);
            assertTrue((Boolean) hasWon.invoke(preferredWinner));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            fail();
        }
    }

    @Test
    public void testNonPreferredHasWon() {
        try {
            Method hasWon = Player.class.getDeclaredMethod("hasWon");
            hasWon.setAccessible(true);
            assertTrue((Boolean) hasWon.invoke(nonPreferredWinner));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            fail();
        }
    }

    @Test
    public void testGetPlayerNumber() {
        try {
            Field playerNumField = Player.class.getDeclaredField("PLAYER_NUMBER");
            playerNumField.setAccessible(true);
            int expectedPlayerNum = (int) playerNumField.get(emptyPlayer);
            assertEquals(expectedPlayerNum, emptyPlayer.getPlayerNumber());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
    }
}