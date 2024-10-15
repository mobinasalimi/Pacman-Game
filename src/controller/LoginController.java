package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javafx.scene.control.Label;
import main.Main;
import model.PeoplesAccounts;

import java.io.IOException;


public class LoginController {
    @FXML
    private Button backButton;
    @FXML
    private Label username;
    @FXML
    private Label password;
    @FXML
    private Label YouHaveNotRegisteredYet;
    @FXML
    private Button playNewGameButton;
    @FXML
    private TextField usernamee;
    @FXML
    private PasswordField passwordd;

    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        Main main=new Main();
        main.changeScene("/main/startMenu.fxml");
    }

    public void playNewGameOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/game.fxml"));
        Parent root = loader.load();
        Stage stg =(Stage) playNewGameButton.getScene().getWindow();
        Controller c =loader.getController();
        stg.getScene().setRoot(root);
        root.setOnKeyPressed(c);
        root.requestFocus();
    }

    public void login(ActionEvent event) {

           if(PeoplesAccounts.checkLoginInfo(username.getText(),password.getText())) {
               YouHaveNotRegisteredYet.setText("login successful!");
           }else {
               YouHaveNotRegisteredYet.setText("username or password is wrong, try again!");
           }
        }

}
