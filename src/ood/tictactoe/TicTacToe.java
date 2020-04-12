package ood.tictactoe;

public class TicTacToe {
    private char[][] board;
    private char currentPlayerMark;
    private boolean gameEnd;

    public TicTacToe() {
        board = new char[3][3];
        initialize();
    }

    public char getCurrentPlayer() {
        return currentPlayerMark;
    }

    public void initialize() {
        gameEnd = false;
        currentPlayerMark = 'x';

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    public boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        gameEnd = true;
        System.out.println("棋盘已经满了。");
        return true;
    }

    public void changePlayer() {
        if (currentPlayerMark == 'x')
            currentPlayerMark = 'o';
        else
            currentPlayerMark = 'x';

    }

    // true means this move wins the game, false means otherwise
    public boolean move(int row, int col) throws AlreadyTakenException, GameEndException {
        isBoardFull();
        if (gameEnd) {
            throw new GameEndException();
        }

        if (board[row][col] != '-') {
            throw new AlreadyTakenException();
        }

        board[row][col] = currentPlayerMark;
        boolean win = chechWin(row, col);
        changePlayer();
        return win;
    }

    private boolean chechWin(int row, int col){
        boolean win;

        //check row
        win = true;
        for (int i = 0; i < board.length; i++) {
            if (board[row][i] != currentPlayerMark) {
                win = false;
                break;
            }
        }

        if (win) {
            gameEnd = true;
            return win;
        }

        //check column
        win = true;
        for (int i = 0; i < board.length; i++) {
            if (board[i][col] != currentPlayerMark) {
                win = false;
                break;
            }
        }

        if (win) {
            gameEnd = true;
            return win;
        }

        //check back diagonal
        win = true;
        for (int i = 0; i < board.length; i++) {
            if (board[i][i] != currentPlayerMark) {
                win = false;
                break;
            }
        }

        if (win) {
            gameEnd = true;
            return win;
        }

        //check forward diagonal
        win = true;
        for (int i = 0; i < board.length; i++) {
            if (board[i][board.length - i - 1] != currentPlayerMark) {
                win = false;
                break;
            }
        }

        if (win) {
            gameEnd = true;
            return win;
        }
        return false;
    }
}


