import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class CardGameTest {
    private CardDeck deck1;
    private CardDeck deck2;
    private File pack;
    private ArrayList<Card> openedpack = new ArrayList<>();
    private CardGame game;


    @Before
    public void setUp() throws Exception {
        deck1 = new CardDeck();
        deck2 = new CardDeck();
        openedpack = new ArrayList<>();
        pack = new File("pack_test.txt");
        pack.createNewFile();
        game = new CardGame();
    }

    @After
    public void tearDown() throws Exception {
        deck1 = null;
        deck2 = null;
        openedpack = null;
        pack.delete();
        pack = null;
    }

    public void testValidPlayersPositive() {
        try {
            Method isValidPlayers = Player.class.getDeclaredMethod("isValidPlayers");
            isValidPlayers.setAccessible(true);
            assertEquals(isValidPlayers.invoke(3), true);

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            fail();
        }
    }

    public void testValidPlayersZero() {
        try {
            Method isValidPlayers = Player.class.getDeclaredMethod("isValidPlayers");
            isValidPlayers.setAccessible(true);
            assertEquals(isValidPlayers.invoke(0), false);

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            fail();
        }
    }

    public void testValidPlayersNegative() {
        try {
            Method isValidPlayers = Player.class.getDeclaredMethod("isValidPlayers");
            isValidPlayers.setAccessible(true);
            assertEquals(isValidPlayers.invoke(-2), false);

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            fail();
        }
    }

    public void testValidPlayersString() {
        try {
            Method isValidPlayers = Player.class.getDeclaredMethod("isValidPlayers");
            isValidPlayers.setAccessible(true);
            assertEquals(isValidPlayers.invoke("abcde"), false);

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            fail();
        }
    }

    public void testreadDeckSixteenPositive() {
        try {
            Method readDeck = Player.class.getDeclaredMethod("readDeck");
            readDeck.setAccessible(true);
            FileWriter writer = new FileWriter("pack_test.txt");
            for (int i = 0; i < 16; i++) {
                writer.write("1\n");
            }
            assertEquals(readDeck.invoke(pack), true);
        } catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            fail();
        }
    }

    public void testreadDeckEightPositive() {
        try {
            Method readDeck = Player.class.getDeclaredMethod("readDeck");
            readDeck.setAccessible(true);
            FileWriter writer = new FileWriter("pack_test.txt");
            for (int i = 0; i < 8; i++) {
                writer.write("1\n");
            }
            assertEquals(readDeck.invoke(pack), false);
        } catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            fail();
        }
    }

    public void testreadDeckTwentyFourPositive() {
        try {
            Method readDeck = Player.class.getDeclaredMethod("readDeck");
            readDeck.setAccessible(true);
            FileWriter writer = new FileWriter("pack_test.txt");
            for (int i = 0; i < 24; i++) {
                writer.write("1\n");
            }
            assertEquals(readDeck.invoke(pack), false);
        } catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            fail();
        }
    }

    public void testreadDeckEightPositiveEightNegative() {
        try {
            Method readDeck = Player.class.getDeclaredMethod("readDeck");
            readDeck.setAccessible(true);
            FileWriter writer = new FileWriter("pack_test.txt");
            for (int i = 0; i < 8; i++) {
                writer.write("1\n");
                writer.write("-1\n");
            }
            assertEquals(readDeck.invoke(pack), false);
        } catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            fail();
        }
    }

    public void testreadDeckSixteenNegative() {
        try {
            Method readDeck = Player.class.getDeclaredMethod("readDeck");
            readDeck.setAccessible(true);
            FileWriter writer = new FileWriter("pack_test.txt");
            for (int i = 0; i < 16; i++) {
                writer.write("-1\n");
            }
            assertEquals(readDeck.invoke(pack), false);
        } catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            fail();
        }
    }

    public void testCreateDecksAndPlayers() {
        try {
            Method createDecksAndsPlayers = Player.class.getDeclaredMethod("createDecksAndPlayers");
            createDecksAndsPlayers.setAccessible(true);
            createDecksAndsPlayers.invoke(game, 2);
            Field field1 = CardGame.class.getDeclaredField("players");
            field1.setAccessible(true);
            Player[] players = (Player[]) field1.get(game);
            Field field2 = Player.class.getDeclaredField("decks");
            field2.setAccessible(true);
            ArrayList<CardDeck> decks = (ArrayList<CardDeck>) field2.get(game);
            assertTrue(decks.size() == 2 && 2 == players.length);

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
            fail();
        }

    }

    @Test
    public void name() {

    }
}