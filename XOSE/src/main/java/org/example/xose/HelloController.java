package org.example.xose;

public class HelloController {
    private GameModel model;
    private AIPlayer aiPlayer;
    private GameView view;

    public HelloController() {
        model = new GameModel();
        aiPlayer = new AIPlayer(model);
    }

    public void setView(GameView view) {
        this.view = view;
    }

    public void handleCellClick(int row, int col) {
        if (model.isGameOver() || model.getCellValue(row, col) != ' ') {
            return;
        }

        // player move
        makeMove(row, col);

        // AI move (if applicable)
        if (!model.isGameOver() &&
                model.getGameMode() == GameModel.GameMode.PLAYER_VS_AI &&
                model.getCurrentPlayer() == 'O') {
            int[] aiMove = aiPlayer.getBestMove();
            if (aiMove != null) {
                makeMove(aiMove[0], aiMove[1]);
            }
        }
    }

    private void makeMove(int row, int col) {
        if (model.makeMove(row, col)) {
            view.updateCell(row, col, model.getCurrentPlayer());

            GameModel.WinResult result = model.checkWinner();
            if (result.hasWinner()) {
                model.setGameOver(true);
                view.showWinner(model.getCurrentPlayer(), result.getWinningCells());
            } else if (model.isBoardFull()) {
                model.setGameOver(true);
                view.showDraw();
            } else {
                model.switchPlayer();
                view.updateStatus(model.getCurrentPlayer());
            }
        }
    }

    public void resetGame() {
        model.reset();
        view.resetBoard();
        view.updateStatus(model.getCurrentPlayer());
    }

    public void toggleMode() {
        if (model.getGameMode() == GameModel.GameMode.PLAYER_VS_PLAYER) {
            model.setGameMode(GameModel.GameMode.PLAYER_VS_AI);
        } else {
            model.setGameMode(GameModel.GameMode.PLAYER_VS_PLAYER);
        }
        resetGame();
        view.updateModeButton(model.getGameMode());
    }

    public GameModel.GameMode getCurrentMode() {
        return model.getGameMode();
    }
}