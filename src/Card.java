public class Card {
    private final int NUMBER;
    public Card(int number){
        assert number >= 1 : "Card number must be a non-negative integer";
        this.NUMBER = number;
    }
    public int getNumber(){
        return this.NUMBER;
    }
    @Override
    public String toString(){
        return Integer.toString(NUMBER);
    }
}
