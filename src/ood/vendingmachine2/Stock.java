package ood.vendingmachine2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Stock {

    private Map<ItemInfo, List<Item>> stock;

    public Stock() {
        this.stock = new HashMap<>();
    }

    public int getQuantity(ItemInfo itemInfo){
        if (stock.get(itemInfo)==null){
            return 0;
        }
        return stock.get(itemInfo).size();
    }

    public void add(Item item){
        ItemInfo itemInfo = item.getItemInfo();
        if (stock.get(itemInfo)==null){
            stock.put(itemInfo, new ArrayList<>());
        }
        stock.get(itemInfo).add(item);
    }

    public Item deduct(ItemInfo itemInfo) throws NotEnoughItemException {
        if (stock.get(itemInfo)!=null){
            if (getQuantity(itemInfo)>0){
                return stock.get(itemInfo).remove(stock.get(itemInfo).size()-1);
            }
        }
        throw new NotEnoughItemException();
    }

    @Override
    public String toString() {
        return "Stock{" +
                "stock=" + stock +
                '}';
    }
}
