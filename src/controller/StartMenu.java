package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.Main;

import java.io.IOException;

public class StartMenu {
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Button playAsGuestButton;
    @FXML
    private Button scoreboardButton;
    @FXML
    private Button settingButton;
    @FXML
    private Button exitButton;

    public void loginOnAction(ActionEvent event) throws IOException {
        Main main=new Main();
        main.changeScene("/main/loginMenu.fxml");
    }

    public void playAsGuestAction(ActionEvent event) throws IOException {
        FXMLLoader l = new FXMLLoader(getClass().getResource("/main/game.fxml"));
        Parent root = l.load();
        Stage stg =(Stage) playAsGuestButton.getScene().getWindow();
        Controller c =l.getController();
        stg.getScene().setRoot(root);
        root.setOnKeyPressed(c);
        root.requestFocus();

    }
    public void settingOnAction(ActionEvent event) throws IOException {
        Main main=new Main();
        main.changeScene("/main/settingMenu.fxml");
    }

    public void exitOnAction(ActionEvent event) throws IOException {
        Stage stage =(Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void scoreboardOnAction(ActionEvent event) throws IOException {
        Main main=new Main();
        main.changeScene("/main/scoreboard.fxml");
    }

    public void registerOnAction(ActionEvent event) throws IOException {
        Main main=new Main();
        main.changeScene("/main/registerMenu.fxml");
    }
}
