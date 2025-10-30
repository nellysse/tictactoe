package org.example.xose;

import javafx.application.Application;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        HelloController controller = new HelloController();
        GameView view = new GameView(controller);

        primaryStage.setTitle("Tic-Tac-Toe Game");
        primaryStage.setScene(view.getScene());
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

