package ood.vendingmachine2;


public class NotEnoughItemException extends Exception{

    public NotEnoughItemException(){
        super("There is no enough this kind of item. ");
    }
}



