/**
 * The Card class represents a card in the game
 *
 * @author Jonathan Rutland & Daniel Giles
 * @version 1.0
 */
public class Card {
    /**
     * A private {@link Integer} stores the number of the card
     */
    private final int NUMBER;

    /**
     * Card constructor sets the number of the card
     * @param number the number of the card
     */
    public Card(int number){
        // Asserts the number is a positive integer and then sets the number field
        assert number >= 1 : "Card number must be a non-negative integer";
        this.NUMBER = number;
    }

    /**
     * Returns the number of the card
     * @return the number of the card
     */
    public int getNumber(){
        return this.NUMBER;
    }

    /**
     * Displays the card as a string
     * @return the card number as a string
     */
    @Override
    public String toString(){
        return Integer.toString(NUMBER);
    }
}
