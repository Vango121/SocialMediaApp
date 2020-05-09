package com.marcel.socialmediaapp;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserManagment {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    static final int RECIEVER=0;
    static final int SENDER=1;
    public UserManagment(){
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    }
    int checkMessageUser(MessageModel messageModel,String userID){

        if(currentUser.getUid().equals(messageModel.getRecieverUID())&&userID.equals(messageModel.getSenderUID())){
            Log.i("msg","reciever"+currentUser.getUid()+" "+messageModel.getWiadomosc());
            return RECIEVER;
        }
        if(currentUser.getUid().equals(messageModel.getSenderUID())&&userID.equals(messageModel.getRecieverUID())){
            Log.i("msg","sender"+currentUser.getUid()+" "+messageModel.getWiadomosc());
            return SENDER;
        }
        Log.i("msg","null");
        return 3;
    }
}
