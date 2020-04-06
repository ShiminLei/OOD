package vendingmachine2;

import java.util.List;

public interface State {
    float selectItem(String selection);
    void insertCoins(List<Coin> coins);
    List<Coin> cancelTransaction();
    Pair executeTransaction();

}
