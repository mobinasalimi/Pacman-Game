package model;


import controller.Controller;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class GameModel {
    static boolean bord;
    static boolean bakht;
    @FXML
    int radif;
    @FXML
    int sotoun;
    int tedadDane;
    int emtiaz;

    public enum Samt {
        SAKEN,
        RAST,
        CHAP,
        BALA,
        PAYIN
    }

    static Samt samteFeli;
    static Samt akharinSamt;

    public enum ArzesheHarKhane {
        PACMAN,
        DIVAR,
        DANE,
        BOMB,
        KHALI,
        ROUHE1,
        ROUHE2,
        ROUHE3,
        ROUHE4
    }

    ArzesheHarKhane[][] khane;
    static boolean khordaneRouh;
    Point2D makanePacman;
    Point2D makaneRouhe1;
    Point2D makaneRouhe2;
    Point2D makaneRouhe3;
    Point2D makaneRouhe4;
    Point2D jahatePacman;
    Point2D jahateRouhe1;
    Point2D jahateRouhe2;
    Point2D jahateRouhe3;
    Point2D jahateRouhe4;

    public Point2D taghireJahat(Samt samt) {
        return switch (samt) {
            case BALA -> new Point2D(-1, 0);
            case PAYIN -> new Point2D(1, 0);
            case RAST -> new Point2D(0, 1);
            case CHAP -> new Point2D(0, -1);
            default -> new Point2D(0, 0);
        };
    }

    public Samt samteRandom(int adad) {
        return switch (adad) {
            case 0 -> Samt.CHAP;
            case 1 -> Samt.RAST;
            case 2 -> Samt.BALA;
            default -> Samt.PAYIN;
        };
    }

    public Point2D tanzimMakaneKharejAzKadr(Point2D makaneShey) {
        if (makaneShey.getY() <= -1) {
            makaneShey = new Point2D(makaneShey.getX(), sotoun - 1);
        }
        if (makaneShey.getY() >= sotoun) {
            makaneShey = new Point2D(makaneShey.getX(), 0);
        }
        return makaneShey;
    }

    public void shorouBaziJadid() {
        bakht = false;
        bord = false;
        khordaneRouh = false;
        tedadDane = 0;
        radif = 0;
        sotoun = 0;
        this.emtiaz = 0;
        this.barresiMarhale(Controller.getZamin());
    }

    public Point2D[] harekateHarRouh(Point2D jahateRouh, Point2D makaneRouh) {
        Random random = new Random();
        if (!khordaneRouh) {
            if (makaneRouh.getX() == makanePacman.getX()) { //agar rouh va pacman dar yek satr boudand
                if (makaneRouh.getY() < makanePacman.getY()) {  //va rouh samte chape pacman boud
                    jahateRouh = taghireJahat(Samt.RAST);    // (rouh) baraye khordane pacman be samte rast taghire jahat dahad
                } else {
                    jahateRouh = taghireJahat(Samt.CHAP);    // va agar rouh va pacman dar yek satr boudand ama rouh samte raste pacman boud be samte chap taghire jahat dahad(baraye khordane pacman)
                }
                Point2D makanZakhire = makaneRouh.add(jahateRouh);
                makanZakhire = tanzimMakaneKharejAzKadr(makanZakhire);//agar rouh dasht az kadr kharej mishod

                while (true) {
                    if (khane[(int) makanZakhire.getX()][(int) makanZakhire.getY()] != ArzesheHarKhane.DIVAR)
                        break; //ta zamani ke rouh be divar bokhore
                    int adadeRandom = random.nextInt(4);// az 0 ta 3 adade random migire
                    Samt samt = samteRandom(adadeRandom); // ta be ye samte random
                    jahateRouh = taghireJahat(samt);// taghire jahat bede
                    makanZakhire = makaneRouh.add(jahateRouh);
                }
                makaneRouh = makanZakhire;//va makane jadidesh un zakhire she

            } else if (makaneRouh.getY() == makanePacman.getY()) {     //agar rouh va pacman dar yek sotun budand
                if (makaneRouh.getX() > makanePacman.getX()) {  // va rouh zire pacman bud
                    jahateRouh = taghireJahat(Samt.BALA);     // rouh be bala taghire jahat dahad baraye khordane pacman
                } else {
                    jahateRouh = taghireJahat(Samt.PAYIN);   // agar ham rouh balaye pacman dar yek sotoun boud be payin taghire jahat dahad
                }
                Point2D makanZakhire = makaneRouh.add(jahateRouh);
                makanZakhire = tanzimMakaneKharejAzKadr(makanZakhire); //agar rouh dasht az kadr kharej mishod
                while (khane[(int) makanZakhire.getX()][(int) makanZakhire.getY()] == ArzesheHarKhane.DIVAR) { //ta zamani ke rouh be divar bokhore
                    int adadeRandom = random.nextInt(4);// az 0 ta 3 adade random migire
                    Samt samt = samteRandom(adadeRandom); // ta be ye samte random
                    jahateRouh = taghireJahat(samt);// taghire jahat bede
                    makanZakhire = makaneRouh.add(jahateRouh);
                }
                makaneRouh = makanZakhire;//va makane jadidesh un zakhire she
            } else {
                Point2D makaneZakhire = makaneRouh.add(jahateRouh);
                makaneZakhire = tanzimMakaneKharejAzKadr(makaneZakhire);
                while (khane[(int) makaneZakhire.getX()][(int) makaneZakhire.getY()] == ArzesheHarKhane.DIVAR) {
                    int adadeR = random.nextInt(4);
                    Samt samteHarekat = samteRandom(adadeR);
                    jahateRouh = taghireJahat(samteHarekat);
                    makaneZakhire = makaneRouh.add(jahateRouh);
                }
                makaneRouh = makaneZakhire;
            }
        } else {//pacmane bomb khorde -> rouh be samte farar azash taghire jahat midahad
            if (makaneRouh.getX() == makanePacman.getX()) { //agar rouh va pacman dar yek satr boudand
                if (makaneRouh.getY() < makanePacman.getY()) {  //va rouh samte chape pacman boud
                    jahateRouh = taghireJahat(Samt.CHAP);    // (rouh) baraye farar az pacman be samte chap taghire jahat dahad
                } else {
                    jahateRouh = taghireJahat(Samt.RAST);    // va agar rouh va pacman dar yek satr boudand ama rouh samte raste pacman boud be samte rast taghire jahat dahad(baraye farar az pacman)
                }
                Point2D makanZakhire = makaneRouh.add(jahateRouh);
                makanZakhire = tanzimMakaneKharejAzKadr(makanZakhire);//agar rouh dasht az kadr kharej mishod

                //ta zamani ke rouh be divar bokhore
                while (khane[(int) makanZakhire.getX()][(int) makanZakhire.getY()] == ArzesheHarKhane.DIVAR) {
                    int adadeRandom = random.nextInt(4);// az 0 ta 3 adade random migire
                    Samt samt = samteRandom(adadeRandom); // ta be ye samte random
                    jahateRouh = taghireJahat(samt);// taghire jahat bede
                    makanZakhire = makaneRouh.add(jahateRouh);
                }
                makaneRouh = makanZakhire;//va makane jadidesh un zakhire she

            } else if (makaneRouh.getY() == makanePacman.getY()) {     //agar rouh va pacman dar yek sotun budand
                if (makaneRouh.getX() > makanePacman.getX()) {  // va rouh zire pacman bud
                    jahateRouh = taghireJahat(Samt.PAYIN);     // rouh be payin taghire jahat dahad baraye farar az pacman
                } else {
                    jahateRouh = taghireJahat(Samt.BALA);   // agar ham rouh balaye pacman dar yek sotoun boud be bala taghire jahat dahad
                }
                Point2D makanZakhire = makaneRouh.add(jahateRouh);
                makanZakhire = tanzimMakaneKharejAzKadr(makanZakhire); //agar rouh dasht az kadr kharej mishod
                while (khane[(int) makanZakhire.getX()][(int) makanZakhire.getY()] == ArzesheHarKhane.DIVAR) { //ta zamani ke rouh be divar bokhore
                    int adadeRandom = random.nextInt(4);// az 0 ta 3 adade random migire
                    Samt samt = samteRandom(adadeRandom); // ta be ye samte random
                    jahateRouh = taghireJahat(samt);// taghire jahat bede
                    makanZakhire = makaneRouh.add(jahateRouh);
                }
                makaneRouh = makanZakhire;//va makane jadidesh un zakhire she
            } else {
                Point2D makaneZakhire = makaneRouh.add(jahateRouh);
                makaneZakhire = tanzimMakaneKharejAzKadr(makaneZakhire);
                while (khane[(int) makaneZakhire.getX()][(int) makaneZakhire.getY()] == ArzesheHarKhane.DIVAR) {
                    int adadeRan = random.nextInt(4);
                    Samt samteHarekatt = samteRandom(adadeRan);
                    jahateRouh = taghireJahat(samteHarekatt);
                    makaneZakhire = makaneRouh.add(jahateRouh);
                }
                makaneRouh = makaneZakhire;
            }
        }
        return new Point2D[]{jahateRouh, makaneRouh};
    }

    public void harekateRouhHa() {
        Point2D[] etelaateRouhe1 = harekateHarRouh(jahateRouhe1, makaneRouhe1);
        Point2D[] etelaateRouhe2 = harekateHarRouh(jahateRouhe2, makaneRouhe2);
        Point2D[] etelaateRouhe3 = harekateHarRouh(jahateRouhe3, makaneRouhe3);
        Point2D[] etelaateRouhe4 = harekateHarRouh(jahateRouhe4, makaneRouhe4);
        jahateRouhe1 = etelaateRouhe1[0];
        makaneRouhe1 = etelaateRouhe1[1];
        jahateRouhe2 = etelaateRouhe2[0];
        makaneRouhe2 = etelaateRouhe2[1];
        jahateRouhe3 = etelaateRouhe3[0];
        makaneRouhe3 = etelaateRouhe3[1];
        jahateRouhe4 = etelaateRouhe4[0];
        makaneRouhe4 = etelaateRouhe4[1];
    }

    public void shoruKhaneRouhe1() {
        int radif = 0;
        while (radif < this.radif) {
            int sotoun = 0;
            while (sotoun < this.sotoun) {
                if (khane[radif][sotoun] == ArzesheHarKhane.ROUHE1) {
                    makaneRouhe1 = new Point2D(radif, sotoun);
                }
                sotoun++;
            }
            radif++;
        }
        jahateRouhe1 = new Point2D(-1, 0);
    }

    public void shoruKhaneRouhe2() {
        int radif = 0;
        while (radif < this.radif) {
            int sotoun = 0;
            while (sotoun < this.sotoun) {
                if (khane[radif][sotoun] == ArzesheHarKhane.ROUHE2) {
                    makaneRouhe2 = new Point2D(radif, sotoun);
                }
                sotoun++;
            }
            radif++;
        }
        jahateRouhe2 = new Point2D(-1, 0);
    }

    public void shoruKhaneRouhe3() {
        int radif = 0;
        while (radif < this.radif) {
            int sotoun = 0;
            while (sotoun < this.sotoun) {
                if (khane[radif][sotoun] == ArzesheHarKhane.ROUHE3) {
                    makaneRouhe3 = new Point2D(radif, sotoun);
                }
                sotoun++;
            }
            radif++;
        }
        jahateRouhe3 = new Point2D(-1, 0);
    }

    public void shoruKhaneRouhe4() {
        int radif = 0;
        while (radif < this.radif) {
            int sotoun = 0;
            while (sotoun < this.sotoun) {
                if (khane[radif][sotoun] == ArzesheHarKhane.ROUHE4) {
                    makaneRouhe4 = new Point2D(radif, sotoun);
                }
                sotoun++;
            }
            radif++;
        }
        jahateRouhe4 = new Point2D(-1, 0);
    }

    public void setAkharinSamt(Samt samt) {
        akharinSamt = samt;
    }

    public void harekatePacman(Samt samteHarekat) {
        Point2D jahateZakhire = taghireJahat(samteHarekat);
        Point2D makaneZakhire = makanePacman.add(jahateZakhire);
        makaneZakhire = tanzimMakaneKharejAzKadr(makaneZakhire);//agar pacman dasht az kadr kharej mishod
        if (samteHarekat.equals(akharinSamt)) {
            if (khane[(int) makaneZakhire.getX()][(int) makaneZakhire.getY()] == ArzesheHarKhane.DIVAR) {
                jahatePacman = taghireJahat(Samt.SAKEN);
                setAkharinSamt(Samt.SAKEN);
            } else {
                jahatePacman = jahateZakhire;
                makanePacman = makaneZakhire;
            }
        } else {
            if (khane[(int) makaneZakhire.getX()][(int) makaneZakhire.getY()] == ArzesheHarKhane.DIVAR) {
                jahateZakhire = taghireJahat(akharinSamt);
                makaneZakhire = makanePacman.add(jahateZakhire);

                if (khane[(int) makaneZakhire.getX()][(int) makaneZakhire.getY()] == ArzesheHarKhane.DIVAR) {
                    jahatePacman = taghireJahat(Samt.SAKEN);
                    setAkharinSamt(Samt.SAKEN);
                } else {
                    jahatePacman = taghireJahat(akharinSamt);
                    makanePacman = makanePacman.add(jahatePacman);
                }
            } else {
                jahatePacman = jahateZakhire;
                makanePacman = makaneZakhire;
                setAkharinSamt(samteHarekat);
            }
        }
    }

    public GameModel() {
        this.shorouBaziJadid();
    }


    public void marhaleHayeBazi(Samt samteHarekat) {
        this.harekatePacman(samteHarekat);
        ArzesheHarKhane vizhegieMakanePacman = khane[(int) makanePacman.getX()][(int) makanePacman.getY()];
        if (vizhegieMakanePacman == ArzesheHarKhane.BOMB) {
            khane[(int) makanePacman.getX()][(int) makanePacman.getY()] = ArzesheHarKhane.KHALI;
            khordaneRouh = true;
            tedadDane--;
            Controller.setTedadKhordaneRouh();
        }
        if (vizhegieMakanePacman == ArzesheHarKhane.DANE) {//agar pacman dar khane dane raft dane ra bokhorad va 5 emtiyaz daryaft konad
            khane[(int) makanePacman.getX()][(int) makanePacman.getY()] = ArzesheHarKhane.KHALI;
            tedadDane--;
            emtiaz += 5;
        }
        this.harekateRouhHa();
        if (khordaneRouh) {
            int shomareshgar = 1;
            if (makanePacman.equals(makaneRouhe1)) {
                shoruKhaneRouhe1();
                shomareshgar++;
                emtiaz += 200 * shomareshgar;
            }
            if (makanePacman.equals(makaneRouhe2)) {
                shoruKhaneRouhe2();
                shomareshgar++;
                emtiaz += 200 * shomareshgar;
            }
            if (makanePacman.equals(makaneRouhe3)) {
                shoruKhaneRouhe3();
                shomareshgar++;
                emtiaz += 200 * shomareshgar;
            }
            if (makanePacman.equals(makaneRouhe4)) {
                shoruKhaneRouhe4();
                shomareshgar++;
                emtiaz += 200 * shomareshgar;
            }
        }
        if (!khordaneRouh) {
            if (makanePacman.equals(makaneRouhe1)) {
                bakht = true;
                jahatePacman = new Point2D(0, 0);
            }
            if (makanePacman.equals(makaneRouhe2)) {
                bakht = true;
                jahatePacman = new Point2D(0, 0);
            }
            if (makanePacman.equals(makaneRouhe3)) {
                bakht = true;
                jahatePacman = new Point2D(0, 0);
            }
            if (makanePacman.equals(makaneRouhe4)) {
                bakht = true;
                jahatePacman = new Point2D(0, 0);
            }
        }
        if (this.tedadDane == 0) {
            jahatePacman = new Point2D(0, 0);
            bord = true;
            shorouBaziJadid();
        }
    }

    public void barresiMarhale(String esmePoushe) {

        File file = new File(esmePoushe);
        Scanner s = null;
        try {
            s = new Scanner(file);
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
        while (s.hasNextLine()) {
            String khat = s.nextLine();
            Scanner scaneYekKhat = new Scanner(khat);
            while (scaneYekKhat.hasNext()) {
                scaneYekKhat.next();
                sotoun++;
            }
            radif++;
        }
        sotoun = sotoun / radif;
        Scanner scannerBadi = null;
        try {
            scannerBadi = new Scanner(file);
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
        khane = new ArzesheHarKhane[radif][sotoun];
        int sotounePacman = 0;
        int radifePacman = 0;
        int radif = 0;
        int radifeRouhe1 = 0;
        int radifeRouhe2 = 0;
        int radifeRouhe3 = 0;
        int radifeRouhe4 = 0;
        int sotouneRouhe1 = 0;
        int sotouneRouhe2 = 0;
        int sotouneRouhe3 = 0;
        int sotouneRouhe4 = 0;
        while (scannerBadi.hasNextLine()) {
            int sotoun = 0;
            String khat = scannerBadi.nextLine();
            Scanner scaneYekKhat = new Scanner(khat);
            while (scaneYekKhat.hasNext()) {
                String arzesheKhane = scaneYekKhat.next();
                ArzesheHarKhane arzesheInKhane;
                switch (arzesheKhane) {
                    case "D" -> arzesheInKhane = ArzesheHarKhane.DIVAR;
                    case "0" -> {
                        arzesheInKhane = ArzesheHarKhane.DANE;
                        tedadDane++; //tedad dane hayi ke mikhad bokhore ro mishmare bara mohasebe inke key barande mishe
                    }
                    case "O" -> {
                        arzesheInKhane = ArzesheHarKhane.BOMB;
                        tedadDane++;
                    }
                    case "a" -> {
                        arzesheInKhane = ArzesheHarKhane.ROUHE1;
                        radifeRouhe1 = radif;
                        sotouneRouhe1 = sotoun;
                    }
                    case "b" -> {
                        arzesheInKhane = ArzesheHarKhane.ROUHE2;
                        radifeRouhe2 = radif;
                        sotouneRouhe2 = sotoun;
                    }
                    case "c" -> {
                        arzesheInKhane = ArzesheHarKhane.ROUHE3;
                        radifeRouhe3 = radif;
                        sotouneRouhe3 = sotoun;
                    }
                    case "d" -> {
                        arzesheInKhane = ArzesheHarKhane.ROUHE4;
                        radifeRouhe4 = radif;
                        sotouneRouhe4 = sotoun;
                    }
                    case "p" -> {
                        arzesheInKhane = ArzesheHarKhane.PACMAN;
                        radifePacman = radif;
                        sotounePacman = sotoun;
                    }
                    default -> arzesheInKhane = ArzesheHarKhane.KHALI;
                }
                khane[radif][sotoun] = arzesheInKhane;
                sotoun++;
            }
            radif++;
        }
        makaneRouhe1 = new Point2D(radifeRouhe1, sotouneRouhe1);
        makaneRouhe2 = new Point2D(radifeRouhe2, sotouneRouhe2);
        makaneRouhe3 = new Point2D(radifeRouhe3, sotouneRouhe3);
        makaneRouhe4 = new Point2D(radifeRouhe4, sotouneRouhe4);
        makanePacman = new Point2D(radifePacman, sotounePacman);
        jahateRouhe1 = new Point2D(-1, 0);
        jahateRouhe2 = new Point2D(-1, 0);
        jahateRouhe3 = new Point2D(-1, 0);
        jahateRouhe4 = new Point2D(-1, 0);
        jahatePacman = new Point2D(0, 0);
        samteFeli = Samt.SAKEN;
        akharinSamt = Samt.SAKEN;
    }

    public static boolean isKhordaneRouh() {
        return khordaneRouh;
    }

    public static void setKhordaneRouh(boolean khordaneRouh) {
        GameModel.khordaneRouh = khordaneRouh;
    }

    public static boolean isBord() {
        return bord;
    }

    public static boolean isBakht() {
        return bakht;
    }

    public ArzesheHarKhane[][] getKhane() {
        return khane;
    }

    public ArzesheHarKhane getArzesheHarKhane(int radif, int sotoun) {
        assert radif >= 0 && radif < this.khane.length && sotoun >= 0 && sotoun < this.khane[0].length;
        return this.khane[radif][sotoun];
    }

    public static Samt getSamteFeli() {
        return samteFeli;
    }

    public void setSamteFeli(Samt samteFeli) {
        GameModel.samteFeli = samteFeli;
    }

    public static Samt getAkharinSamt() {
        return akharinSamt;
    }

    public int getEmtiaz() {
        return emtiaz;
    }

    public void setEmtiaz(int emtiaz) {
        this.emtiaz = emtiaz;
    }

    public void afzoudanEmtiyaz(int meghdar) {
        this.emtiaz += meghdar;
    }

    public int getTedadDane() {
        return tedadDane;
    }

    public void setTedadDane(int tedadDane) {
        this.tedadDane = tedadDane;
    }

    public int getTedadRadif() {
        return radif;
    }

    public void setTedadRadif(int tedadeRadif) {
        this.radif = tedadeRadif;
    }

    public int getTedadSotoun() {
        return sotoun;
    }

    public void setSotoun(int sotoun) {
        this.sotoun = sotoun;
    }

    public Point2D getMakanePacman() {
        return makanePacman;
    }

    public void setMakanePacman(Point2D makanePacman) {
        this.makanePacman = makanePacman;
    }

    public Point2D getMakaneRouhe1() {
        return makaneRouhe1;
    }

    public void setMakaneRouhe1(Point2D makaneRouhe1) {
        this.makaneRouhe1 = makaneRouhe1;
    }

    public Point2D getMakaneRouhe2() {
        return makaneRouhe2;
    }

    public void setMakaneRouhe2(Point2D makaneRouhe2) {
        this.makaneRouhe2 = makaneRouhe2;
    }

    public Point2D getMakaneRouhe3() {
        return makaneRouhe3;
    }

    public void setMakaneRouhe3(Point2D makaneRouhe3) {
        this.makaneRouhe3 = makaneRouhe3;
    }

    public Point2D getMakaneRouhe4() {
        return makaneRouhe4;
    }

    public void setMakaneRouhe4(Point2D makaneRouhe4) {
        this.makaneRouhe4 = makaneRouhe4;
    }

    public Point2D getJahatePacman() {
        return jahatePacman;
    }

    public void setJahatePacman(Point2D jahatePacman) {
        this.jahatePacman = jahatePacman;
    }

    public Point2D getJahateRouhe1() {
        return jahateRouhe1;
    }

    public void setJahateRouhe1(Point2D jahateRouhe1) {
        this.jahateRouhe1 = jahateRouhe1;
    }

    public Point2D getJahateRouhe2() {
        return jahateRouhe2;
    }

    public void setJahateRouhe2(Point2D jahateRouhe2) {
        this.jahateRouhe2 = jahateRouhe2;
    }

    public Point2D getJahateRouhe3() {
        return jahateRouhe3;
    }

    public void setJahateRouhe3(Point2D jahateRouhe3) {
        this.jahateRouhe3 = jahateRouhe3;
    }

    public Point2D getJahateRouhe4() {
        return jahateRouhe4;
    }

    public void setJahateRouhe4(Point2D jahateRouhe4) {
        this.jahateRouhe4 = jahateRouhe4;
    }
}
