import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * The Player class represents players in the card game
 *
 * @author Jonathan Rutland & Daniel Giles
 * @version 1.0
 */
public class Player {
    /**
     * A private {@link LinkedList} of {@link Card} that stores the players hand
     * */
    private final LinkedList<Card> HAND = new LinkedList<>();
    /**
     * A private static {@link Integer} stores the number of players created
     */
    static private int numberOfPlayers = 0;
    /**
     * A private {@link Integer} stores the players number
     */
    private final int PLAYER_NUMBER;
    /**
     * A private {@link CardDeck} references the drawing deck
     */
    private final CardDeck DRAW_DECK;
    /**
     * A private {@link CardDeck} references the discard deck
     */
    private final CardDeck DISCARD_DECK;
    /**
     * A private {@link ArrayList} of {@link String} contains the players history
     */
    private final ArrayList<String> HISTORY = new ArrayList<>();

    /**
     * Player constructor assigns the deck fields and assigns the player number
     * @param drawDeck the players drawing deck
     * @param discardDeck the players discarding deck
     */
    public Player(CardDeck drawDeck, CardDeck discardDeck){
        numberOfPlayers++;
        this.DISCARD_DECK = discardDeck;
        this.DRAW_DECK = drawDeck;
        PLAYER_NUMBER = numberOfPlayers;
    }

    /**
     * Writes into a text file the history of the players game
     * @param winningPlayer the number of the player that won
     */
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
            writer.write("player " + PLAYER_NUMBER + " exits\n" + "player " + PLAYER_NUMBER + " final hand:" + HAND.toString().replaceAll("[,]|[]]|[\\[]", ""));
            writer.close();
        } catch(IOException ignore) {
        }
    }

    /**
     * When run the player draws from the drawing deck and puts it in hand
     * @return the number the player drew
     */
    private int draw(){
        Card card = DRAW_DECK.removeCard();
        HAND.add(card);
        return card.getNumber();
    }

    /**
     * When run the player runs its discard strategy and moves card from hand to discard deck
     * @return returns the number of the card discarded
     */
    private int discard(){
        assert HAND.size() != 0 : "To discard the hand must contain cards";
        ArrayList<Integer> possibleDiscards = new ArrayList<>();
        for(int i = 0; i < HAND.size(); i++){
            if (HAND.get(i).getNumber() != PLAYER_NUMBER){
                possibleDiscards.add(i);
            }
        }
        Random rnd = new Random();
        assert possibleDiscards.size() != 0 : "Hand contains all preferred cards cannot discard";
        int removeIndex = possibleDiscards.get(rnd.nextInt(0,possibleDiscards.size()));
        Card discardedCard = HAND.remove(removeIndex);
        DISCARD_DECK.addCard(discardedCard);
        return discardedCard.getNumber();
    }

    /**
     * When run the player makes one turn in the game
     * @return a boolean indicating if the player has won or lost in that turn
     */
    public boolean makeTurn(){
        int drawnNumber = draw();
        int discardNumber = discard();
        HISTORY.add("player " + PLAYER_NUMBER + " draws a " + drawnNumber + " from deck " + PLAYER_NUMBER);
        HISTORY.add("player " + PLAYER_NUMBER + " discards a " + discardNumber + " to deck " + (PLAYER_NUMBER == numberOfPlayers ? 1: PLAYER_NUMBER + 1));
        HISTORY.add("player " + PLAYER_NUMBER + " current hand is " + HAND.toString().replaceAll("[,]|[]]|[\\[]",""));
        return hasWon();
    }

    /**
     * Adds a card to players hand when 4 cards added initial hand is written in history
     * @param card the card added to the hand
     */
    public void receiveCard(Card card) {
        HAND.add(card);
        if (HAND.size() == 4) {
            HISTORY.add("player " + PLAYER_NUMBER + " initial hand " + HAND.toString().replaceAll("[,]|[]]|[\\[]",""));
        }
    }

    /**
     * Iterates through the players hand and figures out if they have won or not
     * @return a boolean indicating if the player has won or not
     */
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

    /**
     * Getter returns the players number
     * @return the players number
     */
    public int getPlayerNumber(){
        return PLAYER_NUMBER;
    }

}
