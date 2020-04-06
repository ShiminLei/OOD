package vendingmachine2;

import java.util.List;

public class NoSelectionState extends AbstractState {
    public NoSelectionState(VendingMachine vm) {
        super(vm);
    }

    @Override
    public float selectItem(String selection) {
        vm.changeToHasSelectionState();
        return vm.realSelectItem(selection);
    }

    @Override
    public void insertCoins(List<Coin> coins) {
        System.out.println("您还没有选择物品，请先选择物品。");
    }

    @Override
    public List<Coin> cancelTransaction() {
        System.out.println("您还没有选择物品，请先选择物品。");
        return null;
    }

    @Override
    public Pair executeTransaction() {
        System.out.println("您还没有选择物品，请先选择物品。");
        return null;
    }
}
