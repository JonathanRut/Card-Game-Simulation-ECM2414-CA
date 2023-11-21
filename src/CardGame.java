import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CardGame {
    private final int playerNumber;
    private ArrayList<Card> pack;
    private final ArrayList<ConcurrentLinkedQueue<Card>> decks;
    private final Player[] players;
    private volatile int maxTurn = Integer.MAX_VALUE;

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

    }

    public static boolean isValidPlayers(String testNumber){
        int number;
        try{
            number = Integer.parseInt(testNumber);
        }
        catch (NumberFormatException e){
            return false;
        }
        return number < 2;
    }


    public CardGame(int playerNumber) {
        this.playerNumber = playerNumber;
        players = new Player[playerNumber];
        decks = new ArrayList<>();

        for (int i = 0; i < this.playerNumber; i++) {
            ConcurrentLinkedQueue<Card> tempDeck = new ConcurrentLinkedQueue<Card>();
            decks.add(tempDeck);
        }

        for (int i = 0; i < this.playerNumber; i++) {
            Player tempPlayer = new Player(decks.get(i), decks.get(i == this.playerNumber - 1?1:i));
            players[i] = tempPlayer;
        }
    }
    public void playGame() {
        for(Player player : players){
            Thread playerThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    history.add("player " + playerNumber + " initial hand " + hand.toString().replaceAll("[,]|[]]|[\\[]",""));
                    while(player.getTurn() <= maxTurn){
                        maxTurn = player.makeTurn();
                    }
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
            for (int i = 0; i < (playerNumber * 8); i++) {
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
            for (Player emptyplayer : players) {
                emptyplayer.receiveCard(pack.remove(0));
            }
        }
        for(int i = 0; i < 4; i++) {
            for (ConcurrentLinkedQueue<Card> emptyDecks : decks) {
                emptyDecks.add(pack.remove(0));
            }
        }


    }
}
