package vendingmachine2;

public class NotEnoughMoneyException extends Exception{

        public NotEnoughMoneyException(){
            super("The money is not enough. ");
        }

}
