import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class CardGame {
    private int PlayerNumber;
    private ArrayList<Card> Pack;
    private LinkedList<CardDeck> Decks;
    private LinkedList<Player> Players;
    private int FinalTurn = Integer.MAX_VALUE;
    public CardGame(int playerNumber) {
        this.PlayerNumber = playerNumber;
        for (int i = 0; i < PlayerNumber; i++) {
            CardDeck tempDeck = new CardDeck();
            Decks.add(tempDeck);
        }
        for (int i = 0; i < PlayerNumber; i++) {
            Player tempPlayer = new Player(Decks.get(i), Decks.get(i == PlayerNumber - 1?1:i));
            Players.add(tempPlayer)
        }
    }
    public LinkedList<String> PlayGame() {

    }

    private void ReadDeck(String Filename){
        try {
            File File = new File(Filename);
            Scanner OpenedPack = new Scanner(File);
            for (int i = 0; i < (PlayerNumber * 8); i++) {
                int data = Integer.parseInt(OpenedPack.nextLine());
                Card newCard = new Card(data);
                Pack.add(newCard);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    private void DealDeck() {
        Random Randomiser = new Random();
        for (Card card: Pack) {
            Card DealtCard = Pack.get(Randomiser.nextInt(Pack.size()));
            for (CardDeck emptydeck : Decks) {
                if (emptydeck.deckSize() < 4) {
                    emptydeck.addCard(DealtCard);
                }
            }
            for (Player emptyplayer : Players) {
                if (emptyplayer.handSize() < 4) {
                    emptyplayer.receiveCard(DealtCard);
                }
            }
        }
    }
}
