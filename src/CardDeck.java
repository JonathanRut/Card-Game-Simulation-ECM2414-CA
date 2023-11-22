import java.util.LinkedList;

public class CardDeck {
    private LinkedList<Card> deck = new LinkedList<>();

    public CardDeck(){
    }

    public synchronized Card removeCard(){
        while(deck.size() == 0){
            try{
                wait();
            } catch (InterruptedException ignored){}
        }
        return deck.removeFirst();
    }

    public synchronized void addCard(Card card){
        deck.add(card);
        notify();
    }
}