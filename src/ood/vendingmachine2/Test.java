package ood.vendingmachine2;

public class Test {
    public static void main(String[] args) {
        Coke c = new Coke(new ItemInfo(9.0f));
        System.out.println(c.getItemInfo().getPrice());

        Coin e = Coin.DIME;
        System.out.println(e.getValue());
    }
}
