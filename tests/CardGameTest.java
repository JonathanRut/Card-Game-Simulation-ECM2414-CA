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
    private ArrayList<Card> openedPack = new ArrayList<>();
    private CardGame game;


    @Before
    public void setUp() throws Exception {
        deck1 = new CardDeck();
        deck2 = new CardDeck();
        openedPack = new ArrayList<>();
        pack = new File("pack_test.txt");
        pack.createNewFile();
        game = new CardGame();
    }

    @After
    public void tearDown() throws Exception {
        deck1 = null;
        deck2 = null;
        openedPack = null;
        pack.delete();
        pack = null;
        game = null;
    }

    @Test
    public void testValidPlayersPositive() {
        try {
            Method isValidPlayers = CardGame.class.getDeclaredMethod("isValidPlayers", String.class);
            isValidPlayers.setAccessible(true);
            assertEquals(isValidPlayers.invoke(game, "3"), true);

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            fail();
        }
    }

    @Test
    public void testValidPlayersZero() {
        try {
            Method isValidPlayers = CardGame.class.getDeclaredMethod("isValidPlayers", String.class);
            isValidPlayers.setAccessible(true);
            assertEquals(false, isValidPlayers.invoke(game, "0"));

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            fail();
        }
    }

    @Test
    public void testValidPlayersNegative() {
        try {
            Method isValidPlayers = CardGame.class.getDeclaredMethod("isValidPlayers", String.class);
            isValidPlayers.setAccessible(true);
            assertEquals(false, isValidPlayers.invoke(game, "-2"));

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            fail();
        }
    }

    @Test
    public void testValidPlayersString() {
        try {
            Method isValidPlayers = CardGame.class.getDeclaredMethod("isValidPlayers", String.class);
            isValidPlayers.setAccessible(true);
            assertEquals(false, isValidPlayers.invoke(game, "abcde"));

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            fail();
        }
    }

    @Test
    public void testReadPackSixteenPositive(){
        try {
            Field field = CardGame.class.getDeclaredField("numOfPlayers");
            field.setAccessible(true);
            field.set(game, 2);
            Method readPack = CardGame.class.getDeclaredMethod("readPack", String.class);
            readPack.setAccessible(true);
            FileWriter writer = new FileWriter("pack_test.txt");
            for (int i = 0; i < 16; i++) {
                writer.write("1\n");
            }
            writer.close();
            assertEquals(true, readPack.invoke(game, "pack_test.txt"));
        } catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException |
                 NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void testReadPackEightPositive() {
        try {
            Field field = CardGame.class.getDeclaredField("numOfPlayers");
            field.setAccessible(true);
            field.set(game, 2);
            Method readPack = CardGame.class.getDeclaredMethod("readPack", String.class);
            readPack.setAccessible(true);
            FileWriter writer = new FileWriter("pack_test.txt");
            for (int i = 0; i < 8; i++) {
                writer.write("1\n");
            }
            writer.close();
            assertEquals(false, readPack.invoke(game, "pack_test.txt"));
        } catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException |
                 NoSuchFieldException e) {

            fail();
        }
    }

    @Test
    public void testReadPackTwentyFourPositive() {
        try {
            Field field = CardGame.class.getDeclaredField("numOfPlayers");
            field.setAccessible(true);
            field.set(game, 2);
            Method readPack = CardGame.class.getDeclaredMethod("readPack", String.class);
            readPack.setAccessible(true);
            FileWriter writer = new FileWriter("pack_test.txt");
            for (int i = 0; i < 24; i++) {
                writer.write("1\n");
            }
            writer.close();
            assertEquals(false, readPack.invoke(game, "pack_test.txt"));
        } catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException |
                 NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void testReadPackEightPositiveEightNegative() {
        try {
            Field field = CardGame.class.getDeclaredField("numOfPlayers");
            field.setAccessible(true);
            field.set(game, 2);
            Method readPack = CardGame.class.getDeclaredMethod("readPack", String.class);
            readPack.setAccessible(true);
            FileWriter writer = new FileWriter("pack_test.txt");
            for (int i = 0; i < 8; i++) {
                writer.write("1\n");
                writer.write("-1\n");
            }
            writer.close();
            assertEquals(false, readPack.invoke(game, "pack_test.txt"));
        } catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException |
                 NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void testReadPackSixteenNegative() {
        try {
            Field field = CardGame.class.getDeclaredField("numOfPlayers");
            field.setAccessible(true);
            field.set(game, 2);
            Method readPack = CardGame.class.getDeclaredMethod("readPack", String.class);
            readPack.setAccessible(true);
            FileWriter writer = new FileWriter("pack_test.txt");
            for (int i = 0; i < 16; i++) {
                writer.write("-1\n");
            }
            writer.close();
            assertEquals(false, readPack.invoke(game, "pack_test.txt"));
        } catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException |
                 NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void testReadPackWrongFile() {
        try {
            Field field = CardGame.class.getDeclaredField("numOfPlayers");
            field.setAccessible(true);
            field.set(game, 2);
            Method readPack = CardGame.class.getDeclaredMethod("readPack", String.class);
            readPack.setAccessible(true);
            FileWriter writer = new FileWriter("pack_test.txt");
            for (int i = 0; i < 16; i++) {
                writer.write("1\n");
            }
            writer.close();
            assertEquals(false, readPack.invoke(game, "wrong_pack_test.txt"));
        } catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException |
                 NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void testCreateDecksAndPlayers() {
        try {
            Method createDecksAndsPlayers = CardGame.class.getDeclaredMethod("createDecksAndPlayers", int.class);
            createDecksAndsPlayers.setAccessible(true);
            createDecksAndsPlayers.invoke(game, 2);
            Field field1 = CardGame.class.getDeclaredField("players");
            field1.setAccessible(true);
            Player[] players = (Player[]) field1.get(game);
            Field field2 = CardGame.class.getDeclaredField("decks");
            field2.setAccessible(true);
            ArrayList<CardDeck> decks = (ArrayList<CardDeck>) field2.get(game);
            assertTrue(decks.size() == 2 && 2 == players.length);

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
            fail();
        }
    }
}