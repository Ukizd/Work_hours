package com.example.kamil.godzinypracownikow;

import java.util.HashMap;

/**
 * Created by Kamil on 19.08.2016.
 */
public class PracownicyBeen {

    private String login;
    private String iloscGodzin;



    private HashMap<String,String> getDniAndGodziny;
    private HashMap<String, String> hashMapGODZINYOD;
    private HashMap<String, String> hashMapGODZINYDO;

    public PracownicyBeen(String login, HashMap<String, String> getDniAndGodziny, HashMap<String, String> hashMapGODZINYOD, HashMap<String, String> hashMapGODZINYDO) {
        this.login = login;
        this.getDniAndGodziny = getDniAndGodziny;
        this.hashMapGODZINYOD = hashMapGODZINYOD;
        this.hashMapGODZINYDO = hashMapGODZINYDO;
    }

    public HashMap<String, String> getHashMapGODZINYOD() {

        return hashMapGODZINYOD;
    }

    public void setHashMapGODZINYOD(HashMap<String, String> hashMapGODZINYOD) {
        this.hashMapGODZINYOD = hashMapGODZINYOD;
    }

    public HashMap<String, String> getHashMapGODZINYDO() {
        return hashMapGODZINYDO;
    }

    public void setHashMapGODZINYDO(HashMap<String, String> hashMapGODZINYDO) {
        this.hashMapGODZINYDO = hashMapGODZINYDO;
    }

    public PracownicyBeen(String login, HashMap getDniAndGodziny) {
        this.login = login;
        this.getDniAndGodziny = getDniAndGodziny;
    }

    public HashMap getGetDniAndGodziny() {

        return getDniAndGodziny;
    }

    public void setGetDniAndGodziny(HashMap getDniAndGodziny) {
        this.getDniAndGodziny = getDniAndGodziny;
    }

    public PracownicyBeen() {
    }

    public String getLogin() {

        return login;
    }

    public void setLogin(String login) {
        this.login = login;

    }

    public String getIloscGodzin() {
        return iloscGodzin;
    }

    public void setIloscGodzin(String iloscGodzin) {
        this.iloscGodzin = iloscGodzin;
    }



    public PracownicyBeen(String login) {

        this.login = login;
    }
}
