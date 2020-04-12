package ood.coffermaker;

public class CoffeeMaker {

    public Coffee makeCoffee(CoffeePack pack) {
        Coffee coffee = new SimpleCoffee();

        for (int i = 0; i < pack.getNeededMilk(); i++) {
            coffee = new WithMilk(coffee);
        }

        for (int i = 0; i < pack.getNeededSugar(); i++) {
            coffee = new WithSugar(coffee);
        }

        return coffee;
    }
}

