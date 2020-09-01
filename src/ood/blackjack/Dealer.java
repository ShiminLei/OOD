package ood.blackjack;

class Dealer {
    private BlackJack game;
    private Hand hand;
    private int bets;

    public Dealer() {
        hand = new Hand();
        bets = 10000;
    }

    /**
     * 插一张牌
     * @param card
     */
    public void insertCard(Card card) {
        hand.insertCard(card);
    }

    /**
     * 是否赢某个玩家
     * @param p
     * @return
     */
    public boolean largerThan(NormalPlayer p) {
        return hand.getBestValue() >= p.getBestValue();
    }

    /**
     * 更新 bets
     * @param amount
     */
    public void updateBets(int amount) {
        bets += amount;
    }

    /**
     * 加入游戏
     * @param game
     */
    public void setGame(BlackJack game) {
        this.game = game;
    }

    /**
     * 抽一张牌
     */
    public void dealNextCard() {
        insertCard(game.dealNextCard());
    }

    public String printDealer() {
        return "Dealer " + hand.printHand() + ", total bets: " + bets + "\n";
    }
}
