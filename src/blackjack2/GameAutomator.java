package blackjack2;

import blackjack.BlackJack;

import java.util.ArrayList;
import java.util.List;

public class GameAutomator {
    private Deck deck;
    private BlackJackHand[] hands;
    private static final int HIT_UNTIL = 16;

    public GameAutomator(int numPlayers) {
        hands = new BlackJackHand[numPlayers];
        for (int i=0; i<numPlayers; i++){
            hands[i] = new BlackJackHand();
        }
    }

    void initializeDeck(){
        deck = new Deck();
        deck.shuffle();
    }

    boolean dealInitial(){
        for (BlackJackHand hand: hands){
            Card[] cards = deck.dealHand(2);
            if (cards==null){
                return false;
            }
            hand.addCards(cards);
        }
        return true;
    }

    List<Integer> getBlackJacks(){
        List<Integer> winners = new ArrayList<>();
        for (int i=0; i<hands.length; i++){
            if (hands[i].isBlackJack()){
                winners.add(i);
            }
        }
        return winners;
    }

    boolean playHand(BlackJackHand hand){
        while (hand.score()<HIT_UNTIL){
            Card card = deck.dealCard();
            if (card==null){
                return false;
            }
            hand.addCards(new Card[]{card});
        }
        return true;
    }

    boolean playAllHands(){
        for (BlackJackHand hand: hands){
            if (!playHand(hand)){
                return false;
            }
        }
        return true;
    }

    List<Integer> getWinners() {
        List<Integer> winners = new ArrayList<>();
        int winningScore = 0;
        for (int i=0; i<hands.length; i++){
            BlackJackHand hand = hands[i];
            if (!hand.busted()){
                if (hand.score()>winningScore){
                    winningScore = hand.score();
                    winners.clear();
                    winners.add(i);
                }else if (hand.score()==winningScore){
                    winners.add(i);
                }
            }
        }
        return winners;
    }

    void printHandsAndScore(){
        for (int i=0; i<hands.length; i++){
            System.out.println("hand" + i + " (" + hands[i].score() + "): ");
            System.out.println(hands[i]);
            System.out.println();
        }
    }

    public int getScore(int playerID) {
        return hands[playerID].score();
    }

    // return false if the game cannot be successfully simulated.
    public boolean simulate() {
        initializeDeck();
        boolean success = dealInitial();
        if (!success){
            System.out.println("Out of cards.");
            return false;
        }

        System.out.println("-- Initialize --");
        printHandsAndScore();

        List<Integer> blackjacks = getBlackJacks();
        if (blackjacks.size()>0){
            return true;
        }

        success = playAllHands();
        if (!success){
            System.out.println("Out of cards.");
            return false;
        }

        return true;
    }
}