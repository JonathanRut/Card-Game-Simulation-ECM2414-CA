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
        int numPlayers = game.inputNumPlayers();
        game.createDecksAndPlayers(numPlayers);
        game.inputPackLocation();
        game.dealPack();
        game.playGame();
    }
    private int inputNumPlayers() {
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the number of players: ");
        String strNumPlayers = in.nextLine();
        while(!isValidPlayers(strNumPlayers)) {
            System.out.println("Number must be integer greater than or equal to 2");
            System.out.println("Please enter the number of players: ");
            strNumPlayers = in.nextLine();
        }
        return Integer.parseInt(strNumPlayers);
    }

    private void inputPackLocation() {
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the location of the pack to load: ");
        String fileLocation = in.nextLine();
        while (!readDeck(fileLocation)) {
            System.out.println("You have entered an invalid file location");
            System.out.println("Please enter the location of the pack to load: ");
            fileLocation = in.nextLine();
        }
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
                } catch (NumberFormatException | NoSuchElementException e){
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
