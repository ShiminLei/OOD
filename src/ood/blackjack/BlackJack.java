package ood.blackjack;

import java.util.ArrayList;
import java.util.List;

public class BlackJack {
    private List<NormalPlayer> players;
    private Dealer dealer;

    private List<Card> cards;

    public BlackJack() {
        players = new ArrayList<>();
        dealer = new Dealer();
    }

    /**
     * 初始化所有牌
     * @param cards
     */
    public void initCards(List<Card> cards) {
        this.cards = cards;
    }

    /**
     * 加 normal玩家
     * @param p
     */
    public void addPlayer(NormalPlayer p) {
        players.add(p);
    }

    /**
     * 初始化，每个人抽两张牌
     */
    public void dealInitialCards() {
        for (NormalPlayer player : players) {
            player.insertCard(dealNextCard());
        }
        dealer.insertCard(dealNextCard());

        for (NormalPlayer player : players) {
            player.insertCard(dealNextCard());
        }
        dealer.insertCard(dealNextCard());
    }

    /**
     * 下一张牌
     * @return
     */
    public Card dealNextCard() {
        Card card = cards.remove(0);
        return card;
    }

    public Dealer getDealer() {
        return dealer;
    }

    /**
     * 比较输赢
     */
    public void compareResult() {
        for (NormalPlayer p : players) {
            if (dealer.largerThan(p)) {
                dealer.updateBets(p.getCurrentBets());
                p.lose();
            } else {
                dealer.updateBets(-p.getCurrentBets());
                p.win();
            }
        }
    }

    public String print() {
        String s = "";
        for (NormalPlayer player : players) {
            s += "playerid: " + (player.getId() + 1) + " ;" + player.printPlayer();
        }
        return s;
    }
}


