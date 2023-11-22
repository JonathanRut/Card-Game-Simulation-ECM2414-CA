import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Player {
    private LinkedList<Card> hand = new LinkedList<>();
    static private int numberOfPlayers = 1;
    private final int playerNumber;
    private final CardDeck drawDeck;
    private final CardDeck discardDeck;
    private ArrayList<String> history = new ArrayList<>();
    static volatile private Player winner;
    private int turn = 0;
    static volatile private int maxTurn = Integer.MAX_VALUE;

    public Player(CardDeck drawDeck, CardDeck discardDeck){
        this.discardDeck = discardDeck;
        this.drawDeck = drawDeck;
        playerNumber = numberOfPlayers;
        numberOfPlayers++;
    }

    private void writeHistory(){}

    private synchronized int draw(){
        Card card = drawDeck.removeCard();
        hand.add(card);
        return card.getNumber();
    }

    private int discard(){
        ArrayList<Integer> possibleDiscards = new ArrayList<Integer>();
        for(int i = 0; i < hand.size(); i++){
            if (hand.get(i).getNumber() != playerNumber){
                possibleDiscards.add(i);
            }
        }
        Random rnd = new Random();
        int removeIndex = possibleDiscards.get(rnd.nextInt(0,possibleDiscards.size()));
        Card discardedCard = hand.remove(removeIndex);
        discardDeck.addCard(discardedCard);
        return discardedCard.getNumber();
    }

    public synchronized int makeTurn(){
        turn++;
        int drawnNumber = draw();
        int discardNumber = discard();
        System.out.println("" + playerNumber + " " + turn + " " + hand + " " + maxTurn);
        history.add("player " + playerNumber + " draws a " + drawnNumber + " from deck " + playerNumber);
        history.add("player " + playerNumber + "discards a " + discardNumber + " from deck " + (playerNumber + 1 == numberOfPlayers ? 0:playerNumber));
        history.add("player " + playerNumber + " current hand is " + hand.toString().replaceAll("[,]|[]]|[\\[]",""));
        return hasWon();
    }

    public void receiveCard(Card card) {
        hand.add(card);
        if (hand.size() == 4) {
            history.add("player " + playerNumber + " initial hand " + hand.toString().replaceAll("[,]|[]]|[\\[]",""));
        }
    }

    private int hasWon(){
        int matchingCards = 0;
        for(Card card : hand){
            if(card.getNumber() == hand.peekFirst().getNumber()){
                matchingCards += 1;
            }
        }
        return  matchingCards == 4 ? turn:Integer.MAX_VALUE;
    }

    public int getPlayerNumber(){
        return  playerNumber;
    }

    public int getTurn(){
        return turn;
    }
}
