package vendingmachine2;

enum Coin {

    PENNY(1),
    NICKLE(5),
    DIME(10),
    QUARTER(25);

    private float value;

    Coin(float value){
        this.value = value;
    }

    public float getValue(){
        return value;
    }

}
