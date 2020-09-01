package ood.blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlackJackHand {

    private final List<Card> cards = new ArrayList<>();

    /**
     * 得到一张牌的分数
     * @param card
     * @return
     */
    private int[] getScores(Card card){
        if (card.value()>1){
            return new int[]{Math.min(card.value(), 10)};
        }else {
            return new int[]{1,11};
        }
    }

    /**
     * 新抓一张牌，更新此时所有可能的分数
     * @param card
     * @param scores
     */
    private void updateScores(Card card, List<Integer> scores){
        final int[] toAdd = getScores(card);
        if (scores.isEmpty()){
            for (int score: toAdd){
                scores.add(score);
            }
        }else {
            final int length = scores.size();
            for (int i=0; i<length; i++){
                int oldScore = scores.get(i);
                scores.set(i, oldScore+toAdd[0]);
                for (int j=1; j<toAdd.length; j++){
                    scores.add(oldScore+toAdd[j]);
                }
            }
        }
    }

    /**
     * 手上所有牌，所有可能的分数
     * @return
     */
    private List<Integer> possibleScores(){
        List<Integer> scores = new ArrayList<>();
        for (Card card: cards){ // 一张一张牌更新分数
            updateScores(card, scores);
        }
        return scores;
    }

    /**
     * 当前最理想的分数
     * @return
     */
    public int score() {
        List<Integer> scores = possibleScores();
        if (scores.isEmpty()){
            return 0;
        }
        int maxUnder = Integer.MIN_VALUE; // 不超过21中最大的分数
        int minOver = Integer.MAX_VALUE; // 超过21中最小的分数
        for (int score: scores){
            if (score>21 && score<minOver){
                minOver = score;
            }else if (score<=21 && score>maxUnder){
                maxUnder = score;
            }
        }
        return maxUnder==Integer.MIN_VALUE? minOver:maxUnder;
    }

    public void addCards(Card[] c) {
        Collections.addAll(cards, c);
    }

    public int size() {
        return cards.size();
    }

    /**
     * 最理想的分数是不是超过21
     * @return
     */
    public boolean busted() {
        return score()>21;
    }

    /**
     * 判断手上的牌是不是正好是，A 和 10点牌 的组合
     * @return
     */
    public boolean isBlackJack() {
        if (cards.size()!=2){
            return false;
        }
        Card first = cards.get(0);
        Card second = cards.get(1);
        return (isAce(first)&&isFaceCard(second)) || (isAce(second) && isFaceCard(first));
    }

    /**
     * 判断是不是 A （1 或者 11）
     * @param c
     * @return
     */
    private static boolean isAce(Card c){
        return c.value()==1;
    }

    /**
     * 判断点数是不是 10
     * @param c
     * @return
     */
    private static boolean isFaceCard(Card c){
        return c.value()>=11 && c.value()<=13;
    }

    @Override
    public String toString() {
        return "BlackJackHand{" +
                "cards=" + cards +
                '}';
    }
}
