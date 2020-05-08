package com.marcel.socialmediaapp;

import com.google.firebase.Timestamp;

public class CommentModel {
    private String tresc;
    private String autor;
    private Timestamp Data;

    public CommentModel() {
    }

    public CommentModel(String tresc, String autor) {
        this.tresc = tresc;
        this.autor = autor;
    }

    public CommentModel(String tresc, String autor, Timestamp Data) {
        this.tresc = tresc;
        this.autor = autor;
        this.Data = Data;
    }

    public Timestamp getData() {
        return Data;
    }

    public void setData(Timestamp Data) {
        this.Data = Data;
    }

    public String getTresc() {
        return tresc;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
}
