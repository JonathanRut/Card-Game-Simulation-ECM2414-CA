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
import java.util.LinkedList;

import static org.junit.Assert.*;

public class CardGameTest {
    private CardDeck deck1;
    private CardDeck deck2;
    private File pack;
    private ArrayList<Card> openedPack = new ArrayList<>();
    private CardGame game;
    private CardGame twoPlayerDealGame;
    private CardGame fourPlayerDealGame;
    private CardGame twoPlayerGame;


    @Before
    public void setUp() throws Exception {
        deck1 = new CardDeck();
        deck2 = new CardDeck();
        openedPack = new ArrayList<>();
        pack = new File("pack_test.txt");
        pack.createNewFile();
        game = new CardGame();
        twoPlayerDealGame = new CardGame();
        Field packField = CardGame.class.getDeclaredField("pack");
        packField.setAccessible(true);
        ArrayList<Card> pack = new ArrayList<>();
        for(int i = 0; i < 16; i++){
            pack.add(new Card(7));
        }
        packField.set(twoPlayerDealGame, pack);

        Method createDecksAndPlayers = CardGame.class.getDeclaredMethod("createDecksAndPlayers", int.class);
        createDecksAndPlayers.setAccessible(true);
        createDecksAndPlayers.invoke(twoPlayerDealGame,2);

        fourPlayerDealGame = new CardGame();
        pack = new ArrayList<>();
        for(int i = 0; i < 32; i++){
            pack.add(new Card(7));
        }
        packField.set(fourPlayerDealGame, pack);
        createDecksAndPlayers.invoke(fourPlayerDealGame,4);

        twoPlayerGame = new CardGame();
        pack = new ArrayList<>();
        for(int i = 0; i < 16; i++){
            pack.add(new Card(7));
        }
        packField.set(twoPlayerGame, pack);
        createDecksAndPlayers.invoke(twoPlayerGame,2);
        Method dealPack = CardGame.class.getDeclaredMethod("dealPack");
        dealPack.setAccessible(true);
        dealPack.invoke(twoPlayerGame);
    }

    @After
    public void tearDown() throws Exception {
        deck1 = null;
        deck2 = null;
        openedPack = null;
        pack.delete();
        pack = null;
        game = null;
        twoPlayerDealGame = null;
        twoPlayerGame = null;
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

    @Test
    public void testTwoPlayersDealPack() {
        try {
            Method dealPack = CardGame.class.getDeclaredMethod("dealPack");
            dealPack.setAccessible(true);
            dealPack.invoke(twoPlayerDealGame);
            Field playersField = CardGame.class.getDeclaredField("players");
            Field decksField = CardGame.class.getDeclaredField("decks");
            playersField.setAccessible(true);
            decksField.setAccessible(true);
            Player[] players = (Player[]) playersField.get(twoPlayerDealGame);
            ArrayList<CardDeck> decks = (ArrayList<CardDeck>) decksField.get(twoPlayerDealGame);

            for(Player player : players){
                Field handField = Player.class.getDeclaredField("HAND");
                handField.setAccessible(true);
                LinkedList<Card> hand = (LinkedList<Card>) handField.get(player);
                assertEquals(4, hand.size());
            }

            for(CardDeck deck : decks){
                Field deckField = CardDeck.class.getDeclaredField("DECK");
                deckField.setAccessible(true);
                LinkedList<Card> cardsDeck = (LinkedList<Card>) deckField.get(deck);
                assertEquals(4, cardsDeck.size());
            }

        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            fail();
        }

    }

    @Test
    public void testFourPlayersDealPack() {
        try {
            Method dealPack = CardGame.class.getDeclaredMethod("dealPack");
            dealPack.setAccessible(true);
            dealPack.invoke(fourPlayerDealGame);
            Field playersField = CardGame.class.getDeclaredField("players");
            Field decksField = CardGame.class.getDeclaredField("decks");
            playersField.setAccessible(true);
            decksField.setAccessible(true);
            Player[] players = (Player[]) playersField.get(fourPlayerDealGame);
            ArrayList<CardDeck> decks = (ArrayList<CardDeck>) decksField.get(fourPlayerDealGame);

            for(Player player : players){
                Field handField = Player.class.getDeclaredField("HAND");
                handField.setAccessible(true);
                LinkedList<Card> hand = (LinkedList<Card>) handField.get(player);
                assertEquals(4, hand.size());
            }

            for(CardDeck deck : decks){
                Field deckField = CardDeck.class.getDeclaredField("DECK");
                deckField.setAccessible(true);
                LinkedList<Card> cardsDeck = (LinkedList<Card>) deckField.get(deck);
                assertEquals(4, cardsDeck.size());
            }

        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            fail();
        }
    }

    @Test
    public void testTwoPlayerPlayGame() {
        try {
            Method playGame = CardGame.class.getDeclaredMethod("playGame");
            playGame.setAccessible(true);
            playGame.invoke(twoPlayerGame);

            Thread waitToFinishThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        Field playersField = CardGame.class.getDeclaredField("players");
                        Field decksField = CardGame.class.getDeclaredField("decks");
                        playersField.setAccessible(true);
                        decksField.setAccessible(true);
                        Player[] players = (Player[]) playersField.get(twoPlayerGame);
                        ArrayList<CardDeck> decks = (ArrayList<CardDeck>) decksField.get(twoPlayerGame);

                        Field playerWonField = CardGame.class.getDeclaredField("playerWon");
                        playerWonField.setAccessible(true);
                        boolean playerWon = (boolean) playerWonField.get(twoPlayerGame);
                        assertTrue(playerWon);
                        for(Player player : players){
                            File saveFile = new File("player" + player.getPlayerNumber() + "_output.txt");
                            assertTrue(saveFile.exists());
                            saveFile.delete();
                        }

                        for(CardDeck deck : decks){
                            Field deckNumField = CardDeck.class.getDeclaredField("DECK_NUM");
                            deckNumField.setAccessible(true);
                            int deckNum = (int) deckNumField.get(deck);
                            File saveFile = new File("deck" + deckNum + "_output.txt");
                            assertTrue(saveFile.exists());
                            saveFile.delete();
                        }
                    } catch (InterruptedException | NoSuchFieldException | IllegalAccessException e) {
                        fail();
                    }
                }
            });
            waitToFinishThread.start();
            while (waitToFinishThread.isAlive()){
                Thread.sleep(100);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InterruptedException e) {
            fail();
        }
    }
}