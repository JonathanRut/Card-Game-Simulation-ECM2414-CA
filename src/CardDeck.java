import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.lang.reflect.*;
import java.util.Objects;

public class CardDeck {
    private final LinkedList<Card> DECK = new LinkedList<>();
    private static int numDecks = 1;
    private final int DECK_NUM;
    public CardDeck(){
        DECK_NUM = numDecks;
        numDecks++;
    }
    public synchronized Card removeCard(){
        while(DECK.size() == 0){
            try{
                wait();
            } catch (InterruptedException ignored){}
        }
        return DECK.removeFirst();
    }
    public synchronized void addCard(Card card){
        DECK.add(card);
        notify();
    }
    public void writeHistory() {
        File deckFile = new File("deck" + DECK_NUM + "_output.txt");
        Object cd = new CardDeck();
        try {
            deckFile.createNewFile();
            FileWriter writer = new FileWriter("deck" + DECK_NUM + "_output.txt");
            writer.write("deck" + DECK_NUM + " contents:" + DECK.toString().replaceAll("[,]|[]]|[\\[]", ""));
            writer.close();
        } catch(IOException ignore) {
        }
    }
}