public class Card {
    private final int NUMBER;
    public Card(int number){
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
