package ood.vendingmachine2;

import java.util.List;

public class Pair {

    private Item item;
    private List<Coin> coins;

    public Pair(Item item, List<Coin> coins) {
        this.item = item;
        this.coins = coins;
    }
}
