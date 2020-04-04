package tictactoe;

class GameEndException extends Exception{
    public GameEndException()
    {
        super("Game has been ended, cannot make any more moves");
    }
}
