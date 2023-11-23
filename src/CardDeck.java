import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class CardDeck {
    private volatile LinkedList<Card> deck = new LinkedList<>();
    private static int numDecks = 1;
    private int deckNum;
    public CardDeck(){
        deckNum = numDecks;
        numDecks++;
    }

    public synchronized Card removeCard(){
        while(deck.size() == 0){
            try{
                wait();
            } catch (InterruptedException ignored){}
        }
        notify();
        return deck.removeFirst();
    }

    public synchronized void addCard(Card card){
        while(deck.size() == 4){
            try{
                wait();
            } catch (InterruptedException ignored){}
        }
        deck.add(card);
        notify();
    }

    public void writeHistory() {
        File deckFile = new File("deck" + deckNum + "_output.txt");
        try {
            deckFile.createNewFile();
            FileWriter writer = new FileWriter("deck" + deckNum + "_output.txt");
            writer.write("deck" + deckNum + " contents:" + deck.toString().replaceAll("[,]|[]]|[\\[]", ""));
            writer.close();
        } catch(IOException ignore) {
        }
    }
}