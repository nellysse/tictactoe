package org.example.xose;

public class GameModel {
    private char[][] board;
    private char currentPlayer;
    private boolean gameOver;
    private GameMode gameMode;

    public enum GameMode {
        PLAYER_VS_PLAYER,
        PLAYER_VS_AI
    }

    public GameModel() {
        board = new char[3][3];
        currentPlayer = 'X';
        gameOver = false;
        gameMode = GameMode.PLAYER_VS_PLAYER;
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public boolean makeMove(int row, int col) {
        if (gameOver || board[row][col] != ' ') {
            return false;
        }
        board[row][col] = currentPlayer;
        return true;
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public char getCellValue(int row, int col) {
        return board[row][col];
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode mode) {
        this.gameMode = mode;
    }

    public boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public WinResult checkWinner() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != ' ' &&
                    board[i][0] == board[i][1] &&
                    board[i][1] == board[i][2]) {
                return new WinResult(true, new int[][]{{i,0}, {i,1}, {i,2}});
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (board[0][i] != ' ' &&
                    board[0][i] == board[1][i] &&
                    board[1][i] == board[2][i]) {
                return new WinResult(true, new int[][]{{0,i}, {1,i}, {2,i}});
            }
        }

        // Check diagonal (top-left to bottom-right)
        if (board[0][0] != ' ' &&
                board[0][0] == board[1][1] &&
                board[1][1] == board[2][2]) {
            return new WinResult(true, new int[][]{{0,0}, {1,1}, {2,2}});
        }

        // Check diagonal (top-right to bottom-left)
        if (board[0][2] != ' ' &&
                board[0][2] == board[1][1] &&
                board[1][1] == board[2][0]) {
            return new WinResult(true, new int[][]{{0,2}, {1,1}, {2,0}});
        }

        return new WinResult(false, null);
    }

    public void reset() {
        initializeBoard();
        currentPlayer = 'X';
        gameOver = false;
    }

    public static class WinResult {
        private boolean hasWinner;
        private int[][] winningCells;

        public WinResult(boolean hasWinner, int[][] winningCells) {
            this.hasWinner = hasWinner;
            this.winningCells = winningCells;
        }

        public boolean hasWinner() {
            return hasWinner;
        }

        public int[][] getWinningCells() {
            return winningCells;
        }
    }
}

