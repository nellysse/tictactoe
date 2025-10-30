package org.example.xose;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class GameView {
    private Scene scene;
    private Button[][] cellButtons;
    private Label statusLabel;
    private Button newGameBtn;
    private Button modeBtn;
    private HelloController controller;

    private static final String CELL_STYLE = "-fx-font-size: 48px; -fx-font-weight: bold; " +
            "-fx-background-color: #ecf0f1; -fx-border-color: #34495e; -fx-border-width: 2px;";
    private static final String X_STYLE = CELL_STYLE + "-fx-text-fill: #e74c3c;";
    private static final String O_STYLE = CELL_STYLE + "-fx-text-fill: #3498db;";
    private static final String WIN_STYLE = CELL_STYLE + "-fx-background-color: #2ecc71;";

    public GameView(HelloController controller) {
        this.controller = controller;
        controller.setView(this);

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #2c3e50;");

        // Status label
        statusLabel = new Label("Player X's Turn");
        statusLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: white; -fx-font-weight: bold;");
        statusLabel.setAlignment(Pos.CENTER);

        // Game grid
        GridPane grid = createGameGrid();

        // Control buttons
        HBox controls = createControlButtons();

        root.setTop(statusLabel);
        root.setCenter(grid);
        root.setBottom(controls);

        scene = new Scene(root, 500, 600);
    }

    private GridPane createGameGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        cellButtons = new Button[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button btn = new Button("");
                btn.setPrefSize(120, 120);
                btn.setStyle(CELL_STYLE);

                final int row = i;
                final int col = j;
                btn.setOnAction(e -> controller.handleCellClick(row, col));

                cellButtons[i][j] = btn;
                grid.add(btn, j, i);
            }
        }
        return grid;
    }

    private HBox createControlButtons() {
        newGameBtn = new Button("New Game");
        newGameBtn.setStyle("-fx-font-size: 16px; -fx-padding: 10 20; " +
                "-fx-background-color: #27ae60; -fx-text-fill: white; -fx-cursor: hand;");
        newGameBtn.setOnAction(e -> controller.resetGame());

        modeBtn = new Button("Mode: vs Player");
        modeBtn.setStyle("-fx-font-size: 16px; -fx-padding: 10 20; " +
                "-fx-background-color: #3498db; -fx-text-fill: white; -fx-cursor: hand;");
        modeBtn.setOnAction(e -> controller.toggleMode());

        HBox controls = new HBox(20);
        controls.setAlignment(Pos.CENTER);
        controls.setPadding(new Insets(20));
        controls.getChildren().addAll(newGameBtn, modeBtn);

        return controls;
    }

    public void updateCell(int row, int col, char player) {
        cellButtons[row][col].setText(String.valueOf(player));
        cellButtons[row][col].setStyle(player == 'X' ? X_STYLE : O_STYLE);
    }

    public void updateStatus(char player) {
        statusLabel.setText("Player " + player + "'s Turn");
    }

    public void showWinner(char player, int[][] winningCells) {
        statusLabel.setText("Player " + player + " Wins! 🎉");
        for (int[] cell : winningCells) {
            cellButtons[cell[0]][cell[1]].setStyle(WIN_STYLE +
                    "-fx-text-fill: " + (player == 'X' ? "#e74c3c;" : "#3498db;"));
        }
    }

    public void showDraw() {
        statusLabel.setText("It's a Draw! 🤝");
    }

    public void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cellButtons[i][j].setText("");
                cellButtons[i][j].setStyle(CELL_STYLE);
            }
        }
    }

    public void updateModeButton(GameModel.GameMode mode) {
        modeBtn.setText(mode == GameModel.GameMode.PLAYER_VS_AI ?
                "Mode: vs AI 🤖" : "Mode: vs Player 👥");
    }

    public Scene getScene() {
        return scene;
    }
}