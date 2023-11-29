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
    private File pack;
    private CardGame game;
    private CardGame twoPlayerDealGame;
    private CardGame fourPlayerDealGame;
    private CardGame twoPlayerGame;


    @Before
    public void setUp() throws Exception {
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
        pack.delete();
        pack = null;
        game = null;
        twoPlayerDealGame = null;
        twoPlayerGame = null;
    }

    /**
     * Test for when a validating a positive integer as number of players
     */
    @Test
    public void testValidPlayersPositive() {
        try {
            // Reflection is used to invoke the isValidPlayers method and assertion ensures it returns the correct value
            Method isValidPlayers = CardGame.class.getDeclaredMethod("isValidPlayers", String.class);
            isValidPlayers.setAccessible(true);
            assertTrue((boolean)isValidPlayers.invoke(game, "3"));

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            fail();
        }
    }

    /**
     * Test for when validating zero as number of players
     */
    @Test
    public void testValidPlayersZero() {
        try {
            // Reflection is used to invoke the isValidPlayers method and assertion ensures it returns the correct value
            Method isValidPlayers = CardGame.class.getDeclaredMethod("isValidPlayers", String.class);
            isValidPlayers.setAccessible(true);
            assertFalse((boolean)isValidPlayers.invoke(game, "0"));

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            fail();
        }
    }

    /**
     * Test for when validating a negative number as the number of players
     */
    @Test
    public void testValidPlayersNegative() {
        try {
            // Reflection is used to invoke the isValidPlayers method and assertion ensures it returns the correct value
            Method isValidPlayers = CardGame.class.getDeclaredMethod("isValidPlayers", String.class);
            isValidPlayers.setAccessible(true);
            assertFalse((boolean)isValidPlayers.invoke(game, "-2"));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            fail();
        }
    }

    /**
     * Test for validating a string as the number of players
     */
    @Test
    public void testValidPlayersString() {
        try {
            // Reflection is used to invoke the isValidPlayers method and assertion ensures it returns the correct value
            Method isValidPlayers = CardGame.class.getDeclaredMethod("isValidPlayers", String.class);
            isValidPlayers.setAccessible(true);
            assertFalse((boolean)isValidPlayers.invoke(game, "abcde"));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            fail();
        }
    }

    /**
     * Test for reading a pack containing sixteen positive numbers
     */
    @Test
    public void testReadPackSixteenPositive(){
        try {
            // Reflection is used to set the number of players in the game
            Field field = CardGame.class.getDeclaredField("numOfPlayers");
            field.setAccessible(true);
            field.set(game, 2);

            // Reflection is then used to run the readPack method
            Method readPack = CardGame.class.getDeclaredMethod("readPack", String.class);
            readPack.setAccessible(true);

            // The pack_test text file is written with sixteen 1s
            FileWriter writer = new FileWriter("pack_test.txt");
            for (int i = 0; i < 16; i++) {
                writer.write("1\n");
            }
            writer.close();

            // Assertion ensures readPack returns correct value
            assertTrue((boolean)readPack.invoke(game, "pack_test.txt"));
        } catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException |
                 NoSuchFieldException e) {
            fail();
        }
    }

    /**
     * Test for reading a pack with eight positive integers
     */
    @Test
    public void testReadPackEightPositive() {
        try {
            // Reflection is used to set the number of players in the game
            Field field = CardGame.class.getDeclaredField("numOfPlayers");
            field.setAccessible(true);
            field.set(game, 2);

            // Reflection is then used to run the readPack method
            Method readPack = CardGame.class.getDeclaredMethod("readPack", String.class);
            readPack.setAccessible(true);

            // The pack_test text file is written with eight 1s
            FileWriter writer = new FileWriter("pack_test.txt");
            for (int i = 0; i < 8; i++) {
                writer.write("1\n");
            }
            writer.close();

            // Assertion ensures readPack returns correct value
            assertFalse((boolean)readPack.invoke(game, "pack_test.txt"));
        } catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException |
                 NoSuchFieldException e) {

            fail();
        }
    }

    /**
     * Test for reading a pack with twenty-four positive numbers
     */
    @Test
    public void testReadPackTwentyFourPositive() {
        try {
            // Reflection is used to set the number of players in the game
            Field field = CardGame.class.getDeclaredField("numOfPlayers");
            field.setAccessible(true);
            field.set(game, 2);

            // Reflection is then used to run the readPack method
            Method readPack = CardGame.class.getDeclaredMethod("readPack", String.class);
            readPack.setAccessible(true);

            // The pack_test text file is written with twenty-four 1s
            FileWriter writer = new FileWriter("pack_test.txt");
            for (int i = 0; i < 24; i++) {
                writer.write("1\n");
            }
            writer.close();

            // Assertion ensures readPack returns correct value
            assertFalse((boolean)readPack.invoke(game, "pack_test.txt"));
        } catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException |
                 NoSuchFieldException e) {
            fail();
        }
    }

    /**
     * Test for when reading a pack with eight positive numbers and eight negative numbers
     */
    @Test
    public void testReadPackEightPositiveEightNegative() {
        try {
            // Reflection is used to set the number of players in the game
            Field field = CardGame.class.getDeclaredField("numOfPlayers");
            field.setAccessible(true);
            field.set(game, 2);

            // Reflection is then used to run the readPack method
            Method readPack = CardGame.class.getDeclaredMethod("readPack", String.class);
            readPack.setAccessible(true);

            // The pack_test text file is written with 8 ones and 8 negative ones
            FileWriter writer = new FileWriter("pack_test.txt");
            for (int i = 0; i < 8; i++) {
                writer.write("1\n");
                writer.write("-1\n");
            }
            writer.close();

            // Assertion ensures readPack returns correct value
            assertFalse((boolean)readPack.invoke(game, "pack_test.txt"));
        } catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException |
                 NoSuchFieldException e) {
            fail();
        }
    }

    /**
     * Test for when reading a deck with sixteen negative numbers
     */
    @Test
    public void testReadPackSixteenNegative() {
        try {
            // Reflection is used to set the number of players in the game
            Field field = CardGame.class.getDeclaredField("numOfPlayers");
            field.setAccessible(true);
            field.set(game, 2);

            // Reflection is then used to run the readPack method
            Method readPack = CardGame.class.getDeclaredMethod("readPack", String.class);
            readPack.setAccessible(true);

            // The pack_test text file is written with 16 negative ones
            FileWriter writer = new FileWriter("pack_test.txt");
            for (int i = 0; i < 16; i++) {
                writer.write("-1\n");
            }
            writer.close();

            // Assertion ensures readPack returns correct value
            assertEquals(false, readPack.invoke(game, "pack_test.txt"));
        } catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException |
                 NoSuchFieldException e) {
            fail();
        }
    }

    /**
     * Test for when reading a pack when given the wrong file location
     */
    @Test
    public void testReadPackWrongFile() {
        try {
            // Reflection is used to set the number of players in the game
            Field field = CardGame.class.getDeclaredField("numOfPlayers");
            field.setAccessible(true);
            field.set(game, 2);

            // Reflection is then used to run the readPack method
            Method readPack = CardGame.class.getDeclaredMethod("readPack", String.class);
            readPack.setAccessible(true);

            // The pack_test text file is written with 16 ones
            FileWriter writer = new FileWriter("pack_test.txt");
            for (int i = 0; i < 16; i++) {
                writer.write("1\n");
            }
            writer.close();

            // The readPack is invoked with wrong location and assertion ensure false is returned
            assertFalse((boolean)readPack.invoke(game, "wrong_pack_test.txt"));
        } catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException |
                 NoSuchFieldException e) {
            fail();
        }
    }

    /**
     * Test for when creating decks and players
     */
    @Test
    public void testCreateDecksAndPlayers() {
        try {
            // Reflection is used to invoke the created decksAndPlayers method
            Method createDecksAndsPlayers = CardGame.class.getDeclaredMethod("createDecksAndPlayers", int.class);
            createDecksAndsPlayers.setAccessible(true);
            createDecksAndsPlayers.invoke(game, 2);

            // Reflection is used to retrieve the players and decks fields
            Field field1 = CardGame.class.getDeclaredField("players");
            field1.setAccessible(true);
            Player[] players = (Player[]) field1.get(game);
            Field field2 = CardGame.class.getDeclaredField("decks");
            field2.setAccessible(true);
            ArrayList<CardDeck> decks = (ArrayList<CardDeck>) field2.get(game);

            // Assertion ensurers the decks and players fields are correct size
            assertEquals(2, decks.size());
            assertEquals(2, players.length);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
            fail();
        }
    }

    /**
     * Test for when dealing a pack to two players
     */
    @Test
    public void testTwoPlayersDealPack() {
        try {
            // The pack is dealt to the two player game
            Method dealPack = CardGame.class.getDeclaredMethod("dealPack");
            dealPack.setAccessible(true);
            dealPack.invoke(twoPlayerDealGame);

            // players and decks fields are retrieved
            Field playersField = CardGame.class.getDeclaredField("players");
            Field decksField = CardGame.class.getDeclaredField("decks");
            playersField.setAccessible(true);
            decksField.setAccessible(true);
            Player[] players = (Player[]) playersField.get(twoPlayerDealGame);
            ArrayList<CardDeck> decks = (ArrayList<CardDeck>) decksField.get(twoPlayerDealGame);

            // The players field is iterated through
            for(Player player : players){
                // Reflection gets the players hand and ensures it is the correct size
                Field handField = Player.class.getDeclaredField("HAND");
                handField.setAccessible(true);
                LinkedList<Card> hand = (LinkedList<Card>) handField.get(player);
                assertEquals(4, hand.size());
            }

            // The decks field is iterated through
            for(CardDeck deck : decks){
                // Reflection gets the decks content and ensures it is the correct size
                Field deckField = CardDeck.class.getDeclaredField("DECK");
                deckField.setAccessible(true);
                LinkedList<Card> cardsDeck = (LinkedList<Card>) deckField.get(deck);
                assertEquals(4, cardsDeck.size());
            }

        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            fail();
        }

    }

    /**
     * Test for when dealing to four players
     */
    @Test
    public void testFourPlayersDealPack() {
        try {
            // The pack is dealt to the four player game
            Method dealPack = CardGame.class.getDeclaredMethod("dealPack");
            dealPack.setAccessible(true);
            dealPack.invoke(fourPlayerDealGame);

            // players and decks fields are retrieved
            Field playersField = CardGame.class.getDeclaredField("players");
            Field decksField = CardGame.class.getDeclaredField("decks");
            playersField.setAccessible(true);
            decksField.setAccessible(true);
            Player[] players = (Player[]) playersField.get(fourPlayerDealGame);
            ArrayList<CardDeck> decks = (ArrayList<CardDeck>) decksField.get(fourPlayerDealGame);

            // The players field is iterated through
            for(Player player : players){
                // Reflection gets the players hand and ensures it is the correct size
                Field handField = Player.class.getDeclaredField("HAND");
                handField.setAccessible(true);
                LinkedList<Card> hand = (LinkedList<Card>) handField.get(player);
                assertEquals(4, hand.size());
            }

            // The decks field is iterated through
            for(CardDeck deck : decks){
                // Reflection gets the decks content and ensures it is the correct size
                Field deckField = CardDeck.class.getDeclaredField("DECK");
                deckField.setAccessible(true);
                LinkedList<Card> cardsDeck = (LinkedList<Card>) deckField.get(deck);
                assertEquals(4, cardsDeck.size());
            }

        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            fail();
        }
    }

    /**
     * Test for when playing a two player game
     */
    @Test
    public void testTwoPlayerPlayGame() {
        try {
            // Reflection is used to invoke playGame
            Method playGame = CardGame.class.getDeclaredMethod("playGame");
            playGame.setAccessible(true);
            playGame.invoke(twoPlayerGame);

            // A thread is created to sleep for a second allowing the game to finish
            Thread waitToFinishThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);

                        // The players and decks fields are retrieved via reflection
                        Field playersField = CardGame.class.getDeclaredField("players");
                        Field decksField = CardGame.class.getDeclaredField("decks");
                        playersField.setAccessible(true);
                        decksField.setAccessible(true);
                        Player[] players = (Player[]) playersField.get(twoPlayerGame);
                        ArrayList<CardDeck> decks = (ArrayList<CardDeck>) decksField.get(twoPlayerGame);

                        // The playerWonField is retrieved via reflection
                        Field playerWonField = CardGame.class.getDeclaredField("playerWon");
                        playerWonField.setAccessible(true);
                        boolean playerWon = (boolean) playerWonField.get(twoPlayerGame);

                        // Assertion ensures a player has won
                        assertTrue(playerWon);

                        // The players are iterated through and assertion makes sure the save files exists
                        for(Player player : players){
                            File saveFile = new File("player" + player.getPlayerNumber() + "_output.txt");
                            assertTrue(saveFile.exists());
                            saveFile.delete();
                        }

                        // The decks are iterated through and assertion ensures the save files exists
                        for(CardDeck deck : decks){
                            // Reflection is used to get the decks number
                            Field deckNumField = CardDeck.class.getDeclaredField("DECK_NUM");
                            deckNumField.setAccessible(true);
                            int deckNum = (int) deckNumField.get(deck);

                            // The assertion ensures the save file exists and then deletes it
                            File saveFile = new File("deck" + deckNum + "_output.txt");
                            assertTrue(saveFile.exists());
                            saveFile.delete();
                        }
                    } catch (InterruptedException | NoSuchFieldException | IllegalAccessException e) {
                        fail();
                    }
                }
            });

            // The wait to finish thread is started
            waitToFinishThread.start();

            // A while ensures that the waitToFinishThread is finished before the test ends
            while (waitToFinishThread.isAlive()){
                Thread.sleep(100);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InterruptedException e) {
            fail();
        }
    }
}