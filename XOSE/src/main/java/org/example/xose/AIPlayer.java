package org.example.xose;

public class AIPlayer {
    private GameModel model;

    public AIPlayer(GameModel model) {
        this.model = model;
    }

    public int[] getBestMove() {
        // Try to win
        int[] move = findWinningMove('O');
        if (move != null) {
            return move;
        }

        // Block player from winning
        move = findWinningMove('X');
        if (move != null) {
            return move;
        }

        // Take center if available
        if (model.getCellValue(1, 1) == ' ') {
            return new int[]{1, 1};
        }

        // Take a corner
        int[][] corners = {{0,0}, {0,2}, {2,0}, {2,2}};
        for (int[] corner : corners) {
            if (model.getCellValue(corner[0], corner[1]) == ' ') {
                return corner;
            }
        }

        // Take any available spot
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (model.getCellValue(i, j) == ' ') {
                    return new int[]{i, j};
                }
            }
        }

        return null;
    }

    private int[] findWinningMove(char player) {
        // Check all possible positions for a winning move
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (model.getCellValue(i, j) == ' ') {
                    // Check if this move would win
                    if (wouldWin(i, j, player)) {
                        return new int[]{i, j};
                    }
                }
            }
        }
        return null;
    }

    private boolean wouldWin(int row, int col, char player) {
        // Check row
        int rowCount = 0;
        for (int j = 0; j < 3; j++) {
            if (j == col || model.getCellValue(row, j) == player) {
                rowCount++;
            }
        }
        if (rowCount == 3) return true;

        // Check column
        int colCount = 0;
        for (int i = 0; i < 3; i++) {
            if (i == row || model.getCellValue(i, col) == player) {
                colCount++;
            }
        }
        if (colCount == 3) return true;

        // Check diagonal (top-left to bottom-right)
        if (row == col) {
            int diagCount = 0;
            for (int i = 0; i < 3; i++) {
                if (i == row || model.getCellValue(i, i) == player) {
                    diagCount++;
                }
            }
            if (diagCount == 3) return true;
        }

        // Check diagonal (top-right to bottom-left)
        if (row + col == 2) {
            int diagCount = 0;
            for (int i = 0; i < 3; i++) {
                if ((i == row && (2-i) == col) || model.getCellValue(i, 2-i) == player) {
                    diagCount++;
                }
            }
            if (diagCount == 3) return true;
        }

        return false;
    }
}
