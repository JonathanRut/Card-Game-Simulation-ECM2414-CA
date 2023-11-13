import java.util.LinkedList;

public class CardDeck {
    private LinkedList<Card> deck;

    public CardDeck(LinkedList<Card> deck){
        this.deck = deck;
    }

    public synchronized Card removeCard(){
        while(deck.size() == 0){
            try{
                wait();
            } catch (InterruptedException e){}
        }
        return deck.removeFirst();
    }

    public void addCard(Card card){
        deck.add(card);
    }
}
