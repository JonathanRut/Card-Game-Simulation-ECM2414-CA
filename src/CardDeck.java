import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

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
        // Increments the static field by one and then sets the decks number
        numDecks++;
        DECK_NUM = numDecks;
    }

    /**
     * Removes a card from deck will wait if deck is empty
     * @return the card removed from the deck
     */
    public synchronized Card removeCard(){
        // The threads should wait for a card to be added while the deck is empty
        while(DECK.size() == 0){
            try{
                wait();
            } catch (InterruptedException ignored){}
        }

        // Once there are cards in the deck the top card of the deck is removed and returned
        return DECK.removeFirst();
    }

    /**
     * Adds card to the deck and notifies waiting threads
     * @param card the card added to the deck
     */
    public synchronized void addCard(Card card){
        // The card is added to the deck then waiting threads are notified
        DECK.add(card);
        notify();
    }

    /**
     * Writes the decks contents to a new file
     */
    public void writeContent() {
        // A File is created to store the deck
        File deckFile = new File("deck" + DECK_NUM + "_output.txt");
        try {
            deckFile.createNewFile();

            // A writer is then created to write the deck
            FileWriter writer = new FileWriter("deck" + DECK_NUM + "_output.txt");

            // The contents of the deck is written then writer is closed
            writer.write("deck" + DECK_NUM + " contents: " + DECK.toString().replaceAll("[,]|[]]|[\\[]", ""));
            writer.close();
        } catch(IOException ignore) {}
    }
}