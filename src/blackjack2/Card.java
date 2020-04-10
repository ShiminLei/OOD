package blackjack2;

public class Card {
    private final int value;
    private final Suit suit;

    public Card(int c, Suit s) {
        this.value = c;
        this.suit = s;
    }

    public int value() {
        return value;
    }

    public Suit suit() {
        return suit;
    }
}