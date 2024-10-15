package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import model.PeoplesAccounts;

import java.io.IOException;

public class Main extends Application {
    private static Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception {
         PeoplesAccounts.loadUser();
        stage =primaryStage;
        primaryStage.setResizable(false);
        Parent root =FXMLLoader.load(getClass().getResource("startMenu.fxml"));
        primaryStage.setScene(new Scene(root, 502,502));
        primaryStage.show();

    }
    public void changeScene(String fxml) throws IOException {
        Parent pane =FXMLLoader.load(getClass().getResource(fxml));
        stage.getScene().setRoot(pane);

    }
    public static void main(String[] args) {
        launch(args);
    }

}
