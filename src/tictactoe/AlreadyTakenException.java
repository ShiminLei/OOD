package tictactoe;

class AlreadyTakenException extends Exception {
    public AlreadyTakenException()
    {
        super("This place has been taken");
    }
}
