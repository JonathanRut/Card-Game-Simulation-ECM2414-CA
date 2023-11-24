import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CardGame {
    private final int NUM_OF_PLAYERS;
    private ArrayList<Card> pack = new ArrayList<>();
    private final ArrayList<CardDeck> DECKS;
    private final Player[] PLAYERS;
    private volatile boolean playerWon = false;
    private volatile int winningPlayer;

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        String strNumPlayers = "";
        boolean invalid = true;
        while(invalid) {
            System.out.println("Please enter the number of players: ");
            strNumPlayers = in.nextLine();
            invalid = !isValidPlayers(strNumPlayers);
            if(invalid){
                System.out.println("Number must be integer greater than or equal to 2");
            }
        }
        int numPlayers = Integer.parseInt(strNumPlayers);
        CardGame game = new CardGame(numPlayers);

        invalid = true;
        while(invalid){
            System.out.println("Please enter the location of the pack to load: ");
            String fileLocation = in.nextLine();
            invalid = !game.readDeck(fileLocation);
            if(invalid){
                System.out.println("You have entered an invalid file location");
            }
        }
        game.dealDeck();
        game.playGame();
    }
    private static boolean isValidPlayers(String testNumber){
        int number;
        try{
            number = Integer.parseInt(testNumber);
        }
        catch (NumberFormatException e){
            return false;
        }
        return number >= 2;
    }
    private CardGame(int numOfPlayers) {
        this.NUM_OF_PLAYERS = numOfPlayers;
        PLAYERS = new Player[numOfPlayers];
        DECKS = new ArrayList<>();

        for (int i = 0; i < this.NUM_OF_PLAYERS; i++) {
            CardDeck tempDeck = new CardDeck();
            DECKS.add(tempDeck);
        }

        for (int i = 0; i < this.NUM_OF_PLAYERS; i++) {
            Player tempPlayer = new Player(DECKS.get(i), DECKS.get(i == this.NUM_OF_PLAYERS - 1?0:(i + 1)));
            PLAYERS[i] = tempPlayer;
        }
    }
    private void playGame() {
        for(Player player : PLAYERS){
            Thread playerThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(!playerWon){
                        boolean temp = player.makeTurn();
                        if (temp) {
                            playerWon = true;
                            winningPlayer = player.getPlayerNumber();
                            System.out.println("player " + player.getPlayerNumber() + " has won");
                        }
                    }
                    player.writeHistory(winningPlayer);
                    DECKS.get(player.getPlayerNumber() - 1).writeHistory();
                }
            });
            playerThread.start();
        }
    }
    private boolean readDeck(String Filename){
        File readFile = new File(Filename);
        Scanner openedPack;
        try {
            openedPack = new Scanner(readFile);
            for (int i = 0; i < (NUM_OF_PLAYERS * 8); i++) {
                int data;
                try{
                    data = Integer.parseInt(openedPack.nextLine());
                } catch (NumberFormatException e){
                    pack = new ArrayList<>();
                    return false;
                }
                if(data < 0){
                    return false;
                }
                Card newCard = new Card(data);
                pack.add(newCard);
            }
        } catch (FileNotFoundException e) {
            return false;
        }

        try{
            openedPack.nextLine();
        } catch (NoSuchElementException e){
            return true;
        }
        pack = new ArrayList<>();
        return false;
    }
    private void dealDeck() {
        for(int i = 0; i < 4; i++) {
            for (Player emptyplayer : PLAYERS) {
                emptyplayer.receiveCard(pack.remove(0));
            }
        }
        for(int i = 0; i < 4; i++) {
            for (CardDeck emptyDecks : DECKS) {
                emptyDecks.addCard(pack.remove(0));
            }
        }


    }
}
