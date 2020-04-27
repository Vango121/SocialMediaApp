package com.marcel.socialmediaapp;

import com.google.firebase.Timestamp;


import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class MainModel {

    String Nazwa;
    String Tresc;
    int Like;
    int Komentarze;
    Timestamp Data;
    String Uzytkownik;

    public MainModel(){

    }

public MainModel(String nazwa){
    this.Nazwa=nazwa;
    //this.tresc=tresc;
    //this.like=like;
    //this.koment=koment;
}

    public String getNazwa() {
        return Nazwa;
    }

    public void setNazwa(String nazwa) {
        this.Nazwa = nazwa;

    }

    public String getTresc() {
        return Tresc;
    }

    public void setTresc(String tresc) {
        Tresc = tresc;
    }

    public int getLike() {
        return Like;
    }

    public void setLike(int like) {
        Like = like;
    }

    public int getKomentarze() {
        return Komentarze;
    }

    public void setKomentarze(int komentarze) {
        Komentarze = komentarze;
    }

    public Timestamp getData() {
        return Data;
    }

    public void setData(Timestamp data) {
        Data = data;
    }

    public String getUzytkownik() {
        return Uzytkownik;
    }

    public void setUzytkownik(String uzytkownik) {
        Uzytkownik = uzytkownik;
    }
}
