import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * The CardGame is an executable class that represents and runs the game
 *
 * @author Jonathan Rutland & Daniel Giles
 * @version 1.0
 */
public class CardGame {
    /**
     * A private {@link Integer} that stores the number of players in the game
     */
    private int numOfPlayers;
    /**
     * A private {@link ArrayList} of {@link Card} stores the pack of cards for the game
     */
    private ArrayList<Card> pack = new ArrayList<>();
    /**
     * A private {@link ArrayList} of {@link CardDeck} stores the decks in the game
     */
    private ArrayList<CardDeck> decks;
    /**
     * A private array of {@link Player} stores the players in the game
     */
    private Player[] players;
    /**
     * A private {@link Boolean} indicates if a player has won or not
     */
    private volatile boolean playerWon = false;
    /**
     * A private {@link Integer} stores the number of the winning player
     */
    private volatile int winningPlayer;

    /**
     * The Executable main function
     * @param args arguments given when run
     */
    public static void main(String[] args){
        // A new game instance is created
        CardGame game = new CardGame();

        // The number of players is gotten from the user and then the decks and players are created
        int numPlayers = game.inputNumPlayers();
        game.createDecksAndPlayers(numPlayers);

        // The pack location is inputted and read
        game.inputPackLocation();

        // Then the pack is dealt and the game is played
        game.dealPack();
        game.playGame();
    }

    /**
     * Gets an input from user for the number of players
     * @return the number of players
     */
    private int inputNumPlayers() {
        // The program asks for the number of players then retrieves the input
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the number of players: ");
        String strNumPlayers = in.nextLine();

        // The program should keep asking to enter until they enter a valid number of players
        while(!isValidPlayers(strNumPlayers)) {
            System.out.println("Number must be integer greater than or equal to 2");
            System.out.println("Please enter the number of players: ");
            strNumPlayers = in.nextLine();
        }

        // Finally the number of players is returned
        return Integer.parseInt(strNumPlayers);
    }

    /**
     * Gets the user input for the pack location and reads the deck
     */
    private void inputPackLocation() {
        // The program asks to enter the location of the pack then stores the input
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the location of the pack to load: ");
        String fileLocation = in.nextLine();

        // The program should keep asking to enter until they enter a valid pack location
        while (!readPack(fileLocation)) {
            System.out.println("You have entered an invalid file location");
            System.out.println("Please enter the location of the pack to load: ");
            fileLocation = in.nextLine();
        }
    }

    /**
     * Validates if a string is a valid player number
     * @param testNumber the string to be tested
     * @return a boolean indicating if the string is valid
     */
    private static boolean isValidPlayers(String testNumber){
        // An attempt is made to parse the string into an integer
        int number;
        try{
            number = Integer.parseInt(testNumber);
        }
        catch (NumberFormatException e){
            // If it fails then false is returned
            return false;
        }

        // If it passes then returns if the number is greater than or equal to 2 as 2+ players are needed for a game
        return number >= 2;
    }

    /**
     * Reads the pack from given text file
     * @param filename the file containing the pack
     * @return boolean indicating if reading fails or pack is invalid
     */
    private boolean readPack(String filename){
        // A file to be read is created and a scanner to read it is made
        File readFile = new File(filename);
        Scanner openedPack;
        try {
            openedPack = new Scanner(readFile);

            // 8n lines are read where n is the number of players
            for (int i = 0; i < (numOfPlayers * 8); i++) {
                // First, the line is tested to see if it is an integer
                int data;
                try{
                    data = Integer.parseInt(openedPack.nextLine());
                } catch (NumberFormatException | NoSuchElementException e){
                    // If not an integer then the pack field is reset and false is returned
                    pack = new ArrayList<>();
                    return false;
                }

                // Next, the line is tested to see if it is less than one
                if(data < 1){
                    // If less than one false is returned
                    return false;
                }

                // After passing all these tests a new card is created and added to the pack
                Card newCard = new Card(data);
                pack.add(newCard);
            }
        } catch (FileNotFoundException e) {
            // If the file is not found then false is returned
            return false;
        }

        // An attempt is made to read the next line
        try{
            openedPack.nextLine();
        } catch (NoSuchElementException e){
            // If this fails then the pack is valid as it only contains 8n cards where n is number of players
            return true;
        }

        // If the next line is read then the pack is reset and false is returned
        pack = new ArrayList<>();
        return false;
    }

    /**
     * Creates the decks and players for the game
     * @param numOfPlayers the number of players in the game
     */
    private void createDecksAndPlayers(int numOfPlayers){
        // The number of players is set and players and decks fields are initialized
        this.numOfPlayers = numOfPlayers;
        players = new Player[numOfPlayers];
        decks = new ArrayList<>();

        // n decks are created and added to the field where n is number of players
        for (int i = 0; i < this.numOfPlayers; i++) {
            CardDeck tempDeck = new CardDeck();
            decks.add(tempDeck);
        }

        // n players are created and added to the field where n is number of players
        for (int i = 0; i < this.numOfPlayers; i++) {
            Player tempPlayer = new Player(decks.get(i), decks.get(i == this.numOfPlayers - 1?0:(i + 1)));
            players[i] = tempPlayer;
        }
    }

    /**
     * Plays through the game using multithreading
     */
    private void playGame() {
        // For each player in players a Thread is created and started
        for(Player player : players){
            Thread playerThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    // While there is no player that has won the player should make a turn
                    while(!playerWon){
                        // The player makes their turn and a boolean indicating if they have won is saved to a
                        // temporary variable
                        boolean temp = player.makeTurn();

                        // If temp is true then the player has won
                        if (temp) {
                            // The winningPlayer field is set to the players number and the flag playWon is set to true
                            winningPlayer = player.getPlayerNumber();
                            playerWon = true;

                            // The program outputs to the console that this player has won
                            System.out.println("player " + player.getPlayerNumber() + " has won");
                        }
                    }

                    // After a player has won their and their deck's history is written
                    player.writeHistory(winningPlayer);
                    decks.get(player.getPlayerNumber() - 1).writeHistory();
                }
            });

            // The thread is started
            playerThread.start();
        }
    }

    /**
     * The pack is dealt to the players hand and the players decks
     */
    private void dealPack() {
        // First, 4 cards are distributed to the players in a round-robin style
        for(int i = 0; i < 4; i++) {
            for (Player player : players) {
                // The card is removed from the pack and received from the player
                player.receiveCard(pack.remove(0));
            }
        }

        // Then, 4 cards are distributed to the deck in a round-robin style
        for(int i = 0; i < 4; i++) {
            for (CardDeck deck : decks) {
                // The card is removed from the pack and added to the deck
                deck.addCard(pack.remove(0));
            }
        }


    }
}
