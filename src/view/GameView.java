package view;


import controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.GameModel;


public class GameView extends Group {
    public final static double ARZEKHANE = 20.0;

    @FXML
    int tedadeRadif;
    @FXML
    int tedadeSotoun;
    ImageView[][] khaneV;
    Image bomb;
    Image dane;
    Image rouhe1;
    Image rouhe2;
    Image rouhe3;
    Image rouhe4;
    Image rouheDarMarazeKhatar;
    Image pacmanRouBeBala;
    Image pacmanRouBePayin;
    Image pacmanRouBeChap;
    Image pacmanRouBeRast;
    Image divar;

    public GameView() {
        this.rouhe1 = new Image(getClass().getResourceAsStream("/manabe/ghost1.gif"));
        this.rouhe2 = new Image(getClass().getResourceAsStream("/manabe/ghost2.gif"));
        this.rouhe3 = new Image(getClass().getResourceAsStream("/manabe/ghost3.gif"));
        this.rouhe4 = new Image(getClass().getResourceAsStream("/manabe/ghost4.gif"));
        this.rouheDarMarazeKhatar = new Image(getClass().getResourceAsStream("/manabe/blueghost.gif"));
        this.pacmanRouBeRast = new Image(getClass().getResourceAsStream("/manabe/pacmanRight.gif"));
        this.pacmanRouBeBala = new Image(getClass().getResourceAsStream("/manabe/pacmanUp.gif"));
        this.pacmanRouBePayin = new Image(getClass().getResourceAsStream("/manabe/pacmanDown.gif"));
        this.pacmanRouBeChap = new Image(getClass().getResourceAsStream("/manabe/pacmanLeft.gif"));
        this.divar = new Image(getClass().getResourceAsStream("/manabe/wall.png"));
        this.bomb = new Image(getClass().getResourceAsStream("/manabe/whitedot.png"));
        this.dane = new Image(getClass().getResourceAsStream("/manabe/smalldot.png"));

    }
    public void update(GameModel gameModelClass) {
        assert gameModelClass.getTedadRadif() == this.tedadeRadif && gameModelClass.getTedadSotoun() == this.tedadeSotoun;
        int radif = 0;
        while (radif < this.tedadeRadif) {
            int sotoun = 0;
            while (sotoun < this.tedadeSotoun) {
                GameModel.ArzesheHarKhane arzesheHarKhane = gameModelClass.getArzesheHarKhane(radif, sotoun);
                switch (arzesheHarKhane) {
                    case DIVAR -> this.khaneV[radif][sotoun].setImage(this.divar);
                    case BOMB -> this.khaneV[radif][sotoun].setImage(this.bomb);
                    case DANE -> this.khaneV[radif][sotoun].setImage(this.dane);
                    default -> this.khaneV[radif][sotoun].setImage(null);
                }
                if (radif == gameModelClass.getMakanePacman().getX() && sotoun == gameModelClass.getMakanePacman().getY()
                        && (GameModel.getAkharinSamt() == GameModel.Samt.RAST
                        || GameModel.getAkharinSamt() == GameModel.Samt.SAKEN)) {
                    this.khaneV[radif][sotoun].setImage(this.pacmanRouBeRast);
                }
                else if (radif == gameModelClass.getMakanePacman().getX() && sotoun == gameModelClass.getMakanePacman().getY()
                        && GameModel.getAkharinSamt() == GameModel.Samt.CHAP) {
                    this.khaneV[radif][sotoun].setImage(this.pacmanRouBeChap);
                }
                else if (radif == gameModelClass.getMakanePacman().getX() && sotoun == gameModelClass.getMakanePacman().getY()
                        && GameModel.getAkharinSamt() == GameModel.Samt.BALA) {
                    this.khaneV[radif][sotoun].setImage(this.pacmanRouBeBala);
                }
                else if (radif == gameModelClass.getMakanePacman().getX() && sotoun == gameModelClass.getMakanePacman().getY()
                        && GameModel.getAkharinSamt() == GameModel.Samt.PAYIN) {
                    this.khaneV[radif][sotoun].setImage(this.pacmanRouBePayin);
                }
                if (!GameModel.isKhordaneRouh() || (Controller.getTedadKhordaneRouh() == 2 ||
                        Controller.getTedadKhordaneRouh() == 4 || Controller.getTedadKhordaneRouh() == 6)) {
                    if (radif == gameModelClass.getMakaneRouhe1().getX() && sotoun == gameModelClass.getMakaneRouhe1().getY()) {
                        this.khaneV[radif][sotoun].setImage(this.rouhe1);
                    }
                    if (radif == gameModelClass.getMakaneRouhe2().getX() && sotoun == gameModelClass.getMakaneRouhe2().getY()) {
                        this.khaneV[radif][sotoun].setImage(this.rouhe2);
                    }
                    if (radif == gameModelClass.getMakaneRouhe3().getX() && sotoun == gameModelClass.getMakaneRouhe3().getY()) {
                        this.khaneV[radif][sotoun].setImage(this.rouhe3);
                    }
                    if (radif == gameModelClass.getMakaneRouhe4().getX() && sotoun == gameModelClass.getMakaneRouhe4().getY()) {
                        this.khaneV[radif][sotoun].setImage(this.rouhe4);
                    }
                }
                else {
                    if (radif == gameModelClass.getMakaneRouhe1().getX() && sotoun == gameModelClass.getMakaneRouhe1().getY()) {
                        this.khaneV[radif][sotoun].setImage(this.rouheDarMarazeKhatar);
                    }
                    if (radif == gameModelClass.getMakaneRouhe2().getX() && sotoun == gameModelClass.getMakaneRouhe2().getY()) {
                        this.khaneV[radif][sotoun].setImage(this.rouheDarMarazeKhatar);
                    }
                    if (radif == gameModelClass.getMakaneRouhe3().getX() && sotoun == gameModelClass.getMakaneRouhe3().getY()) {
                        this.khaneV[radif][sotoun].setImage(this.rouheDarMarazeKhatar);
                    }
                    if (radif == gameModelClass.getMakaneRouhe4().getX() && sotoun == gameModelClass.getMakaneRouhe4().getY()) {
                        this.khaneV[radif][sotoun].setImage(this.rouheDarMarazeKhatar);
                    }
                }
                sotoun++;
            }
            radif++;
        }
    }
    private void barresiZamin() {
        if (this.tedadeRadif > 0 && this.tedadeSotoun > 0) {
            this.khaneV = new ImageView[this.tedadeRadif][this.tedadeSotoun];
            int radif = 0;
            while (radif < this.tedadeRadif) {
                int sotoun = 0;
                while (sotoun < this.tedadeSotoun) {
                    ImageView imageV = new ImageView();
                    imageV.setX((double) sotoun * ARZEKHANE);
                    imageV.setY((double) radif * ARZEKHANE);
                    imageV.setFitWidth(ARZEKHANE);
                    imageV.setFitHeight(ARZEKHANE);
                    this.khaneV[radif][sotoun] = imageV;
                    this.getChildren().add(imageV);
                    sotoun++;
                }
                radif++;
            }
        }
    }
    public int getTedadeRadif() {
        return this.tedadeRadif;
    }

    public void setTedadeRadif(int tedadeRadif) {
        this.tedadeRadif = tedadeRadif;
        this.barresiZamin();
    }

    public int getTedadeSotoun() {
        return this.tedadeSotoun;
    }

    public void setTedadeSotoun(int tedadeSotoun) {
        this.tedadeSotoun = tedadeSotoun;
        this.barresiZamin();
    }
}
