package controller;

import javafx.event.ActionEvent;
import main.Main;

import java.io.IOException;

public class Scoreboard {
    public void backButtonOnAction(ActionEvent event) throws IOException {
        Main main=new Main();
        main.changeScene("/main/startMenu.fxml");
    }
}
