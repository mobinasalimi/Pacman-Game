package controller;

import javafx.event.ActionEvent;
import main.Main;

import java.io.IOException;

public class SettingMenu {
    public void backButtonOnAction(ActionEvent event) throws IOException {
        Main main=new Main();
        main.changeScene("/main/startMenu.fxml");
    }

    public void accountOnAction(ActionEvent event) throws IOException {
        Main main=new Main();
        main.changeScene("/main/account.fxml");
    }

    public void lpAndManpOnAction(ActionEvent event) throws IOException {
        Main main=new Main();
        main.changeScene("/main/lpAndMaps.fxml");
    }
}
