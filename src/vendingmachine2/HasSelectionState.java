package vendingmachine2;

import java.util.List;

public class HasSelectionState extends AbstractState {
    public HasSelectionState(VendingMachine vm) {
        super(vm);
    }

    @Override
    public float selectItem(String selection) {
        return vm.selectItem(selection);
    }

    @Override
    public void insertCoins(List<Coin> coins) {
        vm.realInsertCoins(coins);
        vm.changeToInsertedMoneyState();
    }

    @Override
    public List<Coin> cancelTransaction() {
        vm.changeToNoSelectionState();
        return vm.realCancelTransaction();
    }

    @Override
    public Pair executeTransaction() {
        System.out.println("您还没有付钱。");
        return null;
    }
}
