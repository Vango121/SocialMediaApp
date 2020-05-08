package com.marcel.socialmediaapp;

import com.google.firebase.Timestamp;

public class MessageModel {
    private String Nazwa;
    private String SenderUID;
    private String RecieverUID;
    private String wiadomosc;
    private Timestamp Data;

    public MessageModel(String nazwa, String senderUID, String recieverUID, String wiadomosc, Timestamp data) {
        Nazwa = nazwa;
        SenderUID = senderUID;
        RecieverUID = recieverUID;
        this.wiadomosc = wiadomosc;
        Data = data;
    }

    public String getNazwa() {
        return Nazwa;
    }

    public void setNazwa(String nazwa) {
        Nazwa = nazwa;
    }

    public String getSenderUID() {
        return SenderUID;
    }

    public void setSenderUID(String senderUID) {
        SenderUID = senderUID;
    }

    public String getRecieverUID() {
        return RecieverUID;
    }

    public void setRecieverUID(String recieverUID) {
        RecieverUID = recieverUID;
    }

    public String getWiadomosc() {
        return wiadomosc;
    }

    public void setWiadomosc(String wiadomosc) {
        this.wiadomosc = wiadomosc;
    }

    public Timestamp getData() {
        return Data;
    }

    public void setData(Timestamp data) {
        Data = data;
    }
}
