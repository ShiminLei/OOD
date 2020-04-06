package vendingmachine2;

public abstract class AbstractState implements State{
    VendingMachine vm;

    public AbstractState(VendingMachine vm) {
        this.vm = vm;
    }
}
