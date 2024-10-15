package model;

import java.io.*;
import java.util.ArrayList;

public class PeoplesAccounts {
    private String username;
    private String password;
    private static ArrayList<String> peoplesAccount = new ArrayList<>();

    public PeoplesAccounts(String username, String password) {
        this.username = username;
        this.password = password;
       // peoplesAccount.add(this);
    }
    public static void addPeople(String username,String password){
        peoplesAccount.add(username);
        peoplesAccount.add(password);
    }
    public static boolean validUsername(String username) {
        for (String user : peoplesAccount) {
            if (!user.equals(username)) return true;
            else return false;
        }
        return false;
    }
    public static boolean checkLoginInfo(String username,String password){
        for (String user : peoplesAccount) {
            if (user.equals(username) && user.equals(password)) return true;
            else return false;
        }return false;
    }
    public static void loadUser(){
        try{
            File saveFile = new File("users.tt");
            if(saveFile.exists())
            {
                FileInputStream fis = new FileInputStream(saveFile);
                ObjectInputStream ois = new ObjectInputStream(fis);
                peoplesAccount = (ArrayList<String>) ois.readObject();
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
    public static void saveUsers(){
        try{
            FileOutputStream fos = new FileOutputStream("users.tt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(peoplesAccount);
            oos.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
 //  private static PeoplesAccounts(PeoplesAccounts account){

 //  }
 // public static PeoplesAccounts getUsernames(String username) {
 //     for (PeoplesAccounts people : peoplesAccount) {
 //         if (people.username.equals(username)) {
 //             return people; }  } return null;  }

   // public static ArrayList<PeoplesAccounts> getAllPeoplesAccount() { return peoplesAccount; }

    public String getUsername() {  return username;}

    public String getPassword() { return password;   }

    public void setUsername(String username) {this.username = username; }

    public void setPassword(String password) { this.password = password;   }
}