package controller;

import javafx.event.ActionEvent;
import main.Main;
import model.GameModel;
import view.GameView;
import javafx.fxml.FXML;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.application.Platform;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Controller implements EventHandler<KeyEvent> {
    final private static double FRAMES_PER_SECOND = 4;

    @FXML
    private Label emtiyaz;
    @FXML
    private Label bord_bakht;
    @FXML
    private GameView pacmanV;
    private GameModel gameModelClass;
    private static final String[] entekhabeZamin =
            {"src/zamin/zamin.txt"};

    private Timer zamanSanj;
    private static int tedadKhordaneRouh;
    private boolean stopped;

    public Controller() {
        this.stopped = false;
    }
    public void initialize() {
        String poushe = getZamin();
        this.gameModelClass = new GameModel();
        this.update(GameModel.Samt.SAKEN);
        tedadKhordaneRouh = 25;
        this.shoroueZamanSanj();
    }
    private void shoroueZamanSanj() {
        this.zamanSanj = new java.util.Timer();
        TimerTask timerT = new TimerTask() {
            public void run() {
                Platform.runLater(() -> update(GameModel.getSamteFeli()));
            }
        };

        long frameTimer = (long) (1000.0 / FRAMES_PER_SECOND);
        this.zamanSanj.schedule(timerT, 0, frameTimer);
    }
 private void update(GameModel.Samt samteHarekat) {
     this.gameModelClass.marhaleHayeBazi(samteHarekat);
     this.pacmanV.update(gameModelClass);
     this.emtiyaz.setText(String.format("(Emtiaz: %d)", this.gameModelClass.getEmtiaz()));
     if (GameModel.isBakht()) {
         this.bord_bakht.setText("shoma bakhtid!");
         this.zamanSanj.cancel();
         this.stopped = true;      ;
     }
     if (GameModel.isBord()) {
         this.bord_bakht.setText("shoma bordid!");
     }
     if (GameModel.isKhordaneRouh()) {
         tedadKhordaneRouh--;
     }
     if (tedadKhordaneRouh == 0 && GameModel.isKhordaneRouh()) {
         GameModel.setKhordaneRouh(false);
     }
 }
    @Override
    public void handle(KeyEvent keyEvent) {
        boolean keyBoardTrue = true;
        KeyCode keyCode = keyEvent.getCode();
        GameModel.Samt samteHarekat = GameModel.Samt.SAKEN;
        switch (keyCode) {
            case UP -> samteHarekat = GameModel.Samt.BALA;
            case DOWN -> samteHarekat = GameModel.Samt.PAYIN;
            case RIGHT -> samteHarekat = GameModel.Samt.RAST;
            case LEFT -> samteHarekat = GameModel.Samt.CHAP;
            case SPACE -> {
                this.zamanSanj.cancel();
                this.stopped = true;
                this.bord_bakht.setText("");
            }
            case ENTER -> {
                this.zamanSanj.cancel();
                this.stopped = true;
                this.gameModelClass.shorouBaziJadid();
                this.bord_bakht.setText("");
                stopped = false;
                this.shoroueZamanSanj();
            }
            default -> keyBoardTrue = false;
        }
        if (keyBoardTrue) {
            keyEvent.consume();
            gameModelClass.setSamteFeli(samteHarekat);
        }
    }
    public static void setTedadKhordaneRouh() {
        tedadKhordaneRouh = 89;
    }

    public static int getTedadKhordaneRouh() {
        return tedadKhordaneRouh;
    }


    public static String getZamin() {
        return entekhabeZamin[0];
    }

    public boolean getStopped() {
        return stopped;
    }
    public void backButtonOnAction(ActionEvent event) throws IOException {
        Main main=new Main();
        main.changeScene("/main/startMenu.fxml");
    }
}

