import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.lang.reflect.*;
import java.util.Objects;

/**
 * The CardDeck class represents a deck of cards in the game
 *
 * @author Jonathan Rutland & Daniel Giles
 * @version 1.0
 */
public class CardDeck {
    /**
     * A private {@link LinkedList} of {@link Card} stores the cards in the deck
     */
    private final LinkedList<Card> DECK = new LinkedList<>();
    /**
     * A private {@link Integer} stores the number of decks created
     */
    private static int numDecks = 0;
    /**
     * A private {@link Integer} stores the deck number
     */
    private final int DECK_NUM;

    /**
     * CardDeck constructor sets the decks number
     */
    public CardDeck(){
        numDecks++;
        DECK_NUM = numDecks;
    }

    /**
     * Removes a card from deck will wait if deck is empty
     * @return the card removed from the deck
     */
    public synchronized Card removeCard(){
        while(DECK.size() == 0){
            try{
                wait();
            } catch (InterruptedException ignored){}
        }
        return DECK.removeFirst();
    }

    /**
     * Adds card to the deck and notifies waiting threads
     * @param card the card added to the deck
     */
    public synchronized void addCard(Card card){
        DECK.add(card);
        notify();
    }

    /**
     * Writes the decks contents to a new file
     */
    public void writeHistory() {
        File deckFile = new File("deck" + DECK_NUM + "_output.txt");
        Object cd = new CardDeck();
        try {
            deckFile.createNewFile();
            FileWriter writer = new FileWriter("deck" + DECK_NUM + "_output.txt");
            writer.write("deck" + DECK_NUM + " contents: " + DECK.toString().replaceAll("[,]|[]]|[\\[]", ""));
            writer.close();
        } catch(IOException ignore) {
        }
    }
}