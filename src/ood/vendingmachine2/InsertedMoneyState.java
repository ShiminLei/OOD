package ood.vendingmachine2;

import java.util.List;

public class InsertedMoneyState extends AbstractState {
    public InsertedMoneyState(VendingMachine vm) {
        super(vm);
    }

    @Override
    public float selectItem(String selection) {
        System.out.println("现在不能进行物品的选择。");
        return 0;
    }

    @Override
    public void insertCoins(List<Coin> coins) {
        vm.realInsertCoins(coins);
    }

    @Override
    public List<Coin> cancelTransaction() {
        vm.changeToNoSelectionState();
        return vm.realCancelTransaction();
    }

    @Override
    public Pair executeTransaction() {
        vm.changeToNoSelectionState();
        return vm.realExecuteTransaction();
    }
}
