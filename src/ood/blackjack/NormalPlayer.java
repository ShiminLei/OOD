package ood.blackjack;

class NormalPlayer {
    private BlackJack game;
    private int id;
    private Hand hand;
    private int totalBets;
    private int bets;
    private boolean stopDealing;

    /**
     * 初始化，玩家押注
     * @param id
     * @param bets
     */
    public NormalPlayer(int id, int bets) {
        this.id = id;
        hand = new Hand();
        totalBets = 1000;
        try{
            placeBets(bets);
        }catch(Exception e){
            e.printStackTrace();
        }
        stopDealing = false;
    }

    public int getId() {
        return this.id;
    }

    /**
     * 插入一张牌
     * @param card
     */
    public void insertCard(Card card) {
        hand.insertCard(card);
    }

    /**
     * 当前最好的分数
     * @return
     */
    public int getBestValue() {
        return hand.getBestValue();
    }

    /**
     * 不再抽牌
     */
    public void stopDealing() {
        stopDealing = true;
    }

    /**
     * 加入游戏
     * @param game
     */
    public void joinGame(BlackJack game) {
        this.game = game;
        game.addPlayer(this);
    }

    /**
     * 抽一张牌
     */
    public void dealNextCard() {
        insertCard(game.dealNextCard());
    }

    /**
     * 押注
     * @param amount
     * @throws Exception
     */
    public void placeBets(int amount) throws Exception {
        if (totalBets < amount) {
            throw new Exception("No enough money.");
        }
        bets = amount;
        totalBets -= bets;
    }

    public int getCurrentBets() {
        return bets;
    }

    /**
     * 获胜得双倍注
     */
    public void win() {
        totalBets += (bets * 2);
        bets = 0;
    }

    /**
     * 失败失去注
     */
    public void lose() {
        bets = 0;
    }

    public String printPlayer() {
        return hand.printHand() + ", current bets: " + bets + ", total bets: " + totalBets + "\n";
    }

}
