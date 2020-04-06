package vendingmachine2;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendingMachine {
    private Map<Float, List<Coin>> coins;
    private Stock stock;
    private Map<String, ItemInfo> itemIdentifier;
    private ItemInfo currentSelection;
    private List<Coin> currentCoins;

    private AbstractState state;
    private NoSelectionState noSelectionState;
    private HasSelectionState hasSelectionState;
    private InsertedMoneyState insertedMoneyState;

    public VendingMachine() {
        this.coins = new HashMap<>();
        this.coins.put(1f, new ArrayList<>());
        this.coins.put(5f, new ArrayList<>());
        this.coins.put(10f, new ArrayList<>());
        this.coins.put(25f, new ArrayList<>());
        this.stock = new Stock();
        this.itemIdentifier = new HashMap<>();
        this.itemIdentifier.put("Coke", new ItemInfo(5f));
        this.itemIdentifier.put("Sprite", new ItemInfo(10f));
        this.itemIdentifier.put("MountainDew", new ItemInfo(15f));

        this.currentSelection = null;
        this.currentCoins = new ArrayList<>();

        this.noSelectionState = new NoSelectionState(this);
        this.hasSelectionState = new HasSelectionState(this);
        this.insertedMoneyState = new InsertedMoneyState(this);
        this.state = noSelectionState;
    }

    public Map<String, ItemInfo> getItemIdentifier() {
        return itemIdentifier;
    }

    public void refillItems(List<Item> items){
        for (Item item: items){
            stock.add(item);
        }
    }

    // 4 个 实际的方法
    public float realSelectItem(String selection){
        currentSelection = itemIdentifier.get(selection);
        try{
            if (currentSelection==null){
                throw new NotEnoughItemException();
            }
        }catch (NotEnoughItemException e){
            System.out.println("Not enough item.");
        }
        return currentSelection.getPrice();
    }

    public void realInsertCoins(List<Coin> coins){
        for (Coin coin: coins){
            currentCoins.add(coin);
        }
    }

    public List<Coin> realCancelTransaction(){
        currentSelection = null;
        List<Coin> refundCoins = currentCoins;
        currentCoins = new ArrayList<>();
        return refundCoins;
    }

    public Pair realExecuteTransaction() {
        Item item;
        try{
            item = stock.deduct(currentSelection);
        }catch (NotEnoughItemException e){
            List<Coin> currentCoinsRecord = currentCoins;
            currentCoins = new ArrayList<>();
            currentSelection = null;
            return new Pair(null, currentCoinsRecord);
        }

        List<Coin> refundCoins;
        try{
            refundCoins = refund();
            if (refundCoins==null){
                stock.add(item);
                List<Coin> currentCoinsRecord = currentCoins;
                currentCoins = new ArrayList<>();
                currentSelection = null;
                return new Pair(null, currentCoinsRecord);
            }
        }catch (NotEnoughMoneyException e){
            stock.add(item);
            List<Coin> currentCoinsRecord = currentCoins;
            currentCoins = new ArrayList<>();
            currentSelection = null;
            return new Pair(null, currentCoinsRecord);
        }
        currentCoins = new ArrayList<>();
        currentSelection = null;
        return new Pair(item, refundCoins);
    }

    private List<Coin> refund() throws NotEnoughMoneyException {
        float price = currentSelection.getPrice();
        float currentMoney = 0f;
        for (Coin coin: currentCoins){
            currentMoney += coin.getValue();
        }
        if (currentMoney<price){
            throw new NotEnoughMoneyException();
        }
        // 下面开始操作
        for (Coin coin: currentCoins){
            coins.get(coin.getValue()).add(coin);
        }
        float gap = currentMoney-price;
        List<Coin> refund = new ArrayList<>();
        for (float value: coins.keySet()){
            while (gap>0 && gap>=value && coins.get(value).size()>0){
                gap -= value;
                refund.add(coins.get(value).remove(coins.get(value).size()-1));
            }
        }
        if (gap!=0){
            for (Coin coin: refund){
                coins.get(coin.getValue()).add(coin);
            }
            for (Coin coin: currentCoins){
                coins.get(coin.getValue()).remove(coin);
            }
            return null;
        }
        return refund;
    }

    public void changeToNoSelectionState() {
        state = noSelectionState;
    }

    public void changeToHasSelectionState() {
        state = hasSelectionState;
    }

    public void changeToInsertedMoneyState() {
        state = insertedMoneyState;
    }

    public float selectItem(String selection) {
        return state.selectItem(selection);
    }

    public void insertCoins(List<Coin> coins) {
        state.insertCoins(coins);
    }

    public List<Coin> cancelTransaction() {
        return state.cancelTransaction();
    }

    public Pair executeTransaction() {
        return state.executeTransaction();
    }



}
