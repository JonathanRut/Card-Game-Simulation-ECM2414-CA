import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CardGame {
    private int numOfPlayers;
    private ArrayList<Card> pack = new ArrayList<>();
    private ArrayList<CardDeck> decks;
    private Player[] players;
    private volatile boolean playerWon = false;
    private volatile int winningPlayer;

    public static void main(String[] args){
        CardGame game = new CardGame();

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

        game.createDecksAndPlayers(numPlayers);

        invalid = true;
        while(invalid) {
            System.out.println("Please enter the location of the pack to load: ");
            String fileLocation = in.nextLine();
            invalid = !game.readDeck(fileLocation);
            if (invalid) {
                System.out.println("You have entered an invalid file location");
            }
        }
        game.dealPack();
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
    private boolean readDeck(String Filename){
        File readFile = new File(Filename);
        Scanner openedPack;
        try {
            openedPack = new Scanner(readFile);
            for (int i = 0; i < (numOfPlayers * 8); i++) {
                int data;
                try{
                    data = Integer.parseInt(openedPack.nextLine());
                } catch (NumberFormatException e){
                    pack = new ArrayList<>();
                    return false;
                }
                if(data < 1){
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
    private void createDecksAndPlayers(int numOfPlayers){
        this.numOfPlayers = numOfPlayers;
        players = new Player[numOfPlayers];
        decks = new ArrayList<>();

        for (int i = 0; i < this.numOfPlayers; i++) {
            CardDeck tempDeck = new CardDeck();
            decks.add(tempDeck);
        }

        for (int i = 0; i < this.numOfPlayers; i++) {
            Player tempPlayer = new Player(decks.get(i), decks.get(i == this.numOfPlayers - 1?0:(i + 1)));
            players[i] = tempPlayer;
        }
    }
    private void playGame() {
        for(Player player : players){
            Thread playerThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(!playerWon){
                        boolean temp = player.makeTurn();
                        if (temp) {
                            winningPlayer = player.getPlayerNumber();
                            playerWon = true;
                            System.out.println("player " + player.getPlayerNumber() + " has won");
                        }
                    }
                    player.writeHistory(winningPlayer);
                    decks.get(player.getPlayerNumber() - 1).writeHistory();
                }
            });
            playerThread.start();
        }
    }
    private void dealPack() {
        for(int i = 0; i < 4; i++) {
            for (Player emptyplayer : players) {
                emptyplayer.receiveCard(pack.remove(0));
            }
        }
        for(int i = 0; i < 4; i++) {
            for (CardDeck emptyDecks : decks) {
                emptyDecks.addCard(pack.remove(0));
            }
        }


    }
}
