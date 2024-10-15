package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.scene.control.Label;
import main.Main;
import model.PeoplesAccounts;

import java.io.IOException;

public class RegisterController {
    @FXML
    private Label DuplicateUsernameError;
    @FXML
    public TextField username;
    @FXML
    public PasswordField password;
    @FXML
    private Button backButton;


    public void registerController(ActionEvent event) throws IOException {
        if(!username.getText().isBlank() || !password.getText().isBlank()){
            DuplicateUsernameError.setText("you haven't written anything yet!");
        }
        if(!PeoplesAccounts.validUsername(username.getText()))
        {DuplicateUsernameError.setText("this account with this username is already exist!");}
        else {
            DuplicateUsernameError.setText("sign up successful");
            PeoplesAccounts.addPeople(username.getText(),password.getText());
            PeoplesAccounts.saveUsers();

        }

    }
      public void backButtonOnAction(ActionEvent event) throws IOException {
        Main main = new Main();
        main.changeScene("/main/startMenu.fxml");
    }

}
