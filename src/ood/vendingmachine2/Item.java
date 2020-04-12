package ood.vendingmachine2;

public class Item {

    protected ItemInfo itemInfo;

    protected Item(ItemInfo itemInfo) {
        this.itemInfo = itemInfo;
    }

    protected ItemInfo getItemInfo() {
        return itemInfo;
    }
}
