import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Player {
    private final LinkedList<Card> HAND = new LinkedList<>();
    static private int numberOfPlayers = 1;
    private final int PLAYER_NUMBER;
    private final CardDeck DRAW_DECK;
    private final CardDeck DISCARD_DECK;
    private final ArrayList<String> HISTORY = new ArrayList<>();

    public Player(CardDeck drawDeck, CardDeck discardDeck){
        this.DISCARD_DECK = discardDeck;
        this.DRAW_DECK = drawDeck;
        PLAYER_NUMBER = numberOfPlayers;
        numberOfPlayers++;
    }

    public void writeHistory(int winningPlayer){
        File playerFile = new File("player" + PLAYER_NUMBER + "_output.txt");
        try {
            playerFile.createNewFile();
            FileWriter writer = new FileWriter("player" + PLAYER_NUMBER + "_output.txt");
            for (String line : HISTORY) {
                writer.write(line + "\n");
            }
            if (PLAYER_NUMBER == winningPlayer)  {
                writer.write("player " + PLAYER_NUMBER + " wins\n");
            } else {
                writer.write("player " + winningPlayer + " has informed player " + PLAYER_NUMBER + " that player " + winningPlayer + " has won\n");
            }
            writer.write("player " + PLAYER_NUMBER + " exits\n" + "player" + PLAYER_NUMBER + " final hand:" + HAND.toString().replaceAll("[,]|[]]|[\\[]", ""));
            writer.close();
        } catch(IOException ignore) {
        }
    }

    private int draw(){
        Card card = DRAW_DECK.removeCard();
        HAND.add(card);
        return card.getNumber();
    }

    private int discard(){
        ArrayList<Integer> possibleDiscards = new ArrayList<>();
        for(int i = 0; i < HAND.size(); i++){
            if (HAND.get(i).getNumber() != PLAYER_NUMBER){
                possibleDiscards.add(i);
            }
        }
        Random rnd = new Random();
        int removeIndex = possibleDiscards.get(rnd.nextInt(0,possibleDiscards.size()));
        Card discardedCard = HAND.remove(removeIndex);
        DISCARD_DECK.addCard(discardedCard);
        return discardedCard.getNumber();
    }

    public boolean makeTurn(){
        int drawnNumber = draw();
        int discardNumber = discard();
        HISTORY.add("player " + PLAYER_NUMBER + " draws a " + drawnNumber + " from deck " + PLAYER_NUMBER);
        HISTORY.add("player " + PLAYER_NUMBER + " discards a " + discardNumber + " from deck " + (PLAYER_NUMBER + 1 == numberOfPlayers ? 0: PLAYER_NUMBER));
        HISTORY.add("player " + PLAYER_NUMBER + " current hand is " + HAND.toString().replaceAll("[,]|[]]|[\\[]",""));
        return hasWon();
    }

    public void receiveCard(Card card) {
        HAND.add(card);
        if (HAND.size() == 4) {
            HISTORY.add("player " + PLAYER_NUMBER + " initial hand " + HAND.toString().replaceAll("[,]|[]]|[\\[]",""));
        }
    }

    private boolean hasWon(){
        int matchingCards = 0;
        for(Card card : HAND){
            assert HAND.peekFirst() != null;
            if(card.getNumber() == HAND.peekFirst().getNumber()){
                matchingCards += 1;
            }
        }
        return matchingCards == 4;
    }

    public int getPlayerNumber(){
        return PLAYER_NUMBER;
    }

}
